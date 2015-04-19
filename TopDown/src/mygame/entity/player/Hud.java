/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.entity.player;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.TextureKey;
import com.jme3.font.BitmapFont;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.texture.Texture;
import mygame.GameManager;
import mygame.util.InteractionManager;
import mygame.util.UtilityManager;
import tonegod.gui.controls.extras.Indicator;
import tonegod.gui.controls.text.TextElement;
import tonegod.gui.core.Element.Orientation;
import tonegod.gui.core.Screen;
import tonegod.gui.effects.Effect;

/**
 *
 * @author Bawb
 */
public class Hud {
    
    private Indicator   healthDisplay;
    private TextElement scoreDisplay;
    private Screen      screen;
    private Player      player;
    private MyJoystick  leftStick, rightStick;
    private AppStateManager stateManager;
    private UtilityManager um;
    
    public Hud(AppStateManager stateManager, Player player) {
        um                = stateManager.getState(GameManager.class).getUtilityManager();
        screen            = um.getGuiManager().getScreen();
        this.stateManager = stateManager;
        this.player       = player;
        createHealthDisplay();
        createScoreDisplay();
        createLeftStick();
        createRightStick();
    }
    
    private void createHealthDisplay() {
        
        healthDisplay = new Indicator(screen, "Health Display", new Vector2f(screen.getWidth()*.9f, screen.getHeight()/10), Orientation.HORIZONTAL) {
            
            @Override
            public void onChange(float a, float b) {}
        
        };
        
        Effect hideEffect = new Effect(Effect.EffectType.FadeOut, Effect.EffectEvent.Hide, 2.2f);
        Effect showEffect = new Effect(Effect.EffectType.FadeIn, Effect.EffectEvent.Show, 2.2f);
        
        healthDisplay.addEffect(hideEffect);
        healthDisplay.addEffect(showEffect);        
        
        healthDisplay.setIndicatorColor(ColorRGBA.Red);
        healthDisplay.setMaxValue(player.getMaxHealth());
        healthDisplay.setX(screen.getWidth()/10 - healthDisplay.getWidth()/3f);
        healthDisplay.setY(0 + healthDisplay.getHeight());
        screen.addElement(healthDisplay);
        healthDisplay.hide();
    }
    
    private void updateHealthDisplay() {
        healthDisplay.setCurrentValue(player.getHealth());
    }
    
    private void createScoreDisplay() {
        
        BitmapFont font = stateManager.getApplication().getAssetManager().loadFont("Interface/Impact.fnt");
        
        scoreDisplay    = new TextElement(screen, "Score Display", new Vector2f(300,300), font) {
        
            @Override
            public void onEffectStart() {}
            
            @Override
            public void onEffectStop() {}
            
            @Override
            public void onUpdate(float tpf){}
            
        };
        
        Effect hideEffect = new Effect(Effect.EffectType.FadeOut, Effect.EffectEvent.Hide, 2.2f);
        Effect showEffect = new Effect(Effect.EffectType.FadeIn, Effect.EffectEvent.Show, 2.2f);
        
        scoreDisplay.addEffect(hideEffect);
        scoreDisplay.addEffect(showEffect);
        scoreDisplay.setText("Score: " + player.getScore());
        ((SimpleApplication) stateManager.getApplication()).getGuiNode().attachChild(scoreDisplay);
        scoreDisplay.setDimensions(screen.getWidth()/5, screen.getHeight()/10);
        scoreDisplay.setPosition(screen.getWidth() - scoreDisplay.getWidth() *1.5f, screen.getHeight() - scoreDisplay.getHeight());
        scoreDisplay.hide();
    }
    
    private void createLeftStick(){
        
        leftStick = new MyJoystick(screen, Vector2f.ZERO, (int)(screen.getWidth()/6)) {
            
            InteractionManager im = um.getInteractionManager();
            
            @Override
            public void show() {
                
                boolean isAndroid = "Dalvik".equals(System.getProperty("java.vm.name"));
                
                if (!isAndroid)
                    return;
                
                super.show();
                
            }
            
            @Override
            public void onUpdate(float tpf, float deltaX, float deltaY) {
            
                
                float dzVal = .3f; // Dead zone threshold
            
                if (deltaX < -dzVal) {
                    im.setLeft(true);
                    im.setRight(false);
                }
            
                else if (deltaX > dzVal) {
                    im.setRight(true);
                    im.setLeft(false);
                }
            
                else {
                    im.setRight(false);
                    im.setLeft(false);
                }
            
                if (deltaY < -dzVal) {
                    im.setDown(true);
                    im.setUp(false);
                }
            
                else if (deltaY > dzVal) {
                    im.setDown(false);
                    im.setUp(true);
                }
            
                else {
                    im.setUp(false);
                    im.setDown(false);
                }
            
            }
        
        };
        
        Effect hideEffect = new Effect(Effect.EffectType.FadeOut, Effect.EffectEvent.Hide, 2.2f);
        Effect showEffect = new Effect(Effect.EffectType.FadeIn, Effect.EffectEvent.Show, 2.2f);       
        
        leftStick.addEffect(hideEffect);
        leftStick.addEffect(showEffect);        
        
        TextureKey key = new TextureKey("Textures/barrel.png", false);
        Texture tex = ((SimpleApplication)stateManager.getApplication()).getAssetManager().loadTexture(key);
        leftStick.setTextureAtlasImage(tex, "x=20|y=20|w=120|h=35");
        leftStick.getThumb().setTextureAtlasImage(tex, "x=20|y=20|w=120|h=35");
        screen.addElement(leftStick, true);
        leftStick.setPosition(screen.getWidth()/10 - leftStick.getWidth()/2, screen.getHeight() / 10f - leftStick.getHeight()/5);
        // Add some fancy effects
        Effect fxIn = new Effect(Effect.EffectType.FadeIn, Effect.EffectEvent.Show,.5f);
        leftStick.addEffect(fxIn);
        Effect fxOut = new Effect(Effect.EffectType.FadeOut, Effect.EffectEvent.Hide,.5f);
        leftStick.addEffect(fxOut);
        leftStick.show();
            
    }
    
    private void createRightStick(){
        
        rightStick = new MyJoystick(screen, Vector2f.ZERO, (int)(screen.getWidth()/6)) {
            InteractionManager im = um.getInteractionManager();
            
            @Override
            public void show() {
                
                boolean isAndroid = "Dalvik".equals(System.getProperty("java.vm.name"));
                
                if (!isAndroid)
                    return;
                
                super.show();
                
            }
            
            @Override
            public void onUpdate(float tpf, float deltaX, float deltaY) {
            
                float dzVal = .3f; // Dead zone threshold
            
                if (deltaX < -dzVal) {
                    im.setLeft1(true);
                    im.setRight1(false);
                }
            
                else if (deltaX > dzVal) {
                    im.setRight1(true);
                    im.setLeft1(false);
                }
            
                else {
                    im.setRight1(false);
                    im.setLeft1(false);
                }
            
                if (deltaY < -dzVal) {
                    im.setDown1(true);
                    im.setUp1(false);
                }
            
                else if (deltaY > dzVal) {
                    im.setDown1(false);
                    im.setUp1(true);
                }
            
                else {
                    im.setUp1(false);
                    im.setDown1(false);
                }
            
            }
        
        };
        
        Effect hideEffect = new Effect(Effect.EffectType.FadeOut, Effect.EffectEvent.Hide, 2.2f);
        Effect showEffect = new Effect(Effect.EffectType.FadeIn, Effect.EffectEvent.Show, 2.2f);       
        
        rightStick.addEffect(hideEffect);
        rightStick.addEffect(showEffect);
        
        TextureKey key = new TextureKey("Textures/barrel.png", false);
        Texture tex = ((SimpleApplication)stateManager.getApplication()).getAssetManager().loadTexture(key);
        rightStick.setTextureAtlasImage(tex, "x=20|y=20|w=120|h=35");
        rightStick.getThumb().setTextureAtlasImage(tex, "x=20|y=20|w=120|h=35");
        screen.addElement(rightStick, true);
        rightStick.setPosition(screen.getWidth()*.9f - rightStick.getWidth()/2, screen.getHeight() / 10f - rightStick.getHeight()/5);
        // Add some fancy effects
        Effect fxIn = new Effect(Effect.EffectType.FadeIn, Effect.EffectEvent.Show,.5f);
        rightStick.addEffect(fxIn);
        Effect fxOut = new Effect(Effect.EffectType.FadeOut, Effect.EffectEvent.Hide,.5f);
        rightStick.addEffect(fxOut);
        rightStick.show();
            
    }    
    
    private void updateScoreDisplay() {
        scoreDisplay.setText("Score: " + player.getScore());
    }
    
    public void showHud() {
        healthDisplay.showWithEffect();
        scoreDisplay.showWithEffect();   
        rightStick.showWithEffect();
        leftStick.showWithEffect();        
    }
    
    public void hideHud() {
        healthDisplay.hideWithEffect();
        scoreDisplay.hideWithEffect();
        leftStick.hideWithEffect();
        rightStick.hideWithEffect();
    }
    
    public void update(float tpf) {
        
        updateHealthDisplay();
        updateScoreDisplay();
        
    }
    
}
