package project.yer0013.eShop.client.resource;

import project.yer0013.eShop.server.model.Item;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/")
public interface Item_Resource {
    @GET
    @Path("item")
    @Produces(MediaType.APPLICATION_JSON)
    List<Item> getAll();

    @GET
    @Path("item/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Item getOne(@PathParam("id") Long id);

    @POST
    @Path("item")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    Long createOne(Item item);

    @PUT
    @Path("item")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    Boolean updateOne(Item item);

    @PUT
    @Path("item/{id}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    Boolean updateRating(@PathParam("id") Long id, Double rating);

    @DELETE
    @Path("item/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    Boolean deleteOne(@PathParam("id") Long id);
}
