package vos;
public enum Checks {
	EQUAL("="),
	DIFERENT("<>"),
	HIGHER(">"),
	HIGHEREQUAL(">="),
	LESS("<"),
	LESSEQUAL("<=");
	
	private final String signo;
	
	Checks(String signo) {
		this.signo=signo;
	}
	
	public String getSigno() {
		return signo;
	}
}