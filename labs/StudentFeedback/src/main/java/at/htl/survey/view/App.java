package at.htl.survey.view;

import at.htl.survey.controller.Persistent;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Persistent controller;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"));
        stage.setScene(scene);
        stage.setTitle("Umfrage Manager");
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        controller.save("");
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        Parent parent = fxmlLoader.load();
//        if (controller != null) {
//            controller.saveData();
//        }
//        controller = fxmlLoader.getController();
//        controller.loadData();
        return parent;
    }

    public static void main(String[] args) {
        launch();
    }
}