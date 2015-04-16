/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.entity.powerup;

import com.jme3.app.state.AppStateManager;
import com.jme3.collision.CollisionResults;
import mygame.GameManager;
import mygame.entity.Entity;
import mygame.entity.player.Player;

/**
 *
 * @author Bawb
 */
public class PowerUp extends Entity {

    private Player player;
    
    public PowerUp(AppStateManager stateManager) {
        player = stateManager.getState(GameManager.class).getPlayerManager().getPlayer();
    }
    
    protected Player getPlayer() {
        return player;
    }
    
    private void spin(float tpf) {
        rotate(3*tpf , 3*tpf, 3*tpf);
    }
    
    private void checkCollision() {
        
        CollisionResults results = new CollisionResults();
        
        collideWith(player.getWorldBound(), results);
        
        if (results.size() > 0) {
            collide();
        }
        
    }
    
    protected void collide() {
        removeFromParent();
    }
    
    @Override
    public void act(float tpf) {
        spin(tpf);
        checkCollision();
    }
    
}
