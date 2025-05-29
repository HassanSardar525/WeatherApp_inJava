
package org.example.oops_project_weatherapp;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Loading the FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(getClass().getResource("/Styling.css").toExternalForm());
        stage.setScene(scene);
        stage.setResizable(false);
        
        stage.getIcons().add(new Image(HelloApplication.class.getResourceAsStream("/images/AppIcon.png")));

        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}
