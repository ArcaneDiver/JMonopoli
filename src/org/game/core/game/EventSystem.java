package org.game.core.game;

public class EventSystem {

    private static EventSystem instance = null;

    private EventSystem() {}

    private static EventSystem getInstance() {
        if(instance ==null)
            synchronized(EventSystem.class) {
                if( instance == null )
                    instance = new EventSystem();
            }
        return instance;
    }
}
