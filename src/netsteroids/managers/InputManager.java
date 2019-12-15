package netsteroids.managers;

import javafx.scene.Scene;

import java.util.ArrayList;

public class InputManager {
    private static ArrayList<String> oldInput = new ArrayList<>();
    private static ArrayList<String> input = new ArrayList<>();

    public InputManager(Scene scene) {
        scene.setOnKeyPressed(
            ev -> {
                String code = ev.getCode().toString();
                //System.out.println(ev.getCode());

                // Only add input once
                if ( !input.contains(code) ) {
                    input.add(code);
                }
        });

        scene.setOnKeyReleased(
            ev -> {
                input.remove(ev.getCode().toString());
        });
    }

    public void FlipInputBuffer(){
        oldInput = new ArrayList<>(input);
    }

    public static boolean IsPressed(String key) {
        key = key.toUpperCase();
        return input.contains(key);
    }

    public static boolean IsJustPressed(String key) {
        key = key.toUpperCase();
        return input.contains(key) && !oldInput.contains(key);
    }
}
