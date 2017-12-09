package rest;

import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.RotondAndesException;
import tm.RotondAndesTM;
import vos.Restaurante;
import vos.TipoComida;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RestauranteAdminServices extends BaseServices implements URLS {

	public RestauranteAdminServices(ServletContext context) {
		this.context = context;
	}

	@GET
	public Response getAll() {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<Restaurante> data;
		try {
			data = tm.getAllRestaurante();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(data).build();
	}

	@GET
	@Path("{" + RESTAURANTEID + ": \\d+}")
	public Response get(@PathParam(RESTAURANTEID) long id) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			Restaurante v = tm.getRestaurante(id);
			return Response.status(200).entity(v).build();
		} catch (RotondAndesException ex) {
			return Response.status(404).entity(doErrorMessage(ex)).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}

	@POST
	@Path("{" + USUARIOID + "}")
	public Response add(@PathParam(USUARIOID) Long idUser,@PathParam(USUARIOID) Long codigo, Restaurante data) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.createRestaurante(idUser, data, codigo);
		} catch (RotondAndesException ex) {
			return Response.status(404).entity(doErrorMessage(ex)).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(data).build();
	}

	@PUT
	@Path("{" + RESTAURANTEID + "}")
	public Response update(@PathParam(USUARIOID) Long idUser,@PathParam(RESTAURANTEID) Long id, Restaurante data) {
		data.setId(id);
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			data = tm.updateRestaurante(idUser,data);
		} catch (RotondAndesException ex) {
			return Response.status(404).entity(doErrorMessage(ex)).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(data).build();
	}

	@DELETE
	@Path("{" + RESTAURANTEID + "}")
	public Response delete(@PathParam(USUARIOID) Long idUser,@PathParam(RESTAURANTEID) Long id) {
		Restaurante restaurante;
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			restaurante = tm.deleteRestaurante(idUser,id);
		} catch (RotondAndesException ex) {
			return Response.status(404).entity(doErrorMessage(ex)).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(restaurante).build();
	}

	@Path("{" + RESTAURANTEID + ": \\d+}/" + REPRESENTANTE)
	public RepresentanteServices representateServices() {
		return new RepresentanteServices(context);
	}

	@DELETE
	@Path("{" + RESTAURANTEID + ": \\d+}/" + TIPOCOMIDA)
	public Response deleteTipoComida(@PathParam(USUARIOID) Long idUser,@PathParam(RESTAURANTEID) Long id) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.deleteRestauranteTipoComida(idUser,id);
		} catch (RotondAndesException ex) {
			return Response.status(404).entity(doErrorMessage(ex)).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(204).build();
	}
	
	@PUT
	@Path("{" + RESTAURANTEID + ": \\d+}/" + TIPOCOMIDA+"/{"+TIPOCOMIDAID+"}")
	public Response deleteTipoComida(@PathParam(USUARIOID) Long idUser,@PathParam(RESTAURANTEID) Long id,@PathParam(TIPOCOMIDAID) String tipoComida) {
		TipoComida ans;
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			ans = tm.setRestauranteTipoComida(idUser,id,tipoComida);
		} catch (RotondAndesException ex) {
			return Response.status(404).entity(doErrorMessage(ex)).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(ans).build();
	}
}
