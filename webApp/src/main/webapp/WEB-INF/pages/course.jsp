<!DOCTYPE html>
<html lang="en">
<head>
    <title>FDM Homepage</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <!-- jquery -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <!-- bootstrap -->
    <link rel="stylesheet" href="resources/css/bootstrap.min.css" type="text/css">
    <script src="resources/js/bootstrap.min.js"></script>
    <!-- dialog -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap3-dialog/1.35.3/js/bootstrap-dialog.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap3-dialog/1.35.3/css/bootstrap-dialog.min.css">
    <!-- local files -->
    <script src="resources/js/course.js"></script>
    <link href="resources/css/course.css" rel="stylesheet" type="text/css">
</head>
<body>
<!-- header -->
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <a href="#" class="navbar-left"><img src="resources/images/fdm_white.png" width="80"></a>
        </div>
        <ul class="nav navbar-nav">
            <li class="active"><a href="/course">Course</a></li>
            <li><a href="/classroom">Classroom</a></li>
            <li><a href="/trainer">Trainer</a></li>
        </ul>
    </div>
</nav>
<!-- body -->
<div class="container-fluid">
        <a href="/createcourse"  class="btn btn-default" onclick="window.open('/createcourse', 'newwindow', 'width=500, height=400'); return false;">Create course</a>
        <a href="/schedulecourse" class="btn btn-default">Schedule course</a>
        <button class="btn btn-info btn-toggle">View Scheduled</button>
    <!-- COURSE TABLE -->
    <div class="table_holder course-holder">
        <table class="table table-striped">
            <thead>
            <tr class="table_header">
                <th>Title</th>
                <th>Description</th>
                <th>Capacity</th>
                <th>Duration</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            ${firstTable}
            </tbody>
        </table>
    </div>
    <!-- THE SCHEDULED TABLE -->
    <div class="table_holder scheduled-holder">
        <table class="table table-striped">
            <thead>
            <tr class="table_header">
                <th>Course</th>
                <th>Start time</th>
                <th>End time</th>
                <th>Classroom</th>
                <th>Trainer</th>
                <th>Remaining places</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            ${secondTable}
            </tbody>
        </table>
    </div>
</div>
<!-- footer -->
<div class="footer">
    <div class="container-fluid">
    </div>
</div>
</body>
</html>
</body>
</html>
