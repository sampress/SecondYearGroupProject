<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>FDM Homepage</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width = device-width, initial-scale = 1"> <!-- set page width to device width -->
    <!-- jquery -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <!-- bootstrap -->
    <link rel="stylesheet" href="resources/css/bootstrap.min.css" type="text/css">
    <script src="resources/js/bootstrap.min.js"></script>
    <!-- local files -->
    <link href="resources/css/index.css" rel="stylesheet" type="text/css">
</head>
<body>
<!-- header -->
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <a href="#" class="navbar-left"><img src="resources/images/fdm_white.png" width="80"></a>
        </div>

    </div>
</nav>
    <!-- Main content - Form -->
    <div class="container-fluid center_div">
        <div class="row">
            <div class="col-lg-6 col-lg-offset-3 col-md-6 col-md-offset-3 col-sm-6 col-sm-offset-3 col-xs-8 col-xs-offset-2">
                <form:form action="/login" method="post">
                    <div class="form-group">
                        <form:label path="username" class="control-label" for="username"><i class="glyphicon glyphicon-user"></i> Username:</form:label>
                        <form:input type="text" path="username" class="form-control" id="username" placeholder="Enter username"/>
                    </div>
                    <div class="form-group">
                        <form:label path="password" class="control-label" for="password"><i class="glyphicon glyphicon-lock"></i> Password:</form:label>
                        <form:input type="password" path="password" class="form-control" id="password" placeholder="Enter password"/>
                    </div>
                    <div class="submit_button_holder">
                        <button type="submit" class="btn btn-default btn-center">Submit</button>
                    </div>
                </form:form>
            </div>
        </div>

    </div>
    <!-- footer -->
    <div class="footer">
        <div class="container-fluid">
        </div>
    </div>
</body>
</html>