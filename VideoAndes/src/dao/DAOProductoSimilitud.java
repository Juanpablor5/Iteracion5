package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import em.Extractor;
import em.GenericDao;
import vos.Producto;
import vos.ProductoSimilitud;

public class DAOProductoSimilitud extends GenericDao<ProductoSimilitud>{

	public DAOProductoSimilitud( Connection conn) {
		super(ProductoSimilitud.class, conn);
	}
	
	public List<Producto> getAll(Long idRest,Long id) throws SQLException{
		String sql= "SELECT * FROM Producto A , ProductoSimilitud B WHERE B.producto2_id=A.id AND B.producto_id = "+id+" AND B.restaurante_id = "+idRest;
		ResultSet rs=executeModification(sql);
		
		return new Extractor<Producto>(Producto.class).extractList(rs);
	}
	
	public void delte(Long idRest,Long id,Long id2) throws SQLException{
		String sql= "DELETE FROM ProductoSimilitud B WHERE B.producto_id = "+id+" AND B.producto2_id = "+id2+" AND B.restaurante_id = "+idRest;
		executeModification(sql);
	}
}
