package vos;

import em.Checks;
import em.Reference;
import em.Tabla;
import org.codehaus.jackson.annotate.JsonProperty;
import em.Check.SISTRANS_Check;
import em.Columna.SISTRANS_Columna;
import em.Foreing.ForeignKey;
import em.Id.SISTRANS_Id;

/**
 * Clase que representa un Restaurante.
 */
@Tabla
public class Restaurante {

	// -------------------------------------------------------------
	// Atributos
	// -------------------------------------------------------------
	@SISTRANS_Id(AutoIncrement = true)
	@JsonProperty(value = "id")
	private Long id;

	@SISTRANS_Columna
	@SISTRANS_Check(value = Checks.DIFERENT, of = "")
	@JsonProperty(value = "nombre")
	private String nombre;

	@SISTRANS_Columna(nullable = true)
	@SISTRANS_Check(value = Checks.DIFERENT, of = "")
	@JsonProperty(value = "paginaWeb")
	private String paginaWeb;

	@Reference
	@ForeignKey(unique = true, nullable = false, cascade = true)
	private Usuario registro;

	@Reference(mappedBy = "restaurante")
	private Representante representante;

	@Reference
	@ForeignKey(nullable = true)
	private TipoComida tipoComida;
	// /**
	// * Id de la zona del restaurante.
	// */
	// @JsonProperty(value = "regristroId")
	// private Integer zonaId;
	//
	// /**
	// * id del tipo de comida asociado.
	// */
	// @JsonProperty(value = "tipoComidaId")
	// private Integer tipoComidaId;

	public Restaurante() {
	}

	// -------------------------------------------------------------
	// Getters & Setters
	// -------------------------------------------------------------

	/**
	 * Método getter del atributo id.
	 * 
	 * @return id del restaurante.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Método setter del atributo id <b>post: </b> El id del restaurante ha sido
	 * cambiado con el valor que entra como parámetro.
	 * 
	 * @param id
	 *            - Id del restaurante.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Método getter del atributo nombre.
	 * 
	 * @return id del restaurante.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Método setter del atributo nombre <b>post: </b> El nombre del restaurante ha
	 * sido cambiado con el valor que entra como parámetro.
	 * 
	 * @param nombre
	 *            - Nombre del restaurante.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Método getter del atributo paginaWeb.
	 * 
	 * @return URL de la página web del restaurante.
	 */
	public String getPaginaWeb() {
		return paginaWeb;
	}

	/**
	 * Método setter del atributo paginaWeb <b>post: </b> El URL de la página web
	 * del restaurante ha sido cambiado con el valor que entra como parámetro.
	 * 
	 * @param nombre
	 *            - Nombre del restaurante.
	 */
	public void setPaginaWeb(String paginaWeb) {
		this.paginaWeb = paginaWeb;
	}

	public Usuario getRegistro() {
		return registro;
	}

	public void setRegistro(Usuario registro) {
		this.registro = registro;
	}

	public Representante getRepresentante() {
		return representante;
	}

	public void setRepresentante(Representante representante) {
		this.representante = representante;
	}

	public TipoComida getTipoComida() {
		return tipoComida;
	}

	public void setTipoComida(TipoComida tipoComida) {
		this.tipoComida = tipoComida;
	}

}
