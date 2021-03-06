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
public class AddToCustomValue implements Action, java.io.Serializable {

    private String valueName;
    private Integer value;

    public AddToCustomValue(String valueName, Integer value) {
        this.valueName = valueName;
        this.value = value;
    }

    public String getValueName() {
        return valueName;
    }

    public void setValueName(String valueName) {
        this.valueName = valueName;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public void act() {
        Game.getInstance().getPlayer().setCustomValue(valueName, value + Game.getInstance().getPlayer().getCtustomValue(valueName), true);
    }

    @Override
    public String toString() {
        return "AddToCustomValue{" + valueName + ":+" + value + '}';
    }
    private boolean isValid = true;

    @Override
    public boolean isValid() {
        return isValid;
    }

    @Override
    public void setValidity(boolean b) {
        isValid = b;
    }

}
