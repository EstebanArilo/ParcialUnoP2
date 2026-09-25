package uqvirutal.edu.co.parcialunop2.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import uqvirutal.edu.co.parcialunop2.model.*;

import java.util.List;
import java.util.stream.Collectors;

public class ServicioController {

    @FXML private ComboBox<Cliente> cmbCliente;
    @FXML private ComboBox<Plan> cmbPlan;
    @FXML private TextField txtCodigo;
    @FXML private TextField txtNombre;
    @FXML private TextField txtPrecio;
    @FXML private TextField txtDescripcion;
    @FXML private CheckBox chkDisponible;

    @FXML private TableView<ServicioAdicional> tblServicios;
    @FXML private TableColumn<ServicioAdicional, String> colCodigo;
    @FXML private TableColumn<ServicioAdicional, String> colNombre;
    @FXML private TableColumn<ServicioAdicional, String> colCliente;
    @FXML private TableColumn<ServicioAdicional, String> colPlan;
    @FXML private TableColumn<ServicioAdicional, Double> colPrecio;
    @FXML private TableColumn<ServicioAdicional, Boolean> colDisponible;
    @FXML private TableColumn<ServicioAdicional, String> colDescripcion;

    private GimnasioControlador gimnasioControlador;
    private ObservableList<ServicioAdicional> listaServiciosObservable;

    @FXML
    private void handleVolverMenu(ActionEvent event) {
        try {
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

        // Cargar Clientes
        cmbCliente.setItems(FXCollections.observableArrayList(gimnasioControlador.getClientes()));

        // Formateo del visor de Clientes
        cmbCliente.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Cliente item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getNombre() + " (" + item.getTelefono() + ")");
            }
        });
        cmbCliente.setButtonCell(cmbCliente.getCellFactory().call(null));

        // Listener en Cascada: Al seleccionar Cliente, cargar SOLO sus Planes
        cmbCliente.getSelectionModel().selectedItemProperty().addListener((obs, oldCli, newCli) -> {
            if (newCli != null) {
                List<Plan> planesDelCliente = gimnasioControlador.getPlanes().stream()
                        .filter(p -> p.getCliente() != null && p.getCliente().getTelefono().equals(newCli.getTelefono()))
                        .collect(Collectors.toList());
                cmbPlan.setItems(FXCollections.observableArrayList(planesDelCliente));
            } else {
                cmbPlan.getItems().clear();
            }
        });

        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colDisponible.setCellValueFactory(new PropertyValueFactory<>("disponible"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

        // Columnas calculadas para asociar Cliente y Plan
        colCliente.setCellValueFactory(cellData -> {
            Plan p = cellData.getValue().getPlanAsociado();
            if (p != null && p.getCliente() != null) {
                return new SimpleStringProperty(p.getCliente().getNombre());
            }
            return new SimpleStringProperty("Sin cliente");
        });

        colPlan.setCellValueFactory(cellData -> {
            Plan p = cellData.getValue().getPlanAsociado();
            if (p != null) {
                return new SimpleStringProperty(p.getNombre() + " (" + p.getCodigo() + ")");
            }
            return new SimpleStringProperty("Sin plan");
        });

        tblServicios.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                cargarServicioEnCampos(newSel);
            }
        });

        cargarTabla();
    }

    private void cargarTabla() {
        listaServiciosObservable = FXCollections.observableArrayList(gimnasioControlador.getServiciosAdicionales());
        tblServicios.setItems(listaServiciosObservable);
    }

    @FXML
    private void handleRegistrarServicio() {
        try {
            Plan planSeleccionado = cmbPlan.getValue();
            String codigo = txtCodigo.getText().trim();
            String nombre = txtNombre.getText().trim();
            String descripcion = txtDescripcion.getText().trim();
            double precio = Double.parseDouble(txtPrecio.getText().trim());
            boolean disponible = chkDisponible.isSelected();

            if (planSeleccionado == null) {
                mostrarAlerta(Alert.AlertType.WARNING, "Atención", "Debe seleccionar un cliente y un plan para asociar el servicio.");
                return;
            }

            if (codigo.isEmpty() || nombre.isEmpty()) {
                mostrarAlerta(Alert.AlertType.WARNING, "Atención", "Complete el código y nombre del servicio.");
                return;
            }

            ServicioAdicional nuevoServicio = new ServicioAdicional(codigo, nombre, descripcion, precio, disponible, planSeleccionado);

            boolean exito = gimnasioControlador.registrarServicioAdicional(nuevoServicio);

            if (exito) {
                planSeleccionado.agregarServicioAdicional(nuevoServicio);

                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Servicio adicional registrado e incluido en el plan.");
                cargarTabla();
                handleLimpiarCampos();
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "El código del servicio ya existe.");
            }

        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atención", "Ingrese un precio válido.");
        }
    }

    @FXML
    private void handleActualizarServicio() {
        ServicioAdicional seleccionado = tblServicios.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atención", "Seleccione un servicio de la tabla.");
            return;
        }

        try {
            seleccionado.setNombre(txtNombre.getText().trim());
            seleccionado.setDescripcion(txtDescripcion.getText().trim());
            seleccionado.setPrecio(Double.parseDouble(txtPrecio.getText().trim()));
            seleccionado.setDisponible(chkDisponible.isSelected());

            if (cmbPlan.getValue() != null) {
                seleccionado.setPlanAsociado(cmbPlan.getValue());
            }

            cargarTabla();
            tblServicios.refresh();
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Servicio actualizado correctamente.");
            handleLimpiarCampos();
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atención", "Ingrese un precio válido.");
        }
    }

    @FXML
    private void handleEliminarServicio() {
        ServicioAdicional seleccionado = tblServicios.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atención", "Seleccione un servicio.");
            return;
        }

        if (gimnasioControlador.eliminarServicioAdicional(seleccionado.getCodigo())) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Servicio eliminado.");
            cargarTabla();
            handleLimpiarCampos();
        }
    }

    @FXML
    private void handleLimpiarCampos() {
        cmbCliente.getSelectionModel().clearSelection();
        cmbPlan.getSelectionModel().clearSelection();
        txtCodigo.clear();
        txtCodigo.setDisable(false);
        txtNombre.clear();
        txtPrecio.clear();
        txtDescripcion.clear();
        chkDisponible.setSelected(true);
        tblServicios.getSelectionModel().clearSelection();
    }

    private void cargarServicioEnCampos(ServicioAdicional servicio) {
        if (servicio.getPlanAsociado() != null) {
            cmbCliente.setValue(servicio.getPlanAsociado().getCliente());
            cmbPlan.setValue(servicio.getPlanAsociado());
        }
        txtCodigo.setText(servicio.getCodigo());
        txtCodigo.setDisable(true);
        txtNombre.setText(servicio.getNombre());
        txtPrecio.setText(String.valueOf(servicio.getPrecio()));
        txtDescripcion.setText(servicio.getDescripcion());
        chkDisponible.setSelected(servicio.isDisponible());
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
