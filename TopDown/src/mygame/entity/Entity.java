/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.entity;

import com.jme3.app.state.AppStateManager;
import com.jme3.scene.Node;

/**
 *
 * @author Bawb
 */
public class Entity extends Node{
    
    private Node    model;
    private boolean isHid;
    private boolean inProx;
    
    public void setModel(String path, AppStateManager stateManager) {
        model = (Node) stateManager.getApplication().getAssetManager().loadModel(path);
        attachChild(model);
    }
    
    public void setModel(Node model) {
        this.model = model;
    }
    
    public Node getModel() {
        return model;
    }
    
    public void act() {
    
    }
    
}
