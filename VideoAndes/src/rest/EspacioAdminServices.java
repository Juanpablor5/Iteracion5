package rest;

import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.RotondAndesException;
import tm.RotondAndesTM;
import vos.Espacio;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EspacioAdminServices extends BaseServices implements URLS {
	
	public EspacioAdminServices(ServletContext context){
		this.context = context;
	}
	
	@GET
	public Response getAll(@PathParam(ESPACIOID) Long idZona){
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<Espacio> data;
		try{
			data = tm.getAllEspacios();
		} catch (RotondAndesException ex) {
			return Response.status(404).entity(doErrorMessage(ex)).build();
		}catch(Exception e){
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(data).build();
	}
	
	@GET
	@Path("{" + ZONAID + ": \\d+}/" + ESPACIOID)
	public Response get(@PathParam(ESPACIOID) Long idZona){
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			Espacio v = tm.getEspacio(idZona);
			return Response.status(200).entity(v).build();
		} catch (RotondAndesException ex) {
			return Response.status(404).entity(doErrorMessage(ex)).build();
		}catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
	
	@POST
	public Response add(@PathParam(USUARIOID) Long idUser, Espacio data) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addEspacio(idUser, data);
		} catch (RotondAndesException ex) {
			return Response.status(404).entity(doErrorMessage(ex)).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(data).build();
	}
	
	@DELETE
	@Path("{" + ZONAID + "}/"+ESPACIOID)
	public Response delete(@PathParam(USUARIOID) Long idUser, @PathParam(ESPACIOID) Long id) {
		Espacio espacio;
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			espacio = tm.deleteEspacio(idUser, id);
		} catch (RotondAndesException ex) {
			return Response.status(404).entity(doErrorMessage(ex)).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(espacio).build();
	}
}
