package netsteroids.interfaces;

import javafx.scene.canvas.GraphicsContext;

public interface IDrawable {
    default boolean IsVisible() {
        return true;
    }
    void OnDraw(GraphicsContext gc);
    void OnDrawGizmos(GraphicsContext gc);
}

