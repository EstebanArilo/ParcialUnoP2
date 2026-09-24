package uqvirutal.edu.co.parcialunop2.model;

/**
 * Representa la informacion general de SmartGym.
 * Encapsula los datos administrativos del negocio.
 *
 * Patron creacional Singleton: solo existe UN gimnasio en todo el
 * sistema, por lo que se controla su creacion y se expone un unico
 * punto de acceso global mediante getInstancia().
 */
public class Gimnasio {

    // Unica instancia de la clase (se crea perezosamente, en el
    // primer llamado a getInstancia()).
    private static Gimnasio instancia;

    private String nombreComercial;
    private String nit;
    private String direccion;
    private String telefono;
    private String correoElectronico;
    private String paginaWeb;

    /**
     * Constructor privado: nadie fuera de la clase puede hacer
     * "new Gimnasio(...)". Asi se evita que existan varias
     * instancias del negocio en la aplicacion.
     */
    private Gimnasio() {
    }

    /**
     * Crea (solo la primera vez) y retorna la unica instancia del
     * gimnasio. Los llamados siguientes devuelven siempre el mismo
     * objeto. Una vez obtenida la instancia, se cargan sus datos
     * con los setters (ej: Gimnasio.getInstancia().setNit("...")).
     */
    public static Gimnasio getInstancia() {
        if (instancia == null) {
            instancia = new Gimnasio();
        }
        return instancia;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getPaginaWeb() {
        return paginaWeb;
    }

    public void setPaginaWeb(String paginaWeb) {
        this.paginaWeb = paginaWeb;
    }

    @Override
    public String toString() {
        return nombreComercial + " (NIT: " + nit + ")";
    }
}