/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.47
 * Generated at: 2019-05-03 09:56:46 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class cartSuccess_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n");
      out.write("<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n");
      out.write("<meta http-equiv=\"pragma\" content=\"no-cache\">\r\n");
      out.write("<meta http-equiv=\"cache-control\" content=\"no-cache\">\r\n");
      out.write("<meta http-equiv=\"expires\" content=\"0\">\r\n");
      out.write("<meta name=\"format-detection\" content=\"telephone=no\">\r\n");
      out.write("\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\"\r\n");
      out.write("\thref=\"/css/base.css\">\r\n");
      out.write("<link href=\"/css/initcart20150123.css\" type=\"text/css\"\r\n");
      out.write("\trel=\"stylesheet\">\r\n");
      out.write("<title>商品已成功加入购物车</title>\r\n");
      out.write("<style id=\"style-1-cropbar-clipper\">\r\n");
      out.write("/* Copyright 2014 Evernote Corporation. All rights reserved. */\r\n");
      out.write(".en-markup-crop-options {\r\n");
      out.write("\ttop: 18px !important;\r\n");
      out.write("\tleft: 50% !important;\r\n");
      out.write("\tmargin-left: -100px !important;\r\n");
      out.write("\twidth: 200px !important;\r\n");
      out.write("\tborder: 2px rgba(255, 255, 255, .38) solid !important;\r\n");
      out.write("\tborder-radius: 4px !important;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write(".en-markup-crop-options div div:first-of-type {\r\n");
      out.write("\tmargin-left: 0px !important;\r\n");
      out.write("}\r\n");
      out.write("</style>\r\n");
      out.write("</head>\r\n");
      out.write("<body class=\"root61\">\r\n");
      out.write("\r\n");
      out.write("    ");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "commons/header.jsp", out, false);
      out.write("\r\n");
      out.write("\t<!--main start-->\r\n");
      out.write("\t<div class=\"w main\">\r\n");
      out.write("\t\t<div class=\"left\">\r\n");
      out.write("\t\t\t<div class=\"m\" id=\"succeed\">\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t<div class=\"corner tl\"></div>\r\n");
      out.write("\t\t\t\t<div class=\"corner tr\"></div>\r\n");
      out.write("\t\t\t\t<div class=\"corner bl\"></div>\r\n");
      out.write("\t\t\t\t<div class=\"corner br\"></div>\r\n");
      out.write("\t\t\t\t<div class=\"success\">\r\n");
      out.write("\t\t\t\t\t<div class=\"success-b\">\r\n");
      out.write("\t\t\t\t\t\t<h3>商品已成功加入购物车！</h3>\r\n");
      out.write("\t\t\t\t\t\t<span id=\"flashBuy\" style=\"display: none\">商品数量有限，请您尽快下单并付款！</span>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t<span id=\"initCart_next_go\"> <a class=\"btn-1\"\r\n");
      out.write("\t\t\t\t\t\thref=\"/cart/cart.html\"\r\n");
      out.write("\t\t\t\t\t\tid=\"GotoShoppingCart\">去购物车结算</a> <span class=\"ml10\">您还可以 <a\r\n");
      out.write("\t\t\t\t\t\t\tclass=\"ftx-05\" href=\"javascript:history.back();\">继续购物</a></span>\r\n");
      out.write("\t\t\t\t\t</span>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t<!--succeed end-->\r\n");
      out.write("\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("   ");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "commons/footer.jsp", out, false);
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("</body>\r\n");
      out.write("</html>");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
