package Tools;

import javafx.scene.paint.Color;
import lombok.Setter;

import java.util.Random;

public class SprayCan implements Tool {

    Random r = new Random();
    /** Determines odds of pixel being colored.
     *
     */
    @Setter private double odds = 0.20;

    /** Determines how big the circle that will be painted is.
     *
     */
    @Setter
    private int radius;

    public SprayCan() { }

    public SprayCan(int radius) {
        this.radius = radius;
    }

    public SprayCan(Observer observer) {
        addObserver(observer);
    }

    public SprayCan(Observer observer, int radius) {
        addObserver(observer);
        this.radius = radius;
    }


    @Override
    public void apply(int x0, int y0, Color color) {
        if (radius >= 0) {
            // Check square area around cursor position
            for (int posx = (x0 - radius); posx <= (x0 + radius); posx++) {
                for (int posy = (y0 - radius); posy <= (y0 + radius); posy++) {
                    if (r.nextDouble() < odds) {
                        if (inCircle(x0, y0, posx, posy, radius)) {
                            // If inside circle with radius, notify observers
                            for (Observer observer : Observers) {
                                observer.update(posx, posy, color);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void setRadius(int radius) {
        this.radius=radius;
    }

    @Override
    public void addObserver(Observer observer) {
        Observers.add(observer);
    }

    public boolean inCircle(int x0, int y0, int posx, int posy, int r) {
        if(Math.pow(x0-posx,2) + Math.pow(y0-posy,2) <= Math.pow(r,2));
        return ((Math.pow((posx - x0), 2) + Math.pow(posy - y0, 2)) <= Math.pow(r,2));
    }
}
