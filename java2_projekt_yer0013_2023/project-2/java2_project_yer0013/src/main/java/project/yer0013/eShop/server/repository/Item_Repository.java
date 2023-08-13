package project.yer0013.eShop.server.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import project.yer0013.eShop.server.model.Item;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Item_Repository implements PanacheRepository<Item> {
}
