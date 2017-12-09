package rest;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import tm.RotondAndesException;
import tm.RotondAndesTM;
import vos.Usuario;

@Path(URLS.USUARIO)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioServices extends BaseServices implements URLS {

	@GET
	@Path("{usu}-{con}")
	public Response get(@PathParam("usu") String usu, @PathParam("con") String con) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		Usuario videos;
		try {
			videos = tm.login(con, usu);
		} catch (RotondAndesException e) {
			return Response.status(412).entity(doErrorMessage(e)).build();
		} catch (SQLException e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(videos).build();
	}

	@POST
	public Response add(Usuario data) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			data = tm.creaUsuario(data);
		} catch (RotondAndesException e) {
			return Response.status(412).entity(doErrorMessage(e)).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(data).build();
	}

	@PUT
	@Path("{" + USUARIOID + ": \\d+}")
	public Response update(Usuario data, @PathParam(USUARIOID) long codigo) {
		data.setCodigo(codigo);
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			data = tm.updateUsuario(data);
		} catch (RotondAndesException ex) {
			return Response.status(404).entity(doErrorMessage(ex)).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(data).build();
	}

	@DELETE
	@Path("{" + USUARIOID + ": \\d+}")
	public Response delete(@PathParam(USUARIOID) long codigo) {
		Usuario data;
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			data = tm.deleteUsuario(codigo);
		} catch (RotondAndesException ex) {
			return Response.status(404).entity(doErrorMessage(ex)).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(data).build();
	}

	@Path("/{" + USUARIOID + ": \\d+}/" + CLIENTE)
	public ClienteServices clienteServices(@PathParam(USUARIOID) Long id) {
		try {
			return new ClienteServices(context);
		} catch (Exception e) {
			throw new WebApplicationException(Response.status(500).entity(doErrorMessage(e)).build());
		}
	}
	
	@Path("{" + USUARIOID + ": \\d+}/" + RESTAURANTE)
	public RestauranteAdminServices restauranteServices(@PathParam(USUARIOID) Long id) {
		try {
			return new RestauranteAdminServices(context);
		} catch (Exception e) {
			throw new WebApplicationException(Response.status(500).entity(doErrorMessage(e)).build());
		}
	}
	
	@Path("{" + USUARIOID + ": \\d+}/" + TIPOCOMIDA)
	public TipoComidaAdminServices TipoComidaServices() {
		try {
			return new TipoComidaAdminServices(context);
		} catch (Exception e) {
			throw new WebApplicationException(Response.status(500).entity(doErrorMessage(e)).build());
		}
	}
	
	@Path("{" + USUARIOID + ": \\d+}/" + INGREDIENTE)
	public IngredienteServices ingredinteServices() {
		try {
			return new IngredienteServices(context);
		} catch (Exception e) {
			throw new WebApplicationException(Response.status(500).entity(doErrorMessage(e)).build());
		}
	}
	
	@Path("{" + USUARIOID + ": \\d+}/" + PRODUCTO)
	public ProductoServices productoServices() {
		try {
			return new ProductoServices(context);
		} catch (Exception e) {
			throw new WebApplicationException(Response.status(500).entity(doErrorMessage(e)).build());
		}
	}
	
	@Path("{" + USUARIOID + ": \\d+}/" + ZONA)
	public ZonaAdminServices zonaServices() {
		try {
			return new ZonaAdminServices(context);
		} catch (Exception e) {
			throw new WebApplicationException(Response.status(500).entity(doErrorMessage(e)).build());
		}
	}

	// @Path(CHANGE+"/{" + REGISTROID + ": \\d+}/" + ZONA)
	// public ZonaModificationServices getZona(@PathParam(REGISTROID) Long id) {
	// RotondAndesTM tm = new RotondAndesTM(getPath());
	// try {
	// if (tm.getRegistro(id).getPermisos() != 3)
	// throw new RotondAndesException("no tiene los permisos necesarios");
	//
	// return new ZonaModificationServices(context);
	// } catch (RotondAndesException ex) {
	// throw new
	// WebApplicationException(Response.status(404).entity(doErrorMessage(ex)).build());
	// } catch (Exception e) {
	// throw new
	// WebApplicationException(Response.status(500).entity(doErrorMessage(e)).build());
	// }
	// }
	//
	//
	// @Path(CHANGE+"/{" + REGISTROID + ": \\d+}/" + RESERVA)
	// public ReservaModificationServices getReservas(@PathParam(REGISTROID) Long
	// id) {
	// RotondAndesTM tm = new RotondAndesTM(getPath());
	// try {
	// if (tm.getRegistro(id).getPermisos() != 3)
	// throw new RotondAndesException("No tiene los permisos necesarios");
	//
	// return new ReservaModificationServices(context);
	// } catch (RotondAndesException ex) {
	// throw new
	// WebApplicationException(Response.status(404).entity(doErrorMessage(ex)).build());
	// } catch (Exception e) {
	// throw new
	// WebApplicationException(Response.status(500).entity(doErrorMessage(e)).build());
	// }
	// }
	//
	// @Path(CHANGE+"/{" + REGISTROID + ": \\d+}/" + ESPACIO)
	// public EspacioModificationServices getEspacio(@PathParam(REGISTROID) Long id)
	// {
	// RotondAndesTM tm = new RotondAndesTM(getPath());
	// try {
	// if (tm.getRegistro(id).getPermisos() != 3)
	// throw new RotondAndesException("No tiene los permisos necesarios");
	//
	// return new EspacioModificationServices(context);
	// } catch (RotondAndesException ex) {
	// throw new
	// WebApplicationException(Response.status(404).entity(doErrorMessage(ex)).build());
	// } catch (Exception e) {
	// throw new
	// WebApplicationException(Response.status(500).entity(doErrorMessage(e)).build());
	// }
	// }
	//
	// @Path(CHANGE+"/{" + REGISTROID + ": \\d+}/" + REPRESENTANTE)
	// public RepresentateModificationServices
	// getRepresentante(@PathParam(REGISTROID) Long id) {
	// RotondAndesTM tm = new RotondAndesTM(getPath());
	// try {
	// if (tm.getRegistro(id).getPermisos() != 3)
	// throw new RotondAndesException("No tiene los permisos necesarios");
	//
	// return new RepresentateModificationServices(context);
	// } catch (RotondAndesException ex) {
	// throw new
	// WebApplicationException(Response.status(404).entity(doErrorMessage(ex)).build());
	// } catch (Exception e) {
	// throw new
	// WebApplicationException(Response.status(500).entity(doErrorMessage(e)).build());
	// }
	// }
	//
	// @Path(CHANGE+"/{" + REGISTROID + ": \\d+}/" + INGREDIENTE)
	// public IngredienteModificationServices getIngrediente(@PathParam(REGISTROID)
	// Long id) {
	// RotondAndesTM tm = new RotondAndesTM(getPath());
	// try {
	// if (tm.getRegistro(id).getPermisos() != 3)
	// throw new RotondAndesException("No tiene los permisos necesarios");
	//
	// return new IngredienteModificationServices(context);
	// } catch (RotondAndesException ex) {
	// throw new
	// WebApplicationException(Response.status(404).entity(doErrorMessage(ex)).build());
	// } catch (Exception e) {
	// throw new
	// WebApplicationException(Response.status(500).entity(doErrorMessage(e)).build());
	// }
	// }
	//
	// @Path("{" + REGISTROID + ": \\d+}/" + INGREDIENTE)
	// public IngredienteCreatorServices
	// getIngredienteCliente(@PathParam(REGISTROID) Long id) {
	// RotondAndesTM tm = new RotondAndesTM(getPath());
	// try {
	// if (tm.getRegistro(id).getPermisos() != 2)
	// throw new RotondAndesException("No tiene los permisos necesarios");
	//
	// return new IngredienteCreatorServices(context);
	// } catch (RotondAndesException ex) {
	// throw new
	// WebApplicationException(Response.status(404).entity(doErrorMessage(ex)).build());
	// } catch (Exception e) {
	// throw new
	// WebApplicationException(Response.status(500).entity(doErrorMessage(e)).build());
	// }
	// }
	//
	// @Path(CHANGE+"/{" + REGISTROID + ": \\d+}/" + PRODUCTO)
	// public ProductoModificationServices getProducto(@PathParam(REGISTROID) Long
	// id) {
	// System.out.println("asdas3");
	// RotondAndesTM tm = new RotondAndesTM(getPath());
	// try {
	// if (tm.getRegistro(id).getPermisos() != 3)
	// throw new RotondAndesException("No tiene los permisos necesarios");
	//
	// return new ProductoModificationServices(context);
	// } catch (RotondAndesException ex) {
	// throw new
	// WebApplicationException(Response.status(404).entity(doErrorMessage(ex)).build());
	// } catch (Exception e) {
	// throw new
	// WebApplicationException(Response.status(500).entity(doErrorMessage(e)).build());
	// }
	// }
	//
	// @Path("{" + REGISTROID + ": \\d+}/" + PRODUCTO)
	// public ProductoCreatorServices getProductoCliente(@PathParam(REGISTROID) Long
	// id) {
	// RotondAndesTM tm = new RotondAndesTM(getPath());
	// try {
	// if (tm.getRegistro(id).getPermisos() != 2)
	// throw new RotondAndesException("No tiene los permisos necesarios");
	//
	// return new ProductoCreatorServices(context);
	// } catch (RotondAndesException ex) {
	// throw new
	// WebApplicationException(Response.status(404).entity(doErrorMessage(ex)).build());
	// } catch (Exception e) {
	// throw new
	// WebApplicationException(Response.status(500).entity(doErrorMessage(e)).build());
	// }
	// }
	//
	// @Path("{" + REGISTROID + ": \\d+}/" + MENU)
	// public MenuModificationServices getMenu(@PathParam(REGISTROID) Long id) {
	// RotondAndesTM tm = new RotondAndesTM(getPath());
	// try {
	// if (tm.getRegistro(id).getPermisos() != 2)
	// throw new RotondAndesException("No tiene los permisos necesarios");
	//
	// return new MenuModificationServices(context);
	// } catch (RotondAndesException ex) {
	// throw new
	// WebApplicationException(Response.status(404).entity(doErrorMessage(ex)).build());
	// } catch (Exception e) {
	// throw new
	// WebApplicationException(Response.status(500).entity(doErrorMessage(e)).build());
	// }
	// }
	//
	// @Path(CHANGE+"/{" + REGISTROID + ": \\d+}/" + PEDIDO)
	// public PedidoModificationServices getPedido(@PathParam(REGISTROID) Long id) {
	// RotondAndesTM tm = new RotondAndesTM(getPath());
	// try {
	// if (tm.getRegistro(id).getPermisos() != 3)
	// throw new RotondAndesException("No tiene los permisos necesarios");
	//
	// return new PedidoModificationServices(context);
	// } catch (RotondAndesException ex) {
	// throw new
	// WebApplicationException(Response.status(404).entity(doErrorMessage(ex)).build());
	// } catch (Exception e) {
	// throw new
	// WebApplicationException(Response.status(500).entity(doErrorMessage(e)).build());
	// }
	// }
	//
	// @Path("{" + REGISTROID + ": \\d+}/" + PEDIDO)
	// public PedidoCreatorServices getPedidoCliente(@PathParam(REGISTROID) Long id)
	// {
	// RotondAndesTM tm = new RotondAndesTM(getPath());
	// try {
	// if (tm.getRegistro(id).getPermisos() != 1)
	// throw new RotondAndesException("No tiene los permisos necesarios");
	//
	// return new PedidoCreatorServices(context);
	// } catch (RotondAndesException ex) {
	// throw new
	// WebApplicationException(Response.status(404).entity(doErrorMessage(ex)).build());
	// } catch (Exception e) {
	// throw new
	// WebApplicationException(Response.status(500).entity(doErrorMessage(e)).build());
	// }
	// }
	//
	//
	// public void integridad(Registro data) throws RotondAndesException {
	// if (data.getCodigo() == null)
	// throw new RotondAndesException("el codigo no puede ser null");
	// if (data.getUsuario() == null)
	// throw new RotondAndesException("el usuario no puede ser null");
	// if (data.getContrasenia() == null)
	// throw new RotondAndesException("la contrasenia no piede ser null");
	// if (data.getPermisos() == null)
	// throw new RotondAndesException("el permiso no puede se null");
	// if (data.getUsuario().equals(""))
	// throw new RotondAndesException("no puede agregar un usuario vacio");
	// if (data.getUsuario().contains(" "))
	// throw new RotondAndesException("un usuario no puede tener espacios");
	// if (data.getUsuario().length() > 100)
	// throw new RotondAndesException("la cadena ususrio supera el limite permitido
	// de caracteres");
	// if (data.getContrasenia().equals(""))
	// throw new RotondAndesException("no puede agregar un usuario vacio");
	// if (data.getContrasenia().length() > 100)
	// throw new RotondAndesException("la cadena ususrio supera el limite permitido
	// de caracteres");
	// if (data.getPermisos() <= 0 || data.getPermisos() > 3)
	// throw new RotondAndesException("el permiso no es valido");
	// }
}
