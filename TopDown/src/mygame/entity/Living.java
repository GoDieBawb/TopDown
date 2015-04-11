/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.entity;

import mygame.entity.Animated;

/**
 *
 * @author Bawb
 */
public interface Living extends Animated {
    
    public void run();
    
    public void die();

}
