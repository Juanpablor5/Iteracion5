package dao;

import java.sql.Connection;

import em.GenericDao;
import vos.Preferencias;

public class DAOPreferencias extends GenericDao<Preferencias>{
	
	public DAOPreferencias(Connection conn) {
		super(Preferencias.class, conn);
	}
}
