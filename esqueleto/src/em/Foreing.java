package em;

import static em.Arista.listFormat;
import static em.Arista.listFormatString;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import em.Id.SISTRANS_Id;

public interface Foreing {

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public @interface ForeignKey {
		boolean unique() default false;
		boolean nullable() default true;
		boolean cascade() default false;
	}
	
	static public String CREATE(Arista arista) throws GenericException {
		Arista tablaRef;
		Field field;
		List<Field> filedsForeings;
		List<String> stringRef;
		
		String SQL="";
		for (int i = 0; i < arista.getForeings().size(); i++) {
			field = arista.getForeingsField().get(i);
			
			tablaRef = arista.getForeings().get(i);
			filedsForeings = tablaRef.getIds();
			stringRef = new LinkedList<>();

			for (Field foreingField : filedsForeings) {
				String n = field.getName() + "_" + foreingField.getName();
				stringRef.add(n);
				SQL += n + " " + Id.type(foreingField, foreingField.getAnnotation(SISTRANS_Id.class)) +((field.getAnnotation(ForeignKey.class).nullable())?",\n": " NOT NULL,\n");
			}

			SQL += "CONSTRAINT FK_" + arista.getTableName() + (i + 1) + " FOREIGN KEY ("
					+ listFormatString(stringRef, ",") + ") REFERENCES " + tablaRef.getTableName() + "("
					+ listFormat(filedsForeings, ",") + ") ON DELETE "+((field.getAnnotation(ForeignKey.class).cascade())?"CASCADE ":"SET NULL ")+",\n";
			if (field.getAnnotation(ForeignKey.class).unique())
				SQL += "CONSTRAINT FK_" + arista.getTableName() + (i + 1) + "_U UNIQUE ("
						+ listFormatString(stringRef, ",") + "),\n";
		}
		return SQL;
	}
}