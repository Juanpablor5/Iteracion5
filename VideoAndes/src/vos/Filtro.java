package vos;

public class Filtro {
	private Criterio criterio;
	private String valor;
	private Checks check;
	
	public Filtro(Criterio criterio, String valor, Checks check) {
		super();
		this.criterio = criterio;
		this.valor = valor;
		this.check = check;
	}
	public Criterio getCriterio() {
		return criterio;
	}
	public void setCriterio(Criterio criterio) {
		this.criterio = criterio;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}

	public Checks getCheck() {
		return check;
	}

	public void setCheck(Checks check) {
		this.check = check;
	}
}
