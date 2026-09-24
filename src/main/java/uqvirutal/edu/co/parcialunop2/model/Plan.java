package uqvirutal.edu.co.parcialunop2.model;

/**
 * Clase base que representa un plan de entrenamiento general.
 * Se especializa mediante herencia en PlanPersonalizado cuando
 * el plan requiere datos adicionales (sesiones, especialidad, objetivos).
 */
public class Plan {

    protected String codigo;
    protected String nombre;
    protected String descripcion;
    protected int duracionMeses;
    protected double valorMensual;
    protected EstadoPlan estado;
    protected TipoPlan tipoPlan;

    public Plan(String codigo, String nombre, String descripcion, int duracionMeses,
                double valorMensual, EstadoPlan estado, TipoPlan tipoPlan) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.duracionMeses = duracionMeses;
        this.valorMensual = valorMensual;
        this.estado = estado;
        this.tipoPlan = tipoPlan;
    }

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
     * Calcula el valor base del plan segun la duracion contratada.
     * Los planes personalizados sobrescriben este metodo (polimorfismo)
     * para sumar el costo del entrenador asignado.
     */
    public double calcularValorBase() {
        return valorMensual * duracionMeses;
    }

    @Override
    public String toString() {
        return codigo + " - " + nombre + " (" + tipoPlan + ")";
    }
}