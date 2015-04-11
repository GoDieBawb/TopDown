/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import mygame.scene.SceneManager;
import mygame.entity.EntityManager;
import mygame.entity.player.PlayerManager;

/**
 *
 * @author Bawb
 */
public class GameManager extends AbstractAppState {
    
    private SceneManager      sceneManager;
    private PlayerManager     playerManager;
    private EntityManager     entityManager;
    private SimpleApplication app;
    
    public GameManager(SimpleApplication app) {
        this.app = app;
        createSceneManager();
        createPlayerManager();
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
    
}
