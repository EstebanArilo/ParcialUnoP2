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
import uqvirutal.edu.co.parcialunop2.model.Cliente;

import java.time.LocalDate;

public class ClienteController {

    @FXML private TextField txtNombre;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtCorreo;
    @FXML private TextField txtEdad;

    @FXML private TableView<Cliente> tblClientes;
    @FXML private TableColumn<Cliente, String> colNombre;
    @FXML private TableColumn<Cliente, String> colTelefono;
    @FXML private TableColumn<Cliente, String> colCorreo;
    @FXML private TableColumn<Cliente, Integer> colEdad;

    private GimnasioControlador gimnasioControlador;
    private ObservableList<Cliente> listaClientesObservable;

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


        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));


        tblClientes.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                cargarClienteEnCampos(newSelection);
            }
        });

        cargarTabla();
    }

    private void cargarTabla() {
        listaClientesObservable = FXCollections.observableArrayList(gimnasioControlador.getClientes());
        tblClientes.setItems(listaClientesObservable);
    }

    @FXML
    private void handleRegistrarCliente() {
        try {
            String nombre = txtNombre.getText().trim();
            String telefono = txtTelefono.getText().trim();
            String correo = txtCorreo.getText().trim();
            int edad = Integer.parseInt(txtEdad.getText().trim());

            if (nombre.isEmpty() || telefono.isEmpty() || correo.isEmpty()) {
                mostrarAlerta(Alert.AlertType.WARNING, "Campos vacíos", "Por favor complete todos los campos.");
                return;
            }


            Cliente nuevoCliente = new Cliente(
                    nombre,
                    telefono,
                    telefono,
                    correo,
                    edad,
                    LocalDate.now()
            );

            boolean exito = gimnasioControlador.registrarCliente(nuevoCliente);

            if (exito) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Cliente registrado correctamente.");
                cargarTabla();
                handleLimpiarCampos();
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "El cliente ya existe (teléfono duplicado).");
            }
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atención", "Ingrese una edad válida (número entero).");
        }
    }

    @FXML
    private void handleActualizarCliente() {

        Cliente clienteSeleccionado = tblClientes.getSelectionModel().getSelectedItem();

        if (clienteSeleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atención", "Por favor, seleccione un cliente de la tabla para actualizar.");
            return;
        }

        try {

            String nombre = txtNombre.getText().trim();
            String telefono = txtTelefono.getText().trim();
            String correo = txtCorreo.getText().trim();
            int edad = Integer.parseInt(txtEdad.getText().trim());

            if (nombre.isEmpty() || telefono.isEmpty() || correo.isEmpty()) {
                mostrarAlerta(Alert.AlertType.WARNING, "Campos vacíos", "Por favor complete todos los campos.");
                return;
            }

            Cliente clienteActualizado = new Cliente(
                    nombre,
                    clienteSeleccionado.getId(),
                    telefono,
                    correo,
                    edad,
                    clienteSeleccionado.getFechaRegistro()
            );


            boolean exito = gimnasioControlador.actualizarCliente(clienteActualizado);

            if (exito) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Los datos del cliente se actualizaron correctamente.");
                cargarTabla();
                tblClientes.refresh();
                handleLimpiarCampos();
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo actualizar el cliente seleccionado.");
            }

        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atención", "Ingrese una edad válida (número entero).");
        }
    }

    @FXML
    private void handleEliminarCliente() {

        Cliente clienteSeleccionado = tblClientes.getSelectionModel().getSelectedItem();

        if (clienteSeleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atención", "Seleccione un cliente de la tabla para eliminar.");
            return;
        }


        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Está seguro de que desea eliminar al cliente " + clienteSeleccionado.getNombre() + "?");

        if (confirmacion.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {

            boolean exito = gimnasioControlador.eliminarCliente(clienteSeleccionado.getTelefono());

            if (exito) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "El cliente ha sido eliminado exitosamente.");
                cargarTabla();
                handleLimpiarCampos();
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo eliminar el cliente.");
            }
        }
    }

    @FXML
    private void handleValidarTelefonoPerfecto() {
        String telefono = txtTelefono.getText();
        if (telefono.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atención", "Ingrese un número de teléfono.");
            return;
        }

        boolean esPerfecto = gimnasioControlador.telefonoDeClienteEsPerfecto(telefono);
        if (esPerfecto) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Resultado", "El teléfono " + telefono + " SÍ es un número perfecto.");
        } else {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Resultado", "El teléfono " + telefono + " NO es un número perfecto (o el cliente no está registrado).");
        }
    }

    @FXML
    private void handleLimpiarCampos() {
        txtNombre.clear();
        txtTelefono.clear();
        txtCorreo.clear();
        txtEdad.clear();
        tblClientes.getSelectionModel().clearSelection();
    }

    private void cargarClienteEnCampos(Cliente cliente) {
        txtNombre.setText(cliente.getNombre());
        txtTelefono.setText(cliente.getTelefono());
        txtCorreo.setText(cliente.getCorreo());
        txtEdad.setText(String.valueOf(cliente.getEdad()));
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
