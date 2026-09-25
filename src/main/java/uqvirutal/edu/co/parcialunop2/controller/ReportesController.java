package uqvirutal.edu.co.parcialunop2.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import uqvirutal.edu.co.parcialunop2.model.Cliente;
import uqvirutal.edu.co.parcialunop2.model.Gimnasio;
import uqvirutal.edu.co.parcialunop2.model.Inscripcion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReportesController {

    // Controles Sección 1 (Número Perfecto)
    @FXML private TextField txtTelefono;
    @FXML private Label lblNombreCliente;
    @FXML private Label lblResultadoPerfecto;

    // Controles Sección 2 (Ingresos por Periodo)
    @FXML private DatePicker dpFechaInicio;
    @FXML private DatePicker dpFechaFin;
    @FXML private Label lblTotalIngresos;

    @FXML private TableView<Inscripcion> tblInscripciones;
    @FXML private TableColumn<Inscripcion, String> colCodigo;
    @FXML private TableColumn<Inscripcion, String> colCliente;
    @FXML private TableColumn<Inscripcion, String> colPlan;
    @FXML private TableColumn<Inscripcion, LocalDate> colFecha;
    @FXML private TableColumn<Inscripcion, Double> colMontoTotal;

    private Gimnasio gimnasio;

    @FXML
    private void handleVolverMenu(ActionEvent event) {
        try {
            // Cargar el FXML del Menú Principal
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/uqvirutal/edu/co/parcialunop2/MenuView.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(new Scene(root));
            stage.setTitle("Gimnasio - Menú Principal");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Reemplaza o añade la variable
    private GimnasioControlador gimnasioControlador;

    @FXML
    public void initialize() {
        // Inicializar el controlador
        this.gimnasioControlador = new GimnasioControlador();

        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigoInscripcion"));
        colCliente.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCliente() != null ? cellData.getValue().getCliente().getNombre() : ""));
        colPlan.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getPlan() != null ? cellData.getValue().getPlan().getNombre() : ""));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaInscripcion"));
        colMontoTotal.setCellValueFactory(new PropertyValueFactory<>("montoTotal"));
    }

    @FXML
    private void handleBuscarClientePorTelefono() {
        String telefonoInput = txtTelefono.getText().trim();

        if (telefonoInput.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atención", "Ingrese un número de teléfono.");
            return;
        }

        Cliente clienteEncontrado = gimnasioControlador.buscarClientePorTelefono(telefonoInput);

        if (clienteEncontrado != null) {
            lblNombreCliente.setText(clienteEncontrado.getNombre() + " (ID: " + clienteEncontrado.getId() + ")");
        } else {
            lblNombreCliente.setText("No se encontró ningún cliente registrado con este teléfono.");
        }

        // Evaluación de Número Perfecto
        try {
            // Extraer solo caracteres numéricos
            String soloNumeros = telefonoInput.replaceAll("\\D+", "");
            if (soloNumeros.isEmpty()) {
                lblResultadoPerfecto.setText("El número ingresado no contiene dígitos válidos.");
                return;
            }

            long numero = Long.parseLong(soloNumeros);
            boolean esPerfecto = esNumeroPerfecto(numero);

            if (esPerfecto) {
                lblResultadoPerfecto.setText("¡El número (" + numero + ") ES UN NÚMERO PERFECTO!");
                lblResultadoPerfecto.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
            } else {
                lblResultadoPerfecto.setText("El número (" + numero + ") NO es un número perfecto.");
                lblResultadoPerfecto.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
            }

        } catch (NumberFormatException e) {
            lblResultadoPerfecto.setText("El número es demasiado largo para ser evaluado.");
            lblResultadoPerfecto.setStyle("-fx-text-fill: orange;");
        }
    }

    /**
     * Algoritmo para determinar si un número es perfecto.
     * Un número es perfecto si la suma de sus divisores propios es igual al mismo número.
     */
    private boolean esNumeroPerfecto(long n) {
        if (n <= 1) return false;

        long sumaDivisores = 1; // 1 siempre es divisor propio
        for (long i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                sumaDivisores += i;
                if (i * i != n) {
                    sumaDivisores += n / i;
                }
            }
        }
        return sumaDivisores == n;
    }

    @FXML
    private void handleCalcularIngresos() {
        LocalDate inicio = dpFechaInicio.getValue();
        LocalDate fin = dpFechaFin.getValue();

        if (inicio == null || fin == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atención", "Seleccione ambas fechas (Inicio y Fin).");
            return;
        }

        if (inicio.isAfter(fin)) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atención", "La fecha de inicio no puede ser posterior a la fecha fin.");
            return;
        }

        double totalAcumulado = gimnasioControlador.consultarIngresosPorPeriodo(inicio, fin);

        List<Inscripcion> inscripcionesEnPeriodo = gimnasioControlador.getInscripciones().stream()
                .filter(ins -> {
                    LocalDate f = ins.getFechaInscripcion();
                    return f != null && (f.isEqual(inicio) || f.isEqual(fin) || (f.isAfter(inicio) && f.isBefore(fin)));
                })
                .collect(Collectors.toList());


        tblInscripciones.setItems(FXCollections.observableArrayList(inscripcionesEnPeriodo));
        lblTotalIngresos.setText(String.format("$ %.2f", totalAcumulado));

        if (inscripcionesEnPeriodo.isEmpty()) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Información", "No se encontraron inscripciones registradas en el periodo seleccionado.");
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}