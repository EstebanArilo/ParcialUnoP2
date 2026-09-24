package uqvirutal.edu.co.parcialunop2.model;
import java.time.LocalDate;

/**
 * Representa a un cliente registrado
 */
public class Cliente extends Persona {

    private String correo;
    private int edad;
    private LocalDate fechaRegistro;

    /**
     * Constructor de la clase hija Cliente que extiende
     * de Persona
     * @param nombre
     * @param id
     * @param telefono
     * @param correo
     * @param edad
     * @param fechaRegistro
     */
    public Cliente(String nombre, String id, String telefono, String correo, int edad, LocalDate fechaRegistro) {
        super(nombre, id, telefono);
        this.correo = correo;
        this.edad = edad;
        this.fechaRegistro = fechaRegistro;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "correo='" + correo + '\'' +
                ", edad=" + edad +
                ", fechaRegistro=" + fechaRegistro +
                ", nombre='" + nombre + '\'' +
                ", id='" + id + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}
