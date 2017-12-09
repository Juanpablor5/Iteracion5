package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import em.Checks;
import em.Reference;
import em.Tabla;
import em.Check.SISTRANS_Check;
import em.Columna.SISTRANS_Columna;
import em.Foreing.ForeignKey;
import em.Id.SISTRANS_Id;
import em.Many.ManytoMany;

/**
 * Clase que representa un Menu.
 */
@Tabla
public class Menu {
	// -------------------------------------------------------------
	// Atributos
	// -------------------------------------------------------------

	@SISTRANS_Id(AutoIncrement = true)
	@JsonProperty(value = "id")
	private Long id;

	@SISTRANS_Columna
	@SISTRANS_Check(value = Checks.HIGHER, of = "0")
	@JsonProperty(value = "cantidad")
	private Integer cantidad;

	@SISTRANS_Columna
	@SISTRANS_Check(value = Checks.HIGHER, of = "0")
	@JsonProperty(value = "costoProduccion")
	private Double costoProduccion;

	@SISTRANS_Columna
	@SISTRANS_Check(value = Checks.HIGHER, of = "0")
	@JsonProperty(value = "valorAlPublico")
	private Double valorAlPublico;
	
	@Reference
	@ForeignKey
	private Producto productoEntrada;
	
	@Reference
	@ForeignKey
	private Producto productoFuerte;

	@Reference
	@ForeignKey
	private Producto productoBebida;
	
	@Reference
	@ForeignKey
	private Producto productoPostre;
	
	@Reference
	@ForeignKey
	private Producto productoAcompanamiento;
	
	@Reference
	@ForeignKey
	private Restaurante restaurante;
	
	@Reference
	@ManytoMany
	private List<Pedido> Menus;
	// -------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------

	/**
	 * Método constructor de la clase menú. <b>post: </b> Crea el menú con los
	 * valores que entran como parámetro.
	 * 
	 * @param id
	 *            - Id del menú.
	 * @param cantidad
	 *            - Nombre del menú. nombre != null
	 * @param costoProduccion
	 *            - Descripción del menú.
	 * @param valorAlPublico
	 *            - Traducción de descripción del menú.
	 * @param productoEntrada 
	 * @param productoFuerte 
	 * @param productoBebida 
	 * @param productoAcompanamiento 
	 * @param restauranteID 
	 * @param procutoPostre 
	 */
	public Menu(@JsonProperty(value = "id") Long id,
			@JsonProperty(value = "cantidad") Integer cantidad,
			@JsonProperty(value = "costoProduccion") Double costoProduccion,
			@JsonProperty(value = "valorAlPublico") Double valorAlPublico) {
		super();
		this.id = id;
		this.cantidad = cantidad;
		this.costoProduccion = costoProduccion;
		this.valorAlPublico = valorAlPublico;
	}

	// -------------------------------------------------------------
		// Getters & Setters
		// -------------------------------------------------------------

		
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Double getCostoProduccion() {
		return costoProduccion;
	}

	public void setCostoProduccion(Double costoProduccion) {
		this.costoProduccion = costoProduccion;
	}

	public Double getValorAlPublico() {
		return valorAlPublico;
	}

	public void setValorAlPublico(Double valorAlPublico) {
		this.valorAlPublico = valorAlPublico;
	}

	public Producto getProductoEntrada() {
		return productoEntrada;
	}

	public void setProductoEntrada(Producto productoEntrada) {
		this.productoEntrada = productoEntrada;
	}

	public Producto getProductoFuerte() {
		return productoFuerte;
	}

	public void setProductoFuerte(Producto productoFuerte) {
		this.productoFuerte = productoFuerte;
	}

	public Producto getProductoBebida() {
		return productoBebida;
	}

	public void setProductoBebida(Producto productoBebida) {
		this.productoBebida = productoBebida;
	}

	public Producto getProductoPostre() {
		return productoPostre;
	}

	public void setProductoPostre(Producto productoPostre) {
		this.productoPostre = productoPostre;
	}

	public Producto getProductoAcompanamiento() {
		return productoAcompanamiento;
	}

	public void setProductoAcompanamiento(Producto productoAcompanamiento) {
		this.productoAcompanamiento = productoAcompanamiento;
	}

	public Restaurante getRestaurante() {
		return restaurante;
	}

	public void setRestaurante(Restaurante restaurante) {
		this.restaurante = restaurante;
	}

	public List<Pedido> getMenus() {
		return Menus;
	}

	public void setMenus(List<Pedido> menus) {
		Menus = menus;
	}
}
