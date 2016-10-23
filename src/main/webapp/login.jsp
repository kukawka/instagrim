<%-- 
    Document   : login.jsp
    Created on : Sep 28, 2014, 12:04:14 PM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Instagrim</title>
        <link rel="stylesheet" type="text/css" href="Styles.css" />

    </head>
    <body>
                <nav class="navbar navbar-default">
            <div class="container-fluid">
                <a class="navbar-brand" href="#">Instagrim</a>
                <ul class="nav navbar-nav">
                    <li><a href="/Instagrim">Home</a></li>
                    <!--<li><a href="/Instagrim/Images/majed">Sample Images</a></li>-->
                </ul>
        </nav>

    <div class="col-md-4"></div>
    <div class="col-md-4 thumbnail" id="registration-form">
        <h3>Instagrim</h3>
        <form method="POST"  action="Login">
            <div class="form-group">
                <label for="username">Username</label>
                <input class="form-control" type="text" name="username" placeholer="Username">
            </div>
            <div class="form-group">
                <label for="password">Password</label>
                <input class="form-control" type="password" name="password" placeholer="Password">
            </div>

            <button type="submit" value="Login" style="width:100%;" class="btn btn-default">Log in</button>
            <br>
        </form>
    </div>
    <div class="col-md-4"></div>
    </body>
</html>
