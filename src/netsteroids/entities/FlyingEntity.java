package netsteroids.entities;

import glm.vec._2.Vec2;
import glm.vec._2.i.Vec2i;
import netsteroids.interfaces.ISynchronizable;
import netsteroids.interfaces.IUpdateable;
import netsteroids.network.Packet;

import java.util.Random;

public class FlyingEntity extends Entity implements IUpdateable, ISynchronizable {
    protected final float MAX_VELOCITY = 60f;
    protected final float MOVE_MARGIN = 20f;

    protected final Random rand = new Random();

    protected Vec2 position;
    protected Vec2 velocity;
    protected Vec2 acceleration;
    protected Vec2i canvasSize;

    public FlyingEntity(Vec2 position, Vec2 velocity, Vec2 acceleration, Vec2i canvasSize){
        this.canvasSize = canvasSize;
        this.position = position;
        this.velocity = velocity;
        this.acceleration = acceleration;
    }

    @Override
    public void OnInit() {

    }

    @Override
    public void OnDestroy() {

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
        velocity.add(acceleration);
        position.add(velocity.mul_(delta));

        if(position.x > (canvasSize.x + MOVE_MARGIN)){
            position.x = -MOVE_MARGIN;
        }else if(position.x < -MOVE_MARGIN){
            position.x = canvasSize.x + MOVE_MARGIN;
        }

        if(position.y > (canvasSize.y + MOVE_MARGIN)){
            position.y = -MOVE_MARGIN;
        }else if(position.y < -MOVE_MARGIN){
            position.y = canvasSize.y + MOVE_MARGIN;
        }
    }
}
