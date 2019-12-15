package netsteroids.entities;

import glm.vec._2.Vec2;
import glm.vec._2.i.Vec2i;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import netsteroids.interfaces.IDrawable;
import netsteroids.network.Packet;

public class Asteroid extends FlyingEntity implements IDrawable {
    private final float MIN_VELOCITY = 20f;
    private final float MAX_VELOCITY = 70f;

    private int size = 20;

    public Asteroid(Vec2i canvasSize){
        super(new Vec2(0, 0), new Vec2(0, 0), new Vec2(0, 0), canvasSize);
    }

    @Override
    public boolean IsVisible() {
        return true;
    }

    @Override
    public void OnInit() {
        // Set random position and velocity
        position = new Vec2(rand.nextFloat() * canvasSize.x, rand.nextFloat() * canvasSize.y);

        float vel = MIN_VELOCITY + rand.nextFloat() * (MAX_VELOCITY - MIN_VELOCITY);
        velocity = new Vec2((rand.nextFloat() - 0.5) * vel, (rand.nextFloat() - 0.5) * vel);
    }

    @Override
    public void OnUpdate(float delta) {
        super.OnUpdate(delta);
    }

    @Override
    public void OnDestroy() {

    }

    @Override
    public void OnDraw(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.fillOval(position.x - (size / 2), position.y - (size / 2), size, size);
    }

    @Override
    public void OnDrawGizmos(GraphicsContext gc) {
        gc.setFill(Color.RED);
        var name = this.toString();
        gc.fillText(name, position.x - (name.length() * 3), position.y - size / 2);
        // Draw headings
        gc.setStroke(Color.BLUE);
        gc.strokeLine(position.x, position.y, position.x + (velocity.x ), position.y + (velocity.y ));
    }

    @Override
    public Packet OnSyncRequest() {
        return new Packet();
    }

    @Override
    public void OnSyncResponse(Packet response) {

    }
}
