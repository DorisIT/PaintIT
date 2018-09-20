package Tools;

import javafx.scene.paint.Color;
import lombok.Setter;

public class Eraser implements Tool {

    /** Determines how big the circle that will be erased is.
     *
     */
    @Setter
    private int radius;

    public Eraser() {

    }

    public Eraser(int radius) {
        this.radius = radius;
    }

    public Eraser(Observer observer, int radius) {
        addObserver(observer);
        this.radius = radius;
    }

    /** Determines if point is in circle or not.
     *
     * @param x0 x-value of the origin of circle.
     * @param y0 y-value of the origin of circle.
     * @param posx x-value of the point that is being checked.
     * @param posy y-value of the point that is being checked.
     * @param r radius of circle.
     * @return Whether or not the point was in the circle or not.
     */

    public boolean inCircle(int x0, int y0, int posx, int posy, int r) {
        return ((Math.pow((posx - x0), 2) + Math.pow(posy - y0, 2)) <= Math.pow(r,2));

    }

    /** Checks square area around brush and fills a circular area.
     * Notifies Observers ({@link Canvas.CanvasController}) of the brush by giving them x and y-values that form a circle around the point that is formed by the arguments.
     * The appearance of the circle is determined by {@link com.PaintIT.app.PaintingView#currentColor} and {@link Brush#radius}
     *
     * @param x0 Determines the x-value for the center of the circle.
     * @param y0 Determines the x-value for the center of the circle.
     */
    public void apply(int x0, int y0, Color color) {
        if (radius >= 0) {
            // Check square area around cursor position
            for (int posx = (x0 - radius); posx <= (x0 + radius); posx++) {
                for (int posy = (y0 - radius); posy <= (y0 + radius); posy++) {
                    if (inCircle(x0, y0, posx, posy, radius)) {
                        // If inside circle with radius, notify observers
                        for (Observer observer : Observers) {
                            observer.update(posx, posy, Color.WHITE); // TODO Should player be able to change default color of canvas? If so, do not assume white
                        }
                    }
                }
            }
        }
    }


    @Override
    public void addObserver(Observer observer) {
        Observers.add(observer);
    }
}