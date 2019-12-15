package netsteroids.entities;

import glm.vec._2.Vec2;
import glm.vec._2.i.Vec2i;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import netsteroids.interfaces.IDrawable;
import netsteroids.managers.AudioManager;
import netsteroids.managers.InputManager;
import netsteroids.managers.ObjectManager;
import netsteroids.network.Packet;

public class Ship extends FlyingEntity implements IDrawable {
    private final float ACCELERATION_POWER = 200f;
    private final float ROTATION_SPEED = 5f;
    private final Vec2i SHIP_SIZE = new Vec2i(25, 15);
    private final float FIRE_FREQUENCY = 0.2f;

    private float heading = 0; // Up
    private float fireTimer = 0;

    public Ship(Vec2 position, Vec2i canvasSize){
        super(position, new Vec2(0, 0), new Vec2(0, 0), canvasSize);
    }

    @Override
    public void OnInit() {

    }

    @Override
    public void OnDestroy() {

    }

    @Override
    public boolean IsVisible() {
        return true;
    }

    @Override
    public void OnDraw(GraphicsContext gc) {
        gc.setStroke(Color.WHITE);

        // Draw a ship
        Vec2[] points = {
            rotatePoint(new Vec2(position.x + SHIP_SIZE.x, position.y), position, heading),
            rotatePoint(new Vec2(position.x - SHIP_SIZE.x, position.y + SHIP_SIZE.y), position, heading),
            rotatePoint(new Vec2(position.x - SHIP_SIZE.x, position.y - SHIP_SIZE.y), position, heading)
        };

        gc.strokeLine(points[0].x, points[0].y, points[1].x, points[1].y);
        gc.strokeLine(points[1].x, points[1].y, points[2].x, points[2].y);
        gc.strokeLine(points[2].x, points[2].y, points[0].x, points[0].y);

        //for(int i = 0; i < 4; i++){
        //    gc.strokeLine(points[i].x, points[i].y, points[(i + 1)%3].x, points[(i + 1)%3].y);
        //}
        //gc.fillRect(position.x, position.y, size.x, size.y);
    }

    @Override
    public void OnDrawGizmos(GraphicsContext gc) {
        // Draw headings
        gc.setLineWidth(2);
        gc.setStroke(Color.BLUE);
        gc.strokeLine(position.x, position.y, position.x + (velocity.x ), position.y + (velocity.y ));
        gc.setStroke(Color.RED);
        gc.strokeLine(position.x, position.y, position.x + (acceleration.x ), position.y + (acceleration.y));

        // Draw text
    }

    @Override
    public Packet OnSyncRequest() {
        return null;
    }

    @Override
    public void OnSyncResponse(Packet response) {

    }

    @Override
    public void OnUpdate(float delta) {
        // Rotate left
        if(InputManager.IsPressed("A") || InputManager.IsPressed("LEFT")) {
            heading -= ROTATION_SPEED * delta;
        }

        // Rotate right
        if(InputManager.IsPressed("D") || InputManager.IsPressed("RIGHT")) {
            heading += ROTATION_SPEED * delta;
        }

        // Accelerate
        if(InputManager.IsPressed("CONTROL") || InputManager.IsPressed("UP")){
            Vec2 dir = new Vec2(Math.cos(heading), Math.sin(heading));
            acceleration = dir.mul(delta * ACCELERATION_POWER);
        }else{
            acceleration.set(0);
        }

        // Fire
        if(InputManager.IsPressed("SPACE") || InputManager.IsPressed("NUMPAD0")) {
            fireTimer += delta;
            if(fireTimer > FIRE_FREQUENCY) {
                FireProjectile();
                fireTimer = 0.f;
            }
        }else{
            fireTimer = 0.f;
        }

        if(InputManager.IsJustPressed("SPACE") || InputManager.IsJustPressed("NUMPAD0")){
            FireProjectile();
        }

        // Debug
        if(InputManager.IsPressed("F5"))
            this.velocity.set(0);

        super.OnUpdate(delta);
    }

    private void FireProjectile() {
        AudioManager.Play("data/laser.wav", false, 0.8f + (rand.nextFloat() * 0.4f));

        Vec2 normal = new Vec2();
        velocity.normalize(normal);
        Vec2 dir = new Vec2(Math.cos(heading), Math.sin(heading));

        Bullet b = new Bullet(dir.mul_(16).add(position), dir.mul_(400), canvasSize);
        ObjectManager.GetInstance().AddObject(b);
    }

    private Vec2 rotatePoint(Vec2 point, Vec2 pivot, float angle) {
        float c = (float)Math.cos(angle);
        float s = (float)Math.sin(angle);

        // Make the point relative to pivot
        point.sub(pivot);

        return new Vec2(c * point.x - s * point.y + pivot.x, s * point.x + c * point.y + pivot.y);
    }
}
