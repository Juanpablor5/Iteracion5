package dao;

import java.sql.Connection;
import java.sql.SQLException;

import em.GenericManyToMany;
import vos.Ingrediente;
import vos.Producto;

public class DAOProductoIngrediente extends GenericManyToMany<Producto, Ingrediente> {

	public DAOProductoIngrediente(Connection conn) throws SQLException {
		super(Producto.class, Ingrediente.class, conn);
	}

}
