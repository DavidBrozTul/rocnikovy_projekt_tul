/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgame.structure;

import textgame.structure.gameEvents.GameEvent;
import java.util.ArrayList;
import textgame.structure.actions.Action;
import textgame.structure.gameEvents.GameEvent.GameEventType;


/**
 *
 * @author David Brož
 */
public class GameEventListener implements java.io.Serializable {

    public GameEventType expectedEventType;
    private int id;
    private String name;
    private boolean enabled = true;
    private ArrayList<Action> actions;
    private ArrayList referencedBy;
    private Object[] expectedValues;

    public enum NumberComparasion {
        GREATER_THAN, LESSER_THAN, EQUAL, ANY
    };
    private NumberComparasion numberComparator = NumberComparasion.EQUAL;
    private boolean hasExpectedValue = false;

    public GameEventListener(String name, GameEventType expectedvEventType, Object[] expectedValue, Action... actionsToDo) {
        referencedBy = new ArrayList();
        this.name = name;
        id = Game.getInstance().getGameEventListenerMaxID();
        Game.getInstance().incGameEventListenerMaxID();
        this.actions = new ArrayList();
        for (Action a : actionsToDo) {
            this.actions.add(a);
        }
        this.expectedEventType = expectedvEventType;
        this.expectedValues = expectedValue;
    }

    void listen(GameEvent gameEvent) {
        
        hasExpectedValue = true;
        //System.out.println("-GAME-EVENT-LISTENER-: Expects GameEvent: "+expectedEventType);
        //System.out.println("-GAME-EVENT-LISTENER-: Got GameEvent: "+gameEvent.getGameEventType());
        if (!enabled ||gameEvent.getGameEventType()!= expectedEventType) {
            return;
        }
        /*System.out.print("-GAME-EVENT-LISTENER-:Expected values values: ");
        for (int i = 0; i < expectedValues.length; i++) {
            System.out.print(expectedValues[i]+": " +expectedValues[i].getClass().toGenericString()+", ");
        }
        System.out.print("-GAME-EVENT-LISTENER-:Got values values: ");
        for (int i = 0; i < gameEvent.getValues().length; i++) {
            System.out.print(gameEvent.getValues()[i]+": " +gameEvent.getValues()[i].getClass().toGenericString()+", ");
        }
        //System.out.println("");*/
        
        for (int i = 0; i < expectedValues.length; i++) {
            if (i >= gameEvent.getValues().length) {
                break;
            }
            if (gameEvent.getValues()[i] instanceof Integer && expectedValues[i] instanceof Integer ) {
               System.out.println("--GAME-EV-L: GOT THE INTEGER");
               hasExpectedValue= comparatorTest((Integer) gameEvent.getValues()[i], (Integer)expectedValues[i]);
               System.out.println("--GAME-EV-L: PASSED TEST "+hasExpectedValue);
               break;
            }
            else if (!expectedValues[i].equals(gameEvent.getValues()[i])) {
                hasExpectedValue = false;
                break;
            }
        }
        if (hasExpectedValue) {
            if(gameEvent.getGameEventType()==GameEventType.CUSTOM_VALUE_CHANGED)System.out.println("EVENT-LISTENER:"+name+" Acting. Got"+ gameEvent.getGameEventType()+"  "+gameEvent.getValues()[0].toString()+"  "+gameEvent.getValues()[1].toString());
            System.out.println("EVENT-LISTENER:"+name+" Acting. Got 2 "+ gameEvent.getGameEventType()+"  "+gameEvent.getValues()[0].toString());
            act();
            return ;
        }
        if(gameEvent.getGameEventType()==GameEventType.CUSTOM_VALUE_CHANGED)System.out.println("EVENT-LISTENER:"+name+" Got "+ gameEvent.getGameEventType()+"  "+gameEvent.getValues()[0].toString()+"  "+gameEvent.getValues()[1].toString());
        return;
    }

    private boolean comparatorTest(Integer tempInt, Integer expectedValue) {
        System.out.println("--GEL--: COMPARATOR " +numberComparator +" numbers "+ tempInt +"  "+ expectedValue);
        switch (numberComparator) {
            case ANY:
                return true;
                
            case EQUAL:
                if (tempInt == expectedValue) {
                    return true;
                }
                break;
            case GREATER_THAN:
                if (tempInt > expectedValue) {
                    return true;
                }
                break;
            case LESSER_THAN:
                if (tempInt < expectedValue) {
                    return true;
                }
                break;
        }
        return false;
    }

    public void enable() {
        enabled = true;
    }

    public void disable() {
        enabled = false;
    }

    @Override
    public String toString() {
        return id + "_" + name;
    }

    public GameEventType getExpectedEventType() {
        return expectedEventType;
    }

    public void setExpectedvEventType(GameEventType expectedvEventType) {
        this.expectedEventType = expectedvEventType;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public ArrayList<Action> getActions() {
        checkActionValidity();
        return actions;
    }

    public Object[] getExpectedValues() {
        return expectedValues;
    }

    public void setExpectedValues(Object[] expectedValue) {
        System.out.println("-GAME-EVENT-LISTENER-: Values set to:"+expectedValue.toString());
        this.expectedValues = expectedValue;
    }

    public void addAction(Action actionToAdd) {
        actions.add(actionToAdd);
    }

    public void deleteAction(Action actionToDelete) {
        actions.remove(actionToDelete);
    }

    private void act() {
        Game.getInstance().throwGameEvent(new GameEvent(new Object[]{this},GameEventType.GAME_EVENT_LISTENER_ACTED));
        for (Action a : actions) {
            a.act();
        }
    }

    public void deleteAllReferencesToThis() {
        for (Object o : referencedBy) {
            if (o instanceof Action) {
                Action a = (Action) o;
                a.setValidity(false);
            }
        }
    }

    void removeIsReferencedBy(Object o) {
        if (referencedBy.contains(o)) {
            referencedBy.remove(o);
        } else {
            System.out.println("-GAME-EVENT-LISTENER-: Referenced by was atempted to be removed but there was not isntance of: " + o.toString());
        }
    }

    void addIsReferencedBy(Object o) {
        referencedBy.add(o);
    }

    private void checkActionValidity() {
        for (Action action : actions) {
            if (!action.isValid()) {
                actions.remove(action);
            }
        }
    }

    public NumberComparasion getNumberComparator() {
        return numberComparator;
    }

    public void setNumberComparator(NumberComparasion numberComparator) {
        this.numberComparator = numberComparator;
    }
}
