/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.entity.monster;

import com.jme3.app.SimpleApplication;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import mygame.GameManager;

/**
 *
 * @author Bawb
 */
public class MonsterManager {
    
    private Node              entityNode;
    private SimpleApplication app;
    
    public MonsterManager(SimpleApplication app) {
        this.app = app;
    }
    
    public void setEntityNode(Node entityNode) {
        this.entityNode = entityNode;
    }
    
    public void createZombie() {
        Zombie zombie;
        zombie = new Zombie(app.getStateManager());
        zombie.createPhys();
        zombie.createFinderControl(app.getStateManager());
        entityNode.attachChild(zombie);
        
        app.getStateManager().getState(GameManager.class).getUtilityManager()
                .getPhysicsManager().getPhysics().getPhysicsSpace()
                    .add(zombie.getPhys());
        
        zombie.getPhys().warp(new Vector3f(5,1,5));
    }    
    
}
