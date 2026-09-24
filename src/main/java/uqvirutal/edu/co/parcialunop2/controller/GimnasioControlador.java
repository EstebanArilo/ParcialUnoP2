package uqvirutal.edu.co.parcialunop2.controller;

import uqvirutal.edu.co.parcialunop2.model.Cliente;
import uqvirutal.edu.co.parcialunop2.model.Entrenador;
import uqvirutal.edu.co.parcialunop2.model.Gimnasio;
import uqvirutal.edu.co.parcialunop2.model.Inscripcion;
import uqvirutal.edu.co.parcialunop2.model.Plan;
import uqvirutal.edu.co.parcialunop2.model.ServicioAdicional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Controlador de la aplicacion (patron MVC). Administra las colecciones
 * del dominio y expone las operaciones que la interfaz grafica (Vista)
 * invoca, sin que la Vista conozca la logica interna del negocio.
 */
public class GimnasioControlador {

    private Gimnasio gimnasio;
    private List<Cliente> clientes;
    private List<Plan> planes;
    private List<Entrenador> entrenadores;
    private List<ServicioAdicional> servicios;
    private List<Inscripcion> inscripciones;

    public GimnasioControlador(Gimnasio gimnasio) {
        this.gimnasio = gimnasio;
        this.clientes = new ArrayList<>();
        this.planes = new ArrayList<>();
        this.entrenadores = new ArrayList<>();
        this.servicios = new ArrayList<>();
        this.inscripciones = new ArrayList<>();
    }

    // ----- Registro basico -----

    public void registrarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public void registrarPlan(Plan plan) {
        planes.add(plan);
    }

    public void registrarEntrenador(Entrenador entrenador) {
        entrenadores.add(entrenador);
    }

    public void registrarServicio(ServicioAdicional servicio) {
        servicios.add(servicio);
    }

    public void registrarInscripcion(Inscripcion inscripcion) {
        inscripciones.add(inscripcion);
    }

    // ----- Consulta 1: buscar cliente por telefono -----

    /**
     * Busca un cliente por su numero de telefono.
     * Retorna null si no se encuentra ningun cliente con ese telefono.
     */
    public Cliente buscarClientePorTelefono(String telefono) {
        for (Cliente cliente : clientes) {
            if (cliente.getTelefono().equals(telefono)) {
                return cliente;
            }
        }
        return null;
    }

    // ----- Consulta 2: numero perfecto -----

    /**
     * Determina si un numero es "perfecto": la suma de sus divisores
     * propios (menores que el mismo numero) es igual al numero.
     * Ejemplo: 6 = 1 + 2 + 3.
     */
    public boolean esNumeroPerfecto(int numero) {
        if (numero <= 0) {
            return false;
        }
        int sumaDivisores = 0;
        for (int i = 1; i < numero; i++) {
            if (numero % i == 0) {
                sumaDivisores += i;
            }
        }
        return sumaDivisores == numero;
    }

    /**
     * Busca un cliente por telefono y, de paso, indica si el telefono
     * (interpretado como numero) es un numero perfecto.
     */
    public boolean telefonoDeClienteEsPerfecto(String telefono) {
        Cliente cliente = buscarClientePorTelefono(telefono);
        if (cliente == null) {
            return false;
        }
        try {
            int numero = Integer.parseInt(telefono);
            return esNumeroPerfecto(numero);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // ----- Consulta 3: ingresos por periodo -----

    /**
     * Recorre las inscripciones registradas en un periodo (rango de fechas)
     * y acumula el valor final de cada una.
     */
    public double consultarIngresosPorPeriodo(LocalDate fechaInicio, LocalDate fechaFin) {
        double totalIngresos = 0.0;
        for (Inscripcion inscripcion : inscripciones) {
            LocalDate fecha = inscripcion.getFechaInscripcion();
            boolean dentroDelPeriodo = !fecha.isBefore(fechaInicio) && !fecha.isAfter(fechaFin);
            if (dentroDelPeriodo) {
                totalIngresos += inscripcion.calcularValorFinal();
            }
        }
        return totalIngresos;
    }

    // ----- Getters para la Vista -----

    public Gimnasio getGimnasio() {
        return gimnasio;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public List<Plan> getPlanes() {
        return planes;
    }

    public List<Entrenador> getEntrenadores() {
        return entrenadores;
    }

    public List<ServicioAdicional> getServicios() {
        return servicios;
    }

    public List<Inscripcion> getInscripciones() {
        return inscripciones;
    }
}