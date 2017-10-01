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
    <script src="resources/js/classroom.js"></script>
    <link href="resources/css/classroom.css" rel="stylesheet" type="text/css">
</head>
<body>
    <!-- header -->
    <nav class="navbar navbar-inverse">
        <div class="container-fluid">
            <div class="navbar-header">
                <a href="#" class="navbar-left"><img src="resources/images/fdm_white.png" width="80"></a>
            </div>
            <ul class="nav navbar-nav">
                <li><a href="/course">Course</a></li>
                <li class="active"><a href="/classroom">Classroom</a></li>
                <li><a href="/trainer">Trainer</a></li>
            </ul>
        </div>
    </nav>
<!-- content -->
<div class="container-fluid">
      <a href="/addclassroom" class="btn btn-default" onclick="window.open('/addclassroom', 'newwindow', 'width=500, height=800'); return false;">Create classroom</a>
<div class="table_holder">
    <table class="table table-striped">
        <thead>
        <tr class="table_header">
            <th>Name</th>
            <th>City</th>
            <th>Address</th>
            <th>Capacity</th>
            <th>Type</th>
            <th>Projector</th>
            <th>studentComp</th>
            <th>Whiteboard</th>
            <th>Audio Recording</th>
            <th>Video Recording</th>
            <th>Wheelchair Access</th>
            <th>Listening System</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        ${table}
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