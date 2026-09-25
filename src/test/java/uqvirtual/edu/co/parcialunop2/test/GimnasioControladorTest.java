package uqvirtual.edu.co.parcialunop2.test;

import org.junit.jupiter.api.Test;
import uqvirutal.edu.co.parcialunop2.controller.GimnasioControlador;
import uqvirutal.edu.co.parcialunop2.controller.PlanFactory;
import uqvirutal.edu.co.parcialunop2.model.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Pruebas sobre GimnasioControlador (fachada de la clase Gimnasio, Singleton).
 *
 * NOTA: Gimnasio es un Singleton cuya instancia y listas persisten durante
 * toda la ejecucion de la suite de pruebas. Por eso cada metodo de prueba
 * usa telefonos, ids, codigos y fechas UNICOS, para no interferir con los
 * datos registrados por los demas metodos de prueba.
 */
class GimnasioControladorTest {

    private final GimnasioControlador gimnasioControlador = new GimnasioControlador();

    @Test
    void testRegistrarClienteCorrectamente() {
        Cliente cliente = new Cliente("Ana Torres", "CC-GC-001", "3001110001",
                "ana.torres@mail.com", 25, LocalDate.of(2024, 1, 10));

        boolean registrado = gimnasioControlador.registrarCliente(cliente);

        assertTrue(registrado);
        assertTrue(gimnasioControlador.getClientes().contains(cliente));
    }

    @Test
    void testBuscarClientePorTelefonoExistente() {
        Cliente cliente = new Cliente("Luis Perez", "CC-GC-002", "3001110002",
                "luis.perez@mail.com", 30, LocalDate.of(2024, 2, 5));
        gimnasioControlador.registrarCliente(cliente);

        Cliente encontrado = gimnasioControlador.buscarClientePorTelefono("3001110002");

        assertTrue(encontrado != null);
        assertTrue(encontrado.getNombre().equals("Luis Perez"));
    }

    @Test
    void testBuscarTelefonoNoRegistrado() {
        Cliente encontrado = gimnasioControlador.buscarClientePorTelefono("0000000000");

        assertTrue(encontrado == null);
    }

    @Test
    void testComprobarNumeroPerfecto() {
        // 28 es un numero perfecto (1 + 2 + 4 + 7 + 14 = 28)
        Cliente cliente = new Cliente("Marta Ruiz", "CC-GC-003", "28",
                "marta.ruiz@mail.com", 28, LocalDate.of(2024, 3, 1));
        gimnasioControlador.registrarCliente(cliente);

        boolean esPerfecto = gimnasioControlador.telefonoDeClienteEsPerfecto("28");

        assertTrue(esPerfecto);
    }

    @Test
    void testComprobarNumeroNoPerfecto() {
        // 30 no es un numero perfecto
        Cliente cliente = new Cliente("Carlos Diaz", "CC-GC-004", "30",
                "carlos.diaz@mail.com", 35, LocalDate.of(2024, 3, 2));
        gimnasioControlador.registrarCliente(cliente);

        boolean esPerfecto = gimnasioControlador.telefonoDeClienteEsPerfecto("30");

        assertTrue(!esPerfecto);
    }

    @Test
    void testConsultarIngresosDeUnPeriodoConInscripciones() {
        Cliente cliente = new Cliente("Sofia Gomez", "CC-GC-005", "3001110005",
                "sofia.gomez@mail.com", 22, LocalDate.of(2024, 4, 1));

        Plan plan = PlanFactory.crearPlan(TipoPlan.BASICO, "PLAN-GC-005", "Basico",
                "Plan basico", 1, 100000.0, cliente);

        Inscripcion inscripcion = new Inscripcion(cliente, plan,
                LocalDate.of(2020, 1, 15), EstadoInscripcion.PAGADA);

        gimnasioControlador.registrarInscripcion(inscripcion);

        double ingresos = gimnasioControlador.consultarIngresosPorPeriodo(
                LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 31));

        assertTrue(Math.abs(ingresos - inscripcion.calcularValorFinal()) < 0.001);
    }

    @Test
    void testConsultarUnPeriodoSinInscripciones() {
        double ingresos = gimnasioControlador.consultarIngresosPorPeriodo(
                LocalDate.of(1990, 1, 1), LocalDate.of(1990, 1, 31));

        assertTrue(ingresos == 0.0);
    }

    @Test
    void testSoloSeAcumulanInscripcionesDelPeriodoIndicado() {
        Cliente cliente = new Cliente("Pedro Leon", "CC-GC-006", "3001110006",
                "pedro.leon@mail.com", 40, LocalDate.of(2024, 5, 1));

        Plan plan = PlanFactory.crearPlan(TipoPlan.PREMIUM, "PLAN-GC-006", "Premium",
                "Plan premium", 1, 150000.0, cliente);

        Inscripcion dentroDelPeriodo = new Inscripcion(cliente, plan,
                LocalDate.of(2021, 5, 10), EstadoInscripcion.PAGADA);
        Inscripcion fueraDelPeriodo = new Inscripcion(cliente, plan,
                LocalDate.of(2021, 6, 10), EstadoInscripcion.PAGADA);

        gimnasioControlador.registrarInscripcion(dentroDelPeriodo);
        gimnasioControlador.registrarInscripcion(fueraDelPeriodo);

        double ingresos = gimnasioControlador.consultarIngresosPorPeriodo(
                LocalDate.of(2021, 5, 1), LocalDate.of(2021, 5, 31));

        assertTrue(Math.abs(ingresos - dentroDelPeriodo.calcularValorFinal()) < 0.001);
    }
}