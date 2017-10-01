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
    <script src="resources/js/bootstrap-datetimepicker.min.js"></script>
    <link rel="stylesheet" href="resources/css/bootstrap-datetimepicker.min.css" type="text/css">
    <!-- local files -->
    <script src="resources/js/schedulecourse.js"></script>
    <link href="resources/css/schedulecourse.css" rel="stylesheet" type="text/css">
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
    <form:form class="form-horzontal" method="post" action="/schedulepluscourse">
        <div class="form-group coursechange">
            <form:label path="courseId" class="control-label col-sm-2" for="courseId">Course:</form:label>
            <div class='col-sm-10'>
                <form:select path="courseId" class="form-control">
                    <form:option value="0" label="-Select courses-"/>
                    <form:options items="${courselist}" />
                </form:select>
            </div>
        </div>
        <div class="form-group startTimeDiv">
            <form:label path="startTime" class="control-label col-sm-2">Start Date</form:label>
            <div class='input-group date col-sm-10'>
                <form:input id='startDate' path="startTime" type='text' class="form-control" />
                <span class="input-group-addon">
                    <span class="glyphicon glyphicon-calendar"></span>
                </span>
            </div>
        </div>
        <div class="form-group endTimeDiv">
            <form:label path="endTime" class="control-label col-sm-2">End Date</form:label>
            <div class='input-group date col-sm-10'>
                <form:input id='endDate' path="endTime" type='text' class="form-control" />
                <span class="input-group-addon">
                    <span class="glyphicon glyphicon-calendar"></span>
                </span>
            </div>
        </div>
        <div class="form-group finalDiv">
            <form:label path="classroomId" class="control-label col-sm-2" for="classroomId">Classroom:</form:label>
            <div class="col-sm-10">
                <form:select path="classroomId" class="form-control">
                    <form:option value="0" label="-Select classroom-"/>
                </form:select>
            </div>
        </div>
        <div class="drop_down_holder">
        <div class="form-group finalDiv">
            <form:label path="trainerId" class="control-label col-sm-2" for="trainerId">Trainer:</form:label>
            <div class="col-sm-10">
                <form:select path="trainerId" class="form-control">
                    <form:option value="0" label="-Select trainer-"/>
                </form:select>
            </div>
        </div>
        </div>
        <div class="form-group btn-submit">
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
