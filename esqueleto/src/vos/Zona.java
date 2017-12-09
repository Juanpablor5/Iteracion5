package vos;

import org.codehaus.jackson.annotate.JsonProperty;
import em.Id.SISTRANS_Id;
import em.Checks;
import em.Tabla;
import em.Check.SISTRANS_Check;
import em.Columna.SISTRANS_Columna;

/**
 * Clase que representa una Zona.
 */
@Tabla
public class Zona {

	// -------------------------------------------------------------
	// Atributos
	// -------------------------------------------------------------

	@SISTRANS_Id(AutoIncrement = true)
	@JsonProperty(value = "id")
	private Long id;

	@SISTRANS_Columna
	@SISTRANS_Check(value = Checks.DIFERENT, of = "")
	@JsonProperty(value = "nombre")
	private String nombre;

	// -------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------

	public Zona() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Método constructor de la clase producto. <b>post: </b> Crea el producto con
	 * los valores que entran como parámetro.
	 * 
	 * @param id
	 *            - Id de la zona.
	 * @param nombre
	 *            - Nombre de la zona. nombre != null
	 */
	public Zona(@JsonProperty(value = "id") Long id, @JsonProperty(value = "nombre") String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}

	// -------------------------------------------------------------
	// Getters & Setters
	// -------------------------------------------------------------

	/**
	 * Método getter del atributo id.
	 * 
	 * @return id de la zona.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Método setter del atributo id <b>post: </b> El id de la zona ha sido cambiado
	 * con el valor que entra como parámetro.
	 * 
	 * @param id
	 *            - Id de la zona.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Método getter del atributo nombre.
	 * 
	 * @return id de la zona.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Método setter del atributo nombre <b>post: </b> El nombre de la zona ha sido
	 * cambiado con el valor que entra como parámetro.
	 * 
	 * @param nombre
	 *            - Nombre de la zona.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
