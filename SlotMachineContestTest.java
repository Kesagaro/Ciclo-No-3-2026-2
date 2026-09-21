/**
 * Pruebas unitarias del Ciclo 3 para SlotMachineContest.
 * Todas las pruebas se ejecutan en modo invisible (sin abrir ventanas).
 *
 * Nomenclatura:
 *   shouldXxx   - comportamiento esperado cuando todo está bien
 *   shouldNotXxx - comportamiento esperado ante una condición inválida
 *
 * @author BlancoS-GarzonR
 * @version 3.0
 */
public class SlotMachineContestTest {

    // ------------------------------------------------------------------ solve

    /** solve con n=3 no debe retornar null. */
    public void shouldSolveReturnNonNull() {
        SlotMachineContest c = new SlotMachineContest();
        int[][] actions = c.solve(3);
        assert actions != null : "solve no debe retornar null";
    }

    /** solve con n=3 debe retornar al menos una acción. */
    public void shouldSolveReturnNonEmptyArray() {
        SlotMachineContest c = new SlotMachineContest();
        int[][] actions = c.solve(3);
        assert actions != null : "solve no debe retornar null";
        assert actions.length > 0 : "solve debe retornar al menos una acción";
    }

    /** solve con n=5 no debe superar el límite de 10 000 acciones. */
    public void shouldSolveActionsWithinLimit() {
        SlotMachineContest c = new SlotMachineContest();
        int[][] actions = c.solve(5);
        assert actions != null : "solve no debe retornar null";
        assert actions.length <= 10000 : "solve no debe superar 10 000 acciones";
    }

    /** Cada acción de solve con n=4 debe tener exactamente dos valores: rueda y pasos. */
    public void shouldSolveEachActionHasTwoElements() {
        SlotMachineContest c = new SlotMachineContest();
        int[][] actions = c.solve(4);
        assert actions != null : "solve no debe retornar null";
        for (int[] action : actions) {
            assert action.length == 2 : "Cada acción debe tener exactamente 2 valores";
        }
    }

    /** Las ruedas indicadas en las acciones de solve con n=4 deben estar entre 2 y n. */
    public void shouldSolveActionsHaveValidWheelIndex() {
        int n = 4;
        SlotMachineContest c = new SlotMachineContest();
        int[][] actions = c.solve(n);
        assert actions != null : "solve no debe retornar null";
        for (int[] action : actions) {
            assert action[0] >= 2 && action[0] <= n
                : "Rueda " + action[0] + " fuera del rango [2," + n + "]";
        }
    }

    /** Los pasos de cada acción de solve con n=4 deben ser positivos. */
    public void shouldSolveActionsHavePositiveSteps() {
        SlotMachineContest c = new SlotMachineContest();
        int[][] actions = c.solve(4);
        assert actions != null : "solve no debe retornar null";
        for (int[] action : actions) {
            assert action[1] > 0 : "Los pasos deben ser positivos, se encontró " + action[1];
        }
    }

    /** solve con n=3 produce una secuencia de acciones válida. */
    public void shouldSolveWorksForN3() {
        SlotMachineContest c = new SlotMachineContest();
        int[][] actions = c.solve(3);
        assert actions != null : "solve(3) no debe retornar null";
        assert actions.length <= 10000 : "solve(3) excede el límite de 10 000 acciones";
    }

    /** solve con n=5 produce una secuencia de acciones válida. */
    public void shouldSolveWorksForN5() {
        SlotMachineContest c = new SlotMachineContest();
        int[][] actions = c.solve(5);
        assert actions != null : "solve(5) no debe retornar null";
        assert actions.length <= 10000 : "solve(5) excede el límite de 10 000 acciones";
    }

    /** solve con n=10 produce una secuencia de acciones válida. */
    public void shouldSolveWorksForN10() {
        SlotMachineContest c = new SlotMachineContest();
        int[][] actions = c.solve(10);
        assert actions != null : "solve(10) no debe retornar null";
        assert actions.length <= 10000 : "solve(10) excede el límite de 10 000 acciones";
    }

    /** El número de acciones de solve con n=8 no supera cuatro veces n al cuadrado. */
    public void shouldSolveActionsCountIsQuadratic() {
        int n = 8;
        SlotMachineContest c = new SlotMachineContest();
        int[][] actions = c.solve(n);
        assert actions != null : "solve no debe retornar null";
        int maximo = 4 * n * n;
        assert actions.length <= maximo
            : "solve(" + n + ") usó " + actions.length + " acciones, se esperaban <= " + maximo;
    }

    // ------------------------------------------------------------------ simulate

    /** simulate con n=3 no debe lanzar ningún error. */
    public void shouldSimulateRunsWithoutException() {
        SlotMachineContest c = new SlotMachineContest();
        boolean sinError = true;
        try {
            c.simulate(3);
        } catch (Exception e) {
            sinError = false;
        }
        assert sinError : "simulate(3) no debe lanzar excepciones";
    }

    /** simulate con n=5 no debe lanzar ningún error. */
    public void shouldSimulateRunsForN5() {
        SlotMachineContest c = new SlotMachineContest();
        boolean sinError = true;
        try {
            c.simulate(5);
        } catch (Exception e) {
            sinError = false;
        }
        assert sinError : "simulate(5) no debe lanzar excepciones";
    }
}
