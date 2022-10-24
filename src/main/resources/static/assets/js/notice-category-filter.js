
$(document).ready(function() {

document.getElementById('noticeFilter').onchange = function() {

    let categorychk = this.value;
    alert(categorychk);

    $.ajax({
             url: '/notice/category',
             type: 'post',
             data: {
                 category : categorychk
             },
             success : function(data) {

                 $('#commentTable').replaceWith(data);

             },
             error: function(xhr, status, error) {
                 alert('error');
             }
         });


}
});