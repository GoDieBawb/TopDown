package mygame.Menu;

import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapFont;
import com.jme3.input.event.MouseButtonEvent;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import mygame.GameManager;
import tonegod.gui.controls.buttons.ButtonAdapter;
import tonegod.gui.controls.text.TextElement;
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
    private TextElement       titleText;
    
    public MainMenu(SimpleApplication app) {
        this.app = app;
        screen   = app.getStateManager().getState(GameManager.class).getUtilityManager().getGuiManager().getScreen();
        createStartButton();
        createTitleText();
    }
    
    private void createTitleText() {
    
        BitmapFont font = app.getAssetManager().loadFont("Interface/Fonts/Plasma.fnt");
        
        titleText = new TextElement(screen, "Text Element", Vector2f.ZERO, new Vector2f(300,50), font) {
        
            @Override
            public void onEffectStop() {
            
            }
            
            @Override
            public void onEffectStart() {
            
            }
            
            @Override
            public void onUpdate(float tpf) {
            
            }
        
        }; 
        
        Material m        = app.getAssetManager().loadMaterial("Materials/Transparent.j3m");
        Effect hideEffect = new Effect(Effect.EffectType.FadeOut, Effect.EffectEvent.Hide, 2.2f);
        Effect showEffect = new Effect(Effect.EffectType.FadeIn, Effect.EffectEvent.Show, 2.2f);
        
        m.setTransparent(true);
        
        titleText.setFontSize(font.getPreferredSize()*1.5f);
        titleText.setFontColor(ColorRGBA.Red);
        titleText.addEffect(hideEffect);
        titleText.addEffect(showEffect);
        titleText.setTextAlign(BitmapFont.Align.Center);
        screen.addElement(titleText);
        titleText.setText("Zombie Shooter"); 
        titleText.setDimensions(screen.getWidth()/2, screen.getWidth()/4);
        titleText.setPosition(screen.getWidth()/2f - titleText.getWidth()/2, screen.getHeight() / 2f + titleText.getHeight()/2);
        
    }
    
    private void createStartButton() {
    
        startButton = new ButtonAdapter(screen, "Start Button", new Vector2f(screen.getWidth()/3, screen.getHeight()/6)) {
        
            @Override
            public void onButtonMouseLeftUp(MouseButtonEvent evt, boolean toggled) {
            
                if (startPressed)
                    return;
                
                hideMenu();
                startPressTime = System.currentTimeMillis();
                startPressed   = true;
                
            } 
            
        };
        
        Material m        = app.getAssetManager().loadMaterial("Materials/Transparent.j3m");
        Effect hideEffect = new Effect(Effect.EffectType.FadeOut, Effect.EffectEvent.Hide, 2.2f);
        Effect showEffect = new Effect(Effect.EffectType.FadeIn, Effect.EffectEvent.Show, 2.2f);
        
        m.setTransparent(true);
        
        startButton.setFont("Interface/Impact.fnt");
        startButton.setFontSize(startButton.getFontSize()*2);
        startButton.setMaterial(m);
        startButton.addEffect(hideEffect);
        startButton.addEffect(showEffect);
        screen.addElement(startButton);
        startButton.setDimensions(screen.getWidth()/3, screen.getWidth()/6);
        startButton.setPosition(screen.getWidth()/2 - startButton.getWidth()/2, screen.getHeight()/2 - startButton.getHeight()/2);
        startButton.setText("Start"); 
    }
    
    public void showMenu() {
        titleText.showWithEffect();
        startButton.showWithEffect();
        active = true;
    }
    
    public void hideMenu() {
        titleText.hideWithEffect();
        startButton.hideWithEffect();
    }
    
    public boolean isActive() {
        return active;
    }
    
    private void startGame() {
        app.getStateManager().getState(GameManager.class).startGame();
        active       = false;
        startPressed = false;
        startButton.setText("Restart");
        titleText.setText("Game Over");
    }
    
    public void update() {
        
        if (startPressed)
            if (System.currentTimeMillis()/100 - startPressTime/100 > 22f) {
                startGame();
            }
        
    }
    
}
