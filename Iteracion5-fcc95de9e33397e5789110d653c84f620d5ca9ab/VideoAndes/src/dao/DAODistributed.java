package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Producto;
import vos.Rentabilidad;

public class DAODistributed {
	
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
	public DAODistributed() {
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

    // -----------------------------------------------------------------
    // RENTABILIDAD
    // -----------------------------------------------------------------

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

    // -----------------------------------------------------------------
    // PRODUCTO
    // -----------------------------------------------------------------
	
	/**
	 * M�todo que, usando la conexi�n a la base de datos, saca todos los videos de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM VIDEOS;
	 * @param string 
	 * @return Arraylist con los videos de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Producto> darProductos(String string) throws SQLException, Exception {
		
		ArrayList<Producto> productos = new ArrayList<Producto>();
		System.out.println("========"+string);
		productos.add(new Producto(20, "Prueba 2", "Desc", "Trad", 20.0));
		return productos;
	}

	/**
	 * M�todo que busca el/los videos con el nombre que entra como par�metro.
	 * @param name - Nombre de el/los videos a buscar
	 * @return ArrayList con los videos encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Producto> buscarProductosPorName(String name) throws SQLException, Exception {
		ArrayList<Producto> productos = new ArrayList<Producto>();

		String sql = "SELECT * FROM PRODUCTO WHERE NAME ='" + name + "'";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String name2 = rs.getString("NOMBRE");
			int id = Integer.parseInt(rs.getString("ID"));
			String descripcion = rs.getString("DESCRIPCION");
			String traduccion = rs.getString("TRADUCCION");
			Double precio = Double.parseDouble(rs.getString("PRECIO"));
			productos.add(new Producto(id, name2, descripcion,traduccion,precio));
		}

		return productos;
	}

	/**
	 * M�todo que agrega el video que entra como par�metro a la base de datos.
	 * @param producto - el video a agregar. video !=  null
	 * <b> post: </b> se ha agregado el video a la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que el video baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el video a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addProducto(Producto producto) throws SQLException, Exception {

		String sql = "INSERT INTO PRODUCTO VALUES (";
		sql += producto.getId() + ",'";
		sql += producto.getNombre() + "','";
		sql += producto.getDescripcion() + "','";
		sql += producto.getTraduccion() + "',";
		sql += producto.getPrecio() + ")";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	/**
	 * M�todo que actualiza el video que entra como par�metro en la base de datos.
	 * @param producto - el video a actualizar. video !=  null
	 * <b> post: </b> se ha actualizado el video en la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el video.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void updateVideo(Producto producto) throws SQLException, Exception {

		String sql = "UPDATE PRODUCTO SET ";
		sql += "nombre='" + producto.getNombre() + "',";
		sql += "descripcion='" + producto.getDescripcion() + "',";
		sql += "traduccion='" + producto.getTraduccion() + "',";
		sql += "precio=" + producto.getPrecio()+ ",";
		sql += " WHERE id = " + producto.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * M�todo que elimina el video que entra como par�metro en la base de datos.
	 * @param producto - el video a borrar. video !=  null
	 * <b> post: </b> se ha borrado el video en la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el video.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deleteVideo(Producto producto) throws SQLException, Exception {

		String sql = "DELETE FROM PRODUCTO";
		sql += " WHERE id = " + producto.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
    // -----------------------------------------------------------------
    // RESTAURANTE
    // -----------------------------------------------------------------
	
	public void deleteRestaurante(long idRestaurante) throws SQLException, Exception {
		System.out.println(idRestaurante+"========================");
	}
}
