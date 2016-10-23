/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.dundee.computing.aec.instagrim.servlets;

import com.datastax.driver.core.Cluster;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import uk.ac.dundee.computing.aec.instagrim.lib.CassandraHosts;
import uk.ac.dundee.computing.aec.instagrim.models.PicModel;
import uk.ac.dundee.computing.aec.instagrim.models.User;
import uk.ac.dundee.computing.aec.instagrim.stores.LoggedIn;
import uk.ac.dundee.computing.aec.instagrim.stores.Pic;
import uk.ac.dundee.computing.aec.instagrim.stores.UserDetails;

/**
 *
 * @author Administrator
 */
@WebServlet(name = "Login", urlPatterns = {"/Login",
    "/Login/*", "/Home"
})
public class Login extends HttpServlet {

    Cluster cluster=null;


    public void init(ServletConfig config) throws ServletException {
        // TODO Auto-generated method stub
        cluster = CassandraHosts.getCluster();
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    

        
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        
        User us=new User();
        us.setCluster(cluster);
        boolean isValid=us.IsValidUser(username, password);
        HttpSession session=request.getSession();
        System.out.println("Session in servlet "+session);
        if (isValid){
            //set user details 
            String[] details= us.getUsersDetails(username);
            //Set<String> likedPictures=us.getLikedPictures(username) ;
            boolean[] privacy=us.getUsersSettings(username);
            UserDetails ud=new UserDetails() ;
            ud.setDetails(details, privacy) ;
            
            LoggedIn lg= new LoggedIn();
            lg.setLogedin();
            lg.setUsername(username);
            //request.setAttribute("LoggedIn", lg);
            
            PicModel temp=new PicModel() ;
            temp.setCluster(cluster);
            Pic profilePic=temp.getProfilePicForUser(username);
            
            session.setAttribute("ProfilePic", profilePic);
            session.setAttribute("UserDetails", ud) ;
            session.setAttribute("LoggedIn", lg);
            
            //request.setAttribute("Validation", "success") ;
            Pic latestPic=temp.getLatestPicture() ;
            session.setAttribute("LatestPic", latestPic);
            
            System.out.println("Session in servlet "+session);
            RequestDispatcher rd=request.getRequestDispatcher("");
	    rd.forward(request,response);
            
        }else{
            session.setAttribute("Login", username) ;
            session.setAttribute("Password", password) ;
            response.sendRedirect("/Instagrim/RetryLogin");
        }
        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
