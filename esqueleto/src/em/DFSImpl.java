package em;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

class DFSImpl{
	private final HashSet<Arista> check = new HashSet<>();
	private final List<Arista> orden = new ArrayList<>();
	
	public List<Arista> getOrden(){
		return orden;
	}
	
	public DFSImpl(Iterator<Arista> iter) {
		while (iter.hasNext()) {
			Arista arista = iter.next();
			if (!check.contains(arista))
				dfs(arista);
		}
	}
	
	private void dfs(Arista arista) {
		check.add(arista);
		for (Arista a : arista.getForeings()) {
			if (!check.contains(a))
				dfs(a);
		}
		orden.add(arista);
	}
}
