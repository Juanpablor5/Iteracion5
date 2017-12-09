package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import em.Tabla;
import em.Columna.SISTRANS_Columna;
import em.Id.SISTRANS_Id;
import em.Many.ManytoMany;
import jdk.nashorn.internal.ir.annotations.Reference;

/**
 * Clase que representa un Ingrediente.
 */
@Tabla
public class Ingrediente {

	// -------------------------------------------------------------
	// Atributos
	// -------------------------------------------------------------

	@SISTRANS_Id(AutoIncrement = true)
	@JsonProperty(value = "id")
	private Long id;

	@SISTRANS_Columna
	@JsonProperty(value = "nombre")
	private String nombre;

	@SISTRANS_Columna(maxSize = 1000)
	@JsonProperty(value = "descripcion")
	private String descripcion;

	@SISTRANS_Columna(maxSize = 1200)
	@JsonProperty(value = "traduccion")
	private String traduccion;

	@Reference
	@ManytoMany
	private List<Producto> productos;

	// -------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------

	public Ingrediente() {
		// TODO Auto-generated constructor stub
	}

	// -------------------------------------------------------------
	// Getters & Setters
	// ------------------------------------------------------------

	/**
	 * Método getter del atributo nombre
	 * 
	 * @return nombre del ingrediente
	 */
	public String getNombre() {
		return nombre;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Método setter del atributo nombre <b>post: </b> El nombre del ingrediente ha
	 * sido cambiado con el valor que entra como parámetro.
	 * 
	 * @param nombre
	 *            - Nombre del ingrediente.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Método getter del atributo descripción.
	 * 
	 * @return descripción del ingrediente.
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Método setter del atributo descripción <b>post: </b> La descripción del
	 * ingrediente ha sido cambiada por el valor que entra como parámetro.
	 * 
	 * @param descripcion
	 *            - Descripción del ingrediente.
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Método getter del atributo traducción.
	 * 
	 * @return traducción de la descripción del ingrediente.
	 */
	public String getTraduccion() {
		return traduccion;
	}

	/**
	 * Método setter del atributo traducción <b>post: </b> La traducción de la
	 * descripción del ingrediente ha sido cambiada por el valor que entra como
	 * parámetro.
	 * 
	 * @param traduccion
	 *            - Traducción de la descripción del ingrediente.
	 */
	public void setTraduccion(String traduccion) {
		this.traduccion = traduccion;
	}

	public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}
}
