package tm;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class baseTM {
	/**
	 * Atributo estatico que contiene el path relativo del archivo que tiene los
	 * datos de la conexion
	 */
	protected static final String CONNECTION_DATA_FILE_NAME_REMOTE = "/conexion.properties";

	/**
	 * Atributo estatico que contiene el path absoluto del archivo que tiene los
	 * datos de la conexion
	 */
	private String connectionDataPath;

	/**
	 * Atributo que guarda el usuario que se va a usar para conectarse a la base de
	 * datos.
	 */
	private String user;

	/**
	 * Atributo que guarda la clave que se va a usar para conectarse a la base de
	 * datos.
	 */
	private String password;

	/**
	 * Atributo que guarda el URL que se va a usar para conectarse a la base de
	 * datos.
	 */
	private String url;

	/**
	 * Atributo que guarda el driver que se va a usar para conectarse a la base de
	 * datos.
	 */
	private String driver;

	/**
	 * conexion a la base de datos
	 */
	protected Connection conn;
	
	/**
	 * Metodo que inicializa los atributos que se usan para la conexion a la base de
	 * datos. <b>post: </b> Se han inicializado los atributos que se usan par la
	 * conexion a la base de datos.
	 */
	protected void initConnectionData() {
		try {
			File arch = new File(this.connectionDataPath);
			Properties prop = new Properties();
			FileInputStream in = new FileInputStream(arch);
			prop.load(in);
			in.close();
			this.url = prop.getProperty("url");
			this.user = prop.getProperty("usuario");
			this.password = prop.getProperty("clave");
			this.driver = prop.getProperty("driver");
			Class.forName(driver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void updateConnection() throws SQLException {
		System.out.println("-----------------------------------------------------------------");
		System.out.println("---ABRIENDO CONEXION(" + url + " | user: " + user + ")---");
		try {
			this.conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			sqlException(e);
		}
	}
	
	/**
	 * Metodo que retorna la conexion a la base de datos
	 * 
	 * @return Connection - la conexion a la base de datos
	 * @throws SQLException
	 *             - Cualquier error que se genere durante la conexion a la base de
	 *             datos
	 */
	protected Connection darConexion() throws SQLException {
		return DriverManager.getConnection(url, user, password);
	}

	protected void sqlException(SQLException e) throws SQLException {
		conn.rollback();
		System.err.println("SQLException:" + e.getMessage());
		e.printStackTrace();
		throw e;
	}

	protected void rotondAndesException(String str) throws RotondAndesException, SQLException {
		try {
			conn.rollback();
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		}
		System.err.println("RotondAndesException:" + str);
		throw new RotondAndesException(str);
	}

	protected void closeConection() throws SQLException {
		System.out.println("---CERRANDO CONEXION---");
		System.out.println("-----------------------------------------------------------------");
		try {
			if (this.conn != null)
				this.conn.close();
		} catch (SQLException exception) {
			System.err.println("SQLException closing resources:" + exception.getMessage());
			exception.printStackTrace();
			throw exception;
		}
	}
	
	protected void setConectionDataPath(String n) {
		connectionDataPath=n;
	}
}
