package project.yer0013.eShop.server.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import project.yer0013.eShop.server.model.OrderCart;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Order_Repository implements PanacheRepository<OrderCart> {
}
