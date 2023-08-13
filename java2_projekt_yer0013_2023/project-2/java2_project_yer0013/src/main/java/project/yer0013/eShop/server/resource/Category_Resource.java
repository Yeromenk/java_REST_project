package project.yer0013.eShop.server.resource;

import lombok.extern.log4j.Log4j2;
import project.yer0013.eShop.server.model.Category;
import project.yer0013.eShop.server.repository.Category_Repository;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/")
@Log4j2
public class Category_Resource {
    @Inject
    Category_Repository categoryRepository;

    @GET
    @Path("category")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Category> getAll() {
        return categoryRepository.listAll();
    }

    @GET
    @Path("category/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Category getOne(@PathParam("id") Long id) {
        return categoryRepository.findById(id);
    }

    @POST
    @Path("category")
    @Transactional
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Long createOne(Category category) {
        log.info("Create category: {}", category);

        categoryRepository.persist(category);

        return category.getId();
    }

    @PUT
    @Path("category")
    @Transactional
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Boolean updateOne(Category category) {
        Category tmp = categoryRepository.findById(category.getId());

        if (tmp == null) return false;

        tmp.setName(category.getName());

        categoryRepository.getEntityManager().merge(tmp);
        return true;
    }

    @DELETE
    @Path("category/{id}")
    @Transactional
    @Produces(MediaType.TEXT_PLAIN)
    public Boolean deleteOne(@PathParam("id") Long id) {
        return categoryRepository.deleteById(id);
    }
}
