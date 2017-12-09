package em;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Type;

import em.Tipos.Tipo;

public interface Check {

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public @interface SISTRANS_Check {
		Checks value();

		String of();

		String to() default "";
	}

	public static String CREATE(Field field) throws GenericException {
		Type clase = field.getType();
		SISTRANS_Check check = field.getAnnotation(SISTRANS_Check.class);
		Checks signo = check.value();

		String ans = "CHECK (" + field.getName() + " " + signo.getSigno() + " ";

		if (Tipos.is(clase, Tipo.INTEGER) || Tipos.is(clase, Tipo.NUMBER))
			return ans + check.of() + ((signo.equals(Checks.BETWEEN)) ? " AND " + check.to() : "") + "),\n";
		if (Tipos.is(clase, Tipo.DATE))
			return ans += " to_date(" + check.of() + ")"
					+ ((signo.equals(Checks.BETWEEN)) ? " AND " + "to_date(" + check.to() + ")" : "") + "),\n";
		if (Tipos.is(clase, Tipo.VARCHAR2))
			if (signo.equals(Checks.EQUAL) || signo.equals(Checks.DIFERENT))
				return ans += "'" + check.of() + "'"
						+ ((signo.equals(Checks.BETWEEN)) ? " AND '" + check.to() + "'" : "") + "),\n";
			else
				throw new GenericException("no se puede comparar esa condicion con tipos string");
		throw new GenericException("el tipo no es un atributo atomico");
	}
}
