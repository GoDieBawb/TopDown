package mygame.entity.player;

import com.jme3.app.SimpleApplication;
import com.jme3.math.Vector3f;
import mygame.GameManager;

/**
 *
 * @author Bawb
 */
public class PlayerManager {
    
    private Player            player;
    private SimpleApplication app;
    
    public PlayerManager(SimpleApplication app) {
        this.app = app;
        createPlayer();
    }
    
    private void createPlayer() {
        player = new Player(app.getStateManager());
    }
    
    public void initPlayer() {
        player.setMaxHealth(25);
        player.setHealth(25);
        player.createPhys();
        player.createControl();
        player.createAttackControl();
        player.createHud();
        GameManager gm = app.getStateManager().getState(GameManager.class);
        gm.getUtilityManager().getMaterialManager().makeUnshaded(player);
    }
    
    public Player getPlayer() {
        return player;
    }
    
    public void placePlayer() {
        
        app.getRootNode().attachChild(player);
        GameManager gm = app.getStateManager().getState(GameManager.class);
        gm.getSceneManager().getScene().attachChild(player);
        gm.getUtilityManager().getPhysicsManager().getPhysics().getPhysicsSpace().add(player.getPhys());
        gm.getUtilityManager().getPhysicsManager().getPhysics().getPhysicsSpace().setGravity(new Vector3f(0,-50,0));
        player.getPhys().warp(new Vector3f(0,5,0));
        
    }
    
    public void update(float tpf) {
        
        if (player.hasSpeed()) 
            if (System.currentTimeMillis()/1000  - player.getSpeedBonusStartTime() / 1000 > 5)
                player.removeSpeedBonus();
                
        if (player.hasRate()) 
            if (System.currentTimeMillis()/1000  - player.getRateBonusStartTime() / 1000 > 5)
                player.removeRateBonus();        
        
        player.getTopDownControl().update(tpf);
        player.getAttackControl().update(tpf);
        player.getHud().update(tpf);
    } 
    
}
