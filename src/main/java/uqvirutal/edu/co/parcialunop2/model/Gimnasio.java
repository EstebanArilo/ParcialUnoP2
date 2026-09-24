package uqvirutal.edu.co.parcialunop2.model;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Representa la informacion general de SmartGym.
 * Encapsula los datos administrativos del negocio.
 *
 * Patron creacional Singleton: solo existe UN gimnasio en todo el
 * sistema, por lo que se controla su creacion y se expone un unico
 * punto de acceso global mediante getInstancia().
 */
public final class Gimnasio {

    // Unica instancia de la clase (se crea perezosamente, en el
    // primer llamado a getInstancia()).
    private static Gimnasio instancia;
    private String nombreComercial;
    private String nit;
    private String direccion;
    private String telefono;
    private String correoElectronico;
    private String paginaWeb;
    private ArrayList<Cliente> listCliente;
    private ArrayList<Entrenador> listEntrenador;
    private ArrayList<Plan> listPlan;
    private ArrayList<ServicioAdicional> listServicioAdicional;
    private ArrayList<Inscripcion> listInscripcion;

    /**
     * Constructor privado: nadie fuera de la clase puede hacer
     * "new Gimnasio(...)". Asi se evita que existan varias
     * instancias del negocio en la aplicacion.
     */
    private Gimnasio() {
        this.nombreComercial = "SmartGym";
        this.nit = "900.123.456-7";
        this.direccion = "Calle Principal # 45-12";
        this.telefono = "660982371";
        this.correoElectronico = "smartGym@gmail.com";
        this.paginaWeb = "www.smartgym.com";
        this.listCliente = new ArrayList<>();
        this.listEntrenador = new ArrayList<>();
        this.listPlan= new ArrayList<>();
        this.listServicioAdicional = new ArrayList<>();
        this.listInscripcion = new ArrayList<>();
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

    public String getNombreComercial() {return nombreComercial;}

    public void setNombreComercial(String nombreComercial) {this.nombreComercial = nombreComercial;}

    public String getNit() {return nit;}

    public void setNit(String nit) {this.nit = nit;}

    public String getDireccion() {return direccion;}

    public void setDireccion(String direccion) {this.direccion = direccion;}

    public String getTelefono() {return telefono;}

    public void setTelefono(String telefono) {this.telefono = telefono;}

    public String getCorreoElectronico() {return correoElectronico;}

    public void setCorreoElectronico(String correoElectronico) {this.correoElectronico = correoElectronico;}

    public String getPaginaWeb() {return paginaWeb;}

    public void setPaginaWeb(String paginaWeb) {this.paginaWeb = paginaWeb;}

    public ArrayList<Cliente> getListCliente() {return listCliente;}

    public void setListCliente(ArrayList<Cliente> listCliente) {this.listCliente = listCliente;}

    public ArrayList<Entrenador> getListEntrenador() {return listEntrenador;}

    public void setListEntrenador(ArrayList<Entrenador> listEntrenador) {this.listEntrenador = listEntrenador;}

    public ArrayList<Plan> getListPlan() {return listPlan;}

    public void setListPlan(ArrayList<Plan> listPlan) {this.listPlan = listPlan;}

    public ArrayList<ServicioAdicional> getListServicioAdicional() {return listServicioAdicional;}

    public void setListServicioAdicional(ArrayList<ServicioAdicional> listServicioAdicional) {this.listServicioAdicional = listServicioAdicional;}

    public ArrayList<Inscripcion> getListInscripcion() {return listInscripcion;}

    public void setListInscripcion(ArrayList<Inscripcion> listInscripcion) {this.listInscripcion = listInscripcion;}

    @Override
    public String toString() {
        return "Gimnasio{" +
                "nombreComercial='" + nombreComercial + '\'' +
                ", nit='" + nit + '\'' +
                ", direccion='" + direccion + '\'' +
                ", telefono='" + telefono + '\'' +
                ", correoElectronico='" + correoElectronico + '\'' +
                ", paginaWeb='" + paginaWeb + '\'' +
                ", listCliente=" + listCliente +
                ", listEntrenador=" + listEntrenador +
                ", listPlan=" + listPlan +
                ", listServicioAdicional=" + listServicioAdicional +
                ", listInscripcion=" + listInscripcion +
                '}';
    }

    /**
     * Metodo CRUD para Cliente
     * @param cliente
     * @return
     */
    public boolean registrarCliente(Cliente cliente) {
        if (cliente == null || buscarClientePorTelefono(cliente.getId()) != null) {
            return false;
        }
        return listCliente.add(cliente);
    }


    public Cliente buscarClientePorTelefono(String telefono) {
        for (Cliente c : listCliente) {
            if (c.getTelefono().equalsIgnoreCase(telefono)) {
                return c;
            }
        }
        return null;
    }

    public boolean actualizarCliente(Cliente clienteActualizado) {
        if (clienteActualizado == null) return false;
        Cliente exist = buscarClientePorTelefono(clienteActualizado.getId());
        if (exist != null) {
            exist.setNombre(clienteActualizado.getNombre());
            exist.setTelefono(clienteActualizado.getTelefono());
            exist.setCorreo(clienteActualizado.getCorreo());
            exist.setEdad(clienteActualizado.getEdad());
            return true;
        }
        return false;
    }

    public boolean eliminarCliente(String documento) {
        Cliente c = buscarClientePorTelefono(documento);
        if (c != null) {
            return listCliente.remove(c);
        }
        return false;
    }

    /**
     * Metodo CRUD para Entrenador
     * @param entrenador
     * @return
     */
    public boolean registrarEntrenador(Entrenador entrenador) {
        if (entrenador == null || buscarEntrenadorPorId(entrenador.getId()) != null) {
            return false;
        }
        return listEntrenador.add(entrenador);
    }

    public Entrenador buscarEntrenadorPorId(String id) {
        for (Entrenador e : listEntrenador) {
            if (e.getId().equalsIgnoreCase(id)) {
                return e;
            }
        }
        return null;
    }

    public boolean actualizarEntrenador(Entrenador entrenadorActualizado) {
        if (entrenadorActualizado == null) return false;
        Entrenador exist = buscarEntrenadorPorId(entrenadorActualizado.getId());
        if (exist != null) {
            exist.setNombre(entrenadorActualizado.getNombre());
            exist.setTelefono(entrenadorActualizado.getTelefono());
            exist.setEspecialidad(entrenadorActualizado.getEspecialidad());
            exist.setTarifaPorSesion(entrenadorActualizado.getTarifaPorSesion());
            return true;
        }
        return false;
    }

    public boolean eliminarEntrenador(String id) {
        Entrenador e = buscarEntrenadorPorId(id);
        if (e != null) {
            return listEntrenador.remove(e);
        }
        return false;
    }

    /**
     * Metodo CRUD para Plan
     * @param plan
     * @return
     */
    public boolean registrarPlan(Plan plan) {
        if (plan == null || buscarPlanPorCodigo(plan.getCodigo()) != null) {
            return false;
        }
        return listPlan.add(plan);
    }

    public Plan buscarPlanPorCodigo(String codigo) {
        for (Plan p : listPlan) {
            if (p.getCodigo().equalsIgnoreCase(codigo)) {
                return p;
            }
        }
        return null;
    }

    public boolean actualizarPlan(Plan planActualizado) {
        if (planActualizado == null) return false;
        Plan exist = buscarPlanPorCodigo(planActualizado.getCodigo());
        if (exist != null) {
            exist.setNombre(planActualizado.getNombre());
            exist.setDescripcion(planActualizado.getDescripcion());
            exist.setDuracionMeses(planActualizado.getDuracionMeses());
            exist.setValorMensual(planActualizado.getValorMensual());
            exist.setEstado(planActualizado.getEstado());
            return true;
        }
        return false;
    }

    public boolean eliminarPlan(String codigo) {
        Plan p = buscarPlanPorCodigo(codigo);
        if (p != null) {
            return listPlan.remove(p);
        }
        return false;
    }

    /**
     * Verifica que el numero de telefono del cliente
     * sea un numero perfecto
     * @param telefono
     * @return
     */
    public boolean validarTelefonoPerfectoCliente(String telefono) {
        Cliente cliente = buscarClientePorTelefono(telefono);
        if (cliente != null) {
            String soloNumeros = telefono.replaceAll("\\D", "");
            if (!soloNumeros.isEmpty()) {
                long num = Long.parseLong(soloNumeros);
                return esNumeroPerfecto(num);
            }
        }
        return false;
    }

    private boolean esNumeroPerfecto(long numero) {
        if (numero <= 1) return false;
        long suma = 1;
        for (long i = 2; i * i <= numero; i++) {
            if (numero % i == 0) {
                suma += i;
                if (i * i != numero) {
                    suma += numero / i;
                }
            }
        }
        return suma == numero;
    }

    /**
     * Recorre las inscripciones y acumula los ingresos generados dentro de un periodo.
     */
    public double calcularIngresosPorPeriodo(LocalDate fechaInicio, LocalDate fechaFin) {
        double total = 0.0;
        for (Inscripcion ins : listInscripcion) {
            LocalDate fecha = ins.getFechaInscripcion();
            if ((fecha.isEqual(fechaInicio) || fecha.isAfter(fechaInicio)) &&
                    (fecha.isEqual(fechaFin) || fecha.isBefore(fechaFin))) {
                total += ins.calcularValorFinal();
            }
        }
        return total;
    }
}