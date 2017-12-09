package dao;

import java.sql.Connection;
import em.GenericDao;
import vos.Categoria;

/**
 * Clase DAO que se conecta la base de datos usando JDBC para resolver los requerimientos de la aplicación
 */
public class DAOCategoria extends GenericDao<Categoria>{

	public DAOCategoria(Connection conn) {
		super(Categoria.class, conn);
	}
}
