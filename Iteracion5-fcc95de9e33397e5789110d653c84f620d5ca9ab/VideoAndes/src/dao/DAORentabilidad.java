package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Producto;
import vos.Rentabilidad;

public class DAORentabilidad {
	
	/**
	 * Arraylits de recursos que se usan para la ejecuci�n de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexi�n a la base de datos
	 */
	private Connection conn;

	/**
	 * M�todo constructor que crea DAOVideo
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAORentabilidad() {
		recursos = new ArrayList<Object>();
	}

	/**
	 * M�todo que cierra todos los recursos que estan enel arreglo de recursos
	 * <b>post: </b> Todos los recurso del arreglo de recursos han sido cerrados
	 */
	public void cerrarRecursos() {
		for(Object ob : recursos){
			if(ob instanceof PreparedStatement)
				try {
					((PreparedStatement) ob).close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
		}
	}

	/**
	 * M�todo que inicializa la connection del DAO a la base de datos con la conexi�n que entra como par�metro.
	 * @param con  - connection a la base de datos
	 */
	public void setConn(Connection con){
		this.conn = con;
	}


	/**
	 * M�todo que, usando la conexi�n a la base de datos, saca todos los videos de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM VIDEOS;
	 * @param string 
	 * @return Arraylist con los videos de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Rentabilidad> darRentabilidad(String string) throws SQLException, Exception {
		
		ArrayList<Rentabilidad> productos = new ArrayList<Rentabilidad>();
		System.out.println("(rent)========"+string);
		productos.add(new Rentabilidad("Prueba 2", "Categoria", "Producto", 2, 5.0));
		return productos;
	}
}
