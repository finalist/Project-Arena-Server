package nl.kennisnet.arena.client.util;

public class AnalyticsUtil {

   public static native void trackHit(String pageName)
   /*-{
   $wnd.pageTracker._initData();
   var trackerResult=$wnd.pageTracker._trackPageview("/arena/"+pageName);
   }-*/;

   public static native void trackEvent(String event)
   /*-{
   $wnd.pageTracker._initData();
   var trackerResult=$wnd.pageTracker._trackEvent('arena event',event); 
   if (!trackerResult){
      $wnd.alert("Het bijhouden van het het event "+event+" is mislukt");
   }
   }-*/;

}