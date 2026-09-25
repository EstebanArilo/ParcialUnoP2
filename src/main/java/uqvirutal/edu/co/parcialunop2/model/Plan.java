package uqvirutal.edu.co.parcialunop2.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase base que representa un plan de entrenamiento general.
 * Se especializa mediante herencia en PlanPersonalizado cuando
 * el plan requiere datos adicionales (sesiones, especialidad, objetivos).
 */
public class Plan implements IClonable {

    private String codigo;
    private String nombre;
    private String descripcion;
    private int duracionMeses;
    private double valorMensual;
    private EstadoPlan estado;
    private TipoPlan tipoPlan;
    private ArrayList<String> beneficios;
    private Cliente cliente; // Relación obligatoria: Todo plan pertenece a un cliente
    private ArrayList<ServicioAdicional> serviciosAdicionales; // Servicios consumidos en este plan

    public Plan(String codigo, String nombre, String descripcion, int duracionMeses,
                double valorMensual, EstadoPlan estado, TipoPlan tipoPlan, Cliente cliente) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.duracionMeses = duracionMeses;
        this.valorMensual = valorMensual;
        this.estado = estado;
        this.tipoPlan = tipoPlan;
        this.cliente = cliente;
        this.beneficios = new ArrayList<>();
        this.serviciosAdicionales = new ArrayList<>();
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public ArrayList<ServicioAdicional> getServiciosAdicionales() {
        return serviciosAdicionales;
    }

    public void setServiciosAdicionales(ArrayList<ServicioAdicional> serviciosAdicionales) {
        this.serviciosAdicionales = serviciosAdicionales;
    }

    public ArrayList<String> getBeneficios() {
        return beneficios;
    }

    public void setBeneficios(ArrayList<String> beneficios) {
        this.beneficios = beneficios;
    }

    // --- MÉTODOS DE NEGOCIO ---

    /**
     * Permite asociar un servicio adicional al plan.
     */
    public void agregarServicioAdicional(ServicioAdicional servicio) {
        if (servicio != null) {
            this.serviciosAdicionales.add(servicio);
        }
    }

    /**
     * Getters y Setters previos
     */
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getDuracionMeses() {
        return duracionMeses;
    }

    public void setDuracionMeses(int duracionMeses) {
        this.duracionMeses = duracionMeses;
    }

    public double getValorMensual() {
        return valorMensual;
    }

    public void setValorMensual(double valorMensual) {
        this.valorMensual = valorMensual;
    }

    public EstadoPlan getEstado() {
        return estado;
    }

    public void setEstado(EstadoPlan estado) {
        this.estado = estado;
    }

    public TipoPlan getTipoPlan() {
        return tipoPlan;
    }

    public void setTipoPlan(TipoPlan tipoPlan) {
        this.tipoPlan = tipoPlan;
    }

    /**
     * Calcula el valor base del plan según la duración contratada.
     */
    public double calcularValorBase() {
        return valorMensual * duracionMeses;
    }

    /**
     * Calcula el valor total del plan sumando el costo base
     * más los servicios adicionales asociados.
     */
    public double calcularValorTotal() {
        double costoServicios = 0.0;
        for (ServicioAdicional servicio : serviciosAdicionales) {
            costoServicios += servicio.getPrecio();
        }
        return calcularValorBase() + costoServicios;
    }

    @Override
    public String toString() {
        String clienteNombre = (cliente != null) ? cliente.getNombre() : "Sin cliente";
        return codigo + " - " + nombre + " (" + tipoPlan + ") - Cliente: " + clienteNombre;
    }

    /**
     * Implementación del patrón Prototype.
     */
    @Override
    public Plan clonar() {
        try {
            Plan clon = (Plan) super.clone();

            // Clonación profunda de listas
            if (this.beneficios != null) {
                clon.beneficios = new ArrayList<>(this.beneficios);
            } else {
                clon.beneficios = new ArrayList<>();
            }

            if (this.serviciosAdicionales != null) {
                clon.serviciosAdicionales = new ArrayList<>(this.serviciosAdicionales);
            } else {
                clon.serviciosAdicionales = new ArrayList<>();
            }

            return clon;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}