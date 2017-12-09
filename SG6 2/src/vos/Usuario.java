package vos;

import org.codehaus.jackson.annotate.JsonProperty;

import em.Check.SISTRANS_Check;
import em.Checks;
import em.Tabla;
import em.Columna.SISTRANS_Columna;
import em.Id.SISTRANS_Id;
import em.Reference;

@Tabla
public class Usuario {
	// -------------------------------------------------------------
	// ATRIBUTOS
	// -------------------------------------------------------------
	@SISTRANS_Id(AutoIncrement = true)
	private Long codigo;

	@SISTRANS_Columna(unique = true)
	@SISTRANS_Check(value = Checks.DIFERENT, of = "")
	@JsonProperty(value = "nickName")
	private String nickName;

	@SISTRANS_Columna
	@SISTRANS_Check(value = Checks.DIFERENT, of = "")
	@JsonProperty(value = "contrasenia")
	private String contrasenia;

	@SISTRANS_Columna
	@SISTRANS_Check(value = Checks.DIFERENT, of = "")
	@JsonProperty(value = "correo")
	private String correo;

	@SISTRANS_Columna
	@SISTRANS_Check(value = Checks.BETWEEN, of = "0", to = "3")
	@JsonProperty(value = "permisos")
	private Integer permisos;

	@Reference(mappedBy = "registro")
	private Cliente cliente;
	@Reference(mappedBy="registro")
	private Restaurante restaurante;

	// -------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------

	public Usuario() {
	}
	// -------------------------------------------------------------
	// Getters & Setters
	// -------------------------------------------------------------

	/**
	 * Método getter del atributo código.
	 * 
	 * @return Código del cliente a registrar.
	 */
	public Long getCodigo() {
		return codigo;
	}

	/**
	 * Método setter del atributo código <b>post: </b> El código del cliente a
	 * registrar ha sido cambiado con el valor que entra como parámetro.
	 * 
	 * @param código
	 *            - Código del cliente a registrar.
	 */
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	/**
	 * Método getter del atributo usuario.
	 * 
	 * @return usuario del cliente a registrar.
	 */
	public String getNickName() {
		return nickName;
	}

	/**
	 * Método setter del atributo usuario <b>post: </b> El usuario del cliente a
	 * registrar ha sido cambiado con el valor que entra como parámetro.
	 * 
	 * @param usuario
	 *            - Usuario del cliente a registrar.
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/**
	 * Método getter del atributo contraseña.
	 * 
	 * @return Contraseña del cliente a registrar.
	 */
	public String getContrasenia() {
		return contrasenia;
	}

	/**
	 * Método setter del atributo contraseña <b>post: </b> La contraseña del cliente
	 * a registrar ha sido cambiado con el valor que entra como parámetro.
	 * 
	 * @param contraseña
	 *            - Contraseña del cliente a registrar.
	 */
	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	/**
	 * Método getter del atributo permisos.
	 * 
	 * @return id del cliente a registrar.
	 */
	public Integer getPermisos() {
		return permisos;
	}

	/**
	 * Método setter del atributo permisos <b>post: </b> Los permisos del cliente a
	 * registrar ha sido cambiado con el valor que entra como parámetro.
	 * 
	 * @param permisos
	 *            - Permisos del cliente a registrar.
	 */
	public void setPermisos(Integer permisos) {
		this.permisos = permisos;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Restaurante getRestaurante() {
		return restaurante;
	}

	public void setRestaurante(Restaurante restaurante) {
		this.restaurante = restaurante;
	}
}
