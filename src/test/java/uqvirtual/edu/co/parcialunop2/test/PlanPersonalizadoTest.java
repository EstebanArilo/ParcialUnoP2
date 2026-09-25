package uqvirtual.edu.co.parcialunop2.test;

import org.junit.jupiter.api.Test;
import uqvirutal.edu.co.parcialunop2.model.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Pruebas sobre PlanPersonalizado (patron Builder) y su relacion con Entrenador.
 */
class PlanPersonalizadoTest {

    private final Cliente cliente = new Cliente("Camila Rojas", "CC-PP-001", "3003330001",
            "camila.rojas@mail.com", 24, LocalDate.of(2024, 2, 1));

    @Test
    void testRegistrarPlanPersonalizadoConSesionesEspecialidadYObjetivos() {
        PlanPersonalizado plan = new PlanPersonalizado.Builder()
                .codigo("PP-001")
                .nombre("Plan a la medida")
                .descripcion("Rutina personalizada")
                .duracionMeses(1)
                .valorMensual(80000.0)
                .cliente(cliente)
                .cantidadSesiones(8)
                .especialidadRequerida("Pesas")
                .objetivosCliente("Perder grasa corporal")
                .build();

        assertTrue(plan.getCantidadSesiones() == 8);
        assertTrue(plan.getEspecialidadRequerida().equals("Pesas"));
        assertTrue(plan.getObjetivosCliente().equals("Perder grasa corporal"));
        assertTrue(plan.getTipoPlan() == TipoPlan.PERSONALIZADO);
    }

    @Test
    void testAsociarCorrectamenteUnEntrenador() {
        Entrenador entrenador = new Entrenador("Jorge Salas", "EN-PP-001", "3009990002",
                "Crossfit", 15000.0);

        PlanPersonalizado plan = new PlanPersonalizado.Builder()
                .codigo("PP-002")
                .nombre("Plan con entrenador")
                .descripcion("Rutina guiada")
                .duracionMeses(1)
                .valorMensual(80000.0)
                .cliente(cliente)
                .cantidadSesiones(4)
                .especialidadRequerida("Crossfit")
                .objetivosCliente("Mejorar resistencia")
                .entrenadorAsignado(entrenador)
                .build();

        double valorEsperado = 80000.0 + (15000.0 * 4);

        assertTrue(plan.getEntrenadorAsignado() == entrenador);
        assertTrue(Math.abs(plan.calcularValorBase() - valorEsperado) < 0.001);
    }
}