package nl.kennisnet.arena.client.panel;

import nl.kennisnet.arena.client.event.EventBus;
import nl.kennisnet.arena.client.event.LogEvent;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ScrollPanel;

public class LogPanel extends ScrollPanel implements LogEvent.Handler{

   private HTML logArea;

   public LogPanel(){
      super();
      EventBus.get().addHandler(LogEvent.TYPE, this);
      logArea=new HTML();
      setWidth("300px");
      add(logArea);
   }

   @Override
   public void onLog(LogEvent p) {
      logArea.setHTML(logArea.getHTML()+"<br>"+p.getLogMessage());
   }
   
   
}
