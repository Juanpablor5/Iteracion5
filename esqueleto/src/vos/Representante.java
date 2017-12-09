package vos;

import org.codehaus.jackson.annotate.JsonProperty;
import em.Checks;
import em.Check.SISTRANS_Check;
import em.Columna.SISTRANS_Columna;
import em.Foreing.ForeignKey;
import em.Id.SISTRANS_Id;
import em.Reference;
import em.Tabla;

@Tabla
public class Representante {

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

	@SISTRANS_Columna
	@SISTRANS_Check(value = Checks.HIGHER, of = "0")
	@JsonProperty(value = "telefono")
	private Integer telefono;

	@SISTRANS_Columna
	@SISTRANS_Check(value = Checks.DIFERENT, of = "")
	@JsonProperty(value = "correo")
	private String correo;

	@Reference
	@ForeignKey(unique=true , nullable = false , cascade =true)
	private Restaurante restaurante;

	// -------------------------------------------------------------
	// constructor
	// -------------------------------------------------------------
	public Representante() {
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

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getTelefono() {
		return telefono;
	}

	public void setTelefono(Integer telefono) {
		this.telefono = telefono;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public Restaurante getRestaurante() {
		return restaurante;
	}

	public void setRestaurante(Restaurante restaurante) {
		this.restaurante = restaurante;
	}

}
