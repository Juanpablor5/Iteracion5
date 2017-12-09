package em;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

abstract class Connector implements AutoCloseable {
	protected ArrayList<Object> recursos;
	protected Connection conn;
	private boolean closed;

	public Connector() {
		recursos = new ArrayList<Object>();
		closed = false;
	}

	public final void close() {
		if (!closed) {
			for (Object ob : recursos)
				if (ob instanceof PreparedStatement)
					try {
						((PreparedStatement) ob).close();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
			closed = true;
		}
	}

	public void setConn(Connection con) {
		this.conn = con;
	}

	protected ResultSet executeModification(String sql) throws SQLException {
		try {
			System.out.println(sql);
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			return prepStmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}
}
