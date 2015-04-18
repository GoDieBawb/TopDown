/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.entity.monster;

import com.jme3.app.state.AppStateManager;
import com.jme3.scene.Node;
import java.util.Random;
import mygame.GameManager;
import mygame.entity.Entity;
import mygame.entity.EntityManager;
import mygame.entity.player.Player;

/**
 *
 * @author Bawb
 */
public class Spawner extends Entity {
    
    private Node          entityNode;
    private EntityManager entityManager;
    private AppStateManager stateManager;
    
    public Spawner(EntityManager entityManager, AppStateManager stateManager) {
        this.entityManager = entityManager;
        entityNode         = entityManager.getEntityNode();
        this.stateManager  = stateManager;
    }
    
    private int randInt(int min, int max) {
        
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
        
    }    
   
    private int calculateMonsterCount() {
    
        int monsterCount = 4;
        
        Player player = stateManager.getState(GameManager.class).getPlayerManager().getPlayer();
        
        if (player.getScore() > 300) {
            monsterCount = 5;
        }
            
        if (player.getScore() > 1200) {
            monsterCount = 5;
        }        
        
        if (player.getScore() > 3000) {
            monsterCount = 6;
        }        
        
        if (player.getScore() > 6000) {
            monsterCount = 7;
        }        
        
        return monsterCount;
        
     }

    public Spawner() {
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
        
        if (monsterCount < calculateMonsterCount()) {
            
            if (randInt(1,4) != 4)
                return;
            
            Zombie z = entityManager.getMonsterManager().createZombie();
            z.getPhys().warp(getWorldTranslation());
            
        }
        
    }
    
}
