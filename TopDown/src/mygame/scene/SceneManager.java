/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.scene;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.scene.Node;
import mygame.GameManager;

/**
 *
 * @author Bawb
 */
public class SceneManager {
    
    private Node              scene;
    private SimpleApplication app;
    
    public SceneManager(SimpleApplication app) {
        this.app = app;
    }
    
    public void setScene(String scenePath) {
        
        GameManager gm       = app.getStateManager().getState(GameManager.class);
        String realPath      = "Scenes/" + scenePath + ".j3o";
               scene         = (Node) app.getAssetManager().loadModel(realPath);
        RigidBodyControl rbc = new RigidBodyControl(0);
               
        app.getRootNode().attachChild(scene);
        scene.getChild("Scene Node").addControl(rbc);
        gm.getEntityManager().initEntities(this);
        gm.getUtilityManager().getMaterialManager().makeUnshaded(scene);
        gm.getUtilityManager().getPhysicsManager().getPhysics().getPhysicsSpace().add(scene.getChild("Scene Node"));
        
    }
    
    public void removeScene() {
    
    }
    
    public Node getScene() {
        return scene;
    }
    
}
