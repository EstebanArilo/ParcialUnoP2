package uqvirutal.edu.co.parcialunop2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        try {
            // Carga la vista inicial del Menú Principal
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/uqvirutal/edu/co/parcialunop2/MenuView.fxml"));
            Parent root = loader.load();

            // Configuración de la escena y ventana principal
            Scene scene = new Scene(root, 700, 500);
            stage.setTitle("SmartGym - Panel Principal");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Error al cargar la vista MenuView.fxml. Revisa la ruta en los recursos.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}