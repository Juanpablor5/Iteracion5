/**-------------------------------------------------------------------
 * $Id$
 * Universidad de los Andes (Bogot� - Colombia)
 * Departamento de Ingenier�a de Sistemas y Computaci�n
 *
 * Materia: Sistemas Transaccionales
 * Ejercicio: VideoAndes
 * Autor: Juan Felipe Garc�a - jf.garcia268@uniandes.edu.co
 * -------------------------------------------------------------------
 */
package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Video;

/**
 * Clase DAO que se conecta la base de datos usando JDBC para resolver los requerimientos de la aplicaci�n
 * @author Juan
 */
public class DAOTablaVideos {


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
	public DAOTablaVideos() {
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
	 * @return Arraylist con los videos de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Video> darVideos() throws SQLException, Exception {
		
		ArrayList<Video> videos = new ArrayList<Video>();
		videos.add(new Video(20, "Prueba 2", 20));
		return videos;
	}

	/**
	 * M�todo que busca el/los videos con el nombre que entra como par�metro.
	 * @param name - Nombre de el/los videos a buscar
	 * @return ArrayList con los videos encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Video> buscarVideosPorName(String name) throws SQLException, Exception {
		ArrayList<Video> videos = new ArrayList<Video>();

		String sql = "SELECT * FROM ISIS2304MO11620.VIDEOS WHERE NAME ='" + name + "'";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String name2 = rs.getString("NAME");
			int id = Integer.parseInt(rs.getString("ID"));
			int duration = Integer.parseInt(rs.getString("DURATION"));
			videos.add(new Video(id, name2, duration));
		}

		return videos;
	}

	/**
	 * M�todo que agrega el video que entra como par�metro a la base de datos.
	 * @param video - el video a agregar. video !=  null
	 * <b> post: </b> se ha agregado el video a la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que el video baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el video a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addVideo(Video video) throws SQLException, Exception {

		String sql = "INSERT INTO ISIS2304MO11620.VIDEOS VALUES (";
		sql += video.getId() + ",'";
		sql += video.getName() + "',";
		sql += video.getDuration() + ")";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	/**
	 * M�todo que actualiza el video que entra como par�metro en la base de datos.
	 * @param video - el video a actualizar. video !=  null
	 * <b> post: </b> se ha actualizado el video en la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el video.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void updateVideo(Video video) throws SQLException, Exception {

		String sql = "UPDATE ISIS2304MO11620.VIDEOS SET ";
		sql += "name='" + video.getName() + "',";
		sql += "duration=" + video.getDuration();
		sql += " WHERE id = " + video.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * M�todo que elimina el video que entra como par�metro en la base de datos.
	 * @param video - el video a borrar. video !=  null
	 * <b> post: </b> se ha borrado el video en la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el video.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deleteVideo(Video video) throws SQLException, Exception {

		String sql = "DELETE FROM ISIS2304MO11620.VIDEOS";
		sql += " WHERE id = " + video.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * M�todo que busca el/los videos mas alquilados.
	 * @return Arraylist con los videos encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Video> darVideoMasAlquilado()  throws SQLException, Exception {
		ArrayList<Video> videos = new ArrayList<Video>();

		String sql = "SELECT * " +
					 "FROM ISIS2304MO11620.VIDEOS " +
				     "WHERE ISIS2304MO11620.VIDEOS.ID IN (SELECT VIDEO_ID " +
				                         "FROM ALQUILERES " +
				                         "GROUP BY VIDEO_ID " +
				                         "HAVING COUNT(*) = (SELECT MAX(COUNT(*)) " +
				                                            "FROM ALQUILERES " +
				                                            "GROUP BY VIDEO_ID)) ";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String name = rs.getString("NAME");
			int id = Integer.parseInt(rs.getString("ID"));
			int duration = Integer.parseInt(rs.getString("DURATION"));
			videos.add(new Video(id, name, duration));
		}

		return videos;
	}


}