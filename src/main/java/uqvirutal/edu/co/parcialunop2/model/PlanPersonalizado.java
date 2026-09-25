package uqvirutal.edu.co.parcialunop2.model;

/**
 * Especializacion de Plan para el caso de planes personalizados,
 * que requieren cantidad de sesiones, especialidad y objetivos
 * ademas de un entrenador asignado.
 *
 * Patron creacional Builder: como el objeto tiene muchos atributos
 * (varios heredados de Plan + los propios de este subtipo), se
 * arma paso a paso con nombres claros en vez de un constructor
 * con muchos parametros posicionales.
 */
public class PlanPersonalizado extends Plan {

    private int cantidadSesiones;
    private String especialidadRequerida;
    private String objetivosCliente;
    private Entrenador entrenadorAsignado;

    /**
     * Constructor privado: la unica forma de crear un
     * PlanPersonalizado es a traves de su Builder.
     */
    private PlanPersonalizado(Builder builder) {

        super(builder.codigo, builder.nombre, builder.descripcion, builder.duracionMeses,
                builder.valorMensual, builder.estado, TipoPlan.PERSONALIZADO, builder.cliente);
        this.cantidadSesiones = builder.cantidadSesiones;
        this.especialidadRequerida = builder.especialidadRequerida;
        this.objetivosCliente = builder.objetivosCliente;
        this.entrenadorAsignado = builder.entrenadorAsignado;
    }

    public int getCantidadSesiones() {
        return cantidadSesiones;
    }

    public void setCantidadSesiones(int cantidadSesiones) {
        this.cantidadSesiones = cantidadSesiones;
    }

    public String getEspecialidadRequerida() {
        return especialidadRequerida;
    }

    public void setEspecialidadRequerida(String especialidadRequerida) {
        this.especialidadRequerida = especialidadRequerida;
    }

    public String getObjetivosCliente() {
        return objetivosCliente;
    }

    public void setObjetivosCliente(String objetivosCliente) {
        this.objetivosCliente = objetivosCliente;
    }

    public Entrenador getEntrenadorAsignado() {
        return entrenadorAsignado;
    }

    public void setEntrenadorAsignado(Entrenador entrenadorAsignado) {
        this.entrenadorAsignado = entrenadorAsignado;
    }

    /**
     * Sobrescribe el calculo del valor base (polimorfismo): al valor
     * normal del plan se le suma el costo de las sesiones con el
     * entrenador asignado, si existe.
     */
    @Override
    public double calcularValorBase() {
        double valorBase = super.calcularValorBase();
        if (entrenadorAsignado != null) {
            valorBase += entrenadorAsignado.calcularCostoSesiones(cantidadSesiones);
        }
        return valorBase;
    }

    @Override
    public String toString() {
        return super.toString() + " - Sesiones: " + cantidadSesiones
                + " - Especialidad: " + especialidadRequerida;
    }

    /**
     * Builder de PlanPersonalizado.
     */
    public static class Builder {
        private String codigo;
        private String nombre;
        private String descripcion;
        private int duracionMeses;
        private double valorMensual;
        private EstadoPlan estado = EstadoPlan.ACTIVO;
        private Cliente cliente; // Nuevo atributo en el Builder
        private int cantidadSesiones;
        private String especialidadRequerida;
        private String objetivosCliente;
        private Entrenador entrenadorAsignado;

        public Builder codigo(String codigo) {
            this.codigo = codigo;
            return this;
        }

        public Builder nombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public Builder descripcion(String descripcion) {
            this.descripcion = descripcion;
            return this;
        }

        public Builder duracionMeses(int duracionMeses) {
            this.duracionMeses = duracionMeses;
            return this;
        }

        public Builder valorMensual(double valorMensual) {
            this.valorMensual = valorMensual;
            return this;
        }

        public Builder estado(EstadoPlan estado) {
            this.estado = estado;
            return this;
        }

        public Builder cliente(Cliente cliente) {
            this.cliente = cliente;
            return this;
        }

        public Builder cantidadSesiones(int cantidadSesiones) {
            this.cantidadSesiones = cantidadSesiones;
            return this;
        }

        public Builder especialidadRequerida(String especialidadRequerida) {
            this.especialidadRequerida = especialidadRequerida;
            return this;
        }

        public Builder objetivosCliente(String objetivosCliente) {
            this.objetivosCliente = objetivosCliente;
            return this;
        }

        // Metodo alternativo si en tu codigo tenias objetivos(String)
        public Builder objetivos(String objetivosCliente) {
            this.objetivosCliente = objetivosCliente;
            return this;
        }

        public Builder entrenadorAsignado(Entrenador entrenadorAsignado) {
            this.entrenadorAsignado = entrenadorAsignado;
            return this;
        }

        /**
         * Construye el PlanPersonalizado con los datos configurados.
         */
        public PlanPersonalizado build() {
            if (codigo == null || nombre == null || cliente == null) {
                throw new IllegalStateException(
                        "codigo, nombre y cliente son obligatorios para construir un PlanPersonalizado.");
            }
            return new PlanPersonalizado(this);
        }
    }
}