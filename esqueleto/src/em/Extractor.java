package em;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import em.Columna.SISTRANS_Columna;
import em.Foreing.ForeignKey;
import em.Many.ManytoMany;

public class Extractor<T> {
	private final static SimpleDateFormat formatoTime = new SimpleDateFormat("dd/MM/YYYY");

	private final String TABLA;
	private final Class<T> clase;

	public Extractor(Class<T> clase) {
		this.clase = clase;
		this.TABLA = clase.getSimpleName();
	}

	public T extract(ResultSet rs) throws SQLException {
		try {
			if (!rs.next())
				return null;
			T t = clase.newInstance();

			for (Field field : clase.getDeclaredFields()) {
				if (field.isAnnotationPresent(Id.SISTRANS_Id.class)
						|| field.isAnnotationPresent(SISTRANS_Columna.class))
					set(field, t, getRs(rs, field));
			}
			return t;
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			throw new SQLException(e.getMessage());
		}
	}

	public List<T> extractList(ResultSet rs) throws SQLException {
		try {
			List<Field> fields = new LinkedList<>();
			List<Method> sets = new LinkedList<>();
			for (Field field : clase.getDeclaredFields()) {
				if (field.isAnnotationPresent(Id.SISTRANS_Id.class)
						|| field.isAnnotationPresent(SISTRANS_Columna.class)) {
					fields.add(field);
					sets.add(clase.getMethod(setName(field.getName()), field.getType()));
				}
			}

			T t;
			List<T> ans = new LinkedList<>();
			while (rs.next()) {
				t = clase.newInstance();
				for (int i = 0; i < fields.size(); i++) {
					sets.get(i).invoke(t, getRs(rs, fields.get(i)));
				}
				ans.add(t);
			}
			return ans;
		} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException
				| IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			throw new SQLException(e.getMessage());
		}
	}

	private Object getRs(ResultSet rs, Field field) throws SQLException {
		try {
			Class<?> clase = field.getType();
			if (clase.equals(Integer.class))
				return rs.getInt(field.getName());
			if (clase.equals(Long.class))
				return rs.getLong(field.getName());
			if (clase.equals(Double.class))
				return rs.getDouble(field.getName());
			if (clase.equals(Date.class))
				if (field.getAnnotation(DateAnotation.class).completa())
					return formatoTime.parse(rs.getString(field.getName()));
				else
					return rs.getDate(field.getName());
			if (clase.equals(String.class))
				return rs.getString(field.getName());
			return rs.getObject(field.getName());
		} catch (ParseException e) {
			e.printStackTrace();
			throw new SQLException("error formato de fechas");
		}
	}

	private void set(Field field, T t, Object value) throws SQLException {
		try {
			clase.getMethod(setName(field.getName()), field.getType()).invoke(t, value);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			throw new SQLException(e.getMessage());
		}
	}

	private String setName(String str) {
		return "set" + Character.toUpperCase(str.charAt(0)) + str.substring(1);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public T extractDetail(ResultSet rs, Connection conn) throws SQLException {
		try {
			if (!rs.next())
				return null;

			List<Field> references = new LinkedList<>();
			List<Field> referencesList = new LinkedList<>();
			List<Field> referencesMany = new LinkedList<>();
			T t = clase.newInstance();
			for (Field field : clase.getDeclaredFields()) {
				if (field.isAnnotationPresent(Id.SISTRANS_Id.class)
						|| field.isAnnotationPresent(SISTRANS_Columna.class)) {
					set(field, t, getRs(rs, field));
				} else if (field.isAnnotationPresent(Reference.class)) {
					if (field.isAnnotationPresent(ForeignKey.class)) {
						GenericDao<Object> tm = new GenericDao(field.getType(), conn);
						set(field, t, tm.get(references(field.getType(), field, rs)));
						tm.close();
					} else if (field.isAnnotationPresent(ManytoMany.class)) {
						referencesMany.add(field);
					} else {
						if (field.getType().equals(List.class))
							referencesList.add(field);
						else
							references.add(field);
					}
				} else if (field.isAnnotationPresent(ManytoMany.class)) {
					referencesMany.add(field);
				}
			}
			for (Field field : references) {
				Field f = field.getType().getDeclaredField(field.getAnnotation(Reference.class).mappedBy());
				GenericDao<Object> tm = new GenericDao(field.getType(), conn);
				List<Object> ans = tm.getWithForeing(f, t);
				if (!ans.isEmpty())
					set(field, t, ans.get(0));
				tm.close();
			}
			for (Field field : referencesList) {
				Class<?> clase = (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
				Field f = clase.getDeclaredField(field.getAnnotation(Reference.class).mappedBy());
				GenericDao<Object> tm = new GenericDao(clase, conn);
				set(field, t, tm.getWithForeing(f, t));
				tm.close();
			}
			for (Field field : referencesMany) {
				Class<?> claseref = (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
				GenericManyToMany<Object, Object> gmtm;
				if (!field.getAnnotation(ManytoMany.class).mapped().equals("")) {
					gmtm = new GenericManyToMany(clase, claseref, conn);
					set(field, t, gmtm.getAllFrom(t));
				}else {
					gmtm = new GenericManyToMany(claseref,clase, conn);
					set(field, t, gmtm.getAllTo(t));
				}
				gmtm.close();
			}
			return t;
		} catch (InstantiationException | IllegalAccessException | SecurityException | IllegalArgumentException
				| NoSuchFieldException e) {
			e.printStackTrace();
			throw new SQLException("no hay constructor vacio en la clase " + TABLA);
		}
	}

	private <S> S references(Class<S> clase, Field field, ResultSet rs) throws SQLException {
		try {
			S s = clase.newInstance();

			for (Field f : Id.ids(clase))
				set(clase, f, s, getRs(field.getName() + "_" + f.getName(), rs, f.getType()));

			return s;
		} catch (InstantiationException | IllegalAccessException | SecurityException | IllegalArgumentException e) {
			throw new SQLException(e.getMessage());
		}
	}

	private Object getRs(String name, ResultSet rs, Class<?> field) throws SQLException {
		try {
			if (field.equals(Integer.class))
				return rs.getInt(name);
			if (field.equals(Long.class))
				return rs.getLong(name);
			if (field.equals(Double.class))
				return rs.getDouble(name);
			if (field.equals(Date.class))
				if (field.getAnnotation(DateAnotation.class).completa())
					return formatoTime.parse(rs.getString(name));
				else
					return rs.getDate(name);
			if (field.equals(String.class))
				return rs.getString(name);
			return rs.getObject(field.getName());
		} catch (ParseException e) {
			e.printStackTrace();
			throw new SQLException("error formato de fechas");
		}
	}

	private <S> void set(Class<S> clase, Field field, S s, Object value) throws SQLException {
		try {
			clase.getMethod(setName(field.getName()), field.getType()).invoke(s, value);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			throw new SQLException(e.getMessage());
		}
	}
}
