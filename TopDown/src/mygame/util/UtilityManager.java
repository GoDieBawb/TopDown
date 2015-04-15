/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.util;

import com.jme3.app.SimpleApplication;

/**
 *
 * @author Bawb
 */
public class UtilityManager {
    
    private InteractionManager interactionManager;
    private PhysicsManager     physicsManager;
    private MaterialManager    materialManager;
    private GuiManager         guiManager;
    private SimpleApplication  app;
    
    public UtilityManager(SimpleApplication app) {
        this.app = app;
        createInteractionManager();
        createPhysicsManager();
        createMaterialManager();
        createGuiManager();
    }
    
    private void createInteractionManager() {
        interactionManager = new InteractionManager(app);
    }
    
    public InteractionManager getInteractionManager() {
        return interactionManager;
    }
    
    private void createPhysicsManager() {
        physicsManager = new PhysicsManager(app.getStateManager());
    }
    
    public PhysicsManager getPhysicsManager() {
        return physicsManager;
    }
    
    private void createMaterialManager() {
       materialManager = new MaterialManager(app.getAssetManager());
    }
    
    public MaterialManager getMaterialManager() {
        return materialManager;
    }
    
    private void createGuiManager() {
        guiManager = new GuiManager(app);
    }
    
    public GuiManager getGuiManager() {
        return guiManager;
    }
    
}
