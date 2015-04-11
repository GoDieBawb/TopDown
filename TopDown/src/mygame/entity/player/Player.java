/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.entity.player;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.control.BetterCharacterControl;
import java.util.HashMap;
import mygame.GameManager;
import mygame.entity.Humanoid;
import mygame.entity.Vulnerable;
import mygame.entity.PhysicalEntity;

/**
 *
 * @author Bawb
 */
public class Player extends Humanoid implements PhysicalEntity, Vulnerable {
    
    private AppStateManager     stateManager;
    private boolean             isDead;
    private int                 maxHealth;
    private int                 currentHealth;
    private Hud                 hud;
    private boolean             hasChecked;
    private BetterCharacterControl phys;
    private HashMap<Object, Object>  inventory;
    
    public Player(AppStateManager stateManager) {
        this.stateManager = stateManager;
        setModel(null, stateManager);
        createAnimControl();
        createHud();
        setName("Player");
        createInventory();
    }
    
    @Override
    public void createPhys() {
        phys = new BetterCharacterControl(.35f, 1.1f, 100);
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
        isDead = true;
    }
    
    public boolean isDead() {
        return isDead;
    }
    
    private void createInventory() {
        inventory = new HashMap();
    }
    
    public HashMap<Object, Object> getInventory() {
        return inventory;
    }
    
    public void dropItem() {
    }
    
    private void createHud() {
        hud = new Hud(stateManager);
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
