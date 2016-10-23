/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.dundee.computing.aec.instagrim.servlets;

import com.datastax.driver.core.Cluster;
import java.io.IOException;
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
import uk.ac.dundee.computing.aec.instagrim.stores.Pic;

/**
 *
 * @author Dagi
 */
@WebServlet(name = "SearchForUser", urlPatterns = {"/SearchForUser", "/SearchForUser/*"})
public class SearchForUser extends HttpServlet {

    Cluster cluster = null;

    public void init(ServletConfig config) throws ServletException {
        // TODO Auto-generated method stub
        cluster = CassandraHosts.getCluster();
    }
    /*protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("SearchedProfile.jsp");
        rd.forward(request, response);
    }*/
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String User = request.getParameter("searcheduser");
        User us = new User();
        us.setCluster(cluster);
        boolean userExists = us.doesUserExist(User);
        if (userExists) {
            //response.sendRedirect("/Instagrim/Images/"+searchedusername+"/2");
            PicModel tm = new PicModel();
            tm.setCluster(cluster);
            //User us = new User();
            //us.setCluster(cluster);

            String[] details = us.getUsersDetails(User);
            boolean[] settings = us.getUsersSettings(User);
            java.util.LinkedList<Pic> lsPics = tm.getPicsForUser(User);
            Pic profilePic = tm.getProfilePicForUser(User);

            RequestDispatcher rd;
            rd = request.getRequestDispatcher("SearchedProfile.jsp");
            HttpSession session=request.getSession();
            session.setAttribute("Privacy", settings);
            session.setAttribute("Pics", lsPics);
            session.setAttribute("Info", details);
            request.setAttribute("ProfilePic", profilePic);
            session.setAttribute("Last searched user", User);

            rd.forward(request, response);

        } //response.sendRedirect("/Instagrim/SearchForUser/"+User);
        //}
        else {
            //was "index.jsp" before, which violates REST
            response.sendRedirect("/Instagrim");
        }

    }

}
