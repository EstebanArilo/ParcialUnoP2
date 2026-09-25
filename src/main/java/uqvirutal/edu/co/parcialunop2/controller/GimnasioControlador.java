package uqvirutal.edu.co.parcialunop2.controller;

import uqvirutal.edu.co.parcialunop2.model.*;
import java.time.LocalDate;
import java.util.List;

public class GimnasioControlador {

    private Gimnasio gimnasio;

    public GimnasioControlador() {

        this.gimnasio = Gimnasio.getInstancia();
    }
    public boolean registrarCliente(Cliente cliente) {return gimnasio.registrarCliente(cliente);}

    public Cliente buscarClientePorTelefono(String telefono) {return gimnasio.buscarClientePorTelefono(telefono);}
    public boolean actualizarCliente(Cliente cliente) {return gimnasio.actualizarCliente(cliente);}

    public boolean eliminarCliente(String id) {
        return gimnasio.eliminarCliente(id);
    }

    public boolean registrarEntrenador(Entrenador entrenador) {
        return gimnasio.registrarEntrenador(entrenador);
    }

    public boolean actualizarEntrenador(Entrenador entrenador) {
        return gimnasio.actualizarEntrenador(entrenador);
    }

    public boolean eliminarEntrenador(String telefono) {
        return gimnasio.eliminarEntrenador(telefono);
    }

    public boolean registrarPlan(Plan plan) {return gimnasio.registrarPlan(plan);}

    public boolean actualizarPlan(Plan plan) {
        return gimnasio.actualizarPlan(plan);
    }

    public boolean eliminarPlan(String codigo) {
        return gimnasio.eliminarPlan(codigo);
    }


    public List<ServicioAdicional> getServiciosAdicionales() {
        return gimnasio.getListServicioAdicional();
    }

    public boolean registrarServicioAdicional(ServicioAdicional servicio) {
        return gimnasio.registrarServicioAdicional(servicio);
    }

    public boolean eliminarServicioAdicional(String codigo) {
        return gimnasio.eliminarServicioAdicional(codigo);
    }

    public List<Inscripcion> getInscripciones() {
        return gimnasio.getListInscripcion();
    }

    public boolean registrarInscripcion(Inscripcion inscripcion) {
        return gimnasio.registrarInscripcion(inscripcion);
    }

    public boolean telefonoDeClienteEsPerfecto(String telefono) {return gimnasio.validarTelefonoPerfectoCliente(telefono);}

    public double consultarIngresosPorPeriodo(LocalDate fechaInicio, LocalDate fechaFin) {return gimnasio.calcularIngresosPorPeriodo(fechaInicio, fechaFin);}

    public List<Cliente> getClientes() {
        return gimnasio.getListCliente();
    }

    public List<Plan> getPlanes() {
        return gimnasio.getListPlan();
    }
}