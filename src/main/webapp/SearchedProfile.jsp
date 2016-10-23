<%-- 
    Document   : SearchedProfile
    Created on : Oct 6, 2016, 10:13:30 AM
    Author     : Dagi
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
        <!--<link rel="stylesheet" type="text/css" href="Styles.css" />-->
    </head>
    <body>
        <nav class="navbar navbar-default navbar-fixed-top">
            <div class="container-fluid">
                <a class="navbar-brand" href="#">Instagrim</a>
                <ul class="nav navbar-nav">
                    <li><a href="/Instagrim" style="font-weight: bold">Home</a></li>
                </ul>
                <form class="navbar-form navbar-right" method="POST"  action="Addfriend">
                    <button type="submit" class="btn btn-primary">Follow</button>
                </form>
            </div>
        </nav>
        <%
            Pic profilePic = (Pic) request.getAttribute("ProfilePic");
            String[] info = (String[]) session.getAttribute("Info");
            boolean[] privacy = (boolean[]) session.getAttribute("Privacy");
            java.util.LinkedList<Pic> lsPics = (java.util.LinkedList<Pic>) session.getAttribute("Pics");%>
        <div class="jumbotron" style="padding-top: 5% ;">
            <div class="container" >
                <div class="col-lg-5 col-md-5 col-sm-12">
                    <% if (profilePic!=null){%>
                    <img src="/Instagrim/Thumb/<%=profilePic.getSUUID()%>" class="img-circle">
                     <%}%>
                </div>
                <div class="col-lg-7 col-md-7 col-sm-12">
                    <% if (info != null) {%>
                    <h2 style="font-weight: bold"><%=info[0]%></h2>  
                    <p><strong>Name:</strong> <%=info[1]%></p>
                    <% if (privacy[1]) {%>
                    <p><strong>Surname:</strong> <%=info[2]%></p> <%}%>
                    <% if (privacy[0]) {%>
                    <p><strong>Email:</strong> <%=info[3]%></p> <%}%>
                    <p><strong>About:</strong> <%=info[4]%></p>
                    <%}%>
                </div>
            </div></div>
        <%if (lsPics == null) {
        %>
        <p>No Pictures found</p>
        <%
        } else {
            Iterator<Pic> iterator;
            iterator = lsPics.iterator();
            while (iterator.hasNext()) {
                Pic p = (Pic) iterator.next();

        %>
        <div class="col-lg-3 col-md-3 col-sm-12">
            <div class="thumbnail">
                <a href="/Instagrim/Image/<%=p.getSUUID()%>" ><img src="/Instagrim/Thumb/<%=p.getSUUID()%>"></a>
                <div class="caption">
                    <!--<h3>Thumbnail label</h3>-->
                    <p><%=p.getDesc()%></p>
                    <%

                        LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn");
                        if (lg != null) {
                            String UserName = lg.getUsername();
                            if (lg.getlogedin()) {
                                if (!p.getLikes().contains(UserName)) {
                    %>
                    <form method="POST"  action="Like">
                        <button type="submit" class="btn btn-default">
                            <span class="glyphicon glyphicon-heart" aria-hidden="true"></span> <%
                                if (p.getLikes().size()>0) {%>
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
                                if (p.getLikes().size()>0) {%>
                            <%=p.getLikes().size()%>
                            <%} else {%>
                            0
                            <%}%>
                        </button>

                   <%}%>
                    <%}} else{%>
                    <button type="button" class="btn btn-default" disabled>
                        <span class="glyphicon glyphicon-heart" aria-hidden="true"></span> <%
                            if (p.getLikes().size()>1) {%>
                        <%=p.getLikes().size()%>
                        <%} else {%>
                        0
                        <%}%>
                    </button> <%}%>
                </div></div>

        </div>
        <%}
}
        %>

        <!--<footer>
            <ul>
                <li class="footer"><a href="/Instagrim">Home</a></li>
            </ul>
        </footer>-->
    </body>
</html>
