import java.util.ArrayList;

/**
 * Resuelve el problema de la tragamonedas de la maratón ICPC 2025.
 *
 * Usa SlotMachine solo como herramienta de prueba y como simulador visual.
 * No contiene la lógica interna del simulador.
 *
 * Para resolver (solve): solo puede crear la máquina, girar ruedas y
 * consultar cuántos símbolos distintos hay visibles.
 *
 * Para simular (simulate): además puede hacer visible o invisible la máquina.
 *
 * Algoritmo: se toma la rueda 1 como referencia fija. Para cada rueda
 * siguiente (de 2 a n), se prueban todas sus posiciones girando un paso
 * a la vez y se deja en la que menos símbolos distintos produce.
 * Esto se repite hasta que todas las ruedas muestren el mismo símbolo.
 *
 * @author BlancoS-GarzonR
 * @version 3.0
 */
public class SlotMachineContest {

    /**
     * Resuelve el problema para una máquina de n ruedas y n símbolos,
     * creada con posiciones iniciales aleatorias. La máquina es invisible.
     *
     * Solo usa: crear la máquina, girar ruedas y consultar distintos.
     *
     * @param n cantidad de ruedas y símbolos (mínimo 2)
     * @return lista de acciones {rueda, pasos} necesarias para lograr el jackpot
     */
    public int[][] solve(int n) {
        ArrayList<int[]> actions = new ArrayList<int[]>();
        SlotMachine sm = new SlotMachine(n);

        for (int wheel = 2; wheel <= n; wheel++) {
            int bestDistinct = sm.distinctSymbols();
            int bestJ = 0;

            // Probar las n posiciones de esta rueda girando un paso a la vez
            for (int step = 1; step <= n; step++) {
                sm.spin(wheel, 1);
                actions.add(new int[]{wheel, 1});
                int d = sm.distinctSymbols();
                if (d == 1) {
                    // Jackpot alcanzado: retornar la secuencia
                    return actions.toArray(new int[0][]);
                }
                if (d < bestDistinct) {
                    bestDistinct = d;
                    bestJ = step;
                }
            }
            // Tras n pasos, la rueda volvió a su posición original.
            // Avanzar bestJ pasos para quedar en la mejor posición encontrada.
            if (bestJ > 0) {
                sm.spin(wheel, bestJ);
                actions.add(new int[]{wheel, bestJ});
                if (sm.distinctSymbols() == 1) {
                    return actions.toArray(new int[0][]);
                }
            }
        }

        return actions.toArray(new int[0][]);
    }

    /**
     * Simula visualmente la solución del problema.
     * Crea su propia máquina con n ruedas, la hace visible y aplica
     * el mismo algoritmo que solve, mostrando la animación de cada giro.
     * Al terminar, oculta la máquina.
     *
     * Solo usa: crear la máquina, girar ruedas, consultar distintos,
     * hacer visible y hacer invisible.
     *
     * @param n cantidad de ruedas y símbolos (mínimo 2)
     */
    public void simulate(int n) {
        SlotMachine sm = new SlotMachine(n);
        sm.makeVisible();

        for (int wheel = 2; wheel <= n; wheel++) {
            int bestDistinct = sm.distinctSymbols();
            int bestJ = 0;

            for (int step = 1; step <= n; step++) {
                sm.spin(wheel, 1);
                int d = sm.distinctSymbols();
                if (d == 1) {
                    // Jackpot alcanzado: ocultar y terminar
                    sm.makeInvisible();
                    return;
                }
                if (d < bestDistinct) {
                    bestDistinct = d;
                    bestJ = step;
                }
            }
            if (bestJ > 0) {
                sm.spin(wheel, bestJ);
                if (sm.distinctSymbols() == 1) {
                    sm.makeInvisible();
                    return;
                }
            }
        }

        sm.makeInvisible();
    }
}
