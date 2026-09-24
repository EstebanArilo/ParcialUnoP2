
package uqvirutal.edu.co.parcialunop2.model;

/**
 * Representa un entrenador del gimnasio, asignable a clientes
 * con planes personalizados.
 */
public class Entrenador extends Persona {

    private String especialidad;
    private double tarifaPorSesion;

    /**
     * Constructor de la clase hija Entrenador que
     * extiende de Persona
     * @param nombre
     * @param id
     * @param telefono
     * @param especialidad
     * @param tarifaPorSesion
     */
    public Entrenador(String nombre, String id, String telefono, String especialidad, double tarifaPorSesion) {
        super(nombre, id, telefono);
        this.especialidad = especialidad;
        this.tarifaPorSesion = tarifaPorSesion;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public double getTarifaPorSesion() {
        return tarifaPorSesion;
    }

    public void setTarifaPorSesion(double tarifaPorSesion) {
        this.tarifaPorSesion = tarifaPorSesion;
    }

    @Override
    public String toString() {
        return "Entrenador{" +
                "especialidad='" + especialidad + '\'' +
                ", tarifaPorSesion=" + tarifaPorSesion +
                ", nombre='" + nombre + '\'' +
                ", id='" + id + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }

    /**
     * Calcula el costo del acompañamiento del entrenador segun
     * la cantidad de sesiones contratadas en un plan personalizado.
     */
    public double calcularCostoSesiones(int cantidadSesiones) {
        return tarifaPorSesion * cantidadSesiones;
    }

}
