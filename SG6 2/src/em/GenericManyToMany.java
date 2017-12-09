package em;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import em.Id.SISTRANS_Id;
import em.Many.ManytoMany;
import em.Tipos.Tipo;

public class GenericManyToMany<T, S> extends Connector {
	private final static SimpleDateFormat formatoDate = new SimpleDateFormat("dd/MM/YYYY");
	private final static SimpleDateFormat formatoTime = new SimpleDateFormat("dd/MM/YYYY");
	private final Class<T> claseT;
	private final Class<S> claseS;
	private final List<String> ids;
	private final List<String> idt;
	protected final String TABLA;

	public GenericManyToMany(Class<T> claseT, Class<S> claseS, Connection conn) throws SQLException {
		setConn(conn);
		this.claseS = claseS;
		this.claseT = claseT;
		this.TABLA = claseT.getSimpleName() + "_" + claseS.getSimpleName();
		ids = new LinkedList<>();
		idt = new LinkedList<>();
		Field fieldT = fieldOfClass(claseS, claseT);
		for (Field f : claseS.getDeclaredFields())
			if (f.isAnnotationPresent(SISTRANS_Id.class))
				idt.add("A." + fieldT.getName() + "_" + f.getName() + " = B." + f.getName());
		Field fieldS = fieldOfClass(claseT, claseS);
		for (Field f : claseT.getDeclaredFields())
			if (f.isAnnotationPresent(SISTRANS_Id.class))
				ids.add("A." + fieldS.getName() + "_" + f.getName() + " = B." + f.getName());
	}

	public List<T> getAllTo(S s) throws SQLException {
		String sql = "SELECT B.* FROM " + TABLA + " A, " + claseT.getSimpleName() + " B";
		sql += " WHERE" + Arista.listFormatString(idt, " AND ") + " AND "
				+ SearchSentence(fieldOfClass(claseT, claseS), s);
		ResultSet rs = executeModification(sql);
		return new Extractor<T>(claseT).extractList(rs);
	}

	public List<S> getAllFrom(T t) throws SQLException {
		String sql = "SELECT B.* FROM " + TABLA + " A, " + claseS.getSimpleName() + " B";
		sql += " WHERE" + Arista.listFormatString(ids, " AND ") + " AND "
				+ SearchSentence(fieldOfClass(claseS, claseT), t);
		ResultSet rs = executeModification(sql);

		return new Extractor<S>(claseS).extractList(rs);
	}

	public void add(S s, T t) throws SQLException {
		try {
			List<String> values = new LinkedList<>();
			List<String> data = new LinkedList<>();
			Field fieldT = fieldOfClass(claseT, claseS);
			for (Field f : claseS.getDeclaredFields())
				if (f.isAnnotationPresent(SISTRANS_Id.class)) {
					Method method = claseS.getMethod(getMethod(f.getName()));
					values.add(fieldT.getName() + "_" + f.getName());
					data.add(format(f, method.invoke(s)));
				}
			Field fieldS = fieldOfClass(claseS, claseT);
			for (Field f : claseT.getDeclaredFields())
				if (f.isAnnotationPresent(SISTRANS_Id.class)) {
					Method method = claseT.getMethod(getMethod(f.getName()));
					values.add(fieldS.getName() + "_" + f.getName());
					data.add(format(f, method.invoke(t)));
				}
			String sql = "INSERT INTO " + TABLA + " (" + Arista.listFormatString(values, ",") + ") VALUES ("
					+ Arista.listFormatString(data, ",") + ")";
			executeModification(sql);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
			throw new SQLException(e.getMessage());
		}
	}

	public void delete(S s, T t) throws SQLException {
		try {
			List<String> values = new LinkedList<>();
			Field fieldT = fieldOfClass(claseT, claseS);
			for (Field f : claseS.getDeclaredFields())
				if (f.isAnnotationPresent(SISTRANS_Id.class)) {
					Method method = claseS.getMethod(getMethod(f.getName()));
					values.add(fieldT.getName() + "_" + f.getName()+" = "+format(f, method.invoke(s)));
				}
			Field fieldS = fieldOfClass(claseS, claseT);
			for (Field f : claseT.getDeclaredFields())
				if (f.isAnnotationPresent(SISTRANS_Id.class)) {
					Method method = claseT.getMethod(getMethod(f.getName()));
					values.add(fieldS.getName() + "_" + f.getName()+" = "+format(f, method.invoke(t)));
				}
			String sql = "DELETE FROM " + TABLA;
			sql += " WHERE " + Arista.listFormatString(values, " AND ");
			executeModification(sql);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | SQLException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			throw new SQLException(e.getMessage());
		}
	}

	private Field fieldOfClass(Class<?> claseA, Class<?> claseB) throws SQLException {
		for (Field field : claseA.getDeclaredFields())
			if (field.isAnnotationPresent(ManytoMany.class) && field.getType().equals(List.class)
					&& (((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0]).equals(claseB))
				return field;
		throw new SQLException("no existe un foreing key de la clase: " + claseB);
	}

	private List<String> foreings(Field fieldB, Object objectA) throws SQLException {
		try {
			List<String> search = new LinkedList<>();
			Class<?> clase = objectA.getClass();
			Field[] idsForeings = Id.ids(clase);
			for (Field f : idsForeings) {
				Method method = clase.getMethod(getMethod(f.getName()));
				search.add(fieldB.getName() + "_" + f.getName() + " = " + format(f, method.invoke(objectA)));
			}
			return search;
		} catch (IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException
				| IllegalAccessException e) {
			e.printStackTrace();
			throw new SQLException(e.getMessage());
		}
	}

	private String getMethod(String str) {
		return "get" + Character.toUpperCase(str.charAt(0)) + str.substring(1);
	}

	private String format(Field field, Object value) throws SQLException {
		if (value == null)
			return "null";
		if (Tipos.is(field.getType(), Tipo.INTEGER) || Tipos.is(field.getType(), Tipo.NUMBER))
			return value.toString();
		if (Tipos.is(field.getType(), Tipo.VARCHAR2))
			return "'" + value.toString() + "'";
		if (Tipos.is(field.getType(), Tipo.DATE)) {
			if (field.getAnnotation(DateAnotation.class).completa())
				return "'" + formatoTime.format((Date) value) + "'";
			else
				return "'" + formatoDate.format((Date) value) + "'";
		}
		throw new SQLException("el id no es un tipo permitido");
	}

	private String SearchSentence(Field fieldB, Object objectA) throws SQLException {
		return Arista.listFormatString(foreings(fieldB, objectA), " AND ");
	}
}
