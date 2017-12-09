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
import vos.Menu;

@Path(URLS.MENU)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MenuServices extends BaseServices implements URLS {

	@GET
	@Path("{" + MENUID + ": \\d+}")
	public Response get(@PathParam(MENUID) long id) {
		Menu v;
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			v = tm.getMenu(id);

		} catch (RotondAndesException e) {
			return Response.status(412).entity(doErrorMessage(e)).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(v).build();
	}

	@GET
	public Response getAll() {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<Menu> espacio;
		try {
			espacio = tm.getAllMenu();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(espacio).build();
	}
}
