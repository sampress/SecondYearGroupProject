<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Add Classroom</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="http://code.jquery.com/ui/1.9.2/jquery-ui.js"></script>
    <!-- local files -->
    <link href="resources/css/addclassroom.css" rel="stylesheet" type="text/css">
</head>
<body>
<!--header-->
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <a href="#" class="navbar-left"><img src="resources/images/fdm_white.png" width="80"></a>
        </div>

    </div>
</nav>
<!-- content -->
<div class="container">
    <h2>Add Classroom</h2>
    <form:form class="form-horizontal" method="post" action="/addplusclassroom">
        <form:hidden path="id" for="id"></form:hidden>
        <div class="form-group">
            <form:label path="name" class="control-label col-sm-2" for="name">Name:</form:label>
            <div class="col-sm-10">
                <form:input type="text" path="name" class="form-control" id="name" placeholder="Enter name"/>
            </div>
        </div>
        <div class="form-group">
            <form:label path="city" class="control-label col-sm-2" for="city">City:</form:label>
            <div class="col-sm-10">
                <form:input path="city" type="text" class="form-control" id="city" placeholder="Enter city"/>
            </div>
        </div>
        <div class="form-group">
            <form:label path="address" class="control-label col-sm-2" for="address">Room address:</form:label>
            <div class="col-sm-10">
                <form:input path="address" type="text" class="form-control" id="address" placeholder="Enter room address"/>
            </div>
        </div>
        <div class="form-group">
            <form:label path="capacity" class="control-label col-sm-2" for="capacity">Room capacity:</form:label>
            <div class="col-sm-10">
                <form:input path="capacity" type="number" class="form-control" id="capacity" placeholder="Enter room capacity"/>
            </div>
        </div>
        <div class="form-group">
            <form:label path="type" class="control-label col-sm-2" for="type">Room type:</form:label>
            <div class="col-sm-10">
                <form:input path="type" type="text" class="form-control" id="type" placeholder="Enter room type"/>
            </div>
        </div>
        <div class="form-group">
            <form:label path="projector" class="control-label col-sm-2" for="projector">Projector:</form:label>
            <div class="col-sm-10">
                <form:checkbox path="projector" id="projector"/>
            </div>
        </div>
        <div class="form-group">
            <form:label path="studentComp" class="control-label col-sm-2" for="studentComp">Student Computers:</form:label>
            <div class="col-sm-10">
                <form:checkbox path="studentComp" id="studentComp"/>
            </div>
        </div>
        <div class="form-group">
            <form:label path="whiteboard" class="control-label col-sm-2" for="whiteboard">Whiteboard:</form:label>
            <div class="col-sm-10">
                <form:checkbox path="whiteboard" id="whiteboard"/>
            </div>
        </div>
        <div class="form-group">
            <form:label path="audioRecording" class="control-label col-sm-2" for="audioRecording">Audio Recording:</form:label>
            <div class="col-sm-10">
                <form:checkbox path="audioRecording" id="audioRecording" />
            </div>
        </div>
        <div class="form-group">
            <form:label path="videoRecording" class="control-label col-sm-2" for="videoRecording">Video Recording:</form:label>
            <div class="col-sm-10">
                <form:checkbox path="videoRecording" id="videoRecording" />
            </div>
        </div>
        <div class="form-group">
            <form:label path="wheelchairAccess" class="control-label col-sm-2" for="wheelchairAccess">Wheel Chair Access:</form:label>
            <div class="col-sm-10">
                <form:checkbox path="wheelchairAccess" id="wheelchairAccess"/>
            </div>
        </div>
        <div class="form-group">
            <form:label path="listeningSystem" class="control-label col-sm-2" for="listeningSystem">Listening System:</form:label>
            <div class="col-sm-10">
                <form:checkbox path="listeningSystem" id="listeningSystem"/>
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