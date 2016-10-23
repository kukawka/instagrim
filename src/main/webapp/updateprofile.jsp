<%-- 
    Document   : profile
    Created on : Sep 21, 2016, 4:07:34 PM
    Author     : Dagi
--%>

<%@page import="uk.ac.dundee.computing.aec.instagrim.stores.LoggedIn"%>
<%@page import="uk.ac.dundee.computing.aec.instagrim.stores.UserDetails"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" type="text/css" href="Styles.css" />

    </head>
    <body  onload="setCheckboxes()">
        <nav class="navbar navbar-default">
            <div class="container-fluid">
                <a class="navbar-brand" href="#">Instagrim</a>
                <ul class="nav navbar-nav">
                    <li><a href="/Instagrim/">Home</a></li>
                    <%
            LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn");
                        if (lg != null) {
                            String UserName = lg.getUsername();
%>  
                    <li><a href="/Instagrim/Images/<%=UserName%>">Your Pictures</a></li>
                    <li><a href="/Instagrim/Images/">Upload</a></li>
                    <!--<li><a href="/Instagrim/Images/majed">Sample Images</a></li>-->
                </ul>
            </div>
        </nav>
        <%
            UserDetails ud = (UserDetails) session.getAttribute("UserDetails");
            if (ud != null) {
                //String name=(String)request.getAttribute("name"); 
%>  
        <!--<p></p>-->

        <div class="col-md-4"></div>
        <div class="col-md-4 thumbnail" id="update-form">
            <center><h3>Update your information</h3></center>
            <hr>
            <form method="POST"  action="UpdateInfo">
                <div class="form-group">
                    <label for="first_name">Forename</label>
                    <input class="form-control" type="text" name="first_name" value="<%=ud.getName()%>">
                </div>
                <div class="form-group">
                    <label for="first_name">Surname</label>
                    <input class="form-control" type="text" name="last_name" value="<%=ud.getSurname()%>">
                </div>
                <div class="checkbox">
                    <label>
                        <input type="checkbox" id="isSurnamePublic" name="isSurnamePublic" value="true">Show in your profile
                    </label>
                </div>
                <div class="form-group">
                    <label for="email">E-mail address:</label>
                    <input type="text" name="email" value="<%=ud.getEmail()%>"></br>
                </div>
                <div class="checkbox">
                    <label>
                        <input type="checkbox" id="isEmailPublic" name="isEmailPublic" value="true">Show in your profile
                    </label>
                </div>  
                <script>
                    function setCheckboxes() {
                        document.getElementById("isEmailPublic").checked = <%=ud.getIsEmailPublic()%>;
                        document.getElementById("isSurnamePublic").checked = <%=ud.getIsSurnamePublic()%>;
                    }
                </script>
                <div class="form-group">
                    <label for="bio">About You:</label>
                    <textarea name="bio" rows="4" cols="30" maxlength="100"><%=ud.getBio()%></textarea>
                </div>
                <button type="submit" value="Update" class="btn btn-default">Update</button>
                <br>
            </form>
        </div>
        <div class="col-md-4"></div>
        <%}%>
        <%}%>
        <!--
         <div class="col-md-4"></div>
         <div class="col-md-4 thumbnail" id="profile-form">
             <h3>Add/edit information about yourself:</h3>
             <form method="POST"  action="ManageProfile">
                 <div class="form-group">
                     <label for="bio">Biography:</label>
                     <textarea name="bio" rows="4" cols="30" maxlength="100"></textarea>
                 </div>
                 <div class="form-group">
                     <label for="password">E-mail address:</label>
                     <input type="text" name="email"></br>
                 </div>
                 <div class="form-group">
                     <label>Date of birth:</label>
                     <input type="number" name="dayOfBirth">
                     <input type="number" name="monthOfBirth">
                     <input type="number" name="yearOfBirth">
                 <button type="submit" value="ManageProfile" class="btn btn-default">Register</button>
                 <br>
             </form>
         </div>
         <div class="col-md-4"></div>-->

    </body>
</html>
