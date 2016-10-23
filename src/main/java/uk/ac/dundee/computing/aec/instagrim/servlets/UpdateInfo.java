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
import uk.ac.dundee.computing.aec.instagrim.models.User;
import uk.ac.dundee.computing.aec.instagrim.stores.LoggedIn;
import uk.ac.dundee.computing.aec.instagrim.stores.UserDetails;

/**
 *
 * @author Administrator
 */
@WebServlet(name = "UpdateInfo", urlPatterns = {"/UpdateInfo"})
public class UpdateInfo extends HttpServlet {
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
    
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("updateprofile.jsp");
        rd.forward(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn");
        String username = lg.getUsername();
        
        //String password=request.getParameter("password");
        String firstname=request.getParameter("first_name");
        String lastname=request.getParameter("last_name");
        String email=request.getParameter("email") ;
        String bio=request.getParameter("bio");
       // int dayOfBirth()
       String temp = request.getParameter("isEmailPublic");
            boolean isEmailPublic = false;
            if (temp != null) {
                isEmailPublic = true;
            }
        temp=null ;
        temp = request.getParameter("isSurnamePublic");
            boolean isSurnamePublic = false;
            if (temp != null) {
                isSurnamePublic = true;
            } 
        
        User us=new User();
        us.setCluster(cluster);
        boolean isupdated=us.UpdateUserInfo(username, firstname, lastname, email, bio, isEmailPublic, isSurnamePublic);
        
        if(isupdated){
            String[] details= us.getUsersDetails(username);
            boolean[] privacy=us.getUsersSettings(username);
            UserDetails ud=new UserDetails() ;
            ud.setDetails(details, privacy) ;
            
            session.setAttribute("UserDetails", ud) ;
        }
	response.sendRedirect("updateprofile.jsp");
        
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

