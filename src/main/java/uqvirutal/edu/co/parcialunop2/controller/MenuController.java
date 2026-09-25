package uqvirutal.edu.co.parcialunop2.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {

    @FXML
    private void handleNavClientes(ActionEvent event) {
        navegarA(event, "/uqvirutal/edu/co/parcialunop2/ClienteView.fxml", "Gestión de Clientes - SmartGym");
    }

    @FXML
    private void handleNavEntrenadores(ActionEvent event) {
        navegarA(event, "/uqvirutal/edu/co/parcialunop2/EntrenadorView.fxml", "Gestión de Entrenadores - SmartGym");
    }

    @FXML
    private void handleNavPlanes(ActionEvent event) {
        navegarA(event, "/uqvirutal/edu/co/parcialunop2/PlanView.fxml", "Gestión de Planes - SmartGym");
    }

    @FXML
    private void handleNavServicios(ActionEvent event) {
        navegarA(event, "/uqvirutal/edu/co/parcialunop2/ServicioView.fxml", "Gestión de Servicios - SmartGym");
    }

    @FXML
    private void handleNavReportes(ActionEvent event) {
        navegarA(event, "/uqvirutal/edu/co/parcialunop2/ReportesView.fxml", "Consultas y Reportes - SmartGym");
    }

    private void navegarA(ActionEvent event, String fxmlPath, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(titulo);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al cargar la vista: " + fxmlPath);
        }
    }
    @FXML
    private void handleSalir(ActionEvent event) {
        // Cierra la aplicación de JavaFX y finaliza el proceso de manera segura
        Platform.exit();
        System.exit(0);
    }
}