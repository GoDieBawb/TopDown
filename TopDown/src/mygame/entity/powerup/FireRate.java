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
public class FireRate extends PowerUp {
    
    public FireRate(AppStateManager stateManager) {
        super(stateManager);
        makeModel(stateManager);
    }
    
    private void makeModel(AppStateManager stateManager) {
        Node entityNode = stateManager.getState(GameManager.class).getEntityManager().getEntityNode();
        Node rateBonus = (Node) stateManager.getApplication().getAssetManager().loadModel("Models/RateBonus.j3o");
        this.setModel(rateBonus, stateManager); 
        getModel().scale(.25f);
        entityNode.attachChild(this);
    }        
    
    private void increaseFireRate() {
        getPlayer().giveRateBonus();
    }
    
    @Override
    protected void collide() {
        super.collide();
        increaseFireRate();
    }
    
}
