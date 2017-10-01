$(document).ready(function () {

    //function that handles the delete button for the course
    $(".btn-delete-course").click(function () {
        var id=this.id;
        BootstrapDialog.confirm({
            title: 'WARNING',
            message: 'Are you sure you want to delete?',
            type: BootstrapDialog.TYPE_WARNING,
            closable: true,
            draggable: true,
            btnCancelLabel: 'Cancel',
            btnOKLabel: 'Delete',
            btnOKClass: 'btn-warning',
            callback: function(result) {
                if(result) {
                    $.ajax({
                        type: "POST",
                        url: "course/delete",
                        data: { id: id},
                        success:function() {
                            location.reload();
                        }
                    })
                }
            }
        });
    })

    //function that handles the edit button
    $(".btn-edit").click(function () {
        var id=this.id;
        window.open('/createcourse'+id, 'newwindow', 'width=500, height=400');
    })

    //hide the scheduled course and make toggling possible
    $(".scheduled-holder").hide();
    $(".btn-toggle").click(function () {
        $(".scheduled-holder").toggle();
        $(".course-holder").toggle();
        if ($(".btn-toggle").text()=="View Scheduled"){
            $(".btn-toggle").html("View Courses");
        }
        else{
            $(".btn-toggle").html("View Scheduled");
        }
    })

    //function that handles the delete button for the scheduled course
    $(".btn-delete-scheduled").click(function () {
        var id=this.id;
        BootstrapDialog.confirm({
            title: 'WARNING',
            message: 'Are you sure you want to delete?',
            type: BootstrapDialog.TYPE_WARNING,
            closable: true,
            draggable: true,
            btnCancelLabel: 'Cancel',
            btnOKLabel: 'Delete',
            btnOKClass: 'btn-warning',
            callback: function(result) {
                if(result) {
                    $.ajax({
                        type: "POST",
                        url: "existing-course/delete",
                        data: { id: id},
                        success:function() {
                            location.reload();
                        }
                    })
                }
            }
        });
    })

    //function that handles the info button button
    $(".btn-info-person").click(function () {
        var id=this.id;
        window.open('/view-person-list'+id, 'newwindow', 'width=500, height=400');
    })

});