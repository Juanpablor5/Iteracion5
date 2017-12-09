package vos;

public class ProductoDetail extends Producto{
	private Long[] ingredientesId;
	
	public ProductoDetail() {
	}

	public Long[] getIngredientesId() {
		return ingredientesId;
	}

	public void setIngredientesId(Long[] ingredientesId) {
		this.ingredientesId = ingredientesId;
	}
}
