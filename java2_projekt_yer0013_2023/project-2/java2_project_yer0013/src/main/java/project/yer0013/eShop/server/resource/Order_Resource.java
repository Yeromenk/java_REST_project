package project.yer0013.eShop.server.resource;

import lombok.extern.log4j.Log4j2;
import project.yer0013.eShop.server.model.OrderCart;
import project.yer0013.eShop.server.model.OrderStatus;
import project.yer0013.eShop.server.repository.Order_Repository;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.List;

@Path("/")
@Log4j2
public class Order_Resource {
    @Inject
    Order_Repository orderRepository;

    @GET
    @Path("order")
    @Produces(MediaType.APPLICATION_JSON)
    public List<OrderCart> getAll() {
        return orderRepository.listAll();
    }

    @GET
    @Path("order/{id}") // TODO REST
    @Produces(MediaType.APPLICATION_JSON)
    public OrderCart getOne(@PathParam("id") Long id) {
        return orderRepository.findById(id);
    }

    @POST
    @Path("order")
    @Transactional
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Long createOne(OrderCart orderCart) {
        log.info("Create order: {}", orderCart);
        orderCart.setStatus(OrderStatus.DEFAULT.getValue());

        orderRepository.getEntityManager().persist(orderCart);

        return orderCart.getId();
    }

    @PUT
    @Path("order")
    @Transactional
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Boolean updateOne(OrderCart orderCart) {
        OrderCart tmp = orderRepository.findById(orderCart.getId());

        if (tmp == null) return false;

        tmp.setStatus(orderCart.getStatus());
        tmp.setItem(orderCart.getItem());
        tmp.setCompletionDate(orderCart.getCompletionDate());

        orderRepository.getEntityManager().merge(tmp);
        return true;
    }

    @DELETE
    @Path("order/{id}")
    @Transactional
    @Produces(MediaType.TEXT_PLAIN)
    public Boolean deleteOne(@PathParam("id") Long id) {
        return orderRepository.deleteById(id);
    }

    @PUT
    @Path("order/buy")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Boolean buy(OrderCart orderCart) {
        OrderCart tmp = orderRepository.findById(orderCart.getId());

        if (tmp == null) return false;

        tmp.setStatus(OrderStatus.BOUGHT.getValue());

        orderRepository.getEntityManager().merge(tmp);
        return true;
    }

    @PUT
    @Path("order/send")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Boolean send(OrderCart orderCart) {
        OrderCart tmp = orderRepository.findById(orderCart.getId());

        if (tmp == null) return false;

        tmp.setStatus(OrderStatus.SENT.getValue());
        tmp.setCompletionDate(new Date());

        orderRepository.getEntityManager().merge(tmp);
        return true;
    }
}
