/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.entity;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.LoopMode;
import com.jme3.app.state.AppStateManager;
import mygame.entity.player.Player;

/**
 *
 * @author Bawb
 */
public abstract class Humanoid extends Entity implements Living {
    
    private AnimControl     animControl;
    private AnimChannel     armChannel, legChannel;
    
    public void createAnimControl() {
        animControl = getModel().getChild("Body").getControl(AnimControl.class);
        armChannel  = animControl.createChannel();
        legChannel  = animControl.createChannel();
        armChannel.addFromRootBone("TopSPine");
        legChannel.addFromRootBone("BottomSpine");
        armChannel.setAnim("StillArms");
        legChannel.setAnim("StillLegs");
    }
    
    @Override
    public AnimControl getAnimControl() {
        return animControl;
    }
    
    @Override
    public void setModel(String path, AppStateManager stateManager) {
        path = "Models/Person/Person.j3o";
        super.setModel(path, stateManager);
        getModel().setLocalScale(.1f);
        getModel().setLocalTranslation(0,.4f,0);
    }
    
    public void run() {
  
        if (this instanceof Player) {
        
            if (!armChannel.getAnimationName().equals("RifleAim")) {
                armChannel.setAnim("RifleAim");
                armChannel.setLoopMode(LoopMode.DontLoop);
            }
            
        }        
        
        else if (!armChannel.getAnimationName().equals("UnarmedRun")) {
            armChannel.setAnim("UnarmedRun");
        }
      
        if (!legChannel.getAnimationName().equals("RunAction")) {
            legChannel.setAnim("RunAction");
        }    
      
    }
  
    public void idle() {
  
        if (this instanceof Player) {
        
            if (!armChannel.getAnimationName().equals("RifleAim")) {
                armChannel.setAnim("RifleAim");
                armChannel.setLoopMode(LoopMode.DontLoop);
            }
            
        }
        
        else if (!armChannel.getAnimationName().equals("StillArms")) {
            armChannel.setAnim("StillArms");
        }
      
        if (!legChannel.getAnimationName().equals("StillLegs")) {
            legChannel.setAnim("StillLegs");
        }
        
    }
    
    public void die() {
        
        if (!armChannel.getAnimationName().equals("Die")) {
            armChannel.setAnim("Die");
            armChannel.setLoopMode(LoopMode.DontLoop);
        }
      
        if (!legChannel.getAnimationName().equals("Die")) {
            legChannel.setAnim("Die");
            legChannel.setLoopMode(LoopMode.DontLoop);
        }   
    
    }
    
}
