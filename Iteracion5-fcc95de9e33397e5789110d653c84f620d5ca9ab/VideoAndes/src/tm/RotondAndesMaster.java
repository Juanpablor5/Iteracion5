/**-------------------------------------------------------------------
 * $Id$
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación
 *
 * Materia: Sistemas Transaccionales
 * Ejercicio: VideoAndes
 * Autor: Juan Felipe García - jf.garcia268@uniandes.edu.co
 * -------------------------------------------------------------------
 */
package tm;

import java.sql.SQLException;
import java.util.ArrayList;
import dao.DAOTablaVideos;
import dtm.RotondAndesDistributed;
import jms.NonReplyException;
import vos.Video;
import vos.ListaVideos;

/**
 * @author Juan
 */
public class RotondAndesMaster extends baseTM {

	private RotondAndesDistributed dtm;
	
	public RotondAndesMaster(String contextPathP) {
		setConectionDataPath(contextPathP + CONNECTION_DATA_FILE_NAME_REMOTE);
		dtm = RotondAndesDistributed.getInstance(this);
		initConnectionData();
	}
	
	////////////////////////////////////////
	///////Transacciones////////////////////
	////////////////////////////////////////
	
	public ListaVideos darVideosLocal() throws Exception {
		ArrayList<Video> videos;
		DAOTablaVideos daoVideos = new DAOTablaVideos();
		try 
		{
			this.conn = darConexion();
			daoVideos.setConn(conn);
			videos = daoVideos.darVideos();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoVideos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaVideos(videos);
	}

	/**
	 * Método que modela la transacción que retorna todos los videos de la base de datos.
	 * @return ListaVideos - objeto que modela  un arreglo de videos. este arreglo contiene el resultado de la búsqueda
	 * @throws Exception -  cualquier error que se genere durante la transacción
	 */
	public ListaVideos darVideos() throws Exception {
		ListaVideos remL = darVideosLocal();
		try
		{
			ListaVideos resp = dtm.getRemoteVideos();
			System.out.println(resp.getVideos().size());
			remL.getVideos().addAll(resp.getVideos());
		}
		catch(NonReplyException e)
		{
			
		}
		return remL;
	}
}
