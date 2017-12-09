package em;

import java.util.List;

public class TransacionMannager extends Connector {
	public <T> T create(T registro) {
		return registro;

	}

	public <T> List<T> getAll(T clase) {
		return null;

	}

	public <T> List<T> get(T clase, Object... ids) {
		return null;

	}

	public <T> T update(T registro) {
		return registro;

	}

	public <T> T remove(T registro) {
		return registro;

	}

	public <T> T remove(T clase, Object... ids) {
		return clase;
	}
}
