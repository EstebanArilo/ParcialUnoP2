package uqvirutal.edu.co.parcialunop2.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import uqvirutal.edu.co.parcialunop2.model.Entrenador;
import uqvirutal.edu.co.parcialunop2.model.Gimnasio;

public class EntrenadorController {

    @FXML private TextField txtId;
    @FXML private TextField txtNombre;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtEspecialidad;

    @FXML private TableView<Entrenador> tblEntrenadores;
    @FXML private TableColumn<Entrenador, String> colId;
    @FXML private TableColumn<Entrenador, String> colNombre;
    @FXML private TableColumn<Entrenador, String> colTelefono;
    @FXML private TableColumn<Entrenador, String> colEspecialidad;

    private GimnasioControlador gimnasioControlador;
    private ObservableList<Entrenador> listaEntrenadoresObservable;

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

    @FXML
    public void initialize() {
        this.gimnasioControlador = new GimnasioControlador();


        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colEspecialidad.setCellValueFactory(new PropertyValueFactory<>("especialidad"));


        tblEntrenadores.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                cargarEntrenadorEnCampos(newSel);
            }
        });

        cargarTabla();
    }

    private void cargarTabla() {
        listaEntrenadoresObservable = FXCollections.observableArrayList(Gimnasio.getInstancia().getListEntrenador());
        tblEntrenadores.setItems(listaEntrenadoresObservable);
    }

    @FXML
    private void handleRegistrarEntrenador() {
        String id = txtId.getText().trim();
        String nombre = txtNombre.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String especialidad = txtEspecialidad.getText().trim();

        if (id.isEmpty() || nombre.isEmpty() || telefono.isEmpty() || especialidad.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos vacíos", "Por favor complete todos los campos.");
            return;
        }

        double tarifaDefecto = 50000.0;
        Entrenador nuevoEntrenador = new Entrenador(nombre, id, telefono, especialidad, tarifaDefecto);

        boolean exito = gimnasioControlador.registrarEntrenador(nuevoEntrenador);

        if (exito) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Entrenador registrado correctamente.");
            cargarTabla();
            handleLimpiarCampos();
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "El entrenador ya existe (ID duplicado).");
        }
    }

    @FXML
    private void handleActualizarEntrenador() {
        Entrenador seleccionado = tblEntrenadores.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atención", "Seleccione un entrenador de la tabla para actualizar.");
            return;
        }

        String id = txtId.getText().trim();
        String nombre = txtNombre.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String especialidad = txtEspecialidad.getText().trim();

        if (id.isEmpty() || nombre.isEmpty() || telefono.isEmpty() || especialidad.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos vacíos", "Por favor complete todos los campos.");
            return;
        }

        Entrenador actualizado = new Entrenador(
                nombre,
                seleccionado.getId(),
                telefono,
                especialidad,
                seleccionado.getTarifaPorSesion()
        );

        boolean exito = gimnasioControlador.actualizarEntrenador(actualizado);

        if (exito) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Datos del entrenador actualizados correctamente.");
            cargarTabla();
            tblEntrenadores.refresh();
            handleLimpiarCampos();
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo actualizar el entrenador.");
        }
    }

    @FXML
    private void handleEliminarEntrenador() {
        Entrenador seleccionado = tblEntrenadores.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atención", "Seleccione un entrenador de la tabla para eliminar.");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Está seguro de eliminar al entrenador " + seleccionado.getNombre() + "?");

        if (confirmacion.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            boolean exito = gimnasioControlador.eliminarEntrenador(seleccionado.getId());

            if (exito) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Entrenador eliminado correctamente.");
                cargarTabla();
                handleLimpiarCampos();
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo eliminar el entrenador.");
            }
        }
    }

    @FXML
    private void handleLimpiarCampos() {
        txtId.clear();
        txtNombre.clear();
        txtTelefono.clear();
        txtEspecialidad.clear();
        txtId.setDisable(false);
        tblEntrenadores.getSelectionModel().clearSelection();
    }

    private void cargarEntrenadorEnCampos(Entrenador entrenador) {
        txtId.setText(entrenador.getId());
        txtId.setDisable(true);
        txtNombre.setText(entrenador.getNombre());
        txtTelefono.setText(entrenador.getTelefono());
        txtEspecialidad.setText(entrenador.getEspecialidad());
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}