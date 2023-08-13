package project.yer0013.eShop.client.resource;

import project.yer0013.eShop.server.model.Category;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/")
public interface Category_Resource {
    @GET
    @Path("category")
    @Produces(MediaType.APPLICATION_JSON)
    List<Category> getAll();

    @GET
    @Path("category/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Category getOne(@PathParam("id") Long id);

    @POST
    @Path("category")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    Long createOne(Category category);

    @PUT
    @Path("category")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    Boolean updateOne(Category category);

    @DELETE
    @Path("category/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    Boolean deleteOne(@PathParam("id") Long id);
}
