package netsteroids.entities;

import glm.vec._2.i.Vec2i;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import netsteroids.Main;
import netsteroids.interfaces.IDrawable;
import netsteroids.interfaces.IUpdateable;
import netsteroids.managers.InputManager;

public class Superpowers extends Entity implements IUpdateable, IDrawable {
    private float poweLevel;
    private float powerRefillSpeed;
    private float powerBurnSpeed;
    private Vec2i cavasSize;
    private boolean using;
    private Color barColor;
    private float time;

    public Superpowers(Vec2i canvasSize) {
        this.cavasSize = canvasSize;
        this.poweLevel = 0;
        this.powerRefillSpeed = 0.25f;
        this.powerBurnSpeed = 0.1f;
        this.using = false;
        this.barColor = Color.WHITE;
    }

    @Override
    public void OnInit() {

    }

    @Override
    public void OnDestroy() {

    }

    @Override
    public void OnDraw(GraphicsContext gc) {
        gc.setFill(this.barColor);
        gc.fillRect(10, this.cavasSize.y - 20, this.cavasSize.x * this.poweLevel - 20, 10);
    }

    @Override
    public void OnDrawGizmos(GraphicsContext gc) {

    }

    @Override
    public void OnUpdate(float delta) {
        time += delta;

        if(InputManager.IsPressed("E") && this.poweLevel >= 1.f) {
            this.using = true;
        }

        if(!this.using && this.poweLevel <= 1.f)
            this.poweLevel += this.powerRefillSpeed * delta;

        if(this.using) {
            this.barColor = Color.WHITE;
            this.poweLevel -= this.powerBurnSpeed * delta;
            Main.deltaMultiplier = 6.0f;

            if(this.poweLevel <= 0.f) {
                this.using = false;
                Main.deltaMultiplier = 1.0f;
            }
        }

        if(this.poweLevel >= 1f)
            this.barColor = Color.WHITE.interpolate(Color.TRANSPARENT, (Math.sin(time * 4.f) + 1.) / 2.);
    }
}
