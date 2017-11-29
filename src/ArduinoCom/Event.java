package ArduinoCom;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 * This class is meant for holding a state/flagg (UP or DOWN) such that an
 * class can set a state and another can wait until a certain state is reached
 * then execute.
 * 
 * @author Morten
 */
public class Event
{

    public enum EventState {UP, DOWN};
    protected   EventState  value;
    
    public Event() {

    }

    /**
     * awaits until the event has the desiered state
     * 
     * @param state, The desiered state of the event
     * @throws InterruptedException 
     */
    public synchronized void await(EventState state)
            throws InterruptedException {
        while (value != state) {
            wait();
        }
    }
    
    
    /**
     * Sets the event state to UP, regardless of previous state;
     */
    public synchronized void set() {
        value = EventState.UP;
        notifyAll();
    }
    
    
    /**
     * Resets the event state to DOWN regardless of previous state.
     */
    public synchronized void reset() {
        value = EventState.DOWN;
        notifyAll();
    }
    
    
    /**
     * Toggles the event to to the different state that it is already in
     * If state is UP, the state will be DOWN after toggle.
     * If state is DOWN, the state will be UP after toggle.
     */
    public synchronized void toggle() {
        if (value == EventState.DOWN) {
            value = EventState.UP;
        } else {
            value = EventState.DOWN;
        }
        notifyAll();
    }
    
    
    /**
     * returns the state of the Event.
     * 
     * @return EventState, the state of the event, UP or DOWN.
     */
    public synchronized EventState state() {
        return value;
    }

}
