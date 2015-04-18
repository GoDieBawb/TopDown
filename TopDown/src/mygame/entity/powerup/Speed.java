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
public class Speed extends PowerUp {
    
    public Speed(AppStateManager stateManager) {
        super(stateManager);
        makeModel(stateManager);
    }
    
    private void makeModel(AppStateManager stateManager) {
        Node entityNode = stateManager.getState(GameManager.class).getEntityManager().getEntityNode();
        Node boot       = (Node) stateManager.getApplication().getAssetManager().loadModel("Models/Boot.j3o");
        this.setModel(boot, stateManager); 
        entityNode.attachChild(this);
    }    
    
    private void speedUp() {
        getPlayer().giveSpeedBonus();
    }
    
    @Override
    protected void collide() {
        super.collide();
        speedUp();
    }
    
}
