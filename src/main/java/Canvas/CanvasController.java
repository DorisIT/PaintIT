package Canvas;

import Tools.Observer;
import javafx.scene.paint.Color;
import lombok.Getter;

public class CanvasController implements Observer {

    CanvasModel canvasModel;
    @Getter CanvasView canvasView;

    public CanvasController() {
        this.canvasModel = new CanvasModel(Color.WHITE);
        this.canvasView = new CanvasView();
    }

    public CanvasController(CanvasView canvasView) {
        this.canvasModel = new CanvasModel(Color.WHITE);
        this.canvasView = canvasView;
    }

    public CanvasController(int xSize, int ySize) {
        this.canvasModel = new CanvasModel(xSize, ySize, Color.WHITE);
        this.canvasView = new CanvasView();
    }

    public void paint(int x, int y, Color newColor) {
        // Check if new color value is different from current value
        if(x > canvasModel.getXBound() || x < 0 || y > canvasModel.getYBound() || y < 0)
            return;

        if(!canvasModel.getPixel(x, y).equals(newColor))
         canvasModel.setPixel(x,y, newColor);

      }

    public void fillCanvas(Color color) {
        canvasModel.fillCanvas(color);
    }


    public void clear() {
        canvasModel.resetCanvas();
    }
    @Override
    public String toString() {
        return canvasModel.toString();
    }

    @Override
    public void update(int x, int y, Color color) {
        paint(x, y, color);
        // Paint ACTUAL view
        //canvasView.getGraphicsContext().fillRect(x,y,x+1,y+1);
        canvasView.setPixel(x,y, color); // TODO CHECK WITH MODEL INSTEAD
    }
}
