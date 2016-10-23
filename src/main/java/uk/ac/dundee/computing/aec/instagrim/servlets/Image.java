package uk.ac.dundee.computing.aec.instagrim.servlets;

import com.datastax.driver.core.Cluster;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import uk.ac.dundee.computing.aec.instagrim.lib.CassandraHosts;
import uk.ac.dundee.computing.aec.instagrim.lib.Convertors;
import uk.ac.dundee.computing.aec.instagrim.models.PicModel;
import uk.ac.dundee.computing.aec.instagrim.models.User;
import uk.ac.dundee.computing.aec.instagrim.stores.LoggedIn;
import uk.ac.dundee.computing.aec.instagrim.stores.Pic;

/**
 * Servlet implementation class Image
 */
@WebServlet(urlPatterns = {
    "/Image",
    "/Image/*",
    "/Thumb/*",
    "/Images",
    "/Images/*",})
@MultipartConfig

public class Image extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private Cluster cluster;
    private HashMap CommandsMap = new HashMap();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Image() {

        super();
        // TODO Auto-generated constructor stub
        CommandsMap.put("Image", 1);
        CommandsMap.put("Images", 2);
        CommandsMap.put("Thumb", 3);
        //CommandsMap.put("Login", 4) ;

    }

    public void init(ServletConfig config) throws ServletException {
        // TODO Auto-generated method stub
        cluster = CassandraHosts.getCluster();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub

        String args[] = Convertors.SplitRequestPath(request);

        int command;
        if (args[1].equals("SearchForUser")) {
            command = 4;
        } else {
            try {
                command = (Integer) CommandsMap.get(args[1]);
            } catch (Exception et) {
                error("Bad Operator", response);
                return;
            }
        }

        switch (command) {
            case 1:
                DisplayImage(Convertors.DISPLAY_PROCESSED, args[2], response);
                break;
            case 2:
                DisplayImageList(args[2], request, response);
                break;
            case 3:
                DisplayImage(Convertors.DISPLAY_THUMB, args[2], response);
                break;
            /*case 4:
                DisplaySearchedProfile(args[2],request, response);
                break;   */
            default:
                error("Bad Operator", response);
        }
    }

    private void DisplayImageList(String User, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PicModel tm = new PicModel();
        tm.setCluster(cluster);

        java.util.LinkedList<Pic> lsPics = tm.getPicsForUser(User);
        RequestDispatcher rd;
        rd = request.getRequestDispatcher("/UsersPics.jsp");
        request.setAttribute("Pics", lsPics);
        request.setAttribute("Descs", lsPics);
        rd.forward(request, response);

    }

    /*
    private void DisplaySearchedProfile(String User, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PicModel tm = new PicModel();
        tm.setCluster(cluster);
        User us = new User();
        us.setCluster(cluster);
        
        String [] details=us.getUsersDetails(User) ;
        boolean[] settings=us.getUsersSettings(User);
        java.util.LinkedList<Pic> lsPics = tm.getPicsForUser(User);
        Pic profilePic =tm.getProfilePicForUser(User);
        
        RequestDispatcher rd;
        rd = request.getRequestDispatcher("/SearchForUser/"+User);
        request.setAttribute("Privacy", settings);
        request.setAttribute("Pics", lsPics);
        request.setAttribute("Info", details);
        request.setAttribute("ProfilePic", profilePic);
        
        rd.forward(request, response);

    }*/

 /*
    private void DisplayProfileImage(String User, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PicModel tm = new PicModel();
        tm.setCluster(cluster);

        Pic profilePic = tm.getProfilePicForUser(User);
        RequestDispatcher rd ;
        rd = request.getRequestDispatcher("/index.jsp");
        request.setAttribute("ProfilePic", profilePic);
        rd.forward(request, response);
    }*/
    private void DisplayImage(int type, String Image, HttpServletResponse response) throws ServletException, IOException {
        PicModel tm = new PicModel();
        tm.setCluster(cluster);

        Pic p = tm.getPic(type, java.util.UUID.fromString(Image));

        OutputStream out = response.getOutputStream();

        response.setContentType(p.getType());
        response.setContentLength(p.getLength());
        //out.write(Image);
        InputStream is = new ByteArrayInputStream(p.getBytes());
        BufferedInputStream input = new BufferedInputStream(is);
        byte[] buffer = new byte[8192];
        for (int length = 0; (length = input.read(buffer)) > 0;) {
            out.write(buffer, 0, length);
        }
        out.close();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String args[] = Convertors.SplitRequestPath(request);
        if (args.length == 2) {
            for (Part part : request.getParts()) {
                System.out.println("Part Name " + part.getName());

                String type = part.getContentType();
                String filename = part.getSubmittedFileName();
                String description = request.getParameter("description");
                String temp = request.getParameter("isprofile");
                String filter = request.getParameter("filter");

                boolean isprofile = false;
                if (temp != null) {
                    isprofile = true;
                }

                InputStream is = request.getPart(part.getName()).getInputStream();
                int i = is.available();
                HttpSession session = request.getSession();
                LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn");
                String username = "majed";
                if (lg.getlogedin()) {
                    username = lg.getUsername();
                }

                if (i > 0) {
                    byte[] b = new byte[i + 1];
                    is.read(b);
                    System.out.println("Length : " + b.length);
                    PicModel tm = new PicModel();
                    tm.setCluster(cluster);
                    tm.insertPic(filter, b, type, filename, username, description, isprofile);

                    is.close();
                }

                //check if there is a need to change profile picture for the session
                if (isprofile) {
                    PicModel profilePicModel = new PicModel();
                    profilePicModel.setCluster(cluster);
                    Pic profilePic = profilePicModel.getProfilePicForUser(username);

                    session.setAttribute("ProfilePic", profilePic);
                }
                //response.sendRedirect("/Instagrim/Images/"+username);

                //HttpSession session=request.getSession();
                //LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn");
                String UserName = lg.getUsername();
                RequestDispatcher rd = request.getRequestDispatcher("/upload.jsp");
                rd.forward(request, response);
                return;
            }

        } else if (args[2].equals("Like")) {
            HttpSession session = request.getSession();
            LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn");
            String UserName = lg.getUsername();
            java.util.UUID picid = java.util.UUID.fromString(request.getParameter("picid"));
            //java.util.Date pic_added= (java.util.Date) request.getParameter("pic_added"));
            PicModel temp = new PicModel();
            temp.setCluster(cluster);
            temp.addALike(UserName, UserName, picid);
            response.sendRedirect("/Instagrim/Images/" + UserName);
        } else if (args[2].equals("Delete")) {
            HttpSession session = request.getSession();
            LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn");
            String UserName = lg.getUsername();
            java.util.UUID picid = java.util.UUID.fromString(request.getParameter("picid"));
            //java.util.Date pic_added= (java.util.Date) request.getParameter("pic_added"));
            PicModel temp = new PicModel();
            temp.setCluster(cluster);
            temp.deleteAPicture(UserName, picid);
            response.sendRedirect("/Instagrim/Images/" + UserName);
        }

    }

    private void error(String mess, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out = null;
        out = new PrintWriter(response.getOutputStream());
        out.println("<h1>You have a na error in your input</h1>");
        out.println("<h2>" + mess + "</h2>");
        out.close();
        return;
    }
}
