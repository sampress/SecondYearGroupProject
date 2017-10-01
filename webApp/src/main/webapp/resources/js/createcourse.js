$(document).ready(function () {
    //hide the second and third prerequisites
    $(".pr2").hide();
    $(".pr3").hide();


    //show the second prerequisite form
    $( ".pr2btn" ).click(function() {
        $(".pr2").show();
    });

    //show the third prerequisite form
    $( ".pr3btn" ).click(function() {
        $(".pr3").show();
    });
});