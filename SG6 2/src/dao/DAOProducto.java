package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import em.Extractor;
import em.GenericDao;
import vos.Producto;

public class DAOProducto extends GenericDao<Producto> {

	public DAOProducto(Connection conn) {
		super(Producto.class, conn);
	}
	
	public Producto getByName(String name) throws SQLException {
		String sql="SELECT *  FROM "+TABLA+" WHERE nombre = '"+name+"'";
		ResultSet rs=executeModification(sql);
		
		return new Extractor<>(Producto.class).extract(rs);
	}
}
