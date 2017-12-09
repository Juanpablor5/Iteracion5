package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaProductosI {

	/**
	 * List con los videos
	 */
	@JsonProperty(value = "productos")
	private List<Productoi> productos;

	/**
		 * Constructor de la clase ListaVideos
		 * @param videos - videos para agregar al arreglo de la clase
		 */
		public ListaProductosI( @JsonProperty(value="productos")List<Productoi> productos){
			this.productos = productos;
		}

	/**
	 * M�todo que retorna la lista de videos
	 * 
	 * @return List - List con los videos
	 */
	public List<Productoi> getVideos() {
		return productos;
	}

	/**
	 * M�todo que asigna la lista de videos que entra como parametro
	 * 
	 * @param videos
	 *            - List con los videos ha agregar
	 */
	public void setVideo(List<Productoi> productos) {
		this.productos = productos;
	}
}
