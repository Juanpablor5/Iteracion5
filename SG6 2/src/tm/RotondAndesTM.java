package tm;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import vos.*;
import dao.*;

public class RotondAndesTM extends baseTM {
	/**
	 * Metodo constructor de la clase VideoAndesMaster, esta clase modela y contiene
	 * cada una de las Transacciónes y la logica de negocios que estas conllevan.
	 * <b>post: </b> Se crea el objeto VideoAndesTM, se inicializa el path absoluto
	 * del archivo de conexion y se inicializa los atributos que se usan par la
	 * conexion a la base de datos.
	 * 
	 * @param contextPathP
	 *            - path absoluto en el servidor del contexto del deploy actual
	 */
	public RotondAndesTM(String contextPathP) {
		setConectionDataPath(contextPathP + CONNECTION_DATA_FILE_NAME_REMOTE);
		initConnectionData();
	}

	// ------------------------------------------------------------------------------
	// USUARIO
	// ------------------------------------------------------------------------------

	private void isPermiso(Long id, Integer... nivel) throws SQLException, RotondAndesException {
		Usuario usuario = null;
		Boolean ans = false;
		Integer acceso;
		try (DAOUsuario dao = new DAOUsuario(conn)) {
			// ------------------------
			// START
			// ------------------------
			usuario = dao.get(id);
			if (usuario == null)
				throw new RotondAndesException("el ususario no existe");
			acceso = usuario.getPermisos();
			for (Integer i : nivel)
				if (acceso.equals(i))
					ans = true;
			if (ans == false)
				throw new RotondAndesException("no tiene los permisos necesarios");
			conn.commit();
			// ------------------------
			// END
			// ------------------------
		} catch (SQLException e) {
			sqlException(e);
		}
	}

	public Usuario login(String con, String usu) throws SQLException, RotondAndesException {
		Usuario usuario = null;
		updateConnection();
		try (DAOUsuario dao = new DAOUsuario(conn)) {
			// ------------------------
			// START
			// ------------------------
			usuario = dao.get(usu, con);
			if (usuario == null)
				throw new RotondAndesException("usuario o contraseña incorectos");
			conn.commit();
			// ------------------------
			// END
			// ------------------------
		} catch (SQLException e) {
			sqlException(e);
		} finally {
			closeConection();
		}
		return usuario;
	}

	public Usuario creaUsuario(Usuario data) throws SQLException, RotondAndesException {
		updateConnection();
		try (DAOUsuario dao = new DAOUsuario(conn)) {
			// ------------------------
			// START
			// ------------------------
			if (data.getPermisos() == 3 && dao.existAdmin())
				throw new RotondAndesException("ya existe un administrador");
			dao.create(data);
			data = dao.get(data.getNickName(), data.getContrasenia());
			conn.commit();
			// ------------------------
			// END
			// ------------------------
		} catch (SQLException e) {
			sqlException(e);
		} finally {
			closeConection();
		}
		return data;
	}

	public Usuario updateUsuario(Usuario data) throws SQLException, RotondAndesException {
		Usuario usuario = null;
		updateConnection();
		try (DAOUsuario dao = new DAOUsuario(conn)) {
			// ------------------------
			// START
			// ------------------------
			if (data.getPermisos() == 3 && dao.existAdmin())
				throw new RotondAndesException("ya existe un administrador");
			usuario = dao.get(data);
			if (usuario == null)
				throw new RotondAndesException("no existe el usuario buscado");
			dao.update(data);
			conn.commit();
			// ------------------------
			// END
			// ------------------------
		} catch (SQLException e) {
			sqlException(e);
		} finally {
			closeConection();
		}
		return usuario;
	}

	public Usuario deleteUsuario(Long codigo) throws SQLException, RotondAndesException {
		Usuario usuario = null;
		updateConnection();
		try (DAOUsuario dao = new DAOUsuario(conn)) {
			// ------------------------
			// START
			// ------------------------
			usuario = dao.get(codigo);
			if (usuario == null)
				throw new RotondAndesException("no existe el usuario buscado");
			dao.remove(usuario);
			conn.commit();
			// ------------------------
			// END
			// ------------------------
		} catch (SQLException e) {
			sqlException(e);
		} finally {
			closeConection();
		}
		return usuario;
	}

	// ------------------------------------------------------------------------------
	// CLIENTE
	// ------------------------------------------------------------------------------

	public List<Cliente> getAllCliente(Long idUser) throws SQLException, RotondAndesException {
		List<Cliente> data = null;
		updateConnection();
		try (DAOCliente daos = new DAOCliente(conn)) {
			// ------------------------
			// START
			// ------------------------
			isPermiso(idUser, 3);
			data = daos.getAll();
			conn.commit();
			// ------------------------
			// END
			// ------------------------
		} catch (SQLException e) {
			sqlException(e);
		} finally {
			closeConection();
		}
		return data;
	}

	public Cliente getCliente(long idUser, long cedula) throws RotondAndesException, Exception {
		Cliente data = null;
		updateConnection();
		try (DAOCliente daos = new DAOCliente(conn)) {
			// ------------------------
			// START
			// ------------------------
			isPermiso(idUser, 3);
			data = daos.getDetail(cedula);
			if (data == null)
				throw new RotondAndesException("El cliente con la cedula:<" + cedula + ">no existe");
			conn.commit();
			// ------------------------
			// END
			// ------------------------
		} catch (SQLException e) {
			sqlException(e);
		} finally {
			closeConection();
		}
		return data;
	}

	public Cliente createCliente(Long idUser, Cliente data, Long codigo) throws SQLException, RotondAndesException {
		updateConnection();
		try (DAOCliente dao = new DAOCliente(conn); DAOUsuario daoUsuario = new DAOUsuario(conn)) {
			// ------------------------
			// START
			// ------------------------
			isPermiso(idUser, 3);
			Usuario usuario = daoUsuario.get(codigo);
			if (usuario.getPermisos() != 1)
				throw new RotondAndesException("el nivel de permiso de la cuenta no corresponde a un cliente");
			data.setRegistro(usuario);
			dao.create(data);
			conn.commit();
			// ------------------------
			// END
			// ------------------------
		} catch (SQLException e) {
			sqlException(e);
		} finally {
			closeConection();
		}
		return data;
	}

	public Cliente updateCliente(Long idUser, Cliente data) throws SQLException, RotondAndesException {
		Cliente old = null;
		updateConnection();
		try (DAOCliente dao = new DAOCliente(conn)) {
			// ------------------------
			// START
			// ------------------------
			isPermiso(idUser, 3);
			old = dao.get(data);
			if (old == null)
				throw new RotondAndesException("no existe el usuario buscado");
			dao.update(data);
			conn.commit();
			// ------------------------
			// END
			// ------------------------
		} catch (SQLException e) {
			sqlException(e);
		} finally {
			closeConection();
		}
		return old;
	}

	public Cliente deleteCliente(Long idUser, Long cedula) throws SQLException, RotondAndesException {
		Cliente cliente = null;
		updateConnection();
		try (DAOCliente dao = new DAOCliente(conn)) {
			// ------------------------
			// START
			// ------------------------
			isPermiso(idUser, 3);
			cliente = dao.get(cedula);
			if (cliente == null)
				throw new RotondAndesException("no existe el cliente buscado");
			dao.remove(cliente);
			conn.commit();
			// ------------------------
			// END
			// ------------------------
		} catch (SQLException e) {
			sqlException(e);
		} finally {
			closeConection();
		}
		return cliente;
	}

	// ------------------------------------------------------------------------------
	// RESTAURANTE
	// ------------------------------------------------------------------------------

	public List<Restaurante> getAllRestaurante() throws SQLException {
		List<Restaurante> data = null;
		updateConnection();
		try (DAORestaurante daos = new DAORestaurante(conn)) {
			// ------------------------
			// START
			// ------------------------
			data = daos.getAll();
			conn.commit();
			// ------------------------
			// END
			// ------------------------
		} catch (SQLException e) {
			sqlException(e);
		} finally {
			closeConection();
		}
		return data;
	}

	public Restaurante getRestaurante(long id) throws RotondAndesException, Exception {
		Restaurante data = null;
		updateConnection();
		try (DAORestaurante daos = new DAORestaurante(conn)) {
			// ------------------------
			// START
			// ------------------------
			data = daos.getDetail(id);
			if (data == null)
				throw new RotondAndesException("El restaurante con el codigo:<" + id + ">no existe");
			conn.commit();
			// ------------------------
			// END
			// ------------------------
		} catch (SQLException e) {
			sqlException(e);
		} finally {
			closeConection();
		}
		return data;
	}

	public Restaurante createRestaurante(Long idUser, Restaurante data, Long codigo)
			throws SQLException, RotondAndesException {
		updateConnection();
		try (DAORestaurante dao = new DAORestaurante(conn); DAOUsuario daoUsuario = new DAOUsuario(conn)) {
			// ------------------------
			// START
			// ------------------------
			isPermiso(idUser, 3);
			Usuario usuario = daoUsuario.get(codigo);
			if (usuario.getPermisos() != 2)
				throw new RotondAndesException("el nivel de permiso de la cuenta no corresponde a un restaurante");
			data.setRegistro(usuario);
			dao.create(data);
			conn.commit();
			// ------------------------
			// END
			// ------------------------
		} catch (SQLException e) {
			sqlException(e);
		} finally {
			closeConection();
		}
		return data;
	}

	public Restaurante updateRestaurante(Long idUser, Restaurante data) throws SQLException, RotondAndesException {
		Restaurante old = null;
		updateConnection();
		try (DAORestaurante dao = new DAORestaurante(conn)) {
			// ------------------------
			// START
			// ------------------------
			isPermiso(idUser, 3);
			old = dao.get(data);
			if (old == null)
				throw new RotondAndesException("no existe el usuario buscado");
			dao.update(data);
			conn.commit();
			// ------------------------
			// END
			// ------------------------
		} catch (SQLException e) {
			sqlException(e);
		} finally {
			closeConection();
		}
		return old;
	}

	public Restaurante deleteRestaurante(Long idUser, Long cedula) throws SQLException, RotondAndesException {
		Restaurante restaurante = null;
		updateConnection();
		try (DAORestaurante dao = new DAORestaurante(conn)) {
			// ------------------------
			// START
			// ------------------------
			isPermiso(idUser, 3);
			restaurante = dao.get(cedula);
			if (restaurante == null)
				throw new RotondAndesException("no existe el restaurante buscado");
			dao.remove(restaurante);
			conn.commit();
			// ------------------------
			// END
			// ------------------------
		} catch (SQLException e) {
			sqlException(e);
		} finally {
			closeConection();
		}
		return restaurante;
	}

	// ------------------------------------------------------------------------------
	// REPRESENTANTE
	// ------------------------------------------------------------------------------
	public Representante getRepresentante(Long idUser, Long idRestaurante) throws RotondAndesException, SQLException {
		Representante data = null;
		updateConnection();
		try (DAORepresentante daoRepresentante = new DAORepresentante(conn)) {
			// ------------------------
			// START
			// ------------------------
			isPermiso(idUser, 3);
			Restaurante restaurante = new Restaurante();
			restaurante.setId(idRestaurante);
			List<Representante> list = daoRepresentante.getAllSub(restaurante);
			if (list.isEmpty())
				throw new RotondAndesException("no existe un representante asociado al restaurante");
			data = list.get(0);
			conn.commit();
			// ------------------------
			// END
			// ------------------------
		} catch (SQLException e) {
			sqlException(e);
		} finally {
			closeConection();
		}
		return data;
	}

	public Representante addRepresentante(Long idUser, Long idRestaurante, Representante data)
			throws SQLException, RotondAndesException {
		updateConnection();
		try (DAORestaurante daoRestaurante = new DAORestaurante(conn);
				DAORepresentante dao = new DAORepresentante(conn);) {
			// ------------------------
			// START
			// ------------------------
			isPermiso(idUser, 3);
			Restaurante restaurante = daoRestaurante.get(idRestaurante);
			if (restaurante == null)
				throw new RotondAndesException("no existe el restaurante");
			data.setRestaurante(restaurante);
			dao.create(data);
			conn.commit();
			// ------------------------
			// END
			// ------------------------
		} catch (SQLException e) {
			sqlException(e);
		} finally {
			closeConection();
		}
		return data;
	}

	public Representante updateRepresentante(Long idUser, Long idRestaurante, Representante data)
			throws SQLException, RotondAndesException {
		Representante old = null;
		updateConnection();
		try (DAORestaurante daoRestaurante = new DAORestaurante(conn);
				DAORepresentante dao = new DAORepresentante(conn);) {
			// ------------------------
			// START
			// ------------------------
			isPermiso(idUser, 3);
			Restaurante restaurante = new Restaurante();
			restaurante.setId(idRestaurante);
			List<Representante> list = dao.getAllSub(restaurante);
			if (list.isEmpty())
				throw new RotondAndesException("no existe un representante asociado al restaurante");
			old = list.get(0);
			data.setId(old.getId());
			data.setRestaurante(null);
			dao.update(data);
			conn.commit();
			// ------------------------
			// END
			// ------------------------
		} catch (SQLException e) {
			sqlException(e);
		} finally {
			closeConection();
		}
		return old;
	}

	public Representante deleteRepresentante(Long idUser, Long idRestaurante)
			throws RotondAndesException, SQLException {
		Representante old = null;
		updateConnection();
		try (DAORestaurante daoRestaurante = new DAORestaurante(conn);
				DAORepresentante dao = new DAORepresentante(conn);) {
			// ------------------------
			// START
			// ------------------------
			isPermiso(idUser, 3);
			Restaurante restaurante = new Restaurante();
			restaurante.setId(idRestaurante);
			List<Representante> list = dao.getAllSub(restaurante);
			if (list.isEmpty())
				throw new RotondAndesException("no existe un representante asociado al restaurante");
			old = list.get(0);
			dao.remove(old);
			conn.commit();
			// ------------------------
			// END
			// ------------------------
		} catch (SQLException e) {
			sqlException(e);
		} finally {
			closeConection();
		}
		return old;
	}

	// ------------------------------------------------------------------------------
	// TIPOCOMIDA
	// ------------------------------------------------------------------------------

	public List<TipoComida> getAllTipoComida() throws SQLException, RotondAndesException {
		List<TipoComida> data = null;
		updateConnection();
		try (DAOTipoComida daos = new DAOTipoComida(conn)) {
			// ------------------------
			// START
			// ------------------------
			data = daos.getAll();
			conn.commit();
			// ------------------------
			// END
			// ------------------------
		} catch (SQLException e) {
			sqlException(e);
		} finally {
			closeConection();
		}
		return data;
	}

	public TipoComida getTipoComida(String id) throws SQLException, RotondAndesException {
		TipoComida data = null;
		updateConnection();
		try (DAOTipoComida daos = new DAOTipoComida(conn)) {
			// ------------------------
			// START
			// ------------------------
			data = daos.getDetail(id);
			if (data == null)
				throw new RotondAndesException("El cliente con el codigo:<" + id + ">no existe");
			conn.commit();
			// ------------------------
			// END
			// ------------------------
		} catch (SQLException e) {
			sqlException(e);
		} finally {
			closeConection();
		}
		return data;
	}

	public void addTipoComida(Long idUser, TipoComida data) throws SQLException, RotondAndesException {
		updateConnection();
		try (DAOTipoComida dao = new DAOTipoComida(conn)) {
			// ------------------------
			// START
			// ------------------------
			isPermiso(idUser, 3);
			dao.create(data);
			conn.commit();
			// ------------------------
			// END
			// ------------------------
		} catch (SQLException e) {
			sqlException(e);
		} finally {
			closeConection();
		}
	}

	public TipoComida deleteTipoComida(Long idUser, String id) throws SQLException, RotondAndesException {
		TipoComida tipo = null;
		updateConnection();
		try (DAOTipoComida dao = new DAOTipoComida(conn); DAORestaurante daoRestaurante = new DAORestaurante(conn)) {
			// ------------------------
			// START
			// ------------------------
			isPermiso(idUser, 3);
			tipo = dao.get(id);
			if (tipo == null)
				throw new RotondAndesException("no existe el tipo de comida buscado buscado");
			dao.remove(tipo);
			conn.commit();
			// ------------------------
			// END
			// ------------------------
		} catch (SQLException e) {
			sqlException(e);
		} finally {
			closeConection();
		}
		return tipo;
	}

	public void deleteRestauranteTipoComida(Long idUser, Long id) throws SQLException, RotondAndesException {
		Restaurante old = new Restaurante();
		updateConnection();
		try (DAORestaurante dao = new DAORestaurante(conn)) {
			// ------------------------
			// START
			// ------------------------
			isPermiso(idUser, 3);
			old.setId(id);
			old.setTipoComida(new TipoComida());
			dao.update(old);
			conn.commit();
			// ------------------------
			// END
			// ------------------------
		} catch (SQLException e) {
			sqlException(e);
		} finally {
			closeConection();
		}
	}

	public TipoComida setRestauranteTipoComida(Long idUser, Long id, String str)
			throws SQLException, RotondAndesException {
		Restaurante old = new Restaurante();
		TipoComida tipoComida = null;
		updateConnection();
		try (DAORestaurante dao = new DAORestaurante(conn); DAOTipoComida daoTipoComida = new DAOTipoComida(conn)) {
			// ------------------------
			// START
			// ------------------------
			isPermiso(idUser, 3);
			tipoComida = daoTipoComida.get(str);
			if (tipoComida == null)
				throw new RotondAndesException("no existe el usuario buscado");
			old.setId(id);
			old.setTipoComida(tipoComida);
			dao.update(old);
			conn.commit();
			// ------------------------
			// END
			// ------------------------
		} catch (SQLException e) {
			sqlException(e);
		} finally {
			closeConection();
		}
		return tipoComida;
	}

	public List<Ingrediente> getAllIngrediente(Long idUser) throws SQLException, RotondAndesException {
		List<Ingrediente> data = null;
		updateConnection();
		try (DAOIngrediente daos = new DAOIngrediente(conn)) {
			// ------------------------
			// START
			// ------------------------
			isPermiso(idUser, 3, 2);
			data = daos.getAll();
			conn.commit();
			// ------------------------
			// END
			// ------------------------
		} catch (SQLException e) {
			sqlException(e);
		} finally {
			closeConection();
		}
		return data;
	}

	public Ingrediente getIngrediente(Long idUser, Long id) throws RotondAndesException, SQLException {
		Ingrediente data = null;
		updateConnection();
		try (DAOIngrediente daos = new DAOIngrediente(conn)) {
			// ------------------------
			// START
			// ------------------------
			isPermiso(idUser, 3, 2);
			data = daos.getDetail(id);
			if (data == null)
				throw new RotondAndesException("El ingrediente con id:<" + id + ">no existe");
			conn.commit();
			// ------------------------
			// END
			// ------------------------
		} catch (SQLException e) {
			sqlException(e);
		} finally {
			closeConection();
		}
		return data;
	}

	public void addIngrediente(Long idUser, Ingrediente data) throws SQLException, RotondAndesException {
		updateConnection();
		try (DAOIngrediente dao = new DAOIngrediente(conn)) {
			// ------------------------
			// START
			// ------------------------
			isPermiso(idUser, 3, 2);
			dao.create(data);
			conn.commit();
			// ------------------------
			// END
			// ------------------------
		} catch (SQLException e) {
			sqlException(e);
		} finally {
			closeConection();
		}
	}

	public Ingrediente updateIngrediente(Long idUser, Ingrediente data) throws SQLException, RotondAndesException {
		Ingrediente old = null;
		updateConnection();
		try (DAOIngrediente dao = new DAOIngrediente(conn)) {
			// ------------------------
			// START
			// ------------------------
			isPermiso(idUser, 3);
			old = dao.get(data);
			if (old == null)
				throw new RotondAndesException("no existe el Ingrediente buscado");
			dao.update(data);
			conn.commit();
			// ------------------------
			// END
			// ------------------------
		} catch (SQLException e) {
			sqlException(e);
		} finally {
			closeConection();
		}
		return old;
	}

	public Ingrediente deleteIngrediente(Long idUser, Ingrediente data) throws SQLException, RotondAndesException {
		Ingrediente old = null;
		updateConnection();
		try (DAOIngrediente dao = new DAOIngrediente(conn)) {
			// ------------------------
			// START
			// ------------------------
			isPermiso(idUser, 3);
			old = dao.get(data);
			if (old == null)
				throw new RotondAndesException("no existe el ingrediente buscado");
			dao.remove(old);
			conn.commit();
			// ------------------------
			// END
			// ------------------------
		} catch (SQLException e) {
			sqlException(e);
		} finally {
			closeConection();
		}
		return old;
	}

	public void addSimilitudIngrediente(Long idUser, Long id, Long id2) throws RotondAndesException, SQLException {
		updateConnection();
		try (DAOUsuario daoUsuario = new DAOUsuario(conn);
				DAORestaurante daoRestaurante = new DAORestaurante(conn);
				DAOIngredienteSimilitud dao = new DAOIngredienteSimilitud(conn)) {
			// ------------------------
			// START
			// ------------------------
			Usuario user = daoUsuario.get(idUser);
			if (user == null)
				throw new RotondAndesException("el usuario no exite");
			if (user.getPermisos() != 2)
				throw new RotondAndesException("no tiene los permisos necesarios");
			List<Restaurante> rests = daoRestaurante.getAllSub(user);
			if (rests.isEmpty())
				throw new RotondAndesException("no hay un restaurante asociado al usuario");
			IngredienteSimilitud aux = new IngredienteSimilitud();
			aux.setRestaurante(rests.get(0));
			Ingrediente i1 = new Ingrediente();
			i1.setId(id);
			Ingrediente i2 = new Ingrediente();
			i2.setId(id2);
			aux.setIngrediente(i1);
			aux.setIngrediente2(i2);
			dao.create(aux);
			aux.setIngrediente(i2);
			aux.setIngrediente2(i1);
			dao.create(aux);
			conn.commit();
			// ------------------------
			// END
			// ------------------------
		} catch (SQLException e) {
			sqlException(e);
		} finally {
			closeConection();
		}
	}

	public List<Ingrediente> getSimilitudIngrediente(Long idUser, Long id) throws RotondAndesException, SQLException {
		List<Ingrediente> data = null;
		updateConnection();
		try (DAOUsuario daoUsuario = new DAOUsuario(conn);
				DAORestaurante daoRestaurante = new DAORestaurante(conn);
				DAOIngredienteSimilitud dao = new DAOIngredienteSimilitud(conn)) {
			// ------------------------
			// START
			// ------------------------
			Usuario user = daoUsuario.get(idUser);
			if (user == null)
				throw new RotondAndesException("el usuario no exite");
			if (user.getPermisos() != 2)
				throw new RotondAndesException("no tiene los permisos necesarios");
			List<Restaurante> rests = daoRestaurante.getAllSub(user);
			if (rests.isEmpty())
				throw new RotondAndesException("no hay un restaurante asociado al usuario");
			data = dao.getAll(rests.get(0).getId(), id);
			conn.commit();
			// ------------------------
			// END
			// ------------------------
		} catch (SQLException e) {
			sqlException(e);
		} finally {
			closeConection();
		}
		return data;
	}

	public void deleteSimilitudIngrediente(Long idUser, Long id, Long id2) throws RotondAndesException, SQLException {
		updateConnection();
		try (DAOUsuario daoUsuario = new DAOUsuario(conn);
				DAORestaurante daoRestaurante = new DAORestaurante(conn);
				DAOIngredienteSimilitud dao = new DAOIngredienteSimilitud(conn)) {
			// ------------------------
			// START
			// ------------------------
			Usuario user = daoUsuario.get(idUser);
			if (user == null)
				throw new RotondAndesException("el usuario no exite");
			if (user.getPermisos() != 2)
				throw new RotondAndesException("no tiene los permisos necesarios");
			List<Restaurante> rests = daoRestaurante.getAllSub(user);
			if (rests.isEmpty())
				throw new RotondAndesException("no hay un restaurante asociado al usuario");
			dao.delte(rests.get(0).getId(), id, id2);
			dao.delte(rests.get(0).getId(), id2, id);
			// ------------------------
			// END
			// ------------------------
		} catch (SQLException e) {
			sqlException(e);
		} finally {
			closeConection();
		}
	}

	// ------------------------------------------------------------------------------
	// ZONA
	// ------------------------------------------------------------------------------

	public List<Zona> getAllZonas() throws SQLException, RotondAndesException {
		List<Zona> data = null;
		updateConnection();
		try (DAOZona daos = new DAOZona(conn)) {
			// ------------------------
			// START
			// ------------------------
			data = daos.getAll();
			conn.commit();
			// ------------------------
			// END
			// ------------------------
		} catch (SQLException e) {
			sqlException(e);
		} finally {
			closeConection();
		}
		return data;
	}

	public Zona getZona(long idZona) throws RotondAndesException, Exception {
		Zona data = null;
		updateConnection();
		try (DAOZona daos = new DAOZona(conn)) {
			// ------------------------
			// START
			// ------------------------
			data = daos.getDetail(idZona);
			if (data == null)
				throw new RotondAndesException("La zona con el id:<" + idZona + "> no existe");
			conn.commit();
			// ------------------------
			// END
			// ------------------------
		} catch (SQLException e) {
			sqlException(e);
		} finally {
			closeConection();
		}
		return data;
	}

	public void addZona(Long idUser, Zona data) throws SQLException, RotondAndesException {
		updateConnection();
		try (DAOZona dao = new DAOZona(conn)) {
			// ------------------------
			// START
			// ------------------------
			isPermiso(idUser, 3);
			dao.create(data);
			conn.commit();
			// ------------------------
			// END
			// ------------------------
		} catch (SQLException e) {
			sqlException(e);
		} finally {
			closeConection();
		}
	}

	public Zona deleteZona(Long idUser, Long id) throws SQLException, RotondAndesException {
		Zona tipo = null;
		updateConnection();
		try (DAOZona dao = new DAOZona(conn)) {
			// ------------------------
			// START
			// ------------------------
			isPermiso(idUser, 3);
			tipo = dao.get(id);
			if (tipo == null)
				throw new RotondAndesException("No existe la zona buscada");
			dao.remove(tipo);
			conn.commit();
			// ------------------------
			// END
			// ------------------------
		} catch (SQLException e) {
			sqlException(e);
		} finally {
			closeConection();
		}
		return tipo;
	}

	public List<Producto> getAllProducto(Long idUser) throws SQLException, RotondAndesException {
		List<Producto> data = null;
		updateConnection();
		try (DAOProducto daos = new DAOProducto(conn)) {
			// ------------------------
			// START
			// ------------------------
			isPermiso(idUser, 3, 2);
			data = daos.getAll();
			conn.commit();
			// ------------------------
			// END
			// ------------------------
		} catch (SQLException e) {
			sqlException(e);
		} finally {
			closeConection();
		}
		return data;
	}

	public Producto getProducto(Long idUser, Long id) throws RotondAndesException, SQLException {
		Producto data = null;
		updateConnection();
		try (DAOProducto daos = new DAOProducto(conn)) {
			// ------------------------
			// START
			// ------------------------
			isPermiso(idUser, 3, 2);
			data = daos.getDetail(id);
			if (data == null)
				throw new RotondAndesException("El producto con id:<" + id + ">no existe");
			conn.commit();
			// ------------------------
			// END
			// ------------------------
		} catch (SQLException e) {
			sqlException(e);
		} finally {
			closeConection();
		}
		return data;
	}

	public void addProducto(Long idUser, ProductoDetail data) throws SQLException, RotondAndesException {
		updateConnection();
		try (DAOProducto dao = new DAOProducto(conn)) {
			// ------------------------
			// START
			// ------------------------
			isPermiso(idUser, 3, 2);
			try {
				Categoria.valueOf(data.getCategoria());
			} catch (IllegalArgumentException e) {
				throw new RotondAndesException("la categoria no corresponde a una categoria permitida");
			}
			dao.create(data);
			Long id=dao.getByName(data.getNombre()).getId();
			for (Long i : data.getIngredientesId())
				addIngredienteProducto((long)0, id, i, true);
			conn.commit();
			// ------------------------
			// END
			// ------------------------
		} catch (SQLException e) {
			sqlException(e);
		} finally {
			closeConection();
		}
	}

	public Producto updateProducto(Long idUser, Producto data) throws SQLException, RotondAndesException {
		Producto old = null;
		updateConnection();
		try (DAOProducto dao = new DAOProducto(conn)) {
			// ------------------------
			// START
			// ------------------------
			isPermiso(idUser, 3);
			try {
				Categoria.valueOf(data.getCategoria());
			} catch (IllegalArgumentException e) {
				throw new RotondAndesException("la categoria no corresponde a una categoria permitida");
			}
			old = dao.get(data);
			if (old == null)
				throw new RotondAndesException("no existe el Producto buscado");
			dao.update(data);
			conn.commit();
			// ------------------------
			// END
			// ------------------------
		} catch (SQLException e) {
			sqlException(e);
		} finally {
			closeConection();
		}
		return old;
	}

	public Producto deleteProducto(Long idUser, Producto data) throws SQLException, RotondAndesException {
		Producto old = null;
		updateConnection();
		try (DAOProducto dao = new DAOProducto(conn)) {
			// ------------------------
			// START
			// ------------------------
			isPermiso(idUser, 3);
			old = dao.get(data);
			if (old == null)
				throw new RotondAndesException("no existe el producto buscado");
			dao.remove(old);
			conn.commit();
			// ------------------------
			// END
			// ------------------------
		} catch (SQLException e) {
			sqlException(e);
		} finally {
			closeConection();
		}
		return old;
	}

	public void addSimilitudProducto(Long idUser, Long id, Long id2) throws RotondAndesException, SQLException {
		updateConnection();
		try (DAOUsuario daoUsuario = new DAOUsuario(conn);
				DAORestaurante daoRestaurante = new DAORestaurante(conn);
				DAOProductoSimilitud dao = new DAOProductoSimilitud(conn)) {
			// ------------------------
			// START
			// ------------------------
			Usuario user = daoUsuario.get(idUser);
			if (user == null)
				throw new RotondAndesException("el usuario no exite");
			if (user.getPermisos() != 2)
				throw new RotondAndesException("no tiene los permisos necesarios");
			List<Restaurante> rests = daoRestaurante.getAllSub(user);
			if (rests.isEmpty())
				throw new RotondAndesException("no hay un restaurante asociado al usuario");
			ProductoSimilitud aux = new ProductoSimilitud();
			aux.setRestaurante(rests.get(0));
			Producto i1 = new Producto();
			i1.setId(id);
			Producto i2 = new Producto();
			i2.setId(id2);
			aux.setProducto(i1);
			aux.setProducto2(i2);
			dao.create(aux);
			aux.setProducto(i2);
			aux.setProducto2(i1);
			dao.create(aux);
			conn.commit();
			// ------------------------
			// END
			// ------------------------
		} catch (SQLException e) {
			sqlException(e);
		} finally {
			closeConection();
		}
	}

	public List<Producto> getSimilitudProducto(Long idUser, Long id) throws RotondAndesException, SQLException {
		List<Producto> data = null;
		updateConnection();
		try (DAOUsuario daoUsuario = new DAOUsuario(conn);
				DAORestaurante daoRestaurante = new DAORestaurante(conn);
				DAOProductoSimilitud dao = new DAOProductoSimilitud(conn)) {
			// ------------------------
			// START
			// ------------------------
			Usuario user = daoUsuario.get(idUser);
			if (user == null)
				throw new RotondAndesException("el usuario no exite");
			if (user.getPermisos() != 2)
				throw new RotondAndesException("no tiene los permisos necesarios");
			List<Restaurante> rests = daoRestaurante.getAllSub(user);
			if (rests.isEmpty())
				throw new RotondAndesException("no hay un restaurante asociado al usuario");
			data = dao.getAll(rests.get(0).getId(), id);
			conn.commit();
			// ------------------------
			// END
			// ------------------------
		} catch (SQLException e) {
			sqlException(e);
		} finally {
			closeConection();
		}
		return data;
	}

	public void deleteSimilitudProducto(Long idUser, Long id, Long id2) throws RotondAndesException, SQLException {
		updateConnection();
		try (DAOUsuario daoUsuario = new DAOUsuario(conn);
				DAORestaurante daoRestaurante = new DAORestaurante(conn);
				DAOProductoSimilitud dao = new DAOProductoSimilitud(conn)) {
			// ------------------------
			// START
			// ------------------------
			Usuario user = daoUsuario.get(idUser);
			if (user == null)
				throw new RotondAndesException("el usuario no exite");
			if (user.getPermisos() != 2)
				throw new RotondAndesException("no tiene los permisos necesarios");
			List<Restaurante> rests = daoRestaurante.getAllSub(user);
			if (rests.isEmpty())
				throw new RotondAndesException("no hay un restaurante asociado al usuario");
			dao.delte(rests.get(0).getId(), id, id2);
			dao.delte(rests.get(0).getId(), id2, id);
			// ------------------------
			// END
			// ------------------------
		} catch (SQLException e) {
			sqlException(e);
		} finally {
			closeConection();
		}
	}

	// ------------------------------------------------------------------------------
	// ESPACIO
	// ------------------------------------------------------------------------------

	public List<Espacio> getAllEspacios() throws SQLException, RotondAndesException {
		List<Espacio> data = null;
		updateConnection();
		try (DAOEspacio daos = new DAOEspacio(conn)) {
			// ------------------------
			// START
			// ------------------------
			data = daos.getAll();
			conn.commit();
			// ------------------------
			// END
			// ------------------------
		} catch (SQLException e) {
			sqlException(e);
		} finally {
			closeConection();
		}
		return data;
	}

	public Espacio getEspacio(long idEspacio) throws RotondAndesException, Exception {
		Espacio data = null;
		updateConnection();
		try (DAOEspacio daos = new DAOEspacio(conn)) {
			// ------------------------
			// START
			// ------------------------
			data = daos.getDetail(idEspacio);
			if (data == null)
				throw new RotondAndesException("El espacio con el id:<" + idEspacio + "> no existe");
			conn.commit();
			// ------------------------
			// END
			// ------------------------
		} catch (SQLException e) {
			sqlException(e);
		} finally {
			closeConection();
		}
		return data;
	}

	public void addEspacio(Long idUser, Espacio data) throws SQLException, RotondAndesException {
		updateConnection();
		try (DAOEspacio dao = new DAOEspacio(conn)) {
			// ------------------------
			// START
			// ------------------------
			isPermiso(idUser, 3);
			dao.create(data);
			conn.commit();
			// ------------------------
			// END
			// ------------------------
		} catch (SQLException e) {
			sqlException(e);
		} finally {
			closeConection();
		}
	}

	public Espacio deleteEspacio(Long idUser, Long id) throws SQLException, RotondAndesException {
		Espacio tipo = null;
		updateConnection();
		try (DAOEspacio dao = new DAOEspacio(conn)) {
			// ------------------------
			// START
			// ------------------------
			isPermiso(idUser, 3);
			tipo = dao.get(id);
			if (tipo == null)
				throw new RotondAndesException("No existe la zona buscada");
			dao.remove(tipo);
			conn.commit();
			// ------------------------
			// END
			// ------------------------
		} catch (SQLException e) {
			sqlException(e);
		} finally {
			closeConection();
		}
		return tipo;
	}

	public List<Ingrediente> getIngredientesProducto(Long idUser, Long id) throws RotondAndesException, SQLException {
		List<Ingrediente> data = new LinkedList<>();
		updateConnection();
		try (DAOProductoIngrediente dao = new DAOProductoIngrediente(conn)) {
			// ------------------------
			// START
			// ------------------------
			isPermiso(idUser, 3, 2);
			Producto p = new Producto();
			p.setId(id);
			data = dao.getAllFrom(p);
			conn.commit();
			// ------------------------
			// END
			// ------------------------
		} catch (SQLException e) {
			sqlException(e);
		} finally {
			closeConection();
		}
		return data;
	}

	public Ingrediente addIngredienteProducto(Long idUser, Long idProd, Long idIng, Boolean... booleans)
			throws RotondAndesException, SQLException {
		Ingrediente data = null;
		Boolean subTrn = (booleans.length == 0) ? false : booleans[0];
		if (!subTrn)
			updateConnection();
		try (DAOProductoIngrediente dao = new DAOProductoIngrediente(conn);
				DAOIngrediente daoIngrediente = new DAOIngrediente(conn)) {
			// ------------------------
			// START
			// ------------------------
			if (!subTrn) {
				isPermiso(idUser, 3);
				data = daoIngrediente.get(idIng);
				if (data == null)
					throw new RotondAndesException("el ingrediente no existe");
			}else {
				data= new Ingrediente();
				data.setId(idIng);
			}
			Producto p = new Producto();
			p.setId(idProd);
			dao.add(data, p);
			conn.commit();
			// ------------------------
			// END
			// ------------------------
		} catch (SQLException e) {
			sqlException(e);
		} finally {
			if (!subTrn)
				closeConection();
		}
		return data;
	}

	public Ingrediente removeIngredienteProducto(Long idUser, Long idProd, Long idIng)
			throws RotondAndesException, SQLException {
		Ingrediente data = null;
		updateConnection();
		try (DAOProductoIngrediente dao = new DAOProductoIngrediente(conn);
				DAOIngrediente daoIngrediente = new DAOIngrediente(conn)) {
			// ------------------------
			// START
			// ------------------------
			isPermiso(idUser, 3);
			data = daoIngrediente.get(idIng);
			if (data == null)
				throw new RotondAndesException("el ingrediente no existe");
			Producto p = new Producto();
			p.setId(idProd);
			dao.delete(data, p);
			conn.commit();
			// ------------------------
			// END
			// ------------------------
		} catch (SQLException e) {
			sqlException(e);
		} finally {
			closeConection();
		}
		return data;
	}

	public List<Menu> getAllMenu() throws SQLException {
		List<Menu> data = null;
		updateConnection();
		try (DAOMenu daos = new DAOMenu(conn)) {
			// ------------------------
			// START
			// ------------------------
			data = daos.getAll();
			conn.commit();
			// ------------------------
			// END
			// ------------------------
		} catch (SQLException e) {
			sqlException(e);
		} finally {
			closeConection();
		}
		return data;
	}

	public Menu getMenu(long id) throws SQLException, RotondAndesException {
		Menu data = null;
		updateConnection();
		try (DAOMenu daos = new DAOMenu(conn)) {
			// ------------------------
			// START
			// ------------------------
			data = daos.get(id);
			if(data==null)
				throw new RotondAndesException("no hay un menu con el id:<"+id+">");
			conn.commit();
			// ------------------------
			// END
			// ------------------------
		} catch (SQLException e) {
			sqlException(e);
		} finally {
			closeConection();
		}
		return data;
	}
}
