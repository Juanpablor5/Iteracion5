package vos;

import org.codehaus.jackson.annotate.JsonProperty;

import em.Check.SISTRANS_Check;
import em.Checks;
import em.Reference;
import em.Id.SISTRANS_Id;
import em.Tabla;
import em.Columna.SISTRANS_Columna;
import em.Foreing.ForeignKey;

/**
 * Clase que representa un Producto.
 */
@Tabla
public class Preferencias {

	// -------------------------------------------------------------
	// Atributos
	// -------------------------------------------------------------
	@SISTRANS_Id(AutoIncrement=true)
	@JsonProperty(value = "id")
	private Long id;
	
	@SISTRANS_Columna
	@SISTRANS_Check(of = "0", value = Checks.HIGHEREQUAL)
	@JsonProperty(value = "precioMenor")
	private Double precioMenor;

	@SISTRANS_Columna
	@SISTRANS_Check(of = "0", value = Checks.HIGHEREQUAL)
	@JsonProperty(value = "precioMayor")
	private Double precioMayor;

	@Reference
	@ForeignKey(unique=true)
	private Cliente cliente;
	// -------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------
	public Preferencias() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * Método constructor de la clase producto. <b>post: </b> Crea el producto
	 * con los valores que entran como parámetro.
	 * 
	 * @param id
	 *            - Id de la preferencia.
	 * @param precioMenor
	 *            - El menor precio con el que se desea filtrar.
	 * @param precioMayor
	 *            - El mayor precio con el que se desea filtrar.
	 */
	public Preferencias(@JsonProperty(value = "id") Long id, @JsonProperty(value = "precioMenor") Double precioMenor,
			@JsonProperty(value = "precioMayor") Double precioMayor) {
		super();
		this.id = id;
		this.precioMayor = precioMayor;
		this.precioMenor = precioMenor;
	}

	/**
	 * Método getter del atributo id
	 * 
	 * @return id de la preferencia
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Método setter del atributo id <b>post: </b> El id de la preferencia ha
	 * sido cambiado con el valor que entra como parámetro.
	 * 
	 * @param id
	 *            - Id de la preferencia
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Método getter del atributo precioMenor
	 * 
	 * @return Menor precio al cual filtrar.
	 */
	public Double getPrecioMenor() {
		return precioMenor;
	}

	/**
	 * Método setter del atributo precioMenor <b>post: </b> El precio menor de
	 * la preferencia ha sido cambiado con el valor que entra como parámetro.
	 * 
	 * @param precioMenor
	 *            - Precio menor de la preferencia.
	 */
	public void setPrecioMenor(Double precioMenor) {
		this.precioMenor = precioMenor;
	}

	/**
	 * Método getter del atributo precioMayor
	 * 
	 * @return Mayor precio al cual filtrar.
	 */
	public Double getPrecioMayor() {
		return precioMayor;
	}

	/**
	 * Método setter del atributo precioMayor <b>post: </b> El precio mayor de
	 * la preferencia ha sido cambiado con el valor que entra como parámetro.
	 * 
	 * @param precioMayor
	 *            - Precio mayor de la preferencia.
	 */
	public void setPrecioMayor(Double precioMayor) {
		this.precioMayor = precioMayor;
	}

}
