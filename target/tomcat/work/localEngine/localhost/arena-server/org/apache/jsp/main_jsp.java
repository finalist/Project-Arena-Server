package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class main_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\n");
      out.write("<!-- The HTML 4.01 Transitional DOCTYPE declaration-->\n");
      out.write("<!-- above set at the top of the file will set     -->\n");
      out.write("<!-- the browser's rendering engine into           -->\n");
      out.write("<!-- \"Quirks Mode\". Replacing this declaration     -->\n");
      out.write("<!-- with a \"Standards Mode\" doctype is supported, -->\n");
      out.write("<!-- but may lead to some differences in layout.   -->\n");
      out.write("\n");
      out.write("<html>\n");
      out.write("<head>\n");
      out.write("<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("\n");
      out.write("<!--                                                               -->\n");
      out.write("<!-- Consider inlining CSS to reduce the number of requested files -->\n");
      out.write("<!--                                                               -->\n");
      out.write("<link type=\"text/css\" rel=\"stylesheet\" href=\"ARenaWA.css\">\n");
      out.write("\n");
      out.write("<!--                                           -->\n");
      out.write("<!-- Any title is fine                         -->\n");
      out.write("<!--                                           -->\n");
      out.write("<title>ARena</title>\n");
      out.write("<script\n");
      out.write("\tsrc=\"http://maps.google.com/maps?file=api&amp;v=2&amp;sensor=true&amp;key=");
      if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
        return;
      out.write("\"\n");
      out.write("\ttype=\"text/javascript\"></script>\n");
      out.write("\n");
      out.write("<!--                                           -->\n");
      out.write("<!-- This script loads your compiled module.   -->\n");
      out.write("<!-- If you add any GWT meta tags, they must   -->\n");
      out.write("<!-- be added before this line.                -->\n");
      out.write("<!--                                           -->\n");
      out.write("<script type=\"text/javascript\" language=\"javascript\"\n");
      out.write("\tsrc=\"arenaserver/arenaserver.nocache.js\"></script>\n");
      out.write("\n");
      out.write("</head>\n");
      out.write("\n");
      out.write("<!--                                           -->\n");
      out.write("<!-- The body can have arbitrary html, or      -->\n");
      out.write("<!-- you can leave the body empty if you want  -->\n");
      out.write("<!-- to create a completely dynamic UI.        -->\n");
      out.write("<!--                                           -->\n");
      out.write("<body>\n");
      out.write("\n");
      out.write("<!-- OPTIONAL: include this if you want history support -->\n");
      out.write("<iframe src=\"javascript:''\" id=\"__gwt_historyFrame\" tabIndex='-1'\n");
      out.write("\tstyle=\"position: absolute; width: 0; height: 0; border: 0\"></iframe>\n");
      out.write("\n");
      out.write("<div id=\"content\" style=\"heigth: 100%; width: 100%\"></div>\n");
      out.write("<script type=\"text/javascript\">\n");
      out.write("    var gaJsHost = ((\"https:\" == document.location.protocol) ? \"https://ssl.\" : \"http://www.\");\n");
      out.write("    document.write(unescape(\"%3Cscript src='\" + gaJsHost + \"google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E\"));\n");
      out.write("</script>\n");
      out.write("<script type=\"text/javascript\">\n");
      out.write("\tvar pageTracker = _gat._getTracker(\"");
      if (_jspx_meth_c_005fout_005f1(_jspx_page_context))
        return;
      out.write("\");\n");
      out.write("    pageTracker._initData();\n");
      out.write("    pageTracker._trackPageview(\"/arena/Designer\");\n");
      out.write("</script>\n");
      out.write("</body>\n");
      out.write("</html>\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }

  private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:out
    org.apache.taglibs.standard.tag.rt.core.OutTag _jspx_th_c_005fout_005f0 = (org.apache.taglibs.standard.tag.rt.core.OutTag) _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(org.apache.taglibs.standard.tag.rt.core.OutTag.class);
    _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
    _jspx_th_c_005fout_005f0.setParent(null);
    // /main.jsp(26,75) name = value type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fout_005f0.setValue((java.lang.Object) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${googleMapsAPIkey}", java.lang.Object.class, (PageContext)_jspx_page_context, null, false));
    int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
    if (_jspx_th_c_005fout_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
    return false;
  }

  private boolean _jspx_meth_c_005fout_005f1(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:out
    org.apache.taglibs.standard.tag.rt.core.OutTag _jspx_th_c_005fout_005f1 = (org.apache.taglibs.standard.tag.rt.core.OutTag) _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(org.apache.taglibs.standard.tag.rt.core.OutTag.class);
    _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
    _jspx_th_c_005fout_005f1.setParent(null);
    // /main.jsp(56,37) name = value type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fout_005f1.setValue((java.lang.Object) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${googleAnalyticskey}", java.lang.Object.class, (PageContext)_jspx_page_context, null, false));
    int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
    if (_jspx_th_c_005fout_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
    return false;
  }
}
