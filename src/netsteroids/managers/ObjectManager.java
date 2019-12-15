package netsteroids.managers;

import netsteroids.entities.Entity;
import netsteroids.interfaces.IDrawable;
import netsteroids.interfaces.IUpdateable;

import java.util.ArrayList;
import java.util.List;

public class ObjectManager {
    private static ObjectManager instance;

    public static ObjectManager GetInstance() {
        return instance;
    }

    private List<Entity> objectManager;
    private List<Entity> objectsToAdd;
    private List<Entity> objectsToRemove;
    private RenderManager renderer;

    public ObjectManager(RenderManager renderer) throws Exception {
        if(ObjectManager.instance != null)
            throw new Exception("Cannot instantiate more than one instance of ObjectManager");

        // Thread safe list
        this.objectsToAdd = new ArrayList<>();
        this.objectsToRemove = new ArrayList<>();
        this.objectManager = new ArrayList<>();
        this.renderer = renderer;
        ObjectManager.instance = this;
    }

    public void AddObject(Entity e) {
        e.OnInit();
        objectsToAdd.add(e);
        //System.out.println("[ObjectManager] Added " + e);
    }

    public void DestroyObject(Entity e) {
        if(objectManager.contains(e)) {
            e.OnDestroy();
            objectsToRemove.add(e);
            //System.out.println("[ObjectManager] Destroyed " + e);
        }
    }

    public void UpdateAndDraw(float delta) {
        renderer.BeginFrame();

        // Add new  and remove old entities
        objectManager.addAll(objectsToAdd);
        objectManager.removeAll(objectsToRemove);
        objectsToAdd.clear();
        objectsToRemove.clear();;

        // Update
        for (Entity e : objectManager) {
            // Update if updateable
            if (e instanceof IUpdateable)
                ((IUpdateable) e).OnUpdate(delta);

            // Draw if drawable
            if (e instanceof IDrawable)
                renderer.DrawObject((IDrawable) e);
        }
    }
}
