/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.entity;

import com.jme3.app.SimpleApplication;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.util.ArrayList;
import mygame.GameManager;
import mygame.entity.monster.Zombie;
import mygame.scene.SceneManager;

/**
 *
 * @author Bawb
 */
public class EntityManager {
    
    private Node              entityNode;
    private SimpleApplication app;
    private ArrayList<Entity> sceneEntities;
    
    public EntityManager(SimpleApplication app) {
        this.app = app;
    }
    
    public void createZombie() {
        Zombie zombie;
        zombie = new Zombie(app.getStateManager());
        zombie.createPhys();
        zombie.createFinderControl(app.getStateManager());
        entityNode.attachChild(zombie);
        
        app.getStateManager().getState(GameManager.class).getUtilityManager()
                .getPhysicsManager().getPhysics().getPhysicsSpace()
                    .add(zombie.getPhys());
        
        zombie.getPhys().warp(new Vector3f(5,1,5));
    }        
    
    public void initEntities(SceneManager sceneManager) {

        Node scene = sceneManager.getScene();
        
        entityNode = (Node) scene.getChild("Entity Node");
        
        if(entityNode == null) {
            entityNode =  new Node();
        }
        
        else {
            
            sceneEntities = new ArrayList();
            
            for (int i = 0; i < entityNode.getQuantity(); i++) {
                
                Entity entity = new Entity();
                Node   model  = (Node) entityNode.getChild(i);
                       
                if (model.getUserData("Type") != null) {
                    
                }
                
                entity.setModel(model);
                entity.setName(model.getName());
                sceneEntities.add(entity);

            }
            
            initEntityNode();
            
        }
        
        scene.attachChild(entityNode);
        
    }
    
    private void initEntityNode() {
        
        for (int i = 0; i < sceneEntities.size(); i++) {
            
           if(sceneEntities.get(i) instanceof Entity) {
               
               Entity e = (Entity) sceneEntities.get(i);
               e.setLocalTranslation(e.getModel().getWorldTranslation());
               e.attachChild(e.getModel());
               e.getModel().setLocalTranslation(0,0,0);
               entityNode.attachChild(e);
               
           }
           
           else {
               entityNode.getChild(i).removeFromParent();
           }
           
        }
        
        sceneEntities = null;
    
    }
    
    public Node getEntityNode() {
        return entityNode;
    }
    
    public void update(float tpf) {
        
        for (int i = 0; i < entityNode.getQuantity(); i++) {
        
            if (!(entityNode.getChild(i) instanceof Entity)) {
                initEntityNode();
                return;
            }
            
            Entity currentEntity = (Entity) entityNode.getChild(i);
                
            currentEntity.act();
            
        }
        
    }
    
}
