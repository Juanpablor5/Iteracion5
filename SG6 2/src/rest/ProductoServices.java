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
import vos.Producto;
import vos.ProductoDetail;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProductoServices extends BaseServices implements URLS {

	public ProductoServices(ServletContext context) {
		this.context = context;
	}

	@GET
	public Response getAll(@PathParam(USUARIOID) Long idUser) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<Producto> zona;
		try {
			zona = tm.getAllProducto(idUser);
		} catch (RotondAndesException ex) {
			return Response.status(404).entity(doErrorMessage(ex)).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(zona).build();
	}

	@GET
	@Path("{" + PRODUCTOID + ": \\d+}")
	public Response get(@PathParam(USUARIOID) Long idUser, @PathParam(PRODUCTOID) Long id) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			Producto v = tm.getProducto(idUser, id);
			return Response.status(200).entity(v).build();
		} catch (RotondAndesException ex) {
			return Response.status(404).entity(doErrorMessage(ex)).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
	}

	@POST
	public Response add(@PathParam(USUARIOID) Long idUser,ProductoDetail data) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addProducto(idUser,data);
		} catch (RotondAndesException ex) {
			return Response.status(404).entity(doErrorMessage(ex)).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(data).build();
	}

	@PUT
	@Path("{" + PRODUCTOID + ": \\d+}")
	public Response update(@PathParam(USUARIOID) Long idUser,Producto data, @PathParam(PRODUCTOID) Long id) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		data.setId(id);
		try {
			data = tm.updateProducto(idUser,data);
		} catch (RotondAndesException ex) {
			return Response.status(404).entity(doErrorMessage(ex)).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(data).build();
	}

	@DELETE
	@Path("{" + PRODUCTOID + ": \\d+}")
	public Response delete(@PathParam(USUARIOID) Long idUser, @PathParam(PRODUCTOID) Long id) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		Producto data=new Producto();
		data.setId(id);
		try {
			data = tm.deleteProducto(idUser,data);
		} catch (RotondAndesException ex) {
			return Response.status(404).entity(doErrorMessage(ex)).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(data).build();
	}
	
	@GET
	@Path("{" + PRODUCTOID + ": \\d+}/SIMILITUD")
	public Response getSimilitud(@PathParam(USUARIOID) Long idUser, @PathParam(PRODUCTOID) Long id) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<Producto> data;
		try {
			data=tm.getSimilitudProducto(idUser,id);
		} catch (RotondAndesException ex) {
			return Response.status(404).entity(doErrorMessage(ex)).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(data).build();
	}
	
	@POST
	@Path("{" + PRODUCTOID + ": \\d+}/SIMILITUD/{"+PRODUCTOID+"2: \\d+}")
	public Response addSimilitud(@PathParam(USUARIOID) Long idUser, @PathParam(PRODUCTOID) Long id,@PathParam(PRODUCTOID+"2") Long id2) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addSimilitudProducto(idUser,id,id2);
		} catch (RotondAndesException ex) {
			return Response.status(404).entity(doErrorMessage(ex)).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(204).build();
	}
	
	@DELETE
	@Path("{" + PRODUCTOID + ": \\d+}/SIMILITUD/{"+PRODUCTOID+"2: \\d+}")
	public Response deleteSimilitud(@PathParam(USUARIOID) Long idUser, @PathParam(PRODUCTOID) Long id,@PathParam(PRODUCTOID+"2") Long id2) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.deleteSimilitudProducto(idUser,id,id2);
		} catch (RotondAndesException ex) {
			return Response.status(404).entity(doErrorMessage(ex)).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(204).build();
	}
	
	@GET
	@Path("{" + PRODUCTOID + ": \\d+}/"+INGREDIENTE)
	public Response getIngredintes(@PathParam(USUARIOID) Long idUser, @PathParam(PRODUCTOID) Long id) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<Ingrediente> data;
		try {
			data = tm.getIngredientesProducto(idUser,id);
		} catch (RotondAndesException ex) {
			return Response.status(404).entity(doErrorMessage(ex)).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(data).build();
	}
	
	@POST
	@Path("{" + PRODUCTOID + ": \\d+}/"+INGREDIENTE+"/{"+INGREDIENTEID+": \\d+}")
	public Response addIngredinte(@PathParam(USUARIOID) Long idUser, @PathParam(PRODUCTOID) Long idProd,@PathParam(INGREDIENTEID)Long idIng) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		Ingrediente data;
		try {
			data=tm.addIngredienteProducto(idUser,idProd,idIng);
		} catch (RotondAndesException ex) {
			return Response.status(404).entity(doErrorMessage(ex)).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(data).build();
	}
	
	@DELETE
	@Path("{" + PRODUCTOID + ": \\d+}/"+INGREDIENTE+"/{"+INGREDIENTEID+": \\d+}")
	public Response removeIngredinte(@PathParam(USUARIOID) Long idUser, @PathParam(PRODUCTOID) Long idProd,@PathParam(INGREDIENTEID)Long idIng) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		Ingrediente data;
		try {
			data=tm.removeIngredienteProducto(idUser,idProd,idIng);
		} catch (RotondAndesException ex) {
			return Response.status(404).entity(doErrorMessage(ex)).build();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(data).build();
	}
}
