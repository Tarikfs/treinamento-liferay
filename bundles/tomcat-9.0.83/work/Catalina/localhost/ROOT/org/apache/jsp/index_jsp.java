/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: JspC/ApacheTomcat9
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.events.ServicePreAction;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(1);
    _jspx_dependants.put("/init.jsp", Long.valueOf(1692174191094L));
  }

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("com.liferay.portal.kernel.util.HtmlUtil");
    _jspx_imports_classes.add("com.liferay.petra.string.StringPool");
    _jspx_imports_classes.add("com.liferay.portal.kernel.servlet.HttpHeaders");
    _jspx_imports_classes.add("com.liferay.portal.events.ServicePreAction");
    _jspx_imports_classes.add("com.liferay.portal.kernel.model.LayoutSet");
    _jspx_imports_classes.add("com.liferay.portal.kernel.util.PortalUtil");
    _jspx_imports_classes.add("com.liferay.portal.kernel.util.Validator");
    _jspx_imports_classes.add("com.liferay.portal.kernel.util.WebKeys");
    _jspx_imports_classes.add("com.liferay.portal.kernel.theme.ThemeDisplay");
    _jspx_imports_classes.add("com.liferay.portal.kernel.model.LayoutConstants");
    _jspx_imports_classes.add("com.liferay.portal.kernel.service.LayoutLocalServiceUtil");
    _jspx_imports_classes.add("com.liferay.petra.string.CharPool");
    _jspx_imports_classes.add("com.liferay.portal.kernel.util.InstancePool");
  }

  private volatile javax.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public javax.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
      throws java.io.IOException, javax.servlet.ServletException {

    if (!javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
      final java.lang.String _jspx_method = request.getMethod();
      if ("OPTIONS".equals(_jspx_method)) {
        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
        return;
      }
      if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method)) {
        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSPs only permit GET, POST or HEAD. Jasper also permits OPTIONS");
        return;
      }
    }

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


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

      out.write('\n');
      out.write('\n');
      out.write('\n');
      out.write('\n');
      out.write('\n');
      out.write('\n');


// According to http://www.webmasterworld.com/forum91/3087.htm a semicolon in
// the URL for a meta-refresh tag does not work in IE 6.

// To work around this issue, we use a URL without a session id for meta-refresh
// and rely on the load event on the body element to properly rewrite the URL.

String redirect = null;

LayoutSet layoutSet = (LayoutSet)request.getAttribute(WebKeys.VIRTUAL_HOST_LAYOUT_SET);

if (layoutSet != null) {
	long defaultPlid = LayoutLocalServiceUtil.getDefaultPlid(layoutSet.getGroupId(), layoutSet.isPrivateLayout());

	if (defaultPlid != LayoutConstants.DEFAULT_PLID) {
		ServicePreAction servicePreAction = (ServicePreAction)InstancePool.get(ServicePreAction.class.getName());

		servicePreAction.run(request, response);

		redirect = PortalUtil.getLayoutURL(LayoutLocalServiceUtil.getLayout(defaultPlid), (ThemeDisplay)request.getAttribute(WebKeys.THEME_DISPLAY));
	}
	else {
		redirect = PortalUtil.getPathMain();
	}
}
else {
	redirect = PortalUtil.getHomeURL(request);
}

if (!request.isRequestedSessionIdFromCookie()) {
	redirect = PortalUtil.getURLWithSessionId(redirect, session.getId());
}

String queryString = request.getQueryString();

if (Validator.isNotNull(queryString)) {
	if (redirect.indexOf(CharPool.QUESTION) == -1) {
		redirect = redirect + StringPool.QUESTION + queryString;
	}
	else {
		redirect = redirect + StringPool.AMPERSAND + queryString;
	}
}

response.setHeader(HttpHeaders.LOCATION, redirect);

response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);

      out.write("\n\n<html>\n	<head>\n		<title></title>\n\n		<meta content=\"1; url=");
      out.print( HtmlUtil.escapeAttribute(redirect) );
      out.write("\" http-equiv=\"refresh\" />\n	</head>\n\n	<body onload=\"javascript:location.replace('");
      out.print( HtmlUtil.escapeJS(redirect) );
      out.write("')\">\n\n	</body>\n</html>");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
