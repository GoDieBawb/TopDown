/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import mygame.scene.SceneManager;
import mygame.entity.EntityManager;
import mygame.entity.player.PlayerManager;
import mygame.util.UtilityManager;

/**
 *
 * @author Bawb
 */
public class GameManager extends AbstractAppState {
    
    private SceneManager      sceneManager;
    private PlayerManager     playerManager;
    private EntityManager     entityManager;
    private UtilityManager    utilityManager;
    private SimpleApplication app;
    
    public GameManager(SimpleApplication app) {
        this.app = app;
        createUtilityManager();
        createSceneManager();
        createPlayerManager();
        createEntityManager();
    }
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        playerManager.initPlayer();
        startGame();
    } 
    
    private void startGame() {
        sceneManager.setScene("Map");
        playerManager.placePlayer();
        entityManager.createZombie();
        entityManager.createZombie();
        entityManager.createZombie();
        entityManager.createZombie();
    }
    
    private void createEntityManager() {
        entityManager = new EntityManager(app);
    }
    
    public EntityManager getEntityManager() {
        return entityManager;
    }
    
    private void createPlayerManager() {
        playerManager = new PlayerManager(app);
    }
    
    public PlayerManager getPlayerManager() {
        return playerManager;
    }
    
    private void createSceneManager() {
        sceneManager = new SceneManager(app);
    }
    
    public SceneManager getSceneManager() {
        return sceneManager;
    }
    
    private void createUtilityManager() {
        utilityManager = new UtilityManager(app);
    }
    
    public UtilityManager getUtilityManager() {
        return utilityManager;
    }
    
    @Override
    public void update(float tpf) {
        playerManager.update(tpf);
        entityManager.update(tpf);
    }
    
}
