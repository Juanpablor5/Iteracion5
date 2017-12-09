package vos;

import em.Foreing.ForeignKey;
import em.Id.SISTRANS_Id;
import em.Reference;
import em.Tabla;

@Tabla
public class IngredienteSimilitud {

	@SISTRANS_Id(AutoIncrement = true)
	private Long id;

	@Reference
	@ForeignKey
	private Restaurante restaurante;

	@Reference
	@ForeignKey
	private Ingrediente ingrediente;

	@Reference
	@ForeignKey
	private Ingrediente ingrediente2;

	public IngredienteSimilitud() {

	}

	public Restaurante getRestaurante() {
		return restaurante;
	}

	public void setRestaurante(Restaurante restaurante) {
		this.restaurante = restaurante;
	}

	public Ingrediente getIngrediente() {
		return ingrediente;
	}

	public void setIngrediente(Ingrediente ingrediente) {
		this.ingrediente = ingrediente;
	}

	public Ingrediente getIngrediente2() {
		return ingrediente2;
	}

	public void setIngrediente2(Ingrediente ingrediente2) {
		this.ingrediente2 = ingrediente2;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
