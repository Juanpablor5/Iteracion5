package rest;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.RotondAndesException;
import tm.RotondAndesTM;
import vos.Representante;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RepresentanteServices extends BaseServices implements URLS{
	
	public RepresentanteServices(ServletContext context) {
		this.context=context;
	}
	
	@GET
	public Response get(@PathParam(USUARIOID) Long idUser,@PathParam(RESTAURANTEID)Long idRestaurante) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			Representante v = tm.getRepresentante(idUser,idRestaurante);
			return Response.status(200).entity(v).build();
		} catch (RotondAndesException ex) {
			return Response.status(404).entity(doErrorMessage(ex)).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}

	@POST
	public Response add(@PathParam(USUARIOID) Long idUser,@PathParam(RESTAURANTEID)Long idRestaurante,Representante data) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			data = tm.addRepresentante(idUser,idRestaurante,data);
		} catch (RotondAndesException ex) {
			return Response.status(404).entity(doErrorMessage(ex)).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(data).build();
	}

	@PUT
	public Response update(@PathParam(USUARIOID) Long idUser,@PathParam(RESTAURANTEID)Long idRestaurante, Representante data) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			data = tm.updateRepresentante(idUser,idRestaurante,data);
		} catch (RotondAndesException ex) {
			return Response.status(404).entity(doErrorMessage(ex)).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(data).build();
	}

	@DELETE
	public Response delete(@PathParam(USUARIOID) Long idUser,@PathParam(RESTAURANTEID)Long idRestaurante) {
		Representante data;
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			data = tm.deleteRepresentante(idUser,idRestaurante);
		} catch (RotondAndesException ex) {
			return Response.status(404).entity(doErrorMessage(ex)).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(data).build();
	}
}
