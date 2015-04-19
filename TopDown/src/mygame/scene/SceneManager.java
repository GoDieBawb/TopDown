/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.scene;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.scene.Node;
import com.jme3.scene.SceneGraphVisitor;
import com.jme3.scene.Spatial;
import com.jme3.terrain.Terrain;
import com.jme3.terrain.geomipmap.TerrainLodControl;
import java.lang.reflect.Field;
import java.util.concurrent.ExecutorService;
import mygame.GameManager;

/**
 *
 * @author Bawb
 */
public class SceneManager {
    
    private Node              scene;
    private SimpleApplication app;
    private RigidBodyControl  rbc;
    
    public SceneManager(SimpleApplication app) {
        this.app = app;
    }
    
    public void setScene(String scenePath) {
        
        try {
            removeScene();
        }
         
        catch(NullPointerException e) {
        }
        
        GameManager gm       = app.getStateManager().getState(GameManager.class);
        String realPath      = "Scenes/" + scenePath + ".j3o";
               scene         = (Node) app.getAssetManager().loadModel(realPath);
               rbc           = new RigidBodyControl(0);
               
        app.getRootNode().attachChild(scene);
        scene.getChild("Scene Node").addControl(rbc);
        gm.getEntityManager().initEntities(this);
        gm.getUtilityManager().getMaterialManager().makeUnshaded(scene);
        gm.getUtilityManager().getPhysicsManager().getPhysics().getPhysicsSpace().add(scene.getChild("Scene Node"));
        
    }
    
    public void removeScene() {
        GameManager gm       = app.getStateManager().getState(GameManager.class);
        gm.getUtilityManager().getPhysicsManager().getPhysics().getPhysicsSpace().remove(scene.getChild("Scene Node"));
        gm.getUtilityManager().getPhysicsManager().clearPhysics(app.getStateManager(), app.getRootNode());
        scene.getChild("Scene Node").removeControl(rbc);
        scene = null;
        rbc   = null;
        app.getRootNode().detachAllChildren();
        clearTerrainLod();
        System.gc();
    }
    
    private void clearTerrainLod() {
    
        SceneGraphVisitor sgv = new SceneGraphVisitor() {
            
            public void visit(Spatial spatial) {
            
                if (spatial instanceof Terrain) {
                    
                    TerrainLodControl tlc = spatial.getControl(TerrainLodControl.class);
                    Field f;
                    
                    try {
                        f = tlc.getClass().getDeclaredField("executor");
                        f.setAccessible(true);
                        ExecutorService ex = (ExecutorService) f.get(tlc);
                        ex.shutdown();
                    }
                    
                    catch (NoSuchFieldException nsf) {
                    }
                    
                    catch (IllegalAccessException e) {
                    }
                    
                    spatial.removeControl(tlc);
                    tlc.setEnabled(false);
            
                }
            }
        
        };
        
        app.getRootNode().depthFirstTraversal(sgv);
        
    }    
    
    public Node getScene() {
        return scene;
    }
    
}
