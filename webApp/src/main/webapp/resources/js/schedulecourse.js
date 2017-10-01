$(document).ready(function () {

    //hide all the dives besides the first one
    $(".startTimeDiv").hide();
    $(".endTimeDiv").hide();
    $(".finalDiv").hide();
    $(".btn-submit").hide();


    //datetimepicker functions
    $(function () {
        $('#startDate').datetimepicker();
        $('#endDate').datetimepicker();
    });

    //get the duration of the course selected
    //store the duration of the current course
    var duration;
    $( ".coursechange" ).change(function() {
        var id=$("#courseId").find(":selected").val();
        if (id!=0) {
            $.ajax({
                type: "GET",
                url: "course/duration",
                data: {id: id},
                success: function (data) {
                    duration = data;
                    $(".startTimeDiv").show();
                }
            })
        }
    });

    //set the ending date based on the duration and show the div
    $("#startDate").change(function () {
        var time=$("#startDate").val();
        $.ajax({
            type: "GET",
            url: "course/addtime",
            data: { duration: duration, time:time},
            success:function(data) {
                var dates=data.split(",");
                $(".endTimeDiv").show();
                $('#endDate').datetimepicker('setStartDate', dates[0]);
                $('#endDate').datetimepicker('setEndDate',dates[1]);
            }
        })
    })

    //when the end time is set show the available classrooms and trainers
    $("#endDate").change(function () {
        //get the desired times
        var startTime=$("#startDate").val();
        var endTime=$("#endDate").val();
        //get the selected id
        var id=$("#courseId").find(":selected").val();
        //get the available classrooms
        $.ajax({
            type: "GET",
            url: "course/fillclassroom",
            data: { startTime: startTime, endTime:endTime, id:id},
            success:function(data) {
                $(".finalDiv").show();
                //fill in the classroom options
                $.each(data, function(key, value) {
                    $('#classroomId').append($("<option/>", {
                        value: key,
                        text: value
                    }));
                });
            }
        })
        //get the available trainers
        $.ajax({
            type: "GET",
            url: "course/filltrainer",
            data: { startTime: startTime, endTime:endTime},
            success:function(data) {
                $(".finalDiv").show();
                //fill in the trainers options
                $.each(data, function(key, value) {
                    $('#trainerId').append($("<option/>", {
                        value: key,
                        text: value
                    }));
                });
            }
        })
        //display the submit button
        $(".btn-submit").show();
    })

});