/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgame.structure.actions;

import textgame.structure.Game;

/**
 *
 * @author David Brož
 */
public class ShowPlayerInventory implements Action{

    @Override
    public void act() {
        Game.getInstance().setInfo_line(Game.getInstance().getPlayer().inventoryToSting());
    }

    @Override
    public String toString() {
        return "ShowPlayerInventory{" + '}';
    }
    
}
