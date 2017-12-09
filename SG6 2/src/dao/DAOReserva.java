package dao;

import java.sql.Connection;
import em.GenericDao;
import vos.Reserva;

public class DAOReserva extends GenericDao<Reserva>{

	public DAOReserva( Connection conn) {
		super(Reserva.class, conn);
	}

}
