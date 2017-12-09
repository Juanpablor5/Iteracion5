package dao;

import java.sql.Connection;
import em.GenericDao;
import vos.Ingrediente;

public class DAOIngrediente extends GenericDao<Ingrediente>{

	public DAOIngrediente(Connection conn) {
		super(Ingrediente.class, conn);
	}
}
