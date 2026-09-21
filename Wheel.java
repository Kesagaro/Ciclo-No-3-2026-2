import java.util.ArrayList;

/**
 * Rueda de la tragamonedas: lista ordenada de símbolos con un índice
 * que apunta al símbolo actualmente visible. Se dibuja como un marco
 * rectangular con el símbolo visible centrado en su interior.
 *
 * <p>Ciclo 2: soporte de bloqueo ({@code lock}/{@code unlock}) y giro
 * por número de pasos ({@code spin(steps)}) con animación visible.
 *
 * @author BlancoS-GarzonR
 * @version 2.0
 */
public class Wheel {

    /** Ancho del marco en píxeles. */
    public static final int WIDTH  = 80;
    /** Alto del marco en píxeles. */
    public static final int HEIGHT = 120;

    private static final int RECT_INIT_X  = 70;
    private static final int RECT_INIT_Y  = 15;
    private static final int SYMBOL_PAD_X = 12;
    private static final int SYMBOL_PAD_Y = 32;

    private final ArrayList<Symbol> symbols;
    private int currentIndex;
    private final Rectangle frame;
    private boolean isVisible;
    private boolean locked;
    private int xPos;
    private int yPos;

    /**
     * Crea una rueda vacía e invisible en la posición indicada.
     *
     * @param x coordenada x del marco en el canvas
     * @param y coordenada y del marco en el canvas
     */
    public Wheel(int x, int y) {
        symbols = new ArrayList<Symbol>();
        currentIndex = 0;
        isVisible = false;
        locked = false;
        frame = new Rectangle();
        frame.changeSize(HEIGHT, WIDTH);
        frame.changeColor("blue");
        xPos = RECT_INIT_X;
        yPos = RECT_INIT_Y;
        moveFrameTo(x, y);
    }

    /**
     * Avanza el símbolo visible una posición de forma circular.
     * No hace nada si la rueda está vacía o bloqueada.
     */
    public void spin() {
        if (symbols.isEmpty() || locked) return;
        hideCurrentSymbol();
        currentIndex = (currentIndex + 1) % symbols.size();
        showCurrentSymbol();
    }

    /**
     * Gira la rueda {@code steps} posiciones. Anima si está visible.
     * No actúa si la rueda está vacía o bloqueada.
     *
     * @param steps pasos a avanzar
     */
    public void spin(int steps) {
        if (symbols.isEmpty() || locked) return;
        for (int i = 0; i < steps; i++) {
            hideCurrentSymbol();
            currentIndex = (currentIndex + 1) % symbols.size();
            showCurrentSymbol();
            if (isVisible) Canvas.getCanvas().wait(150);
        }
    }

    /**
     * Bloquea la rueda; pinta el marco de rojo.
     */
    public void lock() {
        locked = true;
        frame.changeColor("red");
    }

    /**
     * Desbloquea la rueda; restaura el marco a azul.
     */
    public void unlock() {
        locked = false;
        frame.changeColor("blue");
    }

    /**
     * Retorna {@code true} si la rueda está bloqueada.
     */
    public boolean isLocked() {
        return locked;
    }


    /**
     * Posiciona la rueda en el símbolo con el color indicado.
     *
     * @param color color CSS del símbolo destino
     * @return {@code true} si se encontró y colocó; {@code false} si no existe
     */
    public boolean placeSymbol(String color) {
        int index = indexOfColor(color);
        if (index < 0) return false;
        hideCurrentSymbol();
        currentIndex = index;
        showCurrentSymbol();
        return true;
    }

    /**
     * Retorna el color del símbolo actualmente visible.
     *
     * @return color CSS, o {@code null} si la rueda está vacía
     */
    public String getVisibleColor() {
        if (symbols.isEmpty()) return null;
        return symbols.get(currentIndex).getColor();
    }

    /**
     * Retorna los colores de todos los símbolos en orden de posición.
     *
     * @return arreglo de colores CSS; vacío si no hay símbolos
     */
    public String[] getSymbolColors() {
        String[] colors = new String[symbols.size()];
        for (int i = 0; i < symbols.size(); i++) {
            colors[i] = symbols.get(i).getColor();
        }
        return colors;
    }

    /**
     * Inserta un símbolo en la posición 1-based indicada (clamped a {@code [1, n+1]}).
     *
     * @param pos   posición de inserción (base 1)
     * @param color color CSS del nuevo símbolo
     */
    public void addSymbol(int pos, String color) {
        int index = clamp(pos, 1, symbols.size() + 1) - 1;
        Symbol s = new Symbol(color);
        s.moveTo(xPos + SYMBOL_PAD_X, yPos + SYMBOL_PAD_Y);
        symbols.add(index, s);
        adjustIndexAfterInsertion(index);
        if (isVisible) showCurrentSymbol();
    }

    /**
     * Elimina el símbolo con el color indicado.
     *
     * @param color color CSS a eliminar
     * @return {@code true} si se encontró y eliminó; {@code false} si no existe
     */
    public boolean delSymbol(String color) {
        int index = indexOfColor(color);
        if (index < 0) return false;
        if (index == currentIndex) hideCurrentSymbol();
        symbols.remove(index);
        adjustIndexAfterRemoval(index);
        if (isVisible && !symbols.isEmpty()) showCurrentSymbol();
        return true;
    }

    /**
     * Retorna la cantidad de símbolos en la rueda.
     *
     * @return número de símbolos
     */
    public int symbolCount() {
        return symbols.size();
    }

    /**
     * Mueve la rueda y su símbolo visible a una posición absoluta.
     *
     * @param x nueva coordenada x
     * @param y nueva coordenada y
     */
    public void moveTo(int x, int y) {
        moveFrameTo(x, y);
        if (!symbols.isEmpty()) {
            symbols.get(currentIndex).moveTo(xPos + SYMBOL_PAD_X, yPos + SYMBOL_PAD_Y);
        }
    }

    /** Hace visible el marco y el símbolo actual en el canvas. */
    public void makeVisible() {
        frame.makeVisible();
        isVisible = true;
        showCurrentSymbol();
    }

    /** Oculta el marco y el símbolo actual del canvas. */
    public void makeInvisible() {
        hideCurrentSymbol();
        frame.makeInvisible();
        isVisible = false;
    }

    // -------- métodos privados --------

    /** Desplaza el marco a coordenadas absolutas usando deltas. */
    private void moveFrameTo(int x, int y) {
        frame.moveHorizontal(x - xPos);
        frame.moveVertical(y - yPos);
        xPos = x;
        yPos = y;
    }

    /** Busca el índice del símbolo con el color dado; retorna -1 si no existe. */
    private int indexOfColor(String color) {
        for (int i = 0; i < symbols.size(); i++) {
            if (symbols.get(i).getColor().equals(color)) return i;
        }
        return -1;
    }

    /** Posiciona y muestra el símbolo en {@code currentIndex}. */
    private void showCurrentSymbol() {
        if (symbols.isEmpty() || !isVisible) return;
        Symbol s = symbols.get(currentIndex);
        s.moveTo(xPos + SYMBOL_PAD_X, yPos + SYMBOL_PAD_Y);
        s.makeVisible();
    }

    /** Oculta el símbolo en {@code currentIndex}. */
    private void hideCurrentSymbol() {
        if (!symbols.isEmpty()) {
            symbols.get(currentIndex).makeInvisible();
        }
    }

    /** Ajusta {@code currentIndex} tras insertar en {@code insertedIndex}. */
    private void adjustIndexAfterInsertion(int insertedIndex) {
        if (symbols.size() == 1) {
            currentIndex = 0;
        } else if (currentIndex >= insertedIndex) {
            currentIndex++;
        }
    }

    /** Ajusta {@code currentIndex} tras eliminar el elemento en {@code removedIndex}. */
    private void adjustIndexAfterRemoval(int removedIndex) {
        if (symbols.isEmpty()) {
            currentIndex = 0;
        } else if (currentIndex > removedIndex) {
            currentIndex--;
        } else if (currentIndex >= symbols.size()) {
            currentIndex = symbols.size() - 1;
        }
    }

    /** Limita {@code val} al rango {@code [min, max]}. */
    private int clamp(int val, int min, int max) {
        return Math.max(min, Math.min(val, max));
    }
}
