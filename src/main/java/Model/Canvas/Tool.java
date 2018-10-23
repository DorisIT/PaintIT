package Model.Canvas;

import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.Setter;

/**
 * {@link Tool} is implemented by classes who wants to apply a pattern onto the canvas.
 */

public interface Tool {

    int getRadius();

    void setRadius(int radius);

    /**
     * Determines the color of the Tool.
     */
    // @Getter @Setter
    Color getColor();

    void setColor(Color color);


    //Notifies Observers ({@link Controller.CanvasController}) of the brush by giving them x and y-values that form a circle around the point that is formed by the arguments.
    // The appearance of the circle is determined by {@link PaintingView} and {@link Tool#radius}
    // TODO: fix this javadoc or functionality of apply
    /**  Returns whether or not the radius is less or equal to 0
     *
     * @param x0 The x-value for the center of the circle.
     * @param y0 The y-value for the center of the circle.
     * @param x The x-value to be checked if in circle.
     * @param y the y-value to be checked if in circle.
     * @return whether or not the radius is less or equal to 0
     */
    public boolean apply(int x0, int y0, int x, int y);
}
