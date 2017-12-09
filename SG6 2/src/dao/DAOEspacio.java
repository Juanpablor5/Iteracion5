package dao;

import java.sql.Connection;
import em.GenericDao;
import vos.Espacio;

public class DAOEspacio extends GenericDao<Espacio> {
	public DAOEspacio( Connection conn) {
		super(Espacio.class, conn);
	}
}
