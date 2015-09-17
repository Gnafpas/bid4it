/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

/**
 *
 * @author George
 */
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
 
@WebFilter(filterName = "AuthorizationFilter", urlPatterns = { "*.xhtml" })
public class AuthorizationFilter implements Filter {
 
    public AuthorizationFilter() {
    }
 
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
 
    }
 
    
 /**
 *
 * Pages access control.
 * Log out automatically Admin when leave admin page.
 */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        
        try {
 
            HttpServletRequest reqt = (HttpServletRequest) request;
            HttpServletResponse resp = (HttpServletResponse) response;
            HttpSession ses = reqt.getSession(false);
            String reqURI = reqt.getRequestURI();
    
             resp.setHeader("Cache-Control", "no-cache");
             resp.setHeader("Pragma", "no-cache");
             resp.setHeader("Expires", "-1");
      
            if(reqURI.contains("/faces/admin.xhtml") && ses == null ){
                resp.sendRedirect(reqt.getContextPath() + "/faces/welcome-page.xhtml");
                return;
            }
            if(reqURI.contains("/faces/admin.xhtml") && ses != null && ses.getAttribute("adm") == null){
                resp.sendRedirect(reqt.getContextPath() + "/faces/welcome-page.xhtml");
                return;
            }
            if(reqURI.contains("/faces/admin.xhtml") && ses != null && ses.getAttribute("adm") != null && ses.getAttribute("adm").equals("false")  ){
                resp.sendRedirect(reqt.getContextPath() + "/faces/welcome-page.xhtml");
                return;
            }
            if(reqURI.contains("/faces/admin.xhtml")==false && ses != null && ses.getAttribute("adm") != null && ses.getAttribute("adm").equals("true") ){
                ses.invalidate();
                resp.sendRedirect(reqt.getContextPath() + "/faces/welcome-page.xhtml");
                return;
            }
            if(reqURI.contains("/faces/messages.xhtml") && (ses == null || ses.getAttribute("username") == null)){
                resp.sendRedirect(reqt.getContextPath() + "/faces/welcome-page.xhtml");
                return;
            }
            if(reqURI.contains("/faces/write_msg.xhtml") && (ses == null || ses.getAttribute("username") == null)){
                resp.sendRedirect(reqt.getContextPath() + "/faces/welcome-page.xhtml");
                return;
            }
            if(reqURI.contains("/faces/upload_item.xhtml") && (ses == null || ses.getAttribute("username") == null)){
                resp.sendRedirect(reqt.getContextPath() + "/faces/welcome-page.xhtml");
                return;
            }
            if(reqURI.contains("/faces/update_item.xhtml") && (ses == null || ses.getAttribute("username") == null)){
                resp.sendRedirect(reqt.getContextPath() + "/faces/welcome-page.xhtml");
                return;
            }
            if(reqURI.contains("/faces/selling.xhtml") && (ses == null || ses.getAttribute("username") == null)){
                resp.sendRedirect(reqt.getContextPath() + "/faces/welcome-page.xhtml");
                return;
            }
            if(reqURI.contains("/faces/read_msg.xhtml") && (ses == null || ses.getAttribute("username") == null)){
                resp.sendRedirect(reqt.getContextPath() + "/faces/welcome-page.xhtml");
                return;
            }
            if(reqURI.contains("/faces/mybids.xhtml") && (ses == null || ses.getAttribute("username") == null)){
                resp.sendRedirect(reqt.getContextPath() + "/faces/welcome-page.xhtml");
                return;
            }
            if(reqURI.contains("/faces/account_settings.xhtml") && (ses == null || ses.getAttribute("username") == null)){
                resp.sendRedirect(reqt.getContextPath() + "/faces/welcome-page.xhtml");
                return;
            }
            if(reqURI.contains("/faces/welcome-page.xhtml") && ses != null && ses.getAttribute("adm") != null  ){
                resp.sendRedirect(reqt.getContextPath() + "/faces/allready_loggedin.xhtml");
            }
            else
                chain.doFilter(request, response);
            
                
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
 
    @Override
    public void destroy() {
 
    }
}
