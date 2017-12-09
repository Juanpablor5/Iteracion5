package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import em.Checks;
import em.Tabla;
import em.Check.SISTRANS_Check;
import em.Id.SISTRANS_Id;
import em.Reference;

/**
 * Clase que representa un tipo de comida.
 */
@Tabla
public class TipoComida {

	// -------------------------------------------------------------
	// Atributos
	// -------------------------------------------------------------

	@SISTRANS_Id
	@SISTRANS_Check(value = Checks.DIFERENT, of = "")
	@JsonProperty(value = "nombre")
	private String nombre;

	@Reference(mappedBy = "tipoComida")
	private List<Restaurante> restaurantes;

	// -------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------

	public TipoComida() {
	}
	// -------------------------------------------------------------
	// Getters & Setters
	// -------------------------------------------------------------

	/**
	 * Método getter del atributo nombre.
	 * 
	 * @return id del tipo de comida.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Método setter del atributo nombre <b>post: </b> El nombre del tipo de comida
	 * ha sido cambiado con el valor que entra como parámetro.
	 * 
	 * @param nombre
	 *            - Nombre del tipo de comida.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Restaurante> getRestaurantes() {
		return restaurantes;
	}

	public void setRestaurantes(List<Restaurante> restaurantes) {
		this.restaurantes = restaurantes;
	}

}
