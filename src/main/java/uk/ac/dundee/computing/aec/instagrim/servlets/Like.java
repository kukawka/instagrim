/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.dundee.computing.aec.instagrim.servlets;

import com.datastax.driver.core.Cluster;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import uk.ac.dundee.computing.aec.instagrim.lib.CassandraHosts;
import uk.ac.dundee.computing.aec.instagrim.lib.Convertors;
import uk.ac.dundee.computing.aec.instagrim.models.PicModel;
import uk.ac.dundee.computing.aec.instagrim.models.User;
import uk.ac.dundee.computing.aec.instagrim.stores.LoggedIn;

/**
 *
 * @author Dagi
 */
@WebServlet(name = "Like", urlPatterns = {"/Like"
})
public class Like extends HttpServlet {

    Cluster cluster=null;


    @Override
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
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException{
            HttpSession session=request.getSession();
            LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn");
            String searcheduser=(String)session.getAttribute("Last searched user");
            String UserName = lg.getUsername();
            String args[] = Convertors.SplitRequestPath(request);
            java.util.UUID picid= java.util.UUID.fromString(request.getParameter("picid"));
            //java.util.Date pic_added= (java.util.Date) request.getParameter("pic_added"));
            PicModel temp=new PicModel() ;
            temp.setCluster(cluster);
            temp.addALike(searcheduser, UserName, picid);
            response.sendRedirect("/Instagrim/SearchForUser?searcheduser="+searcheduser);
        
        
    }
    
}

