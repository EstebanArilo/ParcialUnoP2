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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import uqvirutal.edu.co.parcialunop2.model.*;

import java.time.LocalDate;

public class PlanController {


    @FXML private ComboBox<Cliente> cmbCliente; // NUEVO
    @FXML private ComboBox<TipoPlan> cmbTipoPlan;
    @FXML private TextField txtCodigo;
    @FXML private TextField txtNombre;
    @FXML private TextField txtDuracionMeses;
    @FXML private TextField txtValorMensual;
    @FXML private TextField txtDescripcion;


    @FXML private Label lblSesiones;
    @FXML private Label lblEspecialidad;
    @FXML private Label lblObjetivos;
    @FXML private Label lblEntrenador;
    @FXML private TextField txtCantidadSesiones;
    @FXML private TextField txtEspecialidad;
    @FXML private TextField txtObjetivos;
    @FXML private ComboBox<Entrenador> cmbEntrenador;

    @FXML private TableView<Plan> tblPlanes;
    @FXML private TableColumn<Plan, String> colCodigo;
    @FXML private TableColumn<Plan, String> colCliente; // NUEVO
    @FXML private TableColumn<Plan, String> colNombre;
    @FXML private TableColumn<Plan, TipoPlan> colTipo;
    @FXML private TableColumn<Plan, Integer> colDuracion;
    @FXML private TableColumn<Plan, Double> colValorMensual;
    @FXML private TableColumn<Plan, EstadoPlan> colEstado;
    @FXML private TableColumn<Plan, String> colDescripcion;

    private GimnasioControlador gimnasioControlador;
    private ObservableList<Plan> listaPlanesObservable;

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

        // Cargar Clientes, Enums y Entrenadores
        cmbCliente.setItems(FXCollections.observableArrayList(gimnasioControlador.getClientes()));
        cmbTipoPlan.setItems(FXCollections.observableArrayList(TipoPlan.values()));
        cmbEntrenador.setItems(FXCollections.observableArrayList(Gimnasio.getInstancia().getListEntrenador()));

        // Personalización del desplegable de Clientes (Muestra Nombre y Teléfono)
        cmbCliente.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Cliente item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getNombre() + " (" + item.getTelefono() + ")");
            }
        });
        cmbCliente.setButtonCell(cmbCliente.getCellFactory().call(null));

        cmbTipoPlan.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            boolean esPersonalizado = (newVal == TipoPlan.PERSONALIZADO);
            toggleCamposPersonalizados(esPersonalizado);
        });

        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipoPlan"));
        colDuracion.setCellValueFactory(new PropertyValueFactory<>("duracionMeses"));
        colValorMensual.setCellValueFactory(new PropertyValueFactory<>("valorMensual"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

        // Muestra el nombre del cliente en la columna correspondiente
        colCliente.setCellValueFactory(cellData -> {
            if (cellData.getValue().getCliente() != null) {
                return new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCliente().getNombre());
            }
            return new javafx.beans.property.SimpleStringProperty("Sin cliente");
        });

        tblPlanes.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                cargarPlanEnCampos(newSel);
            }
        });

        toggleCamposPersonalizados(false);
        cargarTabla();
    }

    private void cargarTabla() {
        listaPlanesObservable = FXCollections.observableArrayList(gimnasioControlador.getPlanes());
        tblPlanes.setItems(listaPlanesObservable);
    }

    @FXML
    private void handleCrearPlan() {
        try {
            Cliente cliente = cmbCliente.getValue();
            TipoPlan tipo = cmbTipoPlan.getValue();
            String codigo = txtCodigo.getText().trim();
            String nombre = txtNombre.getText().trim();
            String descripcion = txtDescripcion.getText().trim();
            int duracion = Integer.parseInt(txtDuracionMeses.getText().trim());
            double valorMensual = Double.parseDouble(txtValorMensual.getText().trim());

            if (cliente == null) {
                mostrarAlerta(Alert.AlertType.WARNING, "Atención", "Debe seleccionar un cliente para asociar el plan.");
                return;
            }

            if (tipo == null || codigo.isEmpty() || nombre.isEmpty()) {
                mostrarAlerta(Alert.AlertType.WARNING, "Atención", "Complete el tipo, código y nombre del plan.");
                return;
            }

            Plan nuevoPlan;

            if (tipo == TipoPlan.PERSONALIZADO) {
                int sesiones = Integer.parseInt(txtCantidadSesiones.getText().trim());
                String especialidad = txtEspecialidad.getText().trim();
                String objetivos = txtObjetivos.getText().trim();
                Entrenador entrenador = cmbEntrenador.getValue();

                nuevoPlan = PlanFactory.crearPlanPersonalizado(
                        codigo, nombre, descripcion, duracion, valorMensual,
                        cliente, sesiones, especialidad, objetivos, entrenador
                );
            } else {
                nuevoPlan = PlanFactory.crearPlan(tipo, codigo, nombre, descripcion, duracion, valorMensual, cliente);
            }

            boolean exito = gimnasioControlador.registrarPlan(nuevoPlan);

            if (exito) {
                Inscripcion nuevaInscripcion = new Inscripcion(
                        cliente,
                        nuevoPlan,
                        LocalDate.now(),
                        EstadoInscripcion.PAGADA
                );

                // Si el plan tiene servicios adicionales asociados, los transfiere a la inscripción
                if (nuevoPlan.getServiciosAdicionales() != null) {
                    for (ServicioAdicional servicio : nuevoPlan.getServiciosAdicionales()) {
                        nuevaInscripcion.agregarServicioAdicional(servicio);
                    }
                }

                // Guardar la inscripción en la lista global del Gimnasio
                gimnasioControlador.registrarInscripcion(nuevaInscripcion);

                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Plan e Inscripción registrados correctamente.");
                cargarTabla();
                handleLimpiarCampos();
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "El código de plan ya existe.");
            }

        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atención", "Verifique los datos numéricos (duración, valor mensual, sesiones).");
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    @FXML
    private void handleClonarPlan() {
        Plan planSeleccionado = tblPlanes.getSelectionModel().getSelectedItem();

        if (planSeleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atención", "Seleccione un plan de la tabla para clonar.");
            return;
        }

        Plan planClonado = planSeleccionado.clonar();

        if (planClonado != null) {
            planClonado.setCodigo(planSeleccionado.getCodigo() + "-CLON");
            planClonado.setNombre(planSeleccionado.getNombre() + " (Copia)");

            boolean exito = gimnasioControlador.registrarPlan(planClonado);

            if (exito) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Prototipo Clonado",
                        "Se clonó con éxito el plan " + planSeleccionado.getCodigo() +
                                "\nNuevo código asignado: " + planClonado.getCodigo());
                cargarTabla();
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "El código del clon (" + planClonado.getCodigo() + ") ya existe.");
            }
        }
    }

    @FXML
    private void handleActualizarPlan() {
        Plan seleccionado = tblPlanes.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atención", "Seleccione un plan de la tabla.");
            return;
        }

        try {
            seleccionado.setCliente(cmbCliente.getValue());
            seleccionado.setTipoPlan(cmbTipoPlan.getValue());
            seleccionado.setNombre(txtNombre.getText().trim());
            seleccionado.setDescripcion(txtDescripcion.getText().trim());
            seleccionado.setDuracionMeses(Integer.parseInt(txtDuracionMeses.getText().trim()));
            seleccionado.setValorMensual(Double.parseDouble(txtValorMensual.getText().trim()));

            boolean exito = gimnasioControlador.actualizarPlan(seleccionado);
            if (exito) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Plan actualizado correctamente.");
                cargarTabla();
                tblPlanes.refresh();
                handleLimpiarCampos();
            }
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atención", "Datos numéricos inválidos.");
        }
    }

    @FXML
    private void handleEliminarPlan() {
        Plan seleccionado = tblPlanes.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atención", "Seleccione un plan.");
            return;
        }

        if (gimnasioControlador.eliminarPlan(seleccionado.getCodigo())) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Plan eliminado.");
            cargarTabla();
            handleLimpiarCampos();
        }
    }

    @FXML
    private void handleLimpiarCampos() {
        cmbCliente.getSelectionModel().clearSelection();
        txtCodigo.clear();
        txtCodigo.setDisable(false);
        txtNombre.clear();
        txtDescripcion.clear();
        txtDuracionMeses.clear();
        txtValorMensual.clear();
        txtCantidadSesiones.clear();
        txtEspecialidad.clear();
        txtObjetivos.clear();
        cmbTipoPlan.getSelectionModel().clearSelection();
        cmbEntrenador.getSelectionModel().clearSelection();
        tblPlanes.getSelectionModel().clearSelection();
    }

    private void cargarPlanEnCampos(Plan plan) {
        cmbCliente.setValue(plan.getCliente());
        txtCodigo.setText(plan.getCodigo());
        txtCodigo.setDisable(true);
        txtNombre.setText(plan.getNombre());
        txtDescripcion.setText(plan.getDescripcion());
        txtDuracionMeses.setText(String.valueOf(plan.getDuracionMeses()));
        txtValorMensual.setText(String.valueOf(plan.getValorMensual()));
        cmbTipoPlan.setValue(plan.getTipoPlan());

        if (plan instanceof PlanPersonalizado) {
            PlanPersonalizado pp = (PlanPersonalizado) plan;
            txtCantidadSesiones.setText(String.valueOf(pp.getCantidadSesiones()));
            txtEspecialidad.setText(pp.getEspecialidadRequerida());
            txtObjetivos.setText(pp.getObjetivosCliente());
            cmbEntrenador.setValue(pp.getEntrenadorAsignado());
        }
    }

    private void toggleCamposPersonalizados(boolean visible) {
        lblSesiones.setVisible(visible);
        lblEspecialidad.setVisible(visible);
        lblObjetivos.setVisible(visible);
        lblEntrenador.setVisible(visible);

        txtCantidadSesiones.setVisible(visible);
        txtEspecialidad.setVisible(visible);
        txtObjetivos.setVisible(visible);
        cmbEntrenador.setVisible(visible);
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}