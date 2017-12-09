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
import vos.Espacio;

@Path(URLS.ESPACIO)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EspacioServices extends BaseServices implements URLS{

	@GET
	public Response getAll() {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<Espacio> espacio;
		try {
			espacio = tm.getAllEspacios();
		} catch (RotondAndesException ex) {
			return Response.status(404).entity(doErrorMessage(ex)).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(espacio).build();
	}

	@GET
	@Path("{" + ZONAID + ": \\d+}/" + ESPACIOID)
	public Response get(@PathParam(ESPACIOID) Long id) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			Espacio v = tm.getEspacio(id);
			return Response.status(200).entity(v).build();
		} catch (RotondAndesException ex) {
			return Response.status(404).entity(doErrorMessage(ex)).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}	
}
