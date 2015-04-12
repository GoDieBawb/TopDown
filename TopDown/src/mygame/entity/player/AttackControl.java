/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.entity.player;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.math.Vector3f;
import mygame.GameManager;
import mygame.entity.Bullet.Bullet;
import mygame.util.InteractionControl;
import mygame.util.InteractionManager;

/**
 *
 * @author Bawb
 */
public class AttackControl extends InteractionControl{
    
    private SimpleApplication    app;
    private Vector3f             walkDirection = new Vector3f();
    private boolean              up, down, left, right;
    private Player               player;
    private long                 lastFired;
    
    public AttackControl(AppStateManager stateManager) {
        app            = (SimpleApplication) stateManager.getApplication();
        player         = app.getStateManager().getState(GameManager.class).getPlayerManager().getPlayer();
        lastFired      = System.currentTimeMillis() - 500;
    }
    
    private void updateKeys() {
        InteractionManager im = app.getStateManager().getState(GameManager.class).getUtilityManager().getInteractionManager();
        up    = im.getIsPressed("Up1");
        down  = im.getIsPressed("Down1");
        left  = im.getIsPressed("Left1");
        right = im.getIsPressed("Right1");
    }
  
    private void rotate(){
        
        if (up) {
      
            if (left) {
                player.getPhys().setViewDirection(new Vector3f(999,0,999));
            }
      
            else if (right) {
                player.getPhys().setViewDirection(new Vector3f(-999,0,999));
            }
      
            else {
                player.getPhys().setViewDirection(new Vector3f(0,0,999));
            }
      
      }
    
        else if (down) {
      
            if (left) {
                player.getPhys().setViewDirection(new Vector3f(999,0,-999));
            }
      
            else if (right) {
                player.getPhys().setViewDirection(new Vector3f(-999,0,-999));
            }
      
            else {
                player.getPhys().setViewDirection(new Vector3f(0,0,-999));
            } 
        
        }
    
        else if (left) {
            player.getPhys().setViewDirection(new Vector3f(999,0,0));
        }
    
        else if (right){
            player.getPhys().setViewDirection(new Vector3f(-999,0,0));
        }
        
    }
    
    private void attack() {
        
        if (System.currentTimeMillis()/100 - lastFired/100  > 1) {
            new Bullet(player.getPhys().getViewDirection(), player.getWorldTranslation(), app.getStateManager());
            lastFired = System.currentTimeMillis();
        }
        
    }
    
    private void attackCheck() {

        if (up || down || left || right) {
            player.setIsAttacking(true);
            rotate();
            attack();
        }
    
        else {
            player.setIsAttacking(false);
        }
    
    }
    
    @Override
    public void update(float tpf) {
        updateKeys();
        attackCheck();
    }
    
}
