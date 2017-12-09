package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;


/**
 * Clase que representa un Producto.
 */
public class Productoi {

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

	@JsonProperty(value = "tiempoPreparacion")
	private Integer tiempoPreparacion;

	private String categoria;
	
	private List<Ingredientei> ingredientes;
	// -------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------
	public Productoi() {
		// TODO Auto-generated constructor stub
	}
	// -------------------------------------------------------------
	// Getters & Setters
	// -------------------------------------------------------------

	/**
	 * M�todo getter del atributo id.
	 * 
	 * @return id del producto.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * M�todo setter del atributo id <b>post: </b> El id del producto ha sido
	 * cambiado con el valor que entra como par�metro.
	 * 
	 * @param id
	 *            - Id del producto.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * M�todo getter del atributo nombre.
	 * 
	 * @return id del producto.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * M�todo setter del atributo nombre <b>post: </b> El nombre del producto ha
	 * sido cambiado con el valor que entra como par�metro.
	 * 
	 * @param nombre
	 *            - Nombre del producto.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * M�todo getter del atributo descripci�n.
	 * 
	 * @return descripci�n del producto.
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * M�todo setter del atributo descripci�n <b>post: </b> La descripci�n del
	 * producto ha sido cambiada por el valor que entra como par�metro.
	 * 
	 * @param descripcion
	 *            - Descripci�n del producto.
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * M�todo getter del atributo traducci�n.
	 * 
	 * @return traducci�n de la descripci�n del producto.
	 */
	public String getTraduccion() {
		return traduccion;
	}

	/**
	 * M�todo setter del atributo traducci�n <b>post: </b> La traducci�n de la
	 * descripci�n del producto ha sido cambiada por el valor que entra como
	 * par�metro.
	 * 
	 * @param traduccion
	 *            - Traducci�n de la descripci�n del producto.
	 */
	public void setTraduccion(String traduccion) {
		this.traduccion = traduccion;
	}

	/**
	 * M�todo getter del atributo tiempoPreparaion.
	 * 
	 * @return tiempo de preparaci�n del producto.
	 */
	public Integer getTiempoPreparacion() {
		return tiempoPreparacion;
	}

	/**
	 * M�todo setter del atributo tiempoPreparacion <b>post: </b> El
	 * tiempoPreparacion del producto ha sido cambiado con el valor que entra como
	 * par�metro.
	 * 
	 * @param tiempoPreparacion
	 *            - Tiempo de preparaci�n del producto.
	 */
	public void setTiempoPreparacion(Integer tiempoPreparacion) {
		this.tiempoPreparacion = tiempoPreparacion;
	}

	public List<Ingredientei> getIngredientes() {
		return ingredientes;
	}

	public void setIngredientes(List<Ingredientei> ingredientes) {
		this.ingredientes = ingredientes;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
	
}
