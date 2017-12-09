package dao;

import java.util.List;

import em.Check;
import vos.Filtro;
import vos.Productoi;

public interface RF13 {

	List<Productoi> rf13(Filtro[] filtros,Check ... checks );
}
