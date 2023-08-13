package project.yer0013.eShop.client.resource;

import project.yer0013.eShop.server.model.OrderCart;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

public interface Order_Resource {
    @GET
    @Path("order")
    @Produces(MediaType.APPLICATION_JSON)
    List<OrderCart> getAll();

    @GET
    @Path("order/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    OrderCart getOne(@PathParam("id") Long id);

    @POST
    @Path("order")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    Long createOne(OrderCart orderCart);

    @PUT
    @Path("order")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    Boolean updateOne(OrderCart orderCart);

    @PUT
    @Path("order/buy")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    Boolean buy(OrderCart orderCart);

    @PUT
    @Path("order/send")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    Boolean send(OrderCart orderCart);

    @DELETE
    @Path("order/{id}")
    @Transactional
    @Produces(MediaType.TEXT_PLAIN)
    Boolean deleteOne(@PathParam("id") Long id);
}
