package nl.kennisnet.arena.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class LogEvent extends GwtEvent<LogEvent.Handler> {

   /**
    * Interface to describe this event. Handlers must implement.
    */
   public interface Handler extends EventHandler {
      public void onLog(LogEvent p);
   }

   @Override
   protected void dispatch(LogEvent.Handler handler) {
      handler.onLog(this);
   }

   @Override
   public GwtEvent.Type<LogEvent.Handler> getAssociatedType() {
      return TYPE;
   }
   
   public LogEvent(String logMessage){
      this.logMessage=logMessage;
   }

   public static final GwtEvent.Type<LogEvent.Handler> TYPE = new GwtEvent.Type<LogEvent.Handler>();

   private String logMessage;

   public String getLogMessage() {
      return logMessage;
   }

   public void setLogMessage(String logMessage) {
      this.logMessage = logMessage;
   }


   
   
   
}
