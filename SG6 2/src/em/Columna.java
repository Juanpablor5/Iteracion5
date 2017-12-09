package em;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Type;

import em.Tipos.Tipo;

public interface Columna {

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public @interface SISTRANS_Columna {
		boolean unique() default false;

		boolean nullable() default false;

		int maxSize() default 100;

		String valorPorDefecto() default "";
	}

	public static String CREATE(Field field) throws GenericException {
		SISTRANS_Columna col = field.getAnnotation(SISTRANS_Columna.class);
		return field.getName() + " " + type(field, col)
				+ ((col.valorPorDefecto().equals("")) ? "" : " DEFAULT " + col.valorPorDefecto())
				+ ((col.nullable()) ? "" : " NOT NULL") + ((col.unique()) ? " UNIQUE" : "") + ",\n";
	}

	static String type(Field field, SISTRANS_Columna col) throws GenericException {
		Type clase = field.getType();
		if (Tipos.is(clase, Tipo.INTEGER))
			return Tipo.INTEGER.name();
		if (Tipos.is(clase, Tipo.NUMBER))
			return Tipo.NUMBER.name();
		if (clase.equals(String.class))
			return "VARCHAR2(" + col.maxSize() + ")";
		if (Tipos.is(clase, Tipo.DATE))
			if (field.isAnnotationPresent(DateAnotation.class))
				if (field.getAnnotation(DateAnotation.class).completa())
					return Tipo.TIMESTAMP.name();
				else
					return Tipo.DATE.name();
			else
				throw new GenericException("el tipo fecha no esta especificado");
		throw new GenericException("el tipo no es un atributo atomico");
	}
}
