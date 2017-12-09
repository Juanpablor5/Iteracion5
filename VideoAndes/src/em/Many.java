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

public interface Many {
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public @interface ManytoMany {
		String mapped() default "";
	}

	public static String CREATE(String name, Arista a1, String na1, Arista a2, String na2) throws GenericException {
		List<String> ids = new LinkedList<>();
		
		String SQL = "CREATE TABLE " + name + "(\n";
		
		List<Field> fileds = a1.getIds();
		List<String> stringFields = new LinkedList<>();
		
		for (Field f : fileds) {
			String n = na1 + "_" + f.getName();
			ids.add(n);
			stringFields.add(n);
			SQL += n + " " + Id.type(f, f.getAnnotation(SISTRANS_Id.class)) + " NOT NULL,\n";
		}
		SQL += "CONSTRAINT FK_" + name + " FOREIGN KEY (" + listFormatString(stringFields, ",") + ") REFERENCES  "
				+ a1.getTableName() + "(" + listFormat(fileds, ",") + ") ON DELETE CASCADE,\n";
		
		fileds = a2.getIds();
		stringFields = new LinkedList<>();
		
		for (Field f : fileds) {
			String n = na2 + "_" + f.getName();
			ids.add(n);
			stringFields.add(n);
			SQL += n + " " + Id.type(f, f.getAnnotation(SISTRANS_Id.class)) + " NOT NULL,\n";
		}
		
		SQL += "CONSTRAINT FK_" + name + "2 FOREIGN KEY (" + listFormatString(stringFields, ",") + ") REFERENCES  "
				+ a2.getTableName() + "(" + listFormat(fileds, ",") + ") ON DELETE CASCADE,\n";
		SQL += "CONSTRAINT PK_" + name + " PRIMARY KEY (" + listFormatString(ids, ",") + ")\n";
		return SQL + ");";
	}
}
