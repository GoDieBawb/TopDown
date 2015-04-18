/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.entity.player;

import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
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
    private TopDownControl           topDownControl;
    private Node                     gun;
    private boolean                  attacking;
    private AttackControl            attackControl;
    private Long                     deathTime;
    private Long                     speedBonusStartTime;
    private Long                     rateBonusStartTime;
    private boolean                  hasSpeed, hasRate;
    private int                      score;
    
    public Player(AppStateManager stateManager) {
        this.stateManager = stateManager;
        setModel("", stateManager);
        createAnimControl();
        setName("Player");
        createGun();
        equipGun();
        getModel().setMaterial(stateManager.getApplication().getAssetManager().loadMaterial("Materials/Slave.j3m"));
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
    
    private void createGun() {
        gun = (Node) stateManager.getApplication().getAssetManager().loadModel("Models/Sten/Sten.j3o");
        attachChild(gun);
        gun.scale(.075f);
        gun.rotate(89.5f,-89.5f,0);
    }
    
    private void equipGun() {
        gun.setLocalTranslation(0, .6f, .25f);
    }
    
    private void dropGun() {
    
        gun.setLocalTranslation(0, 0, .25f);
        
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
    
    public void dropItem() {
    }
    
    public void createHud() {
        hud = new Hud(stateManager, this);
        hud.hideHud();
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
    
    public void giveSpeedBonus() {
        speedBonusStartTime = System.currentTimeMillis();
        hasSpeed            = true;
    }
    
    public void removeSpeedBonus() {
        hasSpeed = false;
    }
    
    public Long getSpeedBonusStartTime() {
        return speedBonusStartTime;
    }
    
    public boolean hasSpeed() {
        return hasSpeed;
    }
    
    public void giveRateBonus() {
        rateBonusStartTime = System.currentTimeMillis();
        hasRate            = true;
    }
    
    public void removeRateBonus() {
        hasRate = false;
    }
    
    public Long getRateBonusStartTime() {
        return rateBonusStartTime;
    }    
    
    public boolean hasRate() {
        return hasRate;
    }
    
    public void setScore(int score) {
        this.score = score;
    }
    
    public int getScore() {
        return score;
    }
    
    public void revive() {
        isDead        = false;
        currentHealth = maxHealth;
        score         = 0;
        hud.showHud();
        equipGun();
    }
    
    @Override
    public void die() {
        super.die();
        deathTime = System.currentTimeMillis();
        isDead = true;
        phys.setWalkDirection(new Vector3f(0,0,0));
        stateManager.getState(GameManager.class).getMainMenu().showMenu();
        hud.hideHud();
        dropGun();
    }
    
    public Long getDeathTime() {
        return deathTime;
    }
    
}
