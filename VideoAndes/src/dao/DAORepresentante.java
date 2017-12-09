package dao;

import java.sql.Connection;
import em.GenericDao;
import vos.Representante;

public class DAORepresentante extends GenericDao<Representante>{

	public DAORepresentante(Connection conn) {
		super(Representante.class, conn);
	}

}
