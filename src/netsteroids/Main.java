package netsteroids;

import glm.vec._2.Vec2;
import glm.vec._2.i.Vec2i;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import netsteroids.entities.Asteroid;
import netsteroids.entities.Ship;
import netsteroids.entities.Superpowers;
import netsteroids.managers.AudioManager;
import netsteroids.managers.InputManager;
import netsteroids.managers.ObjectManager;
import netsteroids.managers.RenderManager;

import java.io.File;
import java.sql.SQLOutput;

public class Main extends Application {
    private static final Vec2i CANVAS_SIZE = new Vec2i(1280, 720);

    private Scene scene;
    private RenderManager renderer;
    private ObjectManager objects;
    private InputManager input;
    private AudioManager audio;

    public static float deltaMultiplier = 1.f;

    @Override
    public void init() throws Exception {
        Canvas canvas = new Canvas(CANVAS_SIZE.x, CANVAS_SIZE.y);
        Group root = new Group(canvas);
        scene = new Scene(root);

        input = new InputManager(scene);
        renderer = new RenderManager(canvas.getGraphicsContext2D(), CANVAS_SIZE);
        objects = new ObjectManager(renderer);
        audio = new AudioManager();
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Asteroids: Multiplayer");
        stage.setResizable(false);
        stage.setScene(scene);

        // Fixed window ratio
        //float ratio = (float)height / (float)width;
        //stage.minHeightProperty().bind(stage.widthProperty().multiply(ratio));
        //stage.maxHeightProperty().bind(stage.widthProperty().multiply(ratio));

        try {
            System.out.println("Running from: " + new File("./").getCanonicalPath());
        }catch (Exception ignored){};

        //TEMP
        for(int i = 0; i < 20; i++) {
            objects.AddObject(new Asteroid(CANVAS_SIZE));
        }

        objects.AddObject(new Ship(new Vec2(CANVAS_SIZE.x /2, CANVAS_SIZE.y/2), CANVAS_SIZE));
        objects.AddObject(new Superpowers(CANVAS_SIZE));

        // Play background music
        //AudioManager.Play("data/goingdown.wav", true, 0.8f);

        // Game and draw loop
        final long startNanoTime = System.nanoTime();
        new AnimationTimer()
        {
            double time, prevTime, delta;
            boolean hyper = false;

            public void handle(long currentNanoTime)
            {
                time = (currentNanoTime - startNanoTime) / 1000000000.0;
                delta = time - prevTime;
                prevTime = time;

                // Draw all entity gizmos
                if(InputManager.IsPressed("F3"))
                    renderer.SetDrawGizmos(true);
                if(InputManager.IsPressed("F4"))
                    renderer.SetDrawGizmos(false);


                if(InputManager.IsJustPressed("F8"))
                    hyper = !hyper;

                if(Main.deltaMultiplier > 1.f)
                    renderer.SetBackgroundColor(Color.color(Math.random(), Math.random(), Math.random()));
                else
                    renderer.SetBackgroundColor(Color.BLACK);
                //System.out.println((float)(delta * (hyper ? .2 : 1.)));

                // Process everything
                objects.UpdateAndDraw((float)(delta * Main.deltaMultiplier));

                // Flip input buffer
                input.FlipInputBuffer();
            }
        }.start();

        stage.show();
    }



    @Override
    public void stop() throws Exception {
        audio.Stop();
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
