package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import em.GenericDao;
import vos.Usuario;

public class DAOUsuario extends GenericDao<Usuario>{
	
	public DAOUsuario(Connection conn) {
		super(Usuario.class, conn);
	}

	public Usuario get(String usuario,String contrasenia) throws SQLException {
		String sql = "SELECT * FROM "+TABLA+" WHERE nickName ='" + usuario +"' AND CONTRASENIA='"+contrasenia+"'";
		ResultSet rs =executeModification(sql);
		
		return extr.extractDetail(rs,conn);
	}
	
	public boolean existAdmin() throws SQLException {
		String sql = "SELECT * FROM "+TABLA+" WHERE PERMISOS=3";
		ResultSet rs =executeModification(sql);
		
		return rs.next();
	}
}
