/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.entity.player;

import mygame.entity.player.Player;
import com.jme3.app.SimpleApplication;

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
    
    public Player getPlayer() {
        return player;
    }
    
}
