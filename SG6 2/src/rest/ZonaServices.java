package rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.RotondAndesException;
import tm.RotondAndesTM;
import vos.Zona;

@Path(URLS.ZONA)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ZonaServices extends BaseServices implements URLS{
	
	@GET
	public Response getAll() {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<Zona> zona;
		try {
			zona = tm.getAllZonas();
		} catch (RotondAndesException ex) {
			return Response.status(404).entity(doErrorMessage(ex)).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(zona).build();
	}

	@GET
	@Path("{" + ZONAID + "}")
	public Response get(@PathParam(ZONAID) Long id) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			Zona v = tm.getZona(id);
			return Response.status(200).entity(v).build();
		} catch (RotondAndesException ex) {
			return Response.status(404).entity(doErrorMessage(ex)).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}
}
