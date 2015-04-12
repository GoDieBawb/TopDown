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

/**
 *
 * @author Bawb
 */
public class TopDownCameraManager {
    
    private SimpleApplication app;
    private Player            player;
    
    public TopDownCameraManager(AppStateManager stateManager) {
        app     = (SimpleApplication) stateManager.getApplication();
        player  = app.getStateManager().getState(GameManager.class).getPlayerManager().getPlayer();
    }
    
    private void topDownCamMove(){
        app.getCamera().setLocation(player.getWorldTranslation().addLocal(0,10,0));
        app.getCamera().lookAt(player.getWorldTranslation().multLocal(1,0,1), new Vector3f(0,1,0));
    }      
    
    public void update(float tpf) {
        topDownCamMove();
    }
    
}
