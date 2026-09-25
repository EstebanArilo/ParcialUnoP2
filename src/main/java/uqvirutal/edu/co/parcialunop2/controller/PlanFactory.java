package uqvirutal.edu.co.parcialunop2.controller;

import uqvirutal.edu.co.parcialunop2.model.*;

/**
 * Fabrica encargada de instanciar los diferentes tipos de planes.
 */
public class PlanFactory {

    /**
     * Crea un plan basico o premium asociandolo a un cliente obligatorio.
     */
    public static Plan crearPlan(TipoPlan tipoPlan, String codigo, String nombre,
                                 String descripcion, int duracionMeses, double valorMensual,
                                 Cliente cliente) {
        if (tipoPlan == TipoPlan.PERSONALIZADO) {
            throw new IllegalArgumentException(
                    "Un plan PERSONALIZADO requiere datos adicionales. Use crearPlanPersonalizado."
            );
        }

        return new Plan(
                codigo,
                nombre,
                descripcion,
                duracionMeses,
                valorMensual,
                EstadoPlan.ACTIVO,
                tipoPlan,
                cliente
        );
    }

    /**
     * Crea un plan personalizado con datos especificos y su cliente asociado.
     */
    public static PlanPersonalizado crearPlanPersonalizado(String codigo, String nombre, String descripcion,
                                                           int duracionMeses, double valorMensual,
                                                           Cliente cliente, int cantidadSesiones,
                                                           String especialidadRequerida, String objetivos,
                                                           Entrenador entrenadorAsignado) {
        return new PlanPersonalizado.Builder()
                .codigo(codigo)
                .nombre(nombre)
                .descripcion(descripcion)
                .duracionMeses(duracionMeses)
                .valorMensual(valorMensual)
                .estado(EstadoPlan.ACTIVO)
                .cliente(cliente)
                .cantidadSesiones(cantidadSesiones)
                .especialidadRequerida(especialidadRequerida)
                .objetivos(objetivos)
                .entrenadorAsignado(entrenadorAsignado)
                .build();
    }
}