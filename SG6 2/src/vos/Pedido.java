package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import em.DateAnotation;
import em.Reference;
import em.Tabla;
import em.Columna.SISTRANS_Columna;
import em.Foreing.ForeignKey;
import em.Id.SISTRANS_Id;
import em.Many.ManytoMany;

/**
 * Clase que representa un pedido.
 */
@Tabla
public class Pedido {

	// -------------------------------------------------------------
	// Atributos
	// -------------------------------------------------------------

	@SISTRANS_Id(AutoIncrement=true)
	@JsonProperty(value = "id")
	private Long id;

	@SISTRANS_Columna(valorPorDefecto = "SYSDATE")
	@DateAnotation(completa=true)
	@JsonProperty(value = "fechahora")
	private String fechahora;
	
	@Reference
	@ForeignKey
	private Cliente cliente;
	
	@Reference
	@ManytoMany(mapped="Menus")
	private List<Menu> pedidos;

	// -------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------

	/**
	 * Método constructor de la clase pedido. <b>post: </b> Crea el pedido con
	 * los valores que entran como parámetro.
	 * 
	 * @param id
	 *            - Id del pedido.
	 * @param fechahora
	 *            - Fecha de ingreso del pedido. nombre != null
	 * @param clienteCedula
	 *            - Cédula del cliente que hizo el pedido.
	 */
	public Pedido(@JsonProperty(value = "id") Long id, @JsonProperty(value = "fechahora") String fechahora,
			Cliente cliente) {
		super();
		this.id = id;
		this.fechahora = fechahora;
	}

	// -------------------------------------------------------------
	// Getters & Setters
	// -------------------------------------------------------------

	/**
	 * Método getter del atributo id
	 * 
	 * @return id del pedido
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Método setter del atributo id <b>post: </b> El id del pedido ha sido
	 * cambiado con el valor que entra como parámetro.
	 * 
	 * @param id
	 *            - Id del pedido
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Método getter del atributo fecha
	 * 
	 * @return fecha de ingreso del pedido
	 */
	public String getFechahora() {
		return fechahora;
	}

	/**
	 * Método setter del atributo fecha <b>post: </b> La fecha del pedido ha
	 * sido cambiado con el valor que entra como parámetro.
	 * 
	 * @param fecha
	 *            - Fecha del pedido
	 */
	public void setFechahora(String fechahora) {
		this.fechahora = fechahora;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
}
