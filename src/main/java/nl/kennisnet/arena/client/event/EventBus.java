package nl.kennisnet.arena.client.event;

import com.google.gwt.event.shared.HandlerManager;

public class EventBus {
   private EventBus() {}
   private static final HandlerManager INSTANCE = new HandlerManager(null);
   public static HandlerManager get() {
           return INSTANCE;
   }
}

