package project.yer0013.eShop.server.resource;

import lombok.extern.log4j.Log4j2;
import project.yer0013.eShop.server.model.Item;
import project.yer0013.eShop.server.repository.Item_Repository;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/")
@Log4j2
public class Item_Resource {
    @Inject
    Item_Repository itemRepository;

    @GET
    @Path("item")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Item> getAll() {
        return itemRepository.listAll();
    }

    @GET
    @Path("item/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Item getOne(@PathParam("id") Long id) {
        return itemRepository.findById(id);
    }

    @POST
    @Path("item")
    @Transactional
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Long createOne(Item item) {
        log.info("Create item: {}", item);

        itemRepository.getEntityManager().persist(item);

        return item.getId();
    }

    @PUT
    @Path("item")
    @Transactional
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Boolean updateOne(Item item) {
        Item tmp = itemRepository.findById(item.getId());

        if (tmp == null) return false;

        tmp.setName(item.getName());
        tmp.setPrice(item.getPrice());
        tmp.setRating(item.getRating());
        tmp.setRatingCount(item.getRatingCount());
        tmp.setCategory(item.getCategory());

        itemRepository.getEntityManager().merge(tmp);
        return true;
    }

    @DELETE
    @Path("item/{id}")
    @Transactional
    @Produces(MediaType.TEXT_PLAIN)
    public Boolean deleteOne(@PathParam("id") Long id) {
        return itemRepository.deleteById(id);
    }

    @PUT
    @Path("item/{id}")
    @Transactional
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public Boolean updateRating(@PathParam("id") Long id, Double rating) {
        Item item = itemRepository.findById(id);

        if(item == null) return false;

        item.updateRating(rating);

        itemRepository.getEntityManager().merge(item);
        return true;
    }
}
