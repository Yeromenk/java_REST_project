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
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import project.yer0013.eShop.client.resource.Order_Resource;
import project.yer0013.eShop.server.model.OrderCart;
import project.yer0013.eShop.server.model.OrderStatus;
import lombok.extern.log4j.Log4j2;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
@Log4j2
public class Worker_Controller {
    @FXML
    private TableColumn<OrderCart, OrderCart> actionColumn;
    @FXML
    private TableColumn<OrderCart, String> categoryColumn;
    @FXML
    private TableColumn<OrderCart, String> nameColumn;
    @FXML
    private TableColumn<OrderCart, Double> priceColumn;
    @FXML
    private TableView<OrderCart> table;

    private final ObservableList<OrderCart> data;
    private final Order_Resource orderResource;

    public Worker_Controller() {
        data = FXCollections.observableArrayList();
        orderResource = JAXRSClientFactory.create("http://localhost:8000", Order_Resource.class,
                Collections.singletonList(JacksonJaxbJsonProvider.class));

        initData();
    }

    @FXML
    public void initialize() {
        priceColumn.setCellValueFactory((v) -> new SimpleDoubleProperty(v.getValue().getItem().getPrice()).asObject()); //TODO lambda
        nameColumn.setCellValueFactory((v) -> new SimpleStringProperty(v.getValue().getItem().getName()));
        categoryColumn.setCellValueFactory((v) -> new SimpleStringProperty(v.getValue().getItem().getCategory().getName()));
        actionColumn.setCellValueFactory((v) -> new ReadOnlyObjectWrapper<>(v.getValue()));
        actionColumn.setCellFactory(this::createActionCell);

        table.setItems(data);
        log.info("Worker IDE is initialized");
    }

    private TableCell<OrderCart, OrderCart> createActionCell(TableColumn<OrderCart, OrderCart> column) {
        return new TableCell<>() {
            final Button btn = new Button("Send Game");

            @Override
            protected void updateItem(OrderCart item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                    return;
                }

                setGraphic(btn);
                btn.setOnAction(event -> {
                    orderResource.send(item);
                    updateData();
                });
            }
        };
    }

    @FXML
    public void initData() {
        updateData();
        log.info("Data is initialized");
    }

    private void updateData() {
        List<OrderCart> orders = orderResource.getAll().stream() //TODO stream
                .filter(v -> v.getStatus().equals(OrderStatus.BOUGHT.getValue())) //TODO lambda
                .collect(Collectors.toList());

        data.clear();
        data.addAll(orders);
        log.info("Data is updated");
    }
}
