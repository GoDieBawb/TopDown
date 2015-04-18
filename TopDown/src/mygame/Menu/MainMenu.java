/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.Menu;

import com.jme3.app.SimpleApplication;
import com.jme3.input.event.MouseButtonEvent;
import com.jme3.math.Vector2f;
import mygame.GameManager;
import tonegod.gui.controls.buttons.ButtonAdapter;
import tonegod.gui.core.Screen;
import tonegod.gui.effects.Effect;

/**
 *
 * @author Bawb
 */
public class MainMenu {
    
    private SimpleApplication app;
    private ButtonAdapter     startButton;
    private Screen            screen;
    private boolean           active;
    private Long              startPressTime;
    private boolean           startPressed;
    
    public MainMenu(SimpleApplication app) {
        this.app = app;
        screen   = app.getStateManager().getState(GameManager.class).getUtilityManager().getGuiManager().getScreen();
        createStartButton();
    }
    
    private void createStartButton() {
    
        startButton = new ButtonAdapter(screen, "Start Button", new Vector2f(screen.getWidth()/5, screen.getHeight()/10)) {
        
            @Override
            public void onButtonMouseLeftUp(MouseButtonEvent evt, boolean toggled) {
            
                if (startPressed)
                    return;
                
                hideMenu();
                startPressTime = System.currentTimeMillis();
                startPressed   = true;
                
            } 
            
        };
        
        Effect hideEffect = new Effect(Effect.EffectType.FadeOut, Effect.EffectEvent.Hide, 2.2f);
        Effect showEffect = new Effect(Effect.EffectType.FadeIn, Effect.EffectEvent.Show, 2.2f);
        startButton.addEffect(hideEffect);
        startButton.addEffect(showEffect);
        screen.addElement(startButton);
        startButton.setPosition(screen.getWidth()/2 - startButton.getWidth()/2, screen.getHeight()/2 - startButton.getHeight()/2);
        startButton.setText("Start Game");
        
    }
    
    public void showMenu() {
        startButton.showWithEffect();
        active = true;
    }
    
    public void hideMenu() {
        startButton.hideWithEffect();
    }
    
    public boolean isActive() {
        return active;
    }
    
    private void startGame() {
        app.getStateManager().getState(GameManager.class).startGame();
        active       = false;
        startPressed = false;
    }
    
    public void update() {
        
        if (startPressed)
            if (System.currentTimeMillis()/100 - startPressTime/100 > 22f) {
                startGame();
            }
        
    }
    
}
