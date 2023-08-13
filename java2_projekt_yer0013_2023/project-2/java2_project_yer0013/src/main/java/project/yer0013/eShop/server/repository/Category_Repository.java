package project.yer0013.eShop.server.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import project.yer0013.eShop.server.model.Category;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Category_Repository implements PanacheRepository<Category> {
}
