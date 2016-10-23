<%--
    Document   : index
    Created on : Oct 15, 2016, 12:19:09 PM
    Author     : Dagi
--%>

<%@page import="java.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="uk.ac.dundee.computing.aec.instagrim.stores.*" %>
<!DOCTYPE html>
<html>
    <head>
        <script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.12.4.min.js"></script>
        <script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.15.0/jquery.validate.js"></script>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
        <title>Instagrim</title>
        <link rel="stylesheet" type="text/css" href="Styles.css"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body class="main-body" onload="myFunction()">
        <header>
            <!--<h1>InstaGrim ! </h1>
            <h2>Your world in Black and White</h2>-->
        </header>
        <nav class="navbar navbar-default">
            <div class="container-fluid">
                <a class="navbar-brand" href="#">Instagrim</a>
                <ul class="nav navbar-nav">

                    <li><a href="/Instagrim">Register</a></li>
                    <!-- <li><a href="login.jsp" data-toggle="tooltip" data-placement="bottom" title="Log in to manage your profile and upload pictures">Log in</a></li>-->
                </ul>
                <form class="navbar-form navbar-left" method="GET" action="SearchForUser">
                    <div class="form-group">
                        <input type="text" name="searcheduser" class="form-control" placeholder="Search" data-toggle="tooltip" data-placement="bottom" title="Search for a user">
                    </div>
                    <button type="submit" class="btn btn-default">Submit</button>
                </form>

            </div>
        </nav>

         <%
                String password = (String) session.getAttribute("Password");
                String login = (String) session.getAttribute("Login");%>
                
        <div class="row welcome-window" >
            <div class="col-sm-6 col-md-4"></div> 
            <div class="col-sm-6 col-md-4">
                <div class="thumbnail">
                    <center>

                        <img src="user.png" alt="..." class="img-circle" >
                        <div class="caption">
                            <h2>Welcome to Instagrim!</h2>
                            <form method="POST"  action="Login" id="Login-form">
                                <div class="form-group has-error has-feedback">
                                    <label for="username">Username</label>
                                    <input class="form-control" type="text" name="username"  id="username" value="<%=login%>" aria-describedby="inputError2Status" required>
                                    <span class="glyphicon glyphicon-remove form-control-feedback"></span>
                                    <span id="inputError2Status">Invalid username or password</span>
                                </div>
                                <div class="form-group has-error has-feedback" id="test">
                                    <label for="password">Password</label>
                                    <input class="form-control" type="password" name="password"  id="password" value="<%=password%>" required>
                                    <span class="glyphicon glyphicon-remove form-control-feedback"></span>
                                            <span id="inputError2Status">Invalid username or password</span>
                                </div>

                                <button type="submit" value="Login" style="width:100%;" class="btn btn-default">Log in</button>
                                <br>
                            </form>   

                            <hr>

                            <p>If you don't have an account yet, <a href="register.jsp">register</a> today.</p>
                    </center>
                    <!--<p><a href="#" class="btn btn-primary" role="button">Button</a> <a href="#" class="btn btn-default" role="button">Button</a></p>-->
                </div>
            </div>
        </div>
    </div>

    <footer>

        <ul>
            <!--<li class="footer"><a href="/Instagrim">Home</a></li>
            <li>&COPY; Andy C</li>-->
        </ul>
    </footer>
</body>
</html>
