/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.util.topdown;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.math.Vector3f;
import mygame.GameManager;
import mygame.entity.player.Player;
import mygame.util.InteractionControl;
import mygame.util.InteractionManager;

/**
 *
 * @author Bawb
 */
public class TopDownControl extends InteractionControl{
    
    private SimpleApplication    app;
    private Vector3f             walkDirection = new Vector3f();
    private boolean              up, down, left, right;
    private Player               player;
    private TopDownCameraManager cameraManager;
    
    public TopDownControl(AppStateManager stateManager) {
        app            = (SimpleApplication) stateManager.getApplication();
        player         = app.getStateManager().getState(GameManager.class).getPlayerManager().getPlayer();
        cameraManager  = new TopDownCameraManager(app.getStateManager());
    }
    
    private void updateKeys() {
        InteractionManager im = app.getStateManager().getState(GameManager.class).getUtilityManager().getInteractionManager();
        up    = im.getIsPressed("Up");
        down  = im.getIsPressed("Down");
        left  = im.getIsPressed("Left");
        right = im.getIsPressed("Right");
    }    
    
    private void topDownMove() {
        
        walkDirection.set(0, 0, 0);
        
        float xMove = 0;
        float zMove = 0;
        
        if (up) {
            zMove = 5;
        }
        
        else if (down) {
            zMove = -5;
        }
        
        if (left) {
            xMove = 5;
        }
        
        else if (right) {
            xMove = -5;
        } 
        
        if(up||down||left||right) {
            
          player.run();
            
        } else {
          player.idle();
        }
       
        walkDirection.addLocal(xMove, 0, zMove); 
       
        float speedMult = 1;
       
        if(player.isAttacking())
            speedMult = .75f;
       
        
        if(player.hasSpeed())
            speedMult *= 2;
       
        player.getPhys().setWalkDirection(walkDirection.mult(speedMult));
       
    }
  
    private void rotate(){
    
        if (player.isAttacking())
            return;
        
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
    
    @Override
    public void update(float tpf) {
        updateKeys();
        rotate();
        topDownMove();
        cameraManager.update(tpf);
    }
    
}
