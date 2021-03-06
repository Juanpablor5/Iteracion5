package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import jdk.nashorn.internal.ir.annotations.Reference;

/**
 * Clase que representa un Ingrediente.
 */
public class Ingredientei {

	// -------------------------------------------------------------
	// Atributos
	// -------------------------------------------------------------

	@JsonProperty(value = "id")
	private Long id;

	@JsonProperty(value = "nombre")
	private String nombre;

	@JsonProperty(value = "descripcion")
	private String descripcion;

	@JsonProperty(value = "traduccion")
	private String traduccion;

	@Reference
	private List<Productoi> productos;

	// -------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------

	public Ingredientei() {
		// TODO Auto-generated constructor stub
	}

	// -------------------------------------------------------------
	// Getters & Setters
	// ------------------------------------------------------------

	/**
	 * M�todo getter del atributo nombre
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
	 * M�todo setter del atributo nombre <b>post: </b> El nombre del ingrediente ha
	 * sido cambiado con el valor que entra como par�metro.
	 * 
	 * @param nombre
	 *            - Nombre del ingrediente.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * M�todo getter del atributo descripci�n.
	 * 
	 * @return descripci�n del ingrediente.
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * M�todo setter del atributo descripci�n <b>post: </b> La descripci�n del
	 * ingrediente ha sido cambiada por el valor que entra como par�metro.
	 * 
	 * @param descripcion
	 *            - Descripci�n del ingrediente.
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * M�todo getter del atributo traducci�n.
	 * 
	 * @return traducci�n de la descripci�n del ingrediente.
	 */
	public String getTraduccion() {
		return traduccion;
	}

	/**
	 * M�todo setter del atributo traducci�n <b>post: </b> La traducci�n de la
	 * descripci�n del ingrediente ha sido cambiada por el valor que entra como
	 * par�metro.
	 * 
	 * @param traduccion
	 *            - Traducci�n de la descripci�n del ingrediente.
	 */
	public void setTraduccion(String traduccion) {
		this.traduccion = traduccion;
	}

	public List<Productoi> getProductos() {
		return productos;
	}

	public void setProductos(List<Productoi> productos) {
		this.productos = productos;
	}
}
