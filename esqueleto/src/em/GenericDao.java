package em;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import em.Columna.SISTRANS_Columna;
import em.Foreing.ForeignKey;
import em.Id.SISTRANS_Id;
import em.Tipos.Tipo;

public class GenericDao<T> extends Connector {
	private final static SimpleDateFormat formatoDate = new SimpleDateFormat("dd/MM/YYYY");
	private final static SimpleDateFormat formatoTime = new SimpleDateFormat("dd/MM/YYYY");
	private final Class<T> clase;
	private final Field[] ids;
	protected final String TABLA;
	protected final Extractor<T> extr;

	public GenericDao(Class<T> clase, Connection conn) {
		this.clase = clase;
		extr = new Extractor<>(clase);
		setConn(conn);
		TABLA = clase.getSimpleName();
		List<Field> ids = new LinkedList<>();
		for (Field f : clase.getDeclaredFields())
			if (f.isAnnotationPresent(SISTRANS_Id.class))
				ids.add(f);
		this.ids = new Field[ids.size()];
		for (int i = 0; i < ids.size(); i++)
			this.ids[i] = ids.get(i);
	}

	public final List<T> getAll() throws SQLException {
		String sql = "SELECT * FROM " + TABLA;
		ResultSet rs = executeModification(sql);

		return extr.extractList(rs);
	}

	public final T get(T registro) throws SQLException {
		String sql = "SELECT * FROM " + TABLA;
		sql += SearchSentence(registro);

		ResultSet rs = executeModification(sql);

		return extr.extract(rs);
	}

	public final T get(Object... ids) throws SQLException {
		String sql = "SELECT * FROM " + TABLA;
		sql += SearchSentence(ids);

		ResultSet rs = executeModification(sql);

		return extr.extract(rs);
	}

	public final T getDetail(T registro) throws SQLException {
		String sql = "SELECT * FROM " + TABLA;
		sql += SearchSentence(registro);

		ResultSet rs = executeModification(sql);

		return extr.extractDetail(rs, conn);
	}

	public final T getDetail(Object... ids) throws SQLException {
		String sql = "SELECT * FROM " + TABLA;
		sql += SearchSentence(ids);

		ResultSet rs = executeModification(sql);

		return extr.extractDetail(rs, conn);
	}

	public final List<T> getAllSub(Object padre) throws SQLException {
		Class<?> claseP = padre.getClass();
		String sql = "SELECT * FROM " + TABLA;
		sql += " WHERE" + SearchSentence(fieldOfClass(claseP), padre);
		ResultSet rs = executeModification(sql);

		return extr.extractList(rs);
	}

	public final T getSub(Object padre, T registro) throws SQLException {
		Class<?> claseP = padre.getClass();
		String sql = "SELECT * FROM " + TABLA;
		sql += SearchSentence(registro) + " AND " + SearchSentence(fieldOfClass(claseP), padre);

		ResultSet rs = executeModification(sql);

		return extr.extract(rs);
	}

	public final T getSub(Object padre, Object... ids) throws SQLException {
		Class<?> claseP = padre.getClass();
		String sql = "SELECT * FROM " + TABLA;
		sql += SearchSentence(ids) + " AND " + SearchSentence(fieldOfClass(claseP), padre);

		ResultSet rs = executeModification(sql);

		return extr.extract(rs);
	}

	public final T getDetailSub(Object padre, T registro) throws SQLException {
		Class<?> claseP = padre.getClass();
		String sql = "SELECT * FROM " + TABLA;
		sql += SearchSentence(registro) + " AND " + SearchSentence(fieldOfClass(claseP), padre);

		ResultSet rs = executeModification(sql);

		return extr.extractDetail(rs, conn);
	}

	public final T getDetailSub(Object padre, Object... ids) throws SQLException {
		Class<?> claseP = padre.getClass();
		String sql = "SELECT * FROM " + TABLA;
		sql += SearchSentence(ids) + " AND " + SearchSentence(fieldOfClass(claseP), padre);

		ResultSet rs = executeModification(sql);

		return extr.extractDetail(rs, conn);
	}

	public final List<T> getWithForeing(Field field, Object object) throws SQLException {
		String sql = "SELECT * FROM " + TABLA;
		sql += " WHERE" + SearchSentence(field, object);

		ResultSet rs = executeModification(sql);

		return extr.extractList(rs);
	}

	public void create(T objeto) throws SQLException {
		try {
			List<String> tabla = new LinkedList<>();
			List<String> values = new LinkedList<>();

			for (Field field : clase.getDeclaredFields()) {
				if (field.isAnnotationPresent(SISTRANS_Columna.class)
						|| (field.isAnnotationPresent(Id.SISTRANS_Id.class)
								&& !field.getAnnotation(SISTRANS_Id.class).AutoIncrement())) {
					tabla.add(field.getName());
					values.add(format(field, get(field, objeto)));
				} else if (field.isAnnotationPresent(ForeignKey.class))
					if (get(field, objeto) != null) {
						getAdd(tabla, values, field, get(field, objeto));
					} else if (!field.getAnnotation(ForeignKey.class).nullable())
						throw new SQLException("la llave foranea: " + field.getName() + " no puede ser nula");
			}
			executeModification("INSERT INTO " + TABLA + " ( " + Arista.listFormatString(tabla, ",") + ") VALUES ("
					+ Arista.listFormatString(values, ",") + ")");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new SQLException(e.getMessage());
		}
	}

	public final void update(T registro) throws SQLException {
		try {
			List<String> seters = new LinkedList<>();

			for (Field field : clase.getDeclaredFields()) {
				if (field.isAnnotationPresent(SISTRANS_Columna.class)) {
					if (get(field, registro) != null)
						seters.add(field.getName() + " = " + format(field, get(field, registro)));
				} else if (field.isAnnotationPresent(ForeignKey.class)) {
					if (get(field, registro) != null)
						seters.addAll(foreings(field, get(field, registro)));
				}
			}
			executeModification(
					"UPDATE " + TABLA + " SET " + Arista.listFormatString(seters, ",") + SearchSentence(registro));
		} catch (IllegalArgumentException | SecurityException e) {
			e.printStackTrace();
			throw new SQLException(e.getMessage());
		}

	}

	public final void remove(T references) throws SQLException {
		String sql = "DELETE FROM " + TABLA;
		sql += SearchSentence(references);

		executeModification(sql);
	}

	private String getMethod(String str) {
		return "get" + Character.toUpperCase(str.charAt(0)) + str.substring(1);
	}

	private Object get(Field field, T t) throws SQLException {
		try {
			return clase.getMethod(getMethod(field.getName())).invoke(t);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			throw new SQLException(e.getMessage());
		}
	}

	private List<String> foreings(Field fieldB, Object objectA) throws SQLException {
		try {
			List<String> search = new LinkedList<>();
			Field[] idsForeings = Id.ids(fieldB.getType());
			for (Field f : idsForeings) {
				Method method = fieldB.getType().getMethod(getMethod(f.getName()));
				search.add(fieldB.getName() + "_" + f.getName() + " = " + format(f, method.invoke(objectA)));
			}
			return search;
		} catch (IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException
				| IllegalAccessException e) {
			e.printStackTrace();
			throw new SQLException(e.getMessage());
		}
	}

	private void getAdd(List<String> dates, List<String> values, Field fieldB, Object objectA) throws SQLException {
		try {
			Field[] idsForeings = Id.ids(fieldB.getType());
			for (Field f : idsForeings) {
				Method method = fieldB.getType().getMethod(getMethod(f.getName()));
				dates.add(fieldB.getName() + "_" + f.getName());
				values.add(format(f, method.invoke(objectA)));
			}
		} catch (IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException
				| IllegalAccessException e) {
			e.printStackTrace();
			throw new SQLException(e.getMessage());
		}
	}

	private String SearchSentence(T object) throws SQLException {
		try {
			List<String> search = new LinkedList<>();

			for (Field field : ids) {
				search.add(field.getName() + " = " + format(field, get(field, object)));
			}
			return " WHERE" + Arista.listFormatString(search, " AND ");
		} catch (IllegalArgumentException | SecurityException e) {
			e.printStackTrace();
			throw new SQLException(e.getMessage());
		}
	}

	private String SearchSentence(Object[] objects) throws SQLException {
		Hashtable<Class<?>, Object> hash = new Hashtable<>();
		for (Object object : objects)
			hash.put(object.getClass(), object);
		List<String> search = new LinkedList<>();

		for (Field field : ids)
			search.add(field.getName() + " = " + format(field, hash.get(field.getType())));
		return " WHERE" + Arista.listFormatString(search, " AND ");
	}

	private String SearchSentence(Field fieldB, Object objectA) throws SQLException {
		return Arista.listFormatString(foreings(fieldB, objectA), " AND ");
	}

	private String format(Field field, Object value) throws SQLException {
		if(value==null)
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

	private Field fieldOfClass(Class<?> claseB) throws SQLException {
		for (Field field : clase.getDeclaredFields())
			if (field.getType().equals(claseB) && field.isAnnotationPresent(ForeignKey.class))
				return field;
		throw new SQLException("no existe un foreing key de la clase: " + claseB);
	}
}
