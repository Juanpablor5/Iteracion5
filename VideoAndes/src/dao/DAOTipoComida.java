package dao;

import java.sql.Connection;
import em.GenericDao;
import vos.TipoComida;

public class DAOTipoComida extends GenericDao<TipoComida>{

	public DAOTipoComida(Connection conn) {
		super(TipoComida.class, conn);
	}

}
