/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.entity.player;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.scene.Node;
import java.util.HashMap;
import mygame.GameManager;
import mygame.entity.Humanoid;
import mygame.entity.Vulnerable;
import mygame.entity.PhysicalEntity;
import mygame.util.topdown.TopDownControl;

/**
 *
 * @author Bawb
 */
public class Player extends Humanoid implements PhysicalEntity, Vulnerable {
    
    private AppStateManager          stateManager;
    private boolean                  isDead;
    private int                      maxHealth;
    private int                      currentHealth;
    private Hud                      hud;
    private boolean                  hasChecked;
    private BetterCharacterControl   phys;
    private HashMap<Object, Object>  inventory;
    private TopDownControl           topDownControl;
    private Node                     gun;
    private boolean                  attacking;
    private AttackControl            attackControl;
    private Long                     deathTime;
    
    public Player(AppStateManager stateManager) {
        this.stateManager = stateManager;
        setModel("", stateManager);
        createAnimControl();
        setName("Player");
        createInventory();
        equipGun();
    }
    
    public void createAttackControl() {
        attackControl = new AttackControl(stateManager);
    }
    
    public AttackControl getAttackControl() {
        return attackControl;
    }
    
    public void setIsAttacking(boolean newVal) {
        attacking = newVal;
    }
    
    public boolean isAttacking() {
        return attacking;
    }
    
    private void equipGun() {
        gun = (Node) stateManager.getApplication().getAssetManager().loadModel("Models/Sten/Sten.j3o");
        attachChild(gun);
        gun.scale(.075f);
        gun.setLocalTranslation(0, .6f, .25f);
        gun.rotate(89.5f,-89.5f,0);
    }
    
    public void createControl() {
        topDownControl = new TopDownControl(stateManager);
    }    
    
    public TopDownControl getTopDownControl() {
        return topDownControl;
    }
    
    @Override
    public void createPhys() {
        phys = new BetterCharacterControl(.25f, 1f, 100);
        addControl(phys);
    }
    
    public BetterCharacterControl getPhys() {
        return phys;
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
        setDeathTime();
        isDead = true;
    }
    
    public boolean isDead() {
        return isDead;
    }
    
    public void setDeathTime() {
        deathTime = System.currentTimeMillis();
    }
    
    private void createInventory() {
        inventory = new HashMap();
    }
    
    public HashMap<Object, Object> getInventory() {
        return inventory;
    }
    
    public void dropItem() {
    }
    
    public void createHud() {
        hud = new Hud(stateManager, this);
    }
    
    public Hud getHud() {
        return hud;
    }
    
    public void setHasChecked(boolean newVal) {
        hasChecked = newVal;
    }
    
    public boolean hasChecked() {
        return hasChecked;
    }
    
    public void restartGame() {
        GameManager gm = stateManager.getState(GameManager.class);
        isDead = false;
    }
    
    @Override
    public void die() {
        super.die();
        ((SimpleApplication) stateManager.getApplication()).getRootNode().detachAllChildren();
        stateManager.getState(GameManager.class).getSceneManager().removeScene();
        inventory = new HashMap();
        detachAllChildren();
        isDead = true;
    }
    
}
