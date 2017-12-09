package vos;

import java.util.Date;
import org.codehaus.jackson.annotate.JsonProperty;
import em.Check.SISTRANS_Check;
import em.Checks;
import em.Columna.SISTRANS_Columna;
import em.DateAnotation;
import em.Foreing.ForeignKey;
import em.Id.SISTRANS_Id;
import em.Reference;
import em.Tabla;

@Tabla
public class Cliente {
	// -------------------------------------------------------------
	// ATRIBUTOS
	// -------------------------------------------------------------
	@SISTRANS_Id
	@JsonProperty(value = "cedula")
	private Long cedula;

	@SISTRANS_Columna
	@SISTRANS_Check(value = Checks.DIFERENT, of = "")
	@JsonProperty(value = "nombre")
	private String nombre;

	@SISTRANS_Columna(valorPorDefecto = "SYSDATE")
	@DateAnotation
	@JsonProperty(value = "fechaIngreso")
	private Date fechaIngreso;

	@Reference
	@ForeignKey(unique = true , nullable =false ,cascade=true)
	private Usuario registro;

	@Reference(mappedBy = "cliente")
	@JsonProperty(value = "preferencias")
	private Preferencias preferencias;

	// -------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------
	public Cliente() {
		// constructorEntity
	}

	/**
	 * Método constructor de la clase cliente. <b>post: </b> Crea el cliente con los
	 * valores que entran como parámetro.
	 * 
	 * @param cedula
	 *            - Cédula del cliente.
	 * @param nombre
	 *            - Nombre del cliente. nombre != null
	 * @param fechaIngreso
	 *            - Fecha de ingreso del cliente.
	 * @param regristroId
	 */
	public Cliente(@JsonProperty(value = "cedula") Long cedula, @JsonProperty(value = "nombre") String nombre,
			@JsonProperty(value = "fechaIngreso") Date fechaIngreso,
			@JsonProperty(value = "preferencias") Preferencias preferencias) {
		super();
		this.cedula = cedula;
		this.nombre = nombre;
		this.fechaIngreso = fechaIngreso;
		// this.preferencias=preferencias;
	}

	// -------------------------------------------------------------
	// Getters & Setters
	// -------------------------------------------------------------

	/**
	 * Método getter del atributo id
	 * 
	 * @return id del cliente
	 */
	public Long getCedula() {
		return cedula;
	}

	/**
	 * Método setter del atributo id <b>post: </b> El id del cliente ha sido
	 * cambiado con el valor que entra como parámetro.
	 * 
	 * @param id
	 *            - Id del cliente
	 */
	public void setCedula(Long cedula) {
		this.cedula = cedula;
	}

	/**
	 * Método getter del atributo nombre
	 * 
	 * @return id del cliente
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Método setter del atributo nombre <b>post: </b> El nombre del cliente ha sido
	 * cambiado con el valor que entra como parámetro.
	 * 
	 * @param nombre
	 *            - Nombre del cliente.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Método getter del atributo fechaIngreso.
	 * 
	 * @return fecha de ingreso del cliente.
	 */
	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	/**
	 * Método setter del atributo fehchaIngreso <b>post: </b> La fecha de ingreso
	 * del cliente ha sido cambiado con el valor que entra como parámetro.
	 * 
	 * @param fechaIngreso
	 *            - Fecha de ingreso del cliente.
	 */
	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public Usuario getRegistro() {
		return registro;
	}

	public void setRegistro(Usuario registro) {
		this.registro = registro;
	}

	public Preferencias getPreferencias() {
		return preferencias;
	}

	public void setPreferencias(Preferencias preferencias) {
		this.preferencias = preferencias;
	}

}
