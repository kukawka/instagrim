<%-- 
    Document   : upload
    Created on : Sep 22, 2014, 6:31:50 PM
    Author     : Administrator
--%>

<%@page import="uk.ac.dundee.computing.aec.instagrim.stores.LoggedIn"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.12.4.min.js"></script>
        <script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.15.0/jquery.validate.js"></script>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
        <title>Instagrim</title>
        <link rel="stylesheet" type="text/css" href="Styles.css" />
    </head>
    <body>
        <nav class="navbar navbar-default">
            <div class="container-fluid">
                <a class="navbar-brand" href="#">Instagrim</a>
                <ul class="nav navbar-nav">
                    <li class="nav"><a href="/Instagrim">Home</a></li>
                        <%
                            LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn");
                        %>  
                    <li><a href="/Instagrim/Images/<%=lg.getUsername()%>">Your Pictures</a></li>
                    <li><a href="/Instagrim/UpdateInfo">Edit Info</a></li>
                </ul>
            </div>
        </nav>

        <div class="col-lg-4 col-md-4"></div>
        <div class="col-lg-4 col-md-4 thumbnail" id="upload-form">
            <center> <h3>Upload a picture</h3></center>
            <hr>
            <form method="POST" enctype="multipart/form-data" action="Image" class="form-horizontal" id="Upload-form">
                File to upload: <input type="file" name="upfile" required><br/>

                <br/>

                <div class="form-group">
                    <label for="description" class="col-sm-3 control-label">Description:</label>
                    <div class="col-sm-9">
                        <input class="form-control" type="text" name="description" id="description">
                    </div>
                </div>
                <div class="form-group">
                <label class="col-sm-4 control-label">Profile picture:</label>
                <div class="col-sm-8">
                <div class="checkbox">
                    <label>
                        <input type="checkbox" name="isprofile" value="true">Set as your profile picture
                    </label>
                </div>
                </div>
                </div>
                <div class="form-group">
                    <label for="filter" class="col-sm-2 control-label">Filter:</label>
                    <div class="col-sm-10">
                        <select class="form-control" name="filter">
                            <option value="strengthened">STRENGTHENER</option>
                            <option value="sunny">SUNNY</option>
                            <option value="BW">BLACK_AND_WHITE</option>
                        </select>
                    </div>
                </div>
                <br>
                <input type="submit" value="Press"> to upload the file!
            </form>
            <script>
            $("#Upload-form").validate();
            </script>

            <div class="col-lg-4 col-md-4"></div>
    </body>
</html>
