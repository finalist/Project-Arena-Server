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

   @RequestMapping(value = "/main.do")
   public ModelAndView handleMain(){
      Map<String,String> model=new HashMap<String, String>();
      model.put("googleAnalyticskey",configuration.getString("google.analytics.key"));
      return new ModelAndView("/main.jsp", model);
   }

   
}
