package em;

public enum Checks {
	EQUAL("="),
	DIFERENT("<>"),
	HIGHER(">"),
	HIGHEREQUAL(">="),
	LESS("<"),
	LESSEQUAL("<="),
	BETWEEN("BETWEEN");
	
	private final String signo;
	
	Checks(String signo) {
		this.signo=signo;
	}
	
	public String getSigno() {
		return signo;
	}
}
