/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.entity.player;

import com.jme3.app.state.AppStateManager;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import mygame.GameManager;
import tonegod.gui.controls.extras.Indicator;
import tonegod.gui.core.Element.Orientation;
import tonegod.gui.core.Screen;

/**
 *
 * @author Bawb
 */
public class Hud {
    
    private Indicator healthDisplay;
    private Screen    screen;
    private Player    player;
    
    public Hud(AppStateManager stateManager, Player player) {
        screen      = stateManager.getState(GameManager.class).getUtilityManager().getGuiManager().getScreen();
        this.player = player;
        createHealthDisplay();
    }
    
    private void createHealthDisplay() {
        
        healthDisplay = new Indicator(screen, "Health Display", new Vector2f(screen.getWidth()*.9f, screen.getHeight()/10), Orientation.HORIZONTAL) {
            
            @Override
            public void onChange(float a, float b) {
            
            }
        
        };
        
        healthDisplay.setIndicatorColor(ColorRGBA.Red);
        healthDisplay.setMaxValue(player.getMaxHealth());
        healthDisplay.setX(screen.getWidth()/10 - healthDisplay.getWidth()/2);
        healthDisplay.setY(0 + healthDisplay.getHeight());
        screen.addElement(healthDisplay);
        
    }
    
    private void updateHealthDisplay() {
        healthDisplay.setCurrentValue(player.getHealth());
    }
    
    public void update(float tpf) {
        
        updateHealthDisplay();
        
    }
    
}
