package project.yer0013.eShop.client.fxml;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;

import java.net.URL;
import java.util.Objects;

@Log4j2
public class Worker extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            URL resourceUrl = getClass().getResource("/fxml/worker.fxml");
            Parent root = FXMLLoader.load(Objects.requireNonNull(resourceUrl));
            Scene scene = new Scene(root);

            primaryStage.setScene(scene);
            primaryStage.setTitle("Worker App - YER0013");
            primaryStage.show();
            log.info("App started");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
