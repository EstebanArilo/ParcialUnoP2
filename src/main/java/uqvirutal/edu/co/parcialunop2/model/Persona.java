package uqvirutal.edu.co.parcialunop2.model;

public abstract class Persona {
    protected String nombre;
    protected String id;
    protected String telefono;

    /**
     * Constructor de la clase padre persona de la cual extienden
     * Cliente y Entrenador
     * @param nombre
     * @param id
     * @param telefono
     */
    public Persona(String nombre, String id, String telefono) {
        this.nombre = nombre;
        this.id = id;
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "nombre='" + nombre + '\'' +
                ", id='" + id + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}
