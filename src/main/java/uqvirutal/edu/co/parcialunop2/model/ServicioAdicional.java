package uqvirutal.edu.co.parcialunop2.model;

/**
 * Representa un servicio adicional que un cliente puede asociar
 * a su inscripcion (ej: nutricion, masajes, casillero, etc).
 */
public class ServicioAdicional {

    private String codigo;
    private String nombre;
    private String descripcion;
    private double precio;
    private boolean disponible;

    public ServicioAdicional(String codigo, String nombre, String descripcion,
                             double precio, boolean disponible) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.disponible = disponible;
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

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    @Override
    public String toString() {
        return nombre + " ($" + precio + ")";
    }
}