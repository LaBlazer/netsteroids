package netsteroids.managers;

import glm.vec._2.i.Vec2i;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import netsteroids.interfaces.IDrawable;

public class RenderManager {
    private boolean drawGizmos;
    private GraphicsContext graphicsContext;
    private Vec2i size;
    private Color backgroundColor;

    public RenderManager(GraphicsContext gc, Vec2i size) {
        this.graphicsContext = gc;
        this.size = size;
        this.backgroundColor = Color.BLACK;
    }

    public void SetDrawGizmos(boolean drawGizmos){
        this.drawGizmos = drawGizmos;
    }

    public boolean GetDrawGizmos() {
        return drawGizmos;
    }

    public void SetBackgroundColor(Color c) {
        this.backgroundColor = c;
    }

    public void Resize(Vec2i size){
        this.size = size;
    }

    public void BeginFrame() {
        graphicsContext.setFill(this.backgroundColor);
        graphicsContext.fillRect(0, 0, size.x, size.y);
    }

    public void DrawObject(IDrawable e){
        if(e.IsVisible()) {
            e.OnDraw(graphicsContext);
            if (drawGizmos)
                e.OnDrawGizmos(graphicsContext);
        }
    }
}
