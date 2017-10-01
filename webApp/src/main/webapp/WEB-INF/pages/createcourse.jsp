<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Create course</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- jquery -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <!-- bootstrap -->
    <link rel="stylesheet" href="resources/css/bootstrap.min.css" type="text/css">
    <script src="resources/js/bootstrap.min.js"></script>
    <!-- local files -->
    <script src="resources/js/createcourse.js"></script>
    <link href="resources/css/createcourse.css" rel="stylesheet" type="text/css">
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
<!-- body -->
<div class="container">
    <h2>Create course</h2>
    <form:form class="form-horizontal" method="post" action="/addcourse">
        <form:hidden path="id" for="id"></form:hidden>
        <div class="form-group">
            <form:label path="title" class="control-label col-sm-2" for="title">Course title:</form:label>
            <div class="col-sm-10">
                <form:input type="text" path="title" class="form-control" id="title" placeholder="Enter course title"/>
            </div>
        </div>
        <div class="form-group">
            <form:label path="description" class="control-label col-sm-2" for="description">Course description:</form:label>
            <div class="col-sm-10">
                <form:textarea path="description" type="text" class="form-control" id="description" placeholder="Enter Course description"/>
            </div>
        </div>
        <div class="form-group">
            <form:label path="capacity" class="control-label col-sm-2" for="capacity">Maximum capacity:</form:label>
            <div class="col-sm-10">
                <form:input path="capacity" type="number" min="1" class="form-control" id="capacity" placeholder="Enter maximum capacity"/>
            </div>
        </div>
        <div class="form-group">
            <form:label path="duration" class="control-label col-sm-2" for="duration">Course duration:</form:label>
            <div class="col-sm-10">
                <form:input path="duration" type="number" min="1" class="form-control" id="duration" placeholder="Enter duration"/>
            </div>
        </div>
        <div class="form-group">
            <form:label path="prerequisiteId1" class="control-label col-sm-2" for="prerequisiteId1">Prerequisite:</form:label>
            <div class="col-sm-10">
            <form:select path="prerequisiteId1" class="form-control">
                <form:option value="0" label="--Select prerequisite courses--"/>
                <form:options items="${courselist}" />
            </form:select>
                <i class="glyphicon glyphicon-plus-sign pr2btn"></i>
            </div>
        </div>
        <div class="form-group pr2">
            <form:label path="prerequisiteId2" class="control-label col-sm-2" for="prerequisiteId2">Prerequisite:</form:label>
            <div class="col-sm-10">
                <form:select path="prerequisiteId2" class="form-control">
                    <form:option value="0" label="--Select prerequisite courses--"/>
                    <form:options items="${courselist}" />
                </form:select>
                <i class="glyphicon glyphicon-plus-sign pr3btn"></i>
            </div>
        </div>
        <div class="form-group pr3">
            <form:label path="prerequisiteId3" class="control-label col-sm-2" for="prerequisiteId3">Prerequisite:</form:label>
            <div class="col-sm-10">
                <form:select path="prerequisiteId3" class="form-control">
                    <form:option value="0" label="--Select prerequisite courses--"/>
                    <form:options items="${courselist}" />
                </form:select>
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