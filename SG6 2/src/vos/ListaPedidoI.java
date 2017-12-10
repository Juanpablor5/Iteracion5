package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaPedidoI {
	/**
	 * List con los videos
	 */
	@JsonProperty(value="usuario")
	private List<Long> usuario;
	
	/**
	 * Constructor de la clase ListaVideos
	 * @param videos - videos para agregar al arreglo de la clase
	 */
	public ListaPedidoI( @JsonProperty(value="usuario")List<Long> usuario){
		this.usuario = usuario;
	}

	/**
	 * Método que retorna la lista de videos
	 * @return  List - List con los videos
	 */
	public List<Long> getUsuario() {
		return usuario;
	}

	/**
	 * Método que asigna la lista de videos que entra como parametro
	 * @param  videos - List con los videos ha agregar
	 */
	public void setUsuario(List<Long> usuario) {
		this.usuario = usuario;
	}
}
