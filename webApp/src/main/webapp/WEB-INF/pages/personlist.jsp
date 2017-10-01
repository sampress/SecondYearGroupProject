<!DOCTYPE html>
<html lang="en">
<head>
    <title>Person list</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <!-- jquery -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <!-- bootstrap -->
    <link rel="stylesheet" href="resources/css/bootstrap.min.css" type="text/css">
    <script src="resources/js/bootstrap.min.js"></script>
    <!-- dialog -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap3-dialog/1.35.3/css/bootstrap-dialog.min.css">
    <!-- local files -->
    <link href="resources/css/course.css" rel="stylesheet" type="text/css">
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
<div class="container-fluid">
    <!-- Person table TABLE -->
    <div class="table_holder course-holder">
        <table class="table table-striped">
            <thead>
            <tr class="table_header">
                <th>Name</th>
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
</body>
</html>
