$(document).ready(function () {
    $(".btn-delete").click(function () {
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
                        url: "classroom/delete",
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
        window.open('/addclassroom'+id, 'newwindow', 'width=500, height=800');
    })
});