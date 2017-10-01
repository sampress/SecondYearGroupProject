<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Add trainer</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="http://code.jquery.com/ui/1.9.2/jquery-ui.js"></script>
    <!-- local files -->
    <link href="resources/css/addtrainer.css" rel="stylesheet" type="text/css">
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
<!-- content -->
<div class="container">
    <h2>Add trainer</h2>
    <form:form class="form-horizontal" method="post" action="/addplustrainer">
        <form:hidden path="id" for="id"></form:hidden>
        <div class="form-group">
            <form:label path="name" class="control-label col-sm-2" for="name">Name:</form:label>
            <div class="col-sm-10">
                <form:input type="text" path="name" class="form-control" id="name" placeholder="Enter name"/>
            </div>
        </div>
        <div class="form-group">
            <form:label path="email" class="control-label col-sm-2" for="email">Email:</form:label>
            <div class="col-sm-10">
                <form:input path="email" type="email" class="form-control" id="email" placeholder="Enter email"/>
            </div>
        </div>
        <div class="form-group">
            <form:label path="address" class="control-label col-sm-2" for="address">Home address:</form:label>
            <div class="col-sm-10">
                <form:input path="address" type="text" class="form-control" id="address" placeholder="Enter home address"/>
            </div>
        </div>
        <div class="form-group">
            <form:label path="phone" class="control-label col-sm-2" for="phone">Phone number:</form:label>
            <div class="col-sm-10">
                <form:input path="phone" type="text" class="form-control bfn-phone" data-country="UK" id="phone" placeholder="Enter phone number"/>
            </div>
        </div>
        <div class="form-group">
            <div class="submit_button_holder">
                <button type="submit" class="btn btn-default btn-center">Submit</button>
            </div>
        </div>
    </form:form>
    </div>
<!-- footer -->
<div class="footer">
    <div class="container-fluid">

    </div>
</div>
</body>
</html>