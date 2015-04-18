/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.entity.monster;

import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import java.util.Random;
import mygame.entity.ai.Finder;
import mygame.entity.ai.FinderControl;
import mygame.GameManager;
import mygame.entity.Humanoid;
import mygame.entity.PhysicalEntity;
import mygame.entity.player.Player;
import mygame.entity.powerup.FireRate;
import mygame.entity.powerup.Health;
import mygame.entity.powerup.PowerUp;
import mygame.entity.powerup.Speed;

/**
 *
 * @author Bawb
 */
public class Zombie extends Humanoid implements PhysicalEntity, Finder, Monster {

    private BetterCharacterControl phys;
    private FinderControl          fc;
    private AppStateManager        stateManager;
    private int                    moveSpeed;
    private int                    maxHealth = 15;
    private int                    currentHealth = 15;
    private boolean                isDead;
    private Long                   deathTime;
    private Long                   lastAttack;
    
    public Zombie(AppStateManager stateManager) {
        this.stateManager = stateManager;
        lastAttack        = System.currentTimeMillis();
        setModel("", stateManager);
        createAnimControl();
        getModel().setMaterial(stateManager.getApplication().getAssetManager().loadMaterial("Materials/Zombie.j3m"));
    }
    
    @Override
    public void createPhys() {
        phys = new BetterCharacterControl(.25f, 1f, 150);
        phys.setGravity(new Vector3f(0,-50,0));
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
        
        if (randInt(1,5) == 5) {
            dropItem();
        }
        
    }    
    
    private void dropItem() {
        
        int item = randInt(1,3);
        
        PowerUp pw = new PowerUp(stateManager);
        
        switch(item) {
            
            case 1:
                pw = (PowerUp) new FireRate(stateManager);
                break;
                
            case 2:
                pw = (PowerUp) new Speed(stateManager);
                break;
                
            case 3:
                pw = (PowerUp) new Health(stateManager);
                break;
                
        }
    
        pw.setLocalTranslation(this.getWorldTranslation().multLocal(1,0,1).add(0,.5f,0));
        
    }
    
    private int randInt(int min, int max) {
        
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
        
    }    
    
    public boolean isDead() {
        return isDead;
    }
    
    public void setDeathTime() {
        deathTime = System.currentTimeMillis();
    }    
    
    @Override
    public void attack() {
        
        punch();
        
        if (System.currentTimeMillis() / 1000 - lastAttack / 1000 > 3) {
            
            Player player = stateManager.getState(GameManager.class).getPlayerManager().getPlayer();
            player.setHealth(player.getHealth() - 3);
            lastAttack    = System.currentTimeMillis();
            
            if (player.getHealth() < 0) {
                player.setHealth(0);
            }
            
        }

    }

    @Override
    public void act() {
        
        if (getWorldTranslation().y < -1) {
            getPhys().warp(getWorldTranslation().addLocal(0,1,0));
        }
        
        if (isDead) {
            
            if (System.currentTimeMillis()/1000 - deathTime/1000 > 3) {
                removeFromParent();
            }
            
            return;
        }
        
        Player player = stateManager.getState(GameManager.class).getPlayerManager().getPlayer();
        float  dist   = player.getWorldTranslation().distance(getWorldTranslation());
        
        if (getFinderControl().atGoal() && dist < 1) {
            attack();
        }
        
        else if (dist < 80 && !getFinderControl().isFinding()) {
            getFinderControl().findTarget(player);
        }
        
        else if (dist > 80) {
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
