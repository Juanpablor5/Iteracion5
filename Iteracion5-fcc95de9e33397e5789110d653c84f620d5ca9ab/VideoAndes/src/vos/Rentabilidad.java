package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Rentabilidad 
{
	
	@JsonProperty(value="nombreZona")
	private String nombreZona;
	
	@JsonProperty(value="categoria")
	private String categoria;
	
	@JsonProperty(value="nombreProducto")
	private String nombreProducto;
	
	@JsonProperty(value="cantidadVentas")
	private Integer cantidadVentas;
	
	@JsonProperty(value="totalVentas")
	private Double totalVentas;
	
	

	public Rentabilidad(@JsonProperty(value="nombreZona") String nombreZona, 
			@JsonProperty(value="categoria") String categoria, 
			@JsonProperty(value="nombreProducto") String nombreProducto, 
			@JsonProperty(value="cantidadVentas") Integer cantidadVentas,
			@JsonProperty(value="totalVentas") Double totalVentas) {
		super();
		this.nombreZona = nombreZona;
		this.categoria = categoria;
		this.nombreProducto = nombreProducto;
		this.cantidadVentas = cantidadVentas;
		this.totalVentas = totalVentas;
	}

	public String getNombreZona() {
		return nombreZona;
	}

	public void setNombreZona(String nombreZona) {
		this.nombreZona = nombreZona;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public Integer getCantidadVentas() {
		return cantidadVentas;
	}

	public void setCantidadVentas(Integer cantidadVentas) {
		this.cantidadVentas = cantidadVentas;
	}

	public Double getTotalVentas() {
		return totalVentas;
	}

	public void setTotalVentas(Double totalVentas) {
		this.totalVentas = totalVentas;
	}
	
	
	
	
	
	
	

}
