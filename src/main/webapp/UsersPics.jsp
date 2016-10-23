<%-- 
    Document   : UsersPics
    Created on : Sep 24, 2014, 2:52:48 PM
    Author     : Administrator
--%>

<%@page import="java.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="uk.ac.dundee.computing.aec.instagrim.stores.*" %>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Instagrim</title>
        <link rel="stylesheet" type="text/css" href="Styles.css" />
    </head>
    <body>
        <!-- <header>
 
             <h1>InstaGrim ! </h1>
             <h2>Your world in Black and White</h2>
         </header>-->

        <nav class="navbar navbar-default">
            <div class="container-fluid">
                <a class="navbar-brand" href="#">Instagrim</a>
                <ul class="nav navbar-nav">
                    <li><a href="/Instagrim/">Home</a></li>
                    <%
            LoggedIn loggedin = (LoggedIn) session.getAttribute("LoggedIn");
                        if (loggedin != null) {
                            String us = loggedin.getUsername();
%>  
                    <li><a href="/Instagrim/Images/<%=us%>">Your Pictures</a></li>
                    <li><a href="/Instagrim/Images/">Upload</a></li>
                    <!--<li><a href="/Instagrim/Images/majed">Sample Images</a></li>-->
                </ul>
            </div>
        </nav>
<%}%>
        <div class="container-fluid">
            <h1>Your Pics</h1>
            <%
                java.util.LinkedList<Pic> lsPics = (java.util.LinkedList<Pic>) request.getAttribute("Pics");
                if (lsPics == null) {
            %>
            <p>No Pictures found</p>
            <%
            } else {
                Iterator<Pic> iterator;
                iterator = lsPics.iterator();

                while (iterator.hasNext()) {
                    Pic p = (Pic) iterator.next();

            %>
            <!-- <div class="col-lg-3 col-md-3 col-sm-12">
                 <div class="thumbnail">
                     <a href="/Instagrim/Image/<%=p.getSUUID()%>" ><img src="/Instagrim/Thumb/<%=p.getSUUID()%>"></a>
                     <div class="caption">
                         <p><%//=p.getDesc()%></p>
                     </div>
                 </div></div>-->
            <div class="col-lg-3 col-md-3 col-sm-12">
                <div class="thumbnail">
                    <a href="/Instagrim/Image/<%=p.getSUUID()%>" ><img src="/Instagrim/Thumb/<%=p.getSUUID()%>"></a>
                    <div class="caption">
                        <!--<h3>Thumbnail label</h3>-->
                        <p><%=p.getDesc()%></p>
                        <%

                            LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn");
                            String UserName = lg.getUsername();
                            if (!p.getLikes().contains(UserName)) {
                        %>
                        <form method="POST"  action="Like">
                            <button type="submit" class="btn btn-default">
                                <span class="glyphicon glyphicon-heart" aria-hidden="true"></span> <%
                                if (p.getLikes().size() > 0) {%>
                                <%=p.getLikes().size()%>
                                <%} else {%>
                                0
                                <%}%>
                            </button>
                            <input name="picid" value="<%=p.getSUUID()%>" hidden>
                        </form>
                        <%} else{%>
                        <button type="submit" class="btn btn-default" disabled>
                            <span class="glyphicon glyphicon-heart" aria-hidden="true"></span> <%
                                if (p.getLikes().size() > 0) {%>
                            <%=p.getLikes().size()%>
                            <%} else {%>
                            0
                            <%}%>
                        </button>

                        <%}%>
                        
                        <form method="POST"  action="Delete">
                            <button type="submit" class="btn btn-default">Delete</button>
                            <input name="picid" value="<%=p.getSUUID()%>" hidden>
                        </form>

                    </div>   </div></div>
                    <%}
                        }
                    %>

    </body>
</html>
