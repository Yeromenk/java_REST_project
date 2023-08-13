package project.yer0013.eShop.client.controller;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import lombok.extern.log4j.Log4j2;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import project.yer0013.eShop.client.resource.Order_Resource;
import project.yer0013.eShop.server.model.Category;
import project.yer0013.eShop.server.model.Item;
import project.yer0013.eShop.client.resource.Category_Resource;
import project.yer0013.eShop.client.resource.Item_Resource;
import project.yer0013.eShop.server.model.OrderCart;
import project.yer0013.eShop.server.model.OrderStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture; //TODO CompetableFuture

@Log4j2
public class Customer_Controller {
    @FXML
    private TableColumn<Item, Item> actionColumn;
    @FXML
    private TableColumn<Item, String> categoryColumn;
    @FXML
    private TableColumn<Item, String> ratingColumn;
    @FXML
    private TableColumn<Item, Double> priceColumn;
    @FXML
    private TableColumn<Item, String> nameColumn;
    @FXML
    private TableView<Item> table;

    private final ObservableList<Item> data;
    private final Item_Resource itemResource;
    private final Category_Resource categoryResource;
    private final Order_Resource orderResource;

    public Customer_Controller() {
        data = FXCollections.observableArrayList();

        itemResource = JAXRSClientFactory.create("http://localhost:8000", Item_Resource.class,
                Collections.singletonList(JacksonJaxbJsonProvider.class));
        categoryResource = JAXRSClientFactory.create("http://localhost:8000", Category_Resource.class,
                Collections.singletonList(JacksonJaxbJsonProvider.class));
        orderResource = JAXRSClientFactory.create("http://localhost:8000", Order_Resource.class,
                Collections.singletonList(JacksonJaxbJsonProvider.class));
        initData();
    }

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory((v) -> new SimpleStringProperty(v.getValue().getName()));
        priceColumn.setCellValueFactory((v) -> new SimpleDoubleProperty(v.getValue().getPrice()).asObject());
        ratingColumn.setCellValueFactory((v) -> new SimpleStringProperty(v.getValue().getRating().toString()));
        ratingColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        categoryColumn.setCellValueFactory((v) -> new SimpleStringProperty(v.getValue().getCategory().getName()));
        actionColumn.setCellValueFactory((v) -> new ReadOnlyObjectWrapper<>(v.getValue()));
        actionColumn.setCellFactory(this::createActionCell);

        table.setItems(data);
        table.setEditable(true);
        ratingColumn.setEditable(true);
        log.info("Customer IDE is initialized");
    }


    private TableCell<Item, Item> createActionCell(TableColumn<Item, Item> column) {
        return new TableCell<>() {
            final Button btn = new Button("Buy game");

            @Override
            protected void updateItem(Item item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                    return;
                }

                setGraphic(btn);
                btn.setOnAction(event -> {
                    OrderCart cart = new OrderCart();
                    cart.setStatus(OrderStatus.DEFAULT.getValue());
                    cart.setItem(item);
                    cart.setId(orderResource.createOne(cart));

                    orderResource.buy(cart);
                });
            }
        };
    }

    @FXML
    public void initData() {
        List<CompletableFuture<Void>> futureList = new ArrayList<>(); //TODO CompletableFuture

        if (itemResource.getAll().isEmpty()) {
            Category category = new Category();
            category.setName("Test");
            category.setId(categoryResource.createOne(category));


            for (int i = 0; i < 10; i++) {
                int itemId = i + 1;
                int ratingCount = itemId * 10;
                double price = itemId * 5.0;

                CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                    Item item = new Item();
                    item.setName("Game " + itemId);
                    item.setRating(itemId * 0.5);
                    item.setRatingCount(ratingCount);
                    item.setPrice(price);
                    item.setCategory(category);

                    item.setId(itemResource.createOne(item));
                });

                futureList.add(future);
            }
        }

        futureList.forEach(voidCompletableFuture -> {
            try {
                voidCompletableFuture.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        this.updateData();
        log.info("Data is initialized");
    }

    @FXML
    public void ratingCommit(TableColumn.CellEditEvent<Item, String> event) {
        Long id = event.getRowValue().getId();
        itemResource.updateRating(id, Double.parseDouble(event.getNewValue()));
        this.updateData();
        log.info("Rating is commited");
    }

    private void updateData() {
        data.clear();
        data.addAll(itemResource.getAll());
        data.sort((o1, o2) -> o1.getId() > o2.getId() ? 1 : 0); //TODO lambda function
        log.info("Data is updated");
    }
}
