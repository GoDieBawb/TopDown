/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.entity.powerup;

import com.jme3.app.state.AppStateManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import mygame.GameManager;

/**
 *
 * @author Bawb
 */
public class Health extends PowerUp {
    
    public Health(AppStateManager stateManager) {
        super(stateManager);
        makeModel(stateManager);
    }
    
    private void makeModel(AppStateManager stateManager) {
        Node entityNode = stateManager.getState(GameManager.class).getEntityManager().getEntityNode();
        Box b           = new Box(.25f,.25f,.25f);
        Geometry g      = new Geometry("Box", b);
        Material m      = new Material(stateManager.getApplication().getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        Node bullet     = new Node("Bullet");
        m.setColor("Color", ColorRGBA.Green);
        g.setMaterial(m);
        bullet.attachChild(g);
        this.setModel(bullet, stateManager); 
        entityNode.attachChild(this);
    }  
    
    private void giveHealth() {
        
        getPlayer().setHealth(getPlayer().getHealth() + 5);
        
        if (getPlayer().getHealth() > getPlayer().getMaxHealth())
            getPlayer().setHealth(getPlayer().getMaxHealth());
        
    }
    
    @Override
    public void collide() {
        super.collide();
        giveHealth();
    }
    
}
