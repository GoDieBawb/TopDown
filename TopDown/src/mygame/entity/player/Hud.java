/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.entity.player;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
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
    
    private Indicator  healthDisplay;
    private BitmapText scoreDisplay;
    private Screen     screen;
    private Player     player;
    private AppStateManager stateManager;
    
    public Hud(AppStateManager stateManager, Player player) {
        screen            = stateManager.getState(GameManager.class).getUtilityManager().getGuiManager().getScreen();
        this.stateManager = stateManager;
        this.player       = player;
        createHealthDisplay();
        createScoreDisplay();
    }
    
    private void createHealthDisplay() {
        
        healthDisplay = new Indicator(screen, "Health Display", new Vector2f(screen.getWidth()*.9f, screen.getHeight()/10), Orientation.HORIZONTAL) {
            
            @Override
            public void onChange(float a, float b) {}
        
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
    
    private void createScoreDisplay() {
        
        BitmapFont font = stateManager.getApplication().getAssetManager().loadFont("Interface/Fonts/Default.fnt");
        scoreDisplay    = new BitmapText(font);
        scoreDisplay.setText("Score: " + player.getScore());
        ((SimpleApplication) stateManager.getApplication()).getGuiNode().attachChild(scoreDisplay);
        scoreDisplay.setLocalTranslation(screen.getWidth() - scoreDisplay.getLineWidth() * 2, screen.getHeight() - scoreDisplay.getLineHeight(), 0);
        
    }
    
    private void updateScoreDisplay() {
        scoreDisplay.setText("Score: " + player.getScore());
    }
    
    public void showHud() {
        healthDisplay.show();
        ((SimpleApplication) stateManager.getApplication()).getGuiNode().attachChild(scoreDisplay);   
    }
    
    public void hideHud() {
        healthDisplay.hide();
        scoreDisplay.removeFromParent();
    }
    
    public void update(float tpf) {
        
        updateHealthDisplay();
        updateScoreDisplay();
        
    }
    
}
