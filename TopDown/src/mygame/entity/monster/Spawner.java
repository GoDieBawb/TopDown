/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.entity.monster;

import com.jme3.scene.Node;
import java.util.Random;
import mygame.entity.Entity;
import mygame.entity.EntityManager;

/**
 *
 * @author Bawb
 */
public class Spawner extends Entity {
    
    private Node          entityNode;
    private EntityManager entityManager;
    
    public Spawner(EntityManager entityManager) {
        this.entityManager = entityManager;
        entityNode         = entityManager.getEntityNode();
    }
    
    private int randInt(int min, int max) {
        
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
        
    }    
    
    @Override
    public void act() {
    
        int monsterCount = 0;
        
        for (int i = 0; i < entityNode.getQuantity(); i++) {
        
            Entity currentEntity = (Entity) entityNode.getChild(i);
            
            if (currentEntity instanceof Monster) {
                monsterCount++;
            }            
            
        }
        
        if (monsterCount < 4) {
            
            if (randInt(1,4) != 4)
                return;
            
            Zombie z = entityManager.getMonsterManager().createZombie();
            z.getPhys().warp(getWorldTranslation());
        }
        
    }
    
}
