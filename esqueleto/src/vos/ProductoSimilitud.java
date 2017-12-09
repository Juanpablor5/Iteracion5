package vos;

import em.Tabla;
import em.Foreing.ForeignKey;
import em.Id.SISTRANS_Id;

@Tabla
public class ProductoSimilitud {

	@SISTRANS_Id(AutoIncrement = true)
	private Long id;

	@ForeignKey
	private Restaurante restaurante;

	@ForeignKey
	private Producto producto;

	@ForeignKey
	private Producto producto2;

	public ProductoSimilitud() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Restaurante getRestaurante() {
		return restaurante;
	}

	public void setRestaurante(Restaurante restaurante) {
		this.restaurante = restaurante;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Producto getProducto2() {
		return producto2;
	}

	public void setProducto2(Producto producto2) {
		this.producto2 = producto2;
	}
}
