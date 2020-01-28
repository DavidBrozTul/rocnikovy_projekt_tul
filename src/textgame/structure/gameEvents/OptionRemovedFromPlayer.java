/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgame.structure.gameEvents;

import textgame.structure.Option;
import textgame.structure.gameEvents.GameEvent;

/**
 *
 * @author David Brož
 */
public class OptionRemovedFromPlayer extends GameEvent {
    
    private Option option;
    
    public OptionRemovedFromPlayer() {
    }

    public OptionRemovedFromPlayer(Option option) {
        this.option=option;
    }

    @Override
    public GameEventType getEventType() {
        return GameEventType.OPTION_REMOVED_FROM_PLAYER;
    }

    @Override
    public Class getReturnClass() {
        return Option.class;
    }

    @Override
    public Class getSettingClass() {
        return Option.class;
    }

    @Override
    public void setValue(Object o) {
        if(!(o instanceof Option))throw new IllegalArgumentException("Expected Option got " + o.getClass());
            option=((Option)o);
    }

    @Override
    public Object getValue() {
        return option;
    }

    @Override
    public String toString() {
        return "OptionRemovedFromPlayer{" + option + '}';
    }

    
    
}