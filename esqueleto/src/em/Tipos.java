package em;

import java.lang.reflect.Type;
import java.util.Date;

public interface Tipos {
	final Class<?>[] INTEGERX = { Long.class, Integer.class, Boolean.class };
	final Class<?>[] NUMBERX = { Double.class, Float.class};
	final Class<?>[] DATEX = { Date.class };
	final Class<?>[] VARCHARX = { String.class };

	static boolean is(Type clase,Tipo tipo) {
		for(Class<?> i:tipo.clases)
			if(clase.equals(i))
				return true;
		return false;
	}
	
	public enum Tipo {
		INTEGER(INTEGERX), NUMBER(NUMBERX),TIMESTAMP(DATEX) ,DATE(DATEX),VARCHAR2(VARCHARX);

		private final Class<?>[] clases;

		Tipo(Class<?>[] clases) {
			this.clases = clases;
		}
	}
}
