package dao;

import java.sql.Connection;
import em.GenericDao;
import vos.Restaurante;

public class DAORestaurante extends GenericDao<Restaurante>{

	public DAORestaurante( Connection conn) {
		super(Restaurante.class, conn);
	}
}
