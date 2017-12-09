package dao;

import java.sql.Connection;

import em.GenericDao;
import vos.Cliente;

public class DAOCliente extends GenericDao<Cliente>{
	
	public DAOCliente(Connection conn) {
		super(Cliente.class, conn);
	}
}
