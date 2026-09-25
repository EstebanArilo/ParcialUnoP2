package uqvirutal.edu.co.parcialunop2.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa la adquisicion de un plan por parte de un cliente,
 * incluyendo los servicios adicionales asociados y el descuento
 * aplicado, si corresponde.
 */
public class Inscripcion {

    // Contador estático para autoincrementar el código dinámicamente
    private static int contadorInscripciones = 1;

    private String codigoInscripcion;
    private Cliente cliente;
    private Plan plan;
    private List<ServicioAdicional> serviciosAdicionales;
    private double porcentajeDescuento; // ej: 0.10 = 10%
    private LocalDate fechaInscripcion;
    private EstadoInscripcion estado;

    public Inscripcion(Cliente cliente, Plan plan, LocalDate fechaInscripcion, EstadoInscripcion estado) {
        // Genera un código autoincrementable formateado (INS-001, INS-002, etc.)
        this.codigoInscripcion = String.format("INS-%03d", contadorInscripciones++);
        this.cliente = cliente;
        this.plan = plan;
        this.fechaInscripcion = fechaInscripcion;
        this.serviciosAdicionales = new ArrayList<>();
        this.porcentajeDescuento = 0.0;
        this.estado = estado;
    }

    public String getCodigoInscripcion() {
        return codigoInscripcion;
    }

    public void setCodigoInscripcion(String codigoInscripcion) {
        this.codigoInscripcion = codigoInscripcion;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public List<ServicioAdicional> getServiciosAdicionales() { return serviciosAdicionales; }

    public void setServiciosAdicionales(List<ServicioAdicional> serviciosAdicionales) { this.serviciosAdicionales = serviciosAdicionales; }

    public double getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(double porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }

    public LocalDate getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(LocalDate fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public EstadoInscripcion getEstado() { return estado; }

    public void setEstado(EstadoInscripcion estado) { this.estado = estado; }

    /**
     * Asocia un servicio adicional a la inscripcion, solo si
     * el servicio esta disponible.
     */
    public boolean agregarServicioAdicional(ServicioAdicional servicio) {
        if (servicio != null && servicio.isDisponible()) {
            serviciosAdicionales.add(servicio);
            return true;
        }
        return false;
    }

    /**
     * Suma el valor de todos los servicios adicionales asociados.
     */
    private double calcularValorServicios() {
        double total = 0.0;
        for (ServicioAdicional servicio : serviciosAdicionales) {
            total += servicio.getPrecio();
        }
        return total;
    }

    /**
     * Calcula el valor final de la inscripcion: valor base del plan
     * (polimorfico, segun el tipo de Plan) + servicios adicionales,
     * menos el descuento aplicado.
     */
    public double calcularValorFinal() {
        double subtotal = plan.calcularValorBase() + calcularValorServicios();
        double descuento = subtotal * porcentajeDescuento;
        return subtotal - descuento;
    }

    public double getMontoTotal() {
        return calcularValorFinal();
    }

    @Override
    public String toString() {
        return codigoInscripcion + " - " + cliente.getNombre() + " -> " + plan.getNombre()
                + " | Total: $" + calcularValorFinal();
    }
}