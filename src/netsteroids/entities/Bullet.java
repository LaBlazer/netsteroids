package netsteroids.entities;

import com.sun.prism.Image;
import glm.vec._2.Vec2;
import glm.vec._2.i.Vec2i;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import netsteroids.interfaces.IDrawable;
import netsteroids.interfaces.IUpdateable;
import netsteroids.managers.ObjectManager;

public class Bullet extends Entity implements IUpdateable, IDrawable {
    private final int SIZE = 5;

    private Vec2 position;
    private Vec2 velocity;
    private Vec2i canvasSize;
    private final int halfSize;

    Bullet(Vec2 position, Vec2 velocity, Vec2i canvasSize) {
        this.position = position;
        this.velocity = velocity;
        this.canvasSize = canvasSize;
        this.halfSize = SIZE / 2;
    }

    @Override
    public void OnInit() {

    }

    @Override
    public void OnDestroy() {

    }

    @Override
    public void OnUpdate(float delta) {
        position.add(velocity.mul_(delta));

        // If out of screen
        if(position.x > canvasSize.x || position.x < 0 || position.y > canvasSize.y || position.y < 0) {
            ObjectManager.GetInstance().DestroyObject(this);
        }
    }

    @Override
    public void OnDraw(GraphicsContext gc) {
        //Vec2 dir = new Vec2();
        //velocity.normalize(dir);

        gc.setStroke(Color.WHITE);
        gc.strokeOval(position.x - halfSize, position.y - halfSize, SIZE, SIZE);
        //gc.strokeLine(position.x, position.y, position.x + dir.x, position.y + dir.y);
    }

    @Override
    public void OnDrawGizmos(GraphicsContext gc) {
        gc.setStroke(Color.RED);
        gc.strokeLine(position.x, position.y, position.x + (velocity.x), position.y + (velocity.y));
    }
}
