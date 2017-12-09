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
import vos.Ingrediente;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class IngredienteServices extends BaseServices implements URLS {

	public IngredienteServices(ServletContext context) {
		this.context = context;
	}

	@GET
	public Response getAll(@PathParam(USUARIOID) Long idUser) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<Ingrediente> zona;
		try {
			zona = tm.getAllIngrediente(idUser);
		} catch (RotondAndesException ex) {
			return Response.status(404).entity(doErrorMessage(ex)).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(zona).build();
	}

	@GET
	@Path("{" + INGREDIENTEID + ": \\d+}")
	public Response get(@PathParam(USUARIOID) Long idUser, @PathParam(INGREDIENTEID) Long id) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			Ingrediente v = tm.getIngrediente(idUser, id);
			return Response.status(200).entity(v).build();
		} catch (RotondAndesException ex) {
			return Response.status(404).entity(doErrorMessage(ex)).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}

	@POST
	public Response add(@PathParam(USUARIOID) Long idUser,Ingrediente data) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addIngrediente(idUser,data);
		} catch (RotondAndesException ex) {
			return Response.status(404).entity(doErrorMessage(ex)).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(data).build();
	}

	@PUT
	@Path("{" + INGREDIENTEID + ": \\d+}")
	public Response update(@PathParam(USUARIOID) Long idUser,Ingrediente data, @PathParam(INGREDIENTEID) Long id) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		data.setId(id);
		try {
			data = tm.updateIngrediente(idUser,data);
		} catch (RotondAndesException ex) {
			return Response.status(404).entity(doErrorMessage(ex)).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(data).build();
	}

	@DELETE
	@Path("{" + INGREDIENTEID + ": \\d+}")
	public Response delete(@PathParam(USUARIOID) Long idUser, @PathParam(INGREDIENTEID) Long id) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		Ingrediente data=new Ingrediente();
		data.setId(id);
		try {
			data = tm.deleteIngrediente(idUser,data);
		} catch (RotondAndesException ex) {
			return Response.status(404).entity(doErrorMessage(ex)).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(data).build();
	}
	
	@GET
	@Path("{" + INGREDIENTEID + ": \\d+}/SIMILITUD")
	public Response getSimilitud(@PathParam(USUARIOID) Long idUser, @PathParam(INGREDIENTEID) Long id) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<Ingrediente> data;
		try {
			data=tm.getSimilitudIngrediente(idUser,id);
		} catch (RotondAndesException ex) {
			return Response.status(404).entity(doErrorMessage(ex)).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(data).build();
	}
	
	@POST
	@Path("{" + INGREDIENTEID + ": \\d+}/SIMILITUD/{"+INGREDIENTEID+"2: \\d+}")
	public Response addSimilitud(@PathParam(USUARIOID) Long idUser, @PathParam(INGREDIENTEID) Long id,@PathParam(INGREDIENTEID+"2") Long id2) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addSimilitudIngrediente(idUser,id,id2);
		} catch (RotondAndesException ex) {
			return Response.status(404).entity(doErrorMessage(ex)).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(204).build();
	}
	
	@DELETE
	@Path("{" + INGREDIENTEID + ": \\d+}/SIMILITUD/{"+INGREDIENTEID+"2: \\d+}")
	public Response deleteSimilitud(@PathParam(USUARIOID) Long idUser, @PathParam(INGREDIENTEID) Long id,@PathParam(INGREDIENTEID+"2") Long id2) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.deleteSimilitudIngrediente(idUser,id,id2);
		} catch (RotondAndesException ex) {
			return Response.status(404).entity(doErrorMessage(ex)).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(204).build();
	}
}
