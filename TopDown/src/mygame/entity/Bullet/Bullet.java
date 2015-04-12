/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.entity.Bullet;

import com.jme3.app.state.AppStateManager;
import com.jme3.collision.CollisionResults;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import mygame.GameManager;
import mygame.entity.Entity;
import mygame.entity.Vulnerable;

/**
 *
 * @author Bawb
 */
public class Bullet extends Entity {
    
    private Vector3f moveDir;
    private Node     entityNode;
    private Vector3f startSpot;
    
    public Bullet(Vector3f moveDir, Vector3f startSpot, AppStateManager stateManager) {
        this.moveDir   = moveDir.clone();
        this.startSpot = startSpot.clone();
        entityNode     = stateManager.getState(GameManager.class).getEntityManager().getEntityNode();
        setLocalTranslation(startSpot.addLocal(0,1,0));
        makeModel(stateManager);
    }
    
    private void makeModel(AppStateManager stateManager) {
        Box b       = new Box(.025f,.025f,.025f);
        Geometry g  = new Geometry("Box", b);
        Material m  = stateManager.getApplication().getAssetManager().loadMaterial("Common/Materials/RedColor.j3m");
        Node bullet = new Node("Bullet");
        g.setMaterial(m);
        bullet.attachChild(g);
        this.setModel(bullet, stateManager); 
        entityNode.attachChild(this);
    }
    
    private void move(float tpf)  {
        
        if (getWorldTranslation().distance(startSpot) > 10) {
            this.removeFromParent();
        }
        
        this.move(moveDir.normalize().mult(10).mult(tpf));
    }
    
    private void collisionCheck() {
        
        CollisionResults results = new CollisionResults();
        
        entityNode.collideWith(this.getWorldBound(), results);
        
        if (results.size() > 0) {
            
            Entity hitEntity = findEntity(results.getCollision(0).getGeometry().getParent());
            
            if (hitEntity instanceof Vulnerable) {
            
                Vulnerable vul = (Vulnerable) hitEntity;
                vul.setHealth(vul.getHealth()-3);
                
            }
            
        }
        
    }
    
    private Entity findEntity(Node node) {
    
        if (node instanceof Entity) {
            return (Entity) node;
        }
        
        else if (!node.getName().toLowerCase().contains("root")) {
            return findEntity(node.getParent());
        }
        
        else {
            return null;
        }
        
    }
    
    @Override
    public void act(float tpf) {
        move(tpf);
        collisionCheck();
    }
    
}
