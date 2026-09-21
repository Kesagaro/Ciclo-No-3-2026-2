import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;

/**
 * Ventana gráfica compartida por todas las formas (singleton).
 * Extendida para el proyecto slotMachine con soporte de colores CSS
 * adicionales, redimensionado dinámico y cierre de ventana.
 *
 * @author BlancoS-GarzonR
 * @version 1.7
 */
public class Canvas {

    private static Canvas canvasSingleton;

    /**
     * Retorna la instancia única del canvas, creándola si no existe.
     *
     * @return instancia singleton
     */
    public static Canvas getCanvas() {
        if (canvasSingleton == null) {
            canvasSingleton = new Canvas("DOPO-POOB — Slot Machine", 400, 220, Color.white);
        }
        canvasSingleton.setVisible(true);
        return canvasSingleton;
    }

    /**
     * Muestra u oculta la ventana sin forzar la creación del singleton.
     *
     * @param visible {@code true} para mostrar, {@code false} para ocultar
     */
    public static void setCanvasVisible(boolean visible) {
        if (canvasSingleton != null) {
            canvasSingleton.frame.setVisible(visible);
        }
    }

    // ----- parte de instancia -----

    private JFrame frame;
    private CanvasPane canvas;
    private Graphics2D graphic;
    private Color backgroundColour;
    private Image canvasImage;
    private List<Object> objects;
    private HashMap<Object, ShapeDescription> shapes;

    /**
     * Construye el canvas con los parámetros dados.
     *
     * @param title    título de la ventana
     * @param width    ancho en píxeles
     * @param height   alto en píxeles
     * @param bgColour color de fondo
     */
    private Canvas(String title, int width, int height, Color bgColour) {
        frame = new JFrame();
        canvas = new CanvasPane();
        frame.setContentPane(canvas);
        frame.setTitle(title);
        canvas.setPreferredSize(new Dimension(width, height));
        backgroundColour = bgColour;
        frame.pack();
        objects = new ArrayList<Object>();
        shapes = new HashMap<Object, ShapeDescription>();
    }

    /**
     * Hace visible u oculta el canvas. En la primera llamada inicializa
     * el buffer offscreen.
     *
     * @param visible {@code true} para mostrar
     */
    public void setVisible(boolean visible) {
        if (graphic == null) {
            Dimension size = canvas.getSize();
            canvasImage = canvas.createImage(size.width, size.height);
            graphic = (Graphics2D) canvasImage.getGraphics();
            graphic.setColor(backgroundColour);
            graphic.fillRect(0, 0, size.width, size.height);
            graphic.setColor(Color.black);
        }
        frame.setVisible(visible);
    }

    /**
     * Redimensiona la ventana y recrea el buffer offscreen.
     *
     * @param width  nuevo ancho en píxeles
     * @param height nuevo alto en píxeles
     */
    public void resize(int width, int height) {
        canvas.setPreferredSize(new Dimension(width, height));
        frame.pack();
        if (graphic != null) {
            Dimension size = canvas.getSize();
            canvasImage = canvas.createImage(size.width, size.height);
            graphic = (Graphics2D) canvasImage.getGraphics();
            graphic.setColor(backgroundColour);
            graphic.fillRect(0, 0, size.width, size.height);
            graphic.setColor(Color.black);
            redraw();
        }
    }

    /**
     * Cierra y libera la ventana del canvas.
     */
    public void close() {
        frame.dispose();
    }

    /**
     * Dibuja una forma en el canvas asociándola a un objeto de referencia.
     *
     * @param referenceObject identificador de la forma
     * @param color           color de relleno
     * @param shape           forma AWT a dibujar
     */
    public void draw(Object referenceObject, String color, Shape shape) {
        objects.remove(referenceObject);
        objects.add(referenceObject);
        shapes.put(referenceObject, new ShapeDescription(shape, color));
        redraw();
    }

    /**
     * Borra la forma asociada al objeto de referencia.
     *
     * @param referenceObject identificador de la forma a borrar
     */
    public void erase(Object referenceObject) {
        objects.remove(referenceObject);
        shapes.remove(referenceObject);
        redraw();
    }

    /**
     * Establece el color de dibujo activo. Soporta nombres CSS estándar
     * y valores hex {@code #rrggbb}.
     *
     * @param colorString nombre CSS o código hex del color
     */
    public void setForegroundColor(String colorString) {
        if (colorString == null) { graphic.setColor(Color.black); return; }
        if (colorString.startsWith("#")) { setHexColor(colorString); return; }
        switch (colorString.toLowerCase()) {
            case "red":       graphic.setColor(Color.red);                      break;
            case "black":     graphic.setColor(Color.black);                    break;
            case "blue":      graphic.setColor(Color.blue);                     break;
            case "yellow":    graphic.setColor(Color.yellow);                   break;
            case "green":     graphic.setColor(new Color(0, 128, 0));           break;
            case "lime":      graphic.setColor(Color.green);                    break;
            case "magenta":
            case "fuchsia":   graphic.setColor(Color.magenta);                  break;
            case "white":     graphic.setColor(Color.white);                    break;
            case "cyan":
            case "aqua":      graphic.setColor(Color.cyan);                     break;
            case "orange":    graphic.setColor(Color.orange);                   break;
            case "pink":      graphic.setColor(Color.pink);                     break;
            case "gray":
            case "grey":      graphic.setColor(Color.gray);                     break;
            case "darkgray":
            case "darkgrey":  graphic.setColor(Color.darkGray);                 break;
            case "lightgray":
            case "lightgrey": graphic.setColor(Color.lightGray);                break;
            case "purple":    graphic.setColor(new Color(128, 0, 128));         break;
            case "brown":     graphic.setColor(new Color(139, 69, 19));         break;
            case "navy":      graphic.setColor(new Color(0, 0, 128));           break;
            case "teal":      graphic.setColor(new Color(0, 128, 128));         break;
            case "maroon":    graphic.setColor(new Color(128, 0, 0));           break;
            case "olive":     graphic.setColor(new Color(128, 128, 0));         break;
            case "coral":     graphic.setColor(new Color(255, 127, 80));        break;
            case "gold":      graphic.setColor(new Color(255, 215, 0));         break;
            case "violet":    graphic.setColor(new Color(238, 130, 238));       break;
            case "indigo":    graphic.setColor(new Color(75, 0, 130));          break;
            case "silver":    graphic.setColor(new Color(192, 192, 192));       break;
            default:          graphic.setColor(Color.black);                    break;
        }
    }

    /**
     * Pausa la ejecución el número de milisegundos indicado.
     *
     * @param milliseconds tiempo de espera en ms
     */
    public void wait(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (Exception e) { /* ignorado */ }
    }

    // -------- métodos privados --------

    /** Aplica un color hexadecimal {@code #rrggbb} al contexto gráfico. */
    private void setHexColor(String hex) {
        try {
            graphic.setColor(Color.decode(hex));
        } catch (NumberFormatException e) {
            graphic.setColor(Color.black);
        }
    }

    /** Redibuja todas las formas registradas en orden de inserción. */
    private void redraw() {
        erase();
        for (Iterator i = objects.iterator(); i.hasNext();) {
            shapes.get(i.next()).draw(graphic);
        }
        canvas.repaint();
    }

    /** Limpia el fondo del canvas sin repintar. */
    private void erase() {
        Color original = graphic.getColor();
        graphic.setColor(backgroundColour);
        Dimension size = canvas.getSize();
        graphic.fill(new java.awt.Rectangle(0, 0, size.width, size.height));
        graphic.setColor(original);
    }

    /** Panel interno que renderiza el buffer offscreen. */
    private class CanvasPane extends JPanel {
        public void paint(Graphics g) {
            g.drawImage(canvasImage, 0, 0, null);
        }
    }

    /** Asocia una forma AWT con su color de relleno. */
    private class ShapeDescription {
        private Shape shape;
        private String colorString;

        public ShapeDescription(Shape shape, String color) {
            this.shape = shape;
            colorString = color;
        }

        public void draw(Graphics2D graphic) {
            setForegroundColor(colorString);
            graphic.draw(shape);
            graphic.fill(shape);
        }
    }
}
