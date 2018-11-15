/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filters;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import services.UserService;

/**
 *
 * @author 659159
 */
public class AdminFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
       
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        
          // this code will execute before HomeServlet and UsersServlet
            HttpServletRequest r = (HttpServletRequest)request;
            HttpSession session = r.getSession();
            UserService us= new UserService();
               String username = (String) session.getAttribute("username");
        try {
            if (us.get(username).getRole().getRolename().equals("admin")) {
                // if they are authenticated, i.e. have a username in the session
                // then allow them to continue on to the servlet
                chain.doFilter(request, response);
            } else {
                // they do not have a username in the sesion
                // so, send them to login page
                HttpServletResponse resp = (HttpServletResponse)response;
                resp.sendRedirect("login");
            }
            
            // this code will execute after HomeServlet and UsersServlet
        } catch (Exception ex) {
            Logger.getLogger(AdminFilter.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }

    @Override
    public void destroy() {
        
    }
    
}
