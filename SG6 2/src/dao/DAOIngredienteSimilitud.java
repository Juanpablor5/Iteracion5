package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import em.Extractor;
import em.GenericDao;
import vos.Ingrediente;
import vos.IngredienteSimilitud;

public class DAOIngredienteSimilitud extends GenericDao<IngredienteSimilitud>{

	public DAOIngredienteSimilitud( Connection conn) {
		super(IngredienteSimilitud.class, conn);
	}
	
	public List<Ingrediente> getAll(Long idRest,Long id) throws SQLException{
		String sql= "SELECT * FROM Ingrediente A , IngredienteSimilitud B WHERE B.ingrediente2_id=A.id AND B.ingrediente_id = "+id+" AND B.restaurante_id = "+idRest;
		ResultSet rs=executeModification(sql);
		
		return new Extractor<Ingrediente>(Ingrediente.class).extractList(rs);
	}
	
	public void delte(Long idRest,Long id,Long id2) throws SQLException{
		String sql= "DELETE FROM IngredienteSimilitud B WHERE B.ingrediente_id = "+id+" AND B.ingrediente2_id = "+id2+" AND B.restaurante_id = "+idRest;
		executeModification(sql);
	}
}
