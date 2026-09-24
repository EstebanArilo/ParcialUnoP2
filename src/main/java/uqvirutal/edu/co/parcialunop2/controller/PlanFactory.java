package uqvirutal.edu.co.parcialunop2.controller;

import uqvirutal.edu.co.parcialunop2.model.EstadoPlan;
import uqvirutal.edu.co.parcialunop2.model.Entrenador;
import uqvirutal.edu.co.parcialunop2.model.Plan;
import uqvirutal.edu.co.parcialunop2.model.PlanPersonalizado;
import uqvirutal.edu.co.parcialunop2.model.TipoPlan;

/**
 * Patron creacional Factory Method.
 * Centraliza la creacion de los distintos tipos de Plan, evitando
 * que el controlador (o la interfaz) tenga que conocer los detalles
 * de construccion de cada tipo de plan (Single Responsibility Principle).
 */
public class PlanFactory {

    /**
     * Crea un plan basico o premium (no requieren datos adicionales).
     */
    public static Plan crearPlan(TipoPlan tipoPlan, String codigo, String nombre,
                                 String descripcion, int duracionMeses, double valorMensual) {
        if (tipoPlan == TipoPlan.PERSONALIZADO) {
            throw new IllegalArgumentException(
                    "Un plan PERSONALIZADO requiere datos adicionales. Use crearPlanPersonalizado().");
        }
        return new Plan(codigo, nombre, descripcion, duracionMeses, valorMensual,
                EstadoPlan.ACTIVO, tipoPlan);
    }

    /**
     * Crea un plan personalizado con sus datos especificos, armando
     * el objeto internamente con PlanPersonalizado.Builder.
     */
    public static PlanPersonalizado crearPlanPersonalizado(String codigo, String nombre,
                                                           String descripcion, int duracionMeses, double valorMensual,
                                                           int cantidadSesiones, String especialidadRequerida, String objetivosCliente,
                                                           Entrenador entrenadorAsignado) {
        return new PlanPersonalizado.Builder()
                .codigo(codigo)
                .nombre(nombre)
                .descripcion(descripcion)
                .duracionMeses(duracionMeses)
                .valorMensual(valorMensual)
                .estado(EstadoPlan.ACTIVO)
                .cantidadSesiones(cantidadSesiones)
                .especialidadRequerida(especialidadRequerida)
                .objetivosCliente(objetivosCliente)
                .entrenadorAsignado(entrenadorAsignado)
                .build();
    }
}