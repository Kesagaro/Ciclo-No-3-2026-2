/**
 * Símbolo de la tragamonedas, identificado por un color CSS.
 * Se representa visualmente como un círculo en el canvas compartido.
 *
 * @author BlancoS-GarzonR
 * @version 1.0
 */
public class Symbol {

    private static final int DIAMETER     = 56;
    private static final int CIRCLE_INIT_X = 20;
    private static final int CIRCLE_INIT_Y = 15;

    private final String color;
    private final Circle circle;
    private int xPos;
    private int yPos;

    /**
     * Crea un símbolo con el color CSS indicado. Inicia invisible.
     *
     * @param color nombre CSS o hex del color (p.ej. {@code "red"}, {@code "#ff6600"})
     */
    public Symbol(String color) {
        this.color = color;
        circle = new Circle();
        circle.changeSize(DIAMETER);
        circle.changeColor(color);
        xPos = CIRCLE_INIT_X;
        yPos = CIRCLE_INIT_Y;
    }

    /**
     * Retorna el color identificador del símbolo.
     *
     * @return color CSS
     */
    public String getColor() {
        return color;
    }

    /**
     * Mueve el símbolo a una posición absoluta en el canvas.
     * Funciona estando visible o invisible.
     *
     * @param x coordenada horizontal en píxeles
     * @param y coordenada vertical en píxeles
     */
    public void moveTo(int x, int y) {
        circle.moveHorizontal(x - xPos);
        circle.moveVertical(y - yPos);
        xPos = x;
        yPos = y;
    }

    /** Muestra el símbolo en el canvas. */
    public void makeVisible() {
        circle.makeVisible();
    }

    /** Oculta el símbolo del canvas. */
    public void makeInvisible() {
        circle.makeInvisible();
    }
}
