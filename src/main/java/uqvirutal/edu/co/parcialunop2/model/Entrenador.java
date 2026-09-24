
package uqvirutal.edu.co.parcialunop2.model;

/**
 * Representa un entrenador del gimnasio, asignable a clientes
 * con planes personalizados.
 */
public class Entrenador {

    private String identificacion;
    private String nombre;
    private String especialidad;
    private String telefono;
    private double tarifaPorSesion;

    public Entrenador(String identificacion, String nombre, String especialidad,
                      String telefono, double tarifaPorSesion) {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.telefono = telefono;
        this.tarifaPorSesion = tarifaPorSesion;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public double getTarifaPorSesion() {
        return tarifaPorSesion;
    }

    public void setTarifaPorSesion(double tarifaPorSesion) {
        this.tarifaPorSesion = tarifaPorSesion;
    }

    /**
     * Calcula el costo del acompañamiento del entrenador segun
     * la cantidad de sesiones contratadas en un plan personalizado.
     */
    public double calcularCostoSesiones(int cantidadSesiones) {
        return tarifaPorSesion * cantidadSesiones;
    }

    @Override
    public String toString() {
        return nombre + " - " + especialidad;
    }
}
