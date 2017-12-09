package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import em.Check;
import em.Extractor;
import em.GenericDao;
import vos.Filtro;
import vos.Producto;
import vos.Productoi;

public class DAOProducto extends GenericDao<Producto> implements RF13{

	public DAOProducto(Connection conn) {
		super(Producto.class, conn);
	}
	
	public Producto getByName(String name) throws SQLException {
		String sql="SELECT *  FROM "+TABLA+" WHERE nombre = '"+name+"'";
		ResultSet rs=executeModification(sql);
		
		return new Extractor<>(Producto.class).extract(rs);
	}

	@Override
	public List<Productoi> rf13(Filtro[] filtros, Check... checks) {
		// TODO Auto-generated method stub
		return null;
	}
}
