package nl.kennisnet.arena.web.frontend;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.configuration.CompositeConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FrontendController {

   private final CompositeConfiguration configuration;

   @Autowired
   public FrontendController(CompositeConfiguration configuration) {
      super();
      this.configuration = configuration;
   }

//   @RequestMapping(value = "/designer.do")
//   public ModelAndView handleDesigner(){
//      return handleGWTPage("designer");
//   }
//
//   @RequestMapping(value = "/monitor.do")
//   public ModelAndView handleMonitor(){
//      return handleGWTPage("monitor");
//   }
//
//   private ModelAndView handleGWTPage(String gwtPage){
//      Map<String,String> model=new HashMap<String, String>();
//      model.put("googleAPIkey",configuration.getString("google.api.key"));
//      model.put("divName",gwtPage);
//      return new ModelAndView("/gwt.jsp", model);
//      
//   }

   @RequestMapping(value = "/main.do")
   public ModelAndView handleMain(){
      Map<String,String> model=new HashMap<String, String>();
      model.put("googleMapsAPIkey",configuration.getString("google.maps.api.key"));
      model.put("googleAnalyticskey",configuration.getString("google.analytics.key"));
      return new ModelAndView("/main.jsp", model);
   }

   
}
