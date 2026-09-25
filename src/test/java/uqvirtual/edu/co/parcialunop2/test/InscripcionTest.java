package uqvirtual.edu.co.parcialunop2.test;

import org.junit.jupiter.api.Test;
import uqvirutal.edu.co.parcialunop2.model.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Pruebas sobre Inscripcion: servicios adicionales, calculo del valor
 * final y aplicacion de descuentos.
 */
class InscripcionTest {

    private final Cliente cliente = new Cliente("Valentina Cruz", "CC-IN-001", "3004440001",
            "valentina.cruz@mail.com", 26, LocalDate.of(2024, 3, 1));

    private Plan crearPlanBase(String codigo, double valorMensual, int duracionMeses) {
        return new Plan(codigo, "Plan Test", "Descripcion", duracionMeses, valorMensual,
                EstadoPlan.ACTIVO, TipoPlan.BASICO, cliente);
    }

    @Test
    void testAgregarServiciosAdicionalesAUnaInscripcion() {
        Plan plan = crearPlanBase("IN-PLAN-001", 100000.0, 1);
        Inscripcion inscripcion = new Inscripcion(cliente, plan,
                LocalDate.of(2022, 1, 10), EstadoInscripcion.PAGADA);

        ServicioAdicional servicioDisponible = new ServicioAdicional("SERV-01", "Nutricion",
                "Asesoria nutricional", 20000.0, true, plan);
        ServicioAdicional servicioNoDisponible = new ServicioAdicional("SERV-02", "Masajes",
                "Masaje deportivo", 30000.0, false, plan);

        boolean agregado1 = inscripcion.agregarServicioAdicional(servicioDisponible);
        boolean agregado2 = inscripcion.agregarServicioAdicional(servicioNoDisponible);

        assertTrue(agregado1);
        assertTrue(!agregado2);
        assertTrue(inscripcion.getServiciosAdicionales().size() == 1);
        assertTrue(inscripcion.getServiciosAdicionales().contains(servicioDisponible));
    }

    @Test
    void testCalcularCorrectamenteElValorFinal() {
        Plan plan = crearPlanBase("IN-PLAN-002", 100000.0, 2); // base = 200000
        Inscripcion inscripcion = new Inscripcion(cliente, plan,
                LocalDate.of(2022, 2, 10), EstadoInscripcion.PAGADA);

        ServicioAdicional servicio = new ServicioAdicional("SERV-03", "Casillero",
                "Casillero personal", 20000.0, true, plan);
        inscripcion.agregarServicioAdicional(servicio);

        double valorEsperado = 200000.0 + 20000.0; // 220000, sin descuento

        assertTrue(Math.abs(inscripcion.calcularValorFinal() - valorEsperado) < 0.001);
    }

    @Test
    void testAplicarUnDescuento() {
        Plan plan = crearPlanBase("IN-PLAN-003", 100000.0, 2); // base = 200000
        Inscripcion inscripcion = new Inscripcion(cliente, plan,
                LocalDate.of(2022, 3, 10), EstadoInscripcion.PAGADA);

        ServicioAdicional servicio = new ServicioAdicional("SERV-04", "Casillero",
                "Casillero personal", 20000.0, true, plan);
        inscripcion.agregarServicioAdicional(servicio);

        inscripcion.setPorcentajeDescuento(0.10); // 10% de descuento

        double subtotal = 200000.0 + 20000.0; // 220000
        double valorEsperado = subtotal - (subtotal * 0.10); // 198000

        assertTrue(Math.abs(inscripcion.calcularValorFinal() - valorEsperado) < 0.001);
    }
}