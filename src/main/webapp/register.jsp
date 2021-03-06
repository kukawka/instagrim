<%-- 
    Document   : register.jsp
    Created on : Sep 28, 2014, 6:29:51 PM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.12.4.min.js"></script>
        <script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.15.0/jquery.validate.js"></script>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Instagrim</title>
        <!--<link rel="stylesheet" type="text/css" href="Styles.css" />-->
    </head>
    <body>
        <header>
            <!--<h1>InstaGrim ! </h1>
            <h2>Your world in Black and White</h2>-->
        </header>
        <nav class="navbar navbar-default">
            <div class="container-fluid">
                <a class="navbar-brand" href="#">Instagrim</a>
                <ul class="nav navbar-nav">
                    <li><a href="/Instagrim">Home</a></li>
                    <!--<li><a href="/Instagrim/Images/majed">Sample Images</a></li>-->
                </ul>
        </nav>

        <div class="col-md-3"></div>
        <div class="col-md-6" id="registration-form">
            <!--<center>
            <h3>Register as a user</h3>
            <hr>-->
            <form method="POST"  action="Register" class="form-horizontal" id="Register-form">
                <div class="form-group">
                    <label for="username" class="col-sm-2 control-label">Username:</label>
                    <div class="col-sm-10">
                        <input class="form-control" type="text" name="username" id="username" required>
                    </div>
                </div>
                <div class="form-group">
                    <label for="first_name" class="col-sm-2 control-label">Forename:</label>
                    <div class="col-sm-10">
                        <input class="form-control" type="text" name="first_name" id="first_name" required>
                    </div>
                </div>
                <div class="form-group">
                    <label for="first_name" class="col-sm-2 control-label">Surname:</label>
                    <div class="col-sm-10">
                        <input class="form-control" type="text" name="last_name" id="last_name" required>
                    </div>

                    <div class="checkbox">
                        <label>
                            <input type="checkbox" name="isSurnamePublic" value="true">Allow surname in your profile
                        </label>
                    </div> 
                </div>
                <div class="form-group">
                    <label for="email" class="col-sm-2 control-label">E-mail:</label>
                    <div class="col-sm-10">
                        <input type="email" name="email" id="email" required>
                    </div>

                    <div class="checkbox">
                        <label>
                            <input type="checkbox" name="isEmailPublic" value="true">Allow e-mail in your profile
                        </label>
                    </div>  
                </div>
                <div class="form-group">
                    <label for="password" class="col-sm-2 control-label">Password:</label>
                    <div class="col-sm-10">
                        <input class="form-control" type="password" id="password" name="password" required>
                    </div>
                </div>
                <div class="form-group">
                    <label for="password" class="col-sm-2 control-label">Repeat password:</label>
                    <div class="col-sm-10">
                        <input class="form-control" type="password" id="password2" name="password2" required>
                    </div>
                </div>
                <div class="form-group">
                    <label for="bio" class="col-sm-2 control-label">About You:</label>
                    <div class="col-sm-10">
                        <textarea type="text" name="bio" rows="3" cols="50" maxlength="100"></textarea>
                    </div>
                </div>
                <button type="submit" onclick="validate()" value="Register" class="btn btn-default" style="width:100%;">Register</button>
                <br>
            </form>
        </div>
        <div class="col-md-3"></div>

        <script>
            $("#Register-form").validate();
            $( "#username" ).rules( "add", {
  minlength: 4
});
$( "#password" ).rules( "add", {
  minlength: 8
});
$( "#password2" ).rules( "add", {
      equalTo: "#password"
});
$( "#first_name" ).rules( "add", {
  minlength: 2
});
$( "#last_name" ).rules( "add", {
  minlength: 2
});
$( "#email" ).rules( "add", {
  minlength: 2
});
$( "#email" ).rules( "add", {
  email: true
});

        </script>

        <footer>
            <!--<ul>
                <li class="footer"><a href="/Instagrim">Home</a></li>
            </ul>-->
        </footer>
    </body>
</html>
