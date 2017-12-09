package em;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import static em.Arista.listFormat;

import em.Check.SISTRANS_Check;
import em.Columna.SISTRANS_Columna;
import em.Foreing.ForeignKey;
import em.Id.SISTRANS_Id;
import em.Many.ManytoMany;
import vos.*;

public class Develop {
	public static void main(String[] args) throws GenericException {
		new Develop(ProductoSimilitud.class,IngredienteSimilitud.class,Cliente.class,Espacio.class,Ingrediente.class,Menu.class,Pedido.class,Preferencias.class,Producto.class,Representante.class,Reserva.class,Restaurante.class,TipoComida.class,Usuario.class,Zona.class);
	}
	
	private final Hashtable<String, Arista> aristas = new Hashtable<>();
	private final Hashtable<String, Arista> aristasAux = new Hashtable<>();
	private final Hashtable<Arista, String> sentencias = new Hashtable<>();
	private final Hashtable<Arista, Class<?>> clasesTabla = new Hashtable<>();

	public Develop(Class<?>... clases) throws GenericException {
		if (clases.length == 0)
			throw new GenericException("se necesita al menos una clase");

		inicializar(clases);
		foreings();
		aristas.putAll(aristasAux);
		String senencia = generateSentencia(new DFSImpl(aristas.values().iterator()).getOrden());

		System.out.println(senencia);
	}

	private String generateSentencia(List<Arista> orden) {
		String senencia = "";
		for (int j = orden.size() - 1; j >= 0; j--)
			senencia += "DROP TABLE " + orden.get(j).getTableName() + ";\n";
		senencia += "\n\n";
		for (Arista a : orden)
			senencia += sentencias.get(a) + "\n\n";
		return senencia + "COMMIT;";
	}

	private void inicializar(Class<?>[] clases) throws GenericException {
		String nombreClase;
		List<Field> ids;
		String SQL;
		Arista arista;

		for (Class<?> clase : clases) {

			if (!clase.isAnnotationPresent(Tabla.class))
				throw new GenericException("no todas las clases pasada son tablas");

			nombreClase = clase.getSimpleName();
			ids = new LinkedList<>();
			SQL = "";

			for (Field f : clase.getDeclaredFields()) {
				if (f.isAnnotationPresent(SISTRANS_Id.class)) {
					ids.add(f);
					SQL += Id.CREATE(f);
				} else if (f.isAnnotationPresent(SISTRANS_Columna.class)) {
					SQL += Columna.CREATE(f);
					if (f.isAnnotationPresent(SISTRANS_Check.class))
						SQL += Check.CREATE(f);
				}
			}
			if(ids.isEmpty())
				throw new GenericException("se requiere de un id");
			arista = new Arista(nombreClase, ids, new LinkedList<>(), new LinkedList<>());
			aristas.put(nombreClase, arista);
			sentencias.put(arista, SQL);
			clasesTabla.put(arista, clase);
		}
	}

	private void foreings() throws GenericException {
		for (Arista actual : aristas.values()) {
			for (Field field : clasesTabla.get(actual).getDeclaredFields()) {
				if (field.isAnnotationPresent(ForeignKey.class)) {
					Arista ref = aristas.get(field.getType().getSimpleName());
					if (ref == null)
						throw new GenericException("la referencia no es a otra tabla");
					actual.getForeings().add(ref);
					actual.getForeingsField().add(field);
				} else if (field.isAnnotationPresent(ManytoMany.class)) {
					Class<?> clase= (Class<?>) ((ParameterizedType)field.getGenericType()).getActualTypeArguments()[0];
					Arista ref = aristas.get(clase.getSimpleName());
					if (ref == null)
						throw new GenericException("la referencia no es a otra tabla");
					crearManyToMany(actual, ref, !field.getAnnotation(ManytoMany.class).mapped().equals(""),field.getName());
				}
			}
			completarSentencia(actual);
		}
	}

	private void completarSentencia(Arista arista) throws GenericException {
		String SQL = "CREATE TABLE " + arista.getTableName() + "(\n";
		SQL += sentencias.get(arista);
		SQL += Foreing.CREATE(arista);
		SQL += "CONSTRAINT PK_" + arista.getTableName() + " PRIMARY KEY (" + listFormat(arista.getIds(), ",") + ")\n";
		SQL += ");";
		sentencias.put(arista, SQL);
	}

	private void crearManyToMany(Arista actual, Arista ref, boolean padre,String actualF) throws GenericException {
		boolean esta = false;
		String refF=null;
		for (Field field : clasesTabla.get(ref).getDeclaredFields())
			if (field.isAnnotationPresent(ManytoMany.class) &&  field.getType().equals(List.class) && ((ParameterizedType)field.getGenericType()).getActualTypeArguments()[0].equals(clasesTabla.get(actual))) {
				esta = true;
				refF = field.getName();
				if (!(!field.getAnnotation(ManytoMany.class).mapped().equals("") ^ padre))
					throw new GenericException("el padre de la relacion es ambiguo:" +actual.getTableName()+"_"+ref.getTableName());
			}
		if (!esta)
			throw new GenericException("la relacion many to many no esta  declarada a ambo lados:");

		List<Arista> listaForeings = new LinkedList<>();
		List<Field> ids = new LinkedList<>();

		listaForeings.add(actual);
		ids.addAll(actual.getIds());

		listaForeings.add(ref);
		ids.addAll(ref.getIds());

		String name = (padre) ? actual.getTableName() + "_" + ref.getTableName()
				: ref.getTableName() + "_" + actual.getTableName();

		Arista arista = new Arista(name, ids, listaForeings, null);
		aristasAux.put(name, arista);
		sentencias.put(arista, Many.CREATE(name,actual,refF, ref,actualF));
	}
}