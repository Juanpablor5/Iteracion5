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
import vos.Zona;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ZonaAdminServices extends BaseServices implements URLS {
	
	public ZonaAdminServices(ServletContext context){
		this.context = context;
	}
	
	@GET
	public Response getAll(@PathParam(ZONAID) Long idZona){
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<Zona> data;
		try{
			data = tm.getAllZonas();
		} catch (RotondAndesException ex) {
			return Response.status(404).entity(doErrorMessage(ex)).build();
		}catch(Exception e){
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(data).build();
	}
	
	@GET
	@Path("{" + ZONAID + ": \\d+}")
	public Response get(@PathParam(ZONAID) Long idZona){
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			Zona v = tm.getZona(idZona);
			return Response.status(200).entity(v).build();
		} catch (RotondAndesException ex) {
			return Response.status(404).entity(doErrorMessage(ex)).build();
		}catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
	
	@POST
	public Response add(@PathParam(USUARIOID) Long idUser, Zona data) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addZona(idUser, data);
		} catch (RotondAndesException ex) {
			return Response.status(404).entity(doErrorMessage(ex)).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(data).build();
	}
	
	@DELETE
	@Path("{" + ZONAID + "}")
	public Response delete(@PathParam(USUARIOID) Long idUser, @PathParam(ZONAID) Long id) {
		Zona zona;
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			zona = tm.deleteZona(idUser, id);
		} catch (RotondAndesException ex) {
			return Response.status(404).entity(doErrorMessage(ex)).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(zona).build();
	}
}
