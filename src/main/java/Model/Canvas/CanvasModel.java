package Model.Canvas;

import Views.Components.CanvasView;
import Util.ColorPoint;
import Util.Observable;
import Util.Observer;
import javafx.scene.paint.Color;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class CanvasModel implements Observable, ObservableCanvasModel {
    /**
     * Represents the canvas x-size.
     */
    @Getter private final int xMax;
    /**
     * Represents the canvas y-size.
     */
    @Getter private final int yMax;
    /**
     * Represents the x-value of the latest pixel that has been modified.
     */
    @Getter private int latestPixelX;
    /**
     * Represents the y-value of the latest pixel that has been modified.
     */
    @Getter private int latestPixelY;
    /**
     * An array of the Canvas. Using the Arrays structure so that the x and y value represents a pixel while its content ({@link ColorPoint})
      represents the value.
     */
    private final Color[][] canvas;
    /**
     * In this case, a list of {@link CanvasView}.
     */
    private List<Observer> observers = new ArrayList<>();

    public CanvasModel(Color initValue) {
        xMax = 1200;
        yMax = 500;
        canvas = new Color[xMax][yMax];
        fillCanvas(initValue);
    }

    public CanvasModel(int xSize, int ySize, Color initColor) {
        xMax = Math.abs(xSize); yMax = Math.abs(ySize);
        canvas = new Color[xMax][yMax];
        fillCanvas(initColor);
    }

    /** Sets the color of a pixel and notifies observers using {@link CanvasModel#notifyObservers()}
     *
     * @param x x-coordinate of pixel.
     * @param y y-coordinate of pixel.
     * @param newValue New color of the pixel.
     * @throws IndexOutOfBoundsException Exception when you try to paint outside the canvas.
     */
    public void setPixel(int x, int y, Color newValue) throws IndexOutOfBoundsException {
        // Check if xMax and yMax are in bounds of canvas
        if(!inBounds(x, y)) {
            throw new IndexOutOfBoundsException();
        }

        canvas[x][y] = newValue;

        this.latestPixelX = x;
        this.latestPixelY = y;

        notifyObservers();
    }

    /** Returns the color of a pixel.
     * @param x x-coordinate of pixel.
     * @param y y-coordinate of pixel.
     * @return The color of the chosen pixel.
     * @throws IndexOutOfBoundsException Exception when you try to get a pixel outside the canvas.
     */
    public Color getPixel(int x, int y) /*throws IndexOutOfBoundsException*/ {
        // Check if xMax and yMax are in bounds of canvas
        return canvas[x][y];
    }

    /**
     * Sets entire canvas to white
     */
    public void resetCanvas() {
        fillCanvas(Color.WHITE);
    }

    /**
     * Sets entire canvas to chosen color.
     * @param color The color of which the canvas will be filled with.
     */

    public void fillCanvas(Color color) {
        for (int i = 0; i < yMax; i++) {
            for (int j = 0; j < xMax; j++) {
                setPixel(j, i, color);
            }
        }
    }

    /**
     * Creates and returns a string of the canvas, used exclusively for testing.
     * @return Returns a String of the Canvas.
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for(int i = 0; i < yMax; i++) {
            sb.append("[ ");
            for (int j = 0; j < xMax; j++) {
                final Color temp = canvas[j][i];
                sb.append("[ " + temp.getRed() + ", " + temp.getGreen() + ", " + temp.getBlue() + " ] ");
            }
            sb.append("] \n");
        }
        return sb.toString();
    }

    /** Checks if x and y value is within CanvasModels bounds.
     *
     * @param x x-value to check.
     * @param y y-value to check.
     * @return Whether or not the values is within CanvasModel bounds.
     * @throws IndexOutOfBoundsException Throws exception if out of bounds.
     */
    public boolean inBounds(int x, int y) throws IndexOutOfBoundsException {
        if(x < 0 || x > this.xMax || y < 0 || y > this.yMax) {
            // xMax or yMax out of bounds, throw exception
            throw new IndexOutOfBoundsException();
        }
        // Within bounds
        return true;
    }
    public int getXBound() {
        return xMax-1;
    }

    public int getYBound() {
        return yMax-1;
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    /**
     * Notifies observers that model has been changed.
     */
    @Override
    public void notifyObservers() {
        for(final Observer observer : observers) {
            observer.update();
        }
    }
}
