package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import em.GenericDao;
import vos.Menu;

public class DAOMenu extends GenericDao<Menu>{
	
	public DAOMenu( Connection conn) {
		super(Menu.class, conn);
	}

	public Long IdRestaurante(Long IDRegistro) throws SQLException, Exception{
		String sql="SELECT ID FROM RESTAURANTE,REGISTRO WHERE REGISTRO.CODIGO=RESTAURANTE.REGISTRO_ID AND REGISTRO.CODIGO="+IDRegistro;
		System.out.println(sql);
		ResultSet rs=executeModification(sql);
		if(rs.next()) {
			return rs.getLong("ID");
		}
		return null;
	}
}
