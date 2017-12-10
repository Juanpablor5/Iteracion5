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

import dao.DAODistributed;
import dtm.RotondAndesDistributed;
import jms.NonReplyException;
import vos.ListaProductoI;
import vos.ProductoI;
import vos.Rentabilidad;
import vos.RentabilidadList;

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
	
	public ListaProductoI darProductosLocal(String string) throws Exception {
		ArrayList<ProductoI> videos;
		DAODistributed daoVideos = new DAODistributed();
		try 
		{
			this.conn = darConexion();
			daoVideos.setConn(conn);
			videos = daoVideos.darProductos(string);

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
		return new ListaProductoI(videos);
	}

	/**
	 * Método que modela la transacción que retorna todos los videos de la base de datos.
	 * @return ListaVideos - objeto que modela  un arreglo de videos. este arreglo contiene el resultado de la búsqueda
	 * @throws Exception -  cualquier error que se genere durante la transacción
	 */
	public ListaProductoI darProductos(String string) throws Exception {
		ListaProductoI remL = darProductosLocal(string);
		try
		{
			ListaProductoI resp = dtm.getRemoteProductos(string);
			System.out.println(resp.getProductos().size());
			remL.getProductos().addAll(resp.getProductos());
		}
		catch(NonReplyException e)
		{
			
		}
		return remL;
	}

	public void deleteLocalRestaurante(long parseLong) throws Exception {
		DAODistributed daoRestaurantes = new DAODistributed();
		try 
		{
			this.conn = darConexion();
			daoRestaurantes.setConn(conn);
			daoRestaurantes.deleteRestaurante(parseLong);

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
				daoRestaurantes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}
	
	public void deleteRestaurante(long idRestaurante) throws Exception {
		deleteLocalRestaurante(idRestaurante);
		try
		{
			dtm.deleteRemoteRestaurante(idRestaurante);
			System.out.println("Eliminado :) ===============");
		}
		catch(NonReplyException e)
		{
			
		}
	}

	public RentabilidadList getLocalRentabilidad(String payload) throws Exception {
		ArrayList<Rentabilidad> rentabilidad;
		DAODistributed daoRentabilidad = new DAODistributed();
		try 
		{
			this.conn = darConexion();
			daoRentabilidad.setConn(conn);
			rentabilidad = daoRentabilidad.darRentabilidad(payload);

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
				daoRentabilidad.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new RentabilidadList(rentabilidad);
	}
	
	public RentabilidadList darRentabilidad(String fecha) throws Exception {
		RentabilidadList remL = getLocalRentabilidad(fecha);
		try
		{
			RentabilidadList resp = dtm.getRemoteRentabilidad(fecha);
			System.out.println(resp.getRentabilidad().size());
			remL.getRentabilidad().addAll(resp.getRentabilidad());
		}
		catch(NonReplyException e)
		{
			
		}
		return remL;
	}
}
