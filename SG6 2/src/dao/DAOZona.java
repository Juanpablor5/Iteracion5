package dao;

import java.sql.Connection;
import em.GenericDao;
import vos.Zona;


public class DAOZona extends GenericDao<Zona>{

	public DAOZona(Connection conn) {
		super(Zona.class, conn);
	}
}
