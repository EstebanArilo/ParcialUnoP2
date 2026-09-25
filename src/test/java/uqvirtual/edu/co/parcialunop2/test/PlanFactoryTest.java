package uqvirtual.edu.co.parcialunop2.test;

import org.junit.jupiter.api.Test;
import uqvirutal.edu.co.parcialunop2.controller.PlanFactory;
import uqvirutal.edu.co.parcialunop2.model.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Pruebas sobre la fabrica PlanFactory (patron Factory Method).
 */
class PlanFactoryTest {

    private final Cliente cliente = new Cliente("Julian Vega", "CC-PF-001", "3002220001",
            "julian.vega@mail.com", 27, LocalDate.of(2024, 1, 1));

    @Test
    void testCrearPlanBasico() {
        Plan plan = PlanFactory.crearPlan(TipoPlan.BASICO, "PF-BAS-01", "Plan Basico",
                "Acceso general al gimnasio", 1, 60000.0, cliente);

        assertTrue(plan.getTipoPlan() == TipoPlan.BASICO);
        assertTrue(plan.getCodigo().equals("PF-BAS-01"));
        assertTrue(plan.getCliente() == cliente);
        assertTrue(plan.calcularValorBase() == 60000.0);
    }

    @Test
    void testCrearPlanPremium() {
        Plan plan = PlanFactory.crearPlan(TipoPlan.PREMIUM, "PF-PRE-01", "Plan Premium",
                "Acceso total + clases grupales", 2, 90000.0, cliente);

        assertTrue(plan.getTipoPlan() == TipoPlan.PREMIUM);
        assertTrue(plan.getCodigo().equals("PF-PRE-01"));
        assertTrue(plan.calcularValorBase() == 180000.0);
    }

    @Test
    void testCrearPlanPersonalizado() {
        Entrenador entrenador = new Entrenador("Diana Rios", "EN-PF-001", "3009990001",
                "Funcional", 20000.0);

        PlanPersonalizado plan = PlanFactory.crearPlanPersonalizado(
                "PF-PER-01", "Plan Personalizado", "Entrenamiento a la medida",
                1, 100000.0, cliente, 5, "Funcional", "Ganar masa muscular", entrenador);

        assertTrue(plan.getTipoPlan() == TipoPlan.PERSONALIZADO);
        assertTrue(plan instanceof PlanPersonalizado);
        assertTrue(plan.getCantidadSesiones() == 5);
        assertTrue(plan.getEspecialidadRequerida().equals("Funcional"));
        assertTrue(plan.getObjetivosCliente().equals("Ganar masa muscular"));
        assertTrue(plan.getEntrenadorAsignado() == entrenador);
    }
}