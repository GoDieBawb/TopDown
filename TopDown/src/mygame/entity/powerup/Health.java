/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.entity.powerup;

import com.jme3.app.state.AppStateManager;
import com.jme3.scene.Node;
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
        Node entityNode  = stateManager.getState(GameManager.class).getEntityManager().getEntityNode();
        Node healthBonus = (Node) stateManager.getApplication().getAssetManager().loadModel("Models/HealthBonus.j3o");
        this.setModel(healthBonus, stateManager); 
        getModel().scale(.25f);
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
