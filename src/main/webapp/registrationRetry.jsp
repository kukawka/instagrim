<%-- 
    Document   : registrationRetry.jsp
    Created on : Oct 15, 2016, 6:29:49 PM
    Author     : Dagi
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
    <body onload="setUpPage()">
        <header>
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
            <%String password=(String) session.getAttribute("password");
              String username=(String) session.getAttribute("username");
              String first_name=(String) session.getAttribute("first_name");
              String last_name=(String) session.getAttribute("last_name");
              String email=(String) session.getAttribute("last_name");
              String bio=(String) session.getAttribute("bio");
              boolean isEmailPublic=(boolean) session.getAttribute("isEmailPublic");
              boolean isSurnamePublic=(boolean) session.getAttribute("isEmailPublic");
            %>
            <form method="POST"  action="Register" class="form-horizontal" id="Register-form">
                <div class="form-group has-error has-feedback">
                    <label for="username" class="col-sm-2 control-label">Username:</label>
                    <div class="col-sm-10">
                        <input class="form-control" type="text" name="username" value="<%=username%>" required>
                        <span class="glyphicon glyphicon-remove form-control-feedback"></span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="first_name" class="col-sm-2 control-label">Forename:</label>
                    <div class="col-sm-10">
                        <input class="form-control" type="text" name="first_name" value="<%=first_name%>" required>
                    </div>
                </div>
                <div class="form-group">
                    <label for="first_name" class="col-sm-2 control-label">Surname:</label>
                    <div class="col-sm-10">
                        <input class="form-control" type="text" name="last_name" value="<%=last_name%>" required>
                    </div>

                    <div class="checkbox">
                        <label>
                            <input type="checkbox" name="isSurnamePublic" value="true">Show in your profile
                        </label>
                    </div> 
                </div>
                <div class="form-group">
                    <label for="email" class="col-sm-2 control-label">E-mail address:</label>
                    <div class="col-sm-10">
                        <input type="text" name="email" value="<%=email%>" required></br>
                    </div>

                    <div class="checkbox">
                        <label>
                            <input type="checkbox" name="isEmailPublic" value="true">Show in your profile
                        </label>
                    </div>  
                </div>
                <div class="form-group">
                    <label for="password" class="col-sm-2 control-label">Password:</label>
                    <div class="col-sm-10">
                        <input class="form-control" type="password" name="password" value="<%=password%>" required>
                    </div>
                </div>
                <div class="form-group">
                    <label for="bio" class="col-sm-2 control-label">About You:</label>
                    <div class="col-sm-10">
                        <textarea name="bio" rows="3" cols="50" maxlength="100"><%=bio%></textarea>
                    </div>
                </div>
                <button type="submit" value="Register" class="btn btn-default" style="width:100%;">Register</button>
                <br>
            </form>
        </div>
        <div class="col-md-3"></div>

        <script>
            $("#Register-form").validate();
        </script>
        
        <script>
                    function setUpPage() {
                        alert("A user with this name already exists!");
                        document.getElementById("isEmailPublic").checked = <%=isEmailPublic%>;
                        document.getElementById("isSurnamePublic").checked = <%=isSurnamePublic%>;
                        
                    }
        </script>

        <footer>
            <!--<ul>
                <li class="footer"><a href="/Instagrim">Home</a></li>
            </ul>-->
        </footer>
    </body>
</html>
