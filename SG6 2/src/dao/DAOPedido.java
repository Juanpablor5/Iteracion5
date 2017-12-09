package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import em.GenericDao;
import vos.Pedido;

public class DAOPedido extends GenericDao<Pedido>{
	
	
	public DAOPedido(Connection conn) {
		super(Pedido.class,conn);
	}

	public void addPedidoCliente(long id, Pedido data) throws SQLException, Exception {
		String sql = "SELECT CEDULA FROM CLIENTEINFO, REGISTRO WHERE REGISTRO.CODIGO = CLIENTEINFO.REGISTRO_ID AND REGISTRO.CODIGO = "+ id;
		ResultSet rs = executeModification(sql);
		Long cedula = (long) 0;
		
		if (rs.next()) {
			cedula = rs.getLong("CEDULA");
		}else throw new SQLException("registro inexistente");

		String sql2 = "INSERT INTO " + TABLA + " VALUES (";
		sql2 += data.getId() + ",'";
		sql2 += data.getFechahora() + "',";
		sql2 += cedula + ")";
		executeModification(sql2);
	}
}
