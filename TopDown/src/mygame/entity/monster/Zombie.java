/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.entity.monster;

import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.scene.Spatial;
import mygame.entity.ai.Finder;
import mygame.entity.ai.FinderControl;
import mygame.GameManager;
import mygame.entity.Humanoid;
import mygame.entity.PhysicalEntity;
import mygame.entity.player.Player;

/**
 *
 * @author Bawb
 */
public class Zombie extends Humanoid implements PhysicalEntity, Finder, Monster {

    private BetterCharacterControl phys;
    private FinderControl          fc;
    private AppStateManager        stateManager;
    private int                    moveSpeed;
    private int                    maxHealth = 25;
    private int                    currentHealth = 25;
    private boolean                isDead;
    private Long                   deathTime;
    
    public Zombie(AppStateManager stateManager) {
        this.stateManager = stateManager;
        setModel("", stateManager);
        createAnimControl();
        getModel().setMaterial(stateManager.getApplication().getAssetManager().loadMaterial("Materials/Zombie.j3m"));
    }
    
    @Override
    public void createPhys() {
        phys = new BetterCharacterControl(.35f, 1.1f, 150);
        addControl(phys);
    }

    @Override
    public BetterCharacterControl getPhys() {
        return phys;
    }

    @Override
    public void createFinderControl(AppStateManager stateManager) {
        
        fc = new FinderControl(stateManager, this) {
        
            @Override
            public void findTarget(Spatial target) {
                super.findTarget(target);
                run();
            }
            
            @Override
            public void stopFinding() {
                super.stopFinding();
                idle();
            }
        
        };
        
        addControl(fc);
        
    }

    @Override
    public FinderControl getFinderControl() {
        return fc;
    }
    
    public void setMaxHealth(int newVal) {
        maxHealth = newVal;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setHealth(int newVal) {
        currentHealth = newVal;
    }

    public int getHealth() {
        return currentHealth;
    }

    public void endLife() {
        
        stateManager.getState(GameManager.class).getUtilityManager()
                .getPhysicsManager().getPhysics().getPhysicsSpace()
                    .remove(getPhys());
        
        getFinderControl().stopFinding();
        setDeathTime();
        die();
        isDead = true;
    }    
    
    public boolean isDead() {
        return isDead;
    }
    
    public void setDeathTime() {
        deathTime = System.currentTimeMillis();
    }    
    
    @Override
    public void attack() {
    
    }

    @Override
    public void act() {
        
        if (isDead) {
            
            if (System.currentTimeMillis()/1000 - deathTime/1000 > 3) {
                removeFromParent();
            }
            
            return;
        }
        
        Player player = stateManager.getState(GameManager.class).getPlayerManager().getPlayer();
        float  dist   = player.getWorldTranslation().distance(getWorldTranslation());
        
        if (getFinderControl().atGoal()) {
            attack();
        }
        
        else if (dist < 50 && !getFinderControl().isFinding()) {
            getFinderControl().findTarget(player);
        }
        
        else if (dist > 50) {
            getFinderControl().stopFinding();
        }
        
    }

    public void setMoveSpeed() {
        moveSpeed = 5;
    }

    public int getMoveSpeed() {
        return moveSpeed;
    }
    
}
