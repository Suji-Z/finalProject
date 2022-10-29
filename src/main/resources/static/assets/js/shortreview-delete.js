
function shortReviewDelete(event){

    let packageNum = $("#packagenum").val();

    $.ajax({
        url: '/package/shortReview/delete',
        type: 'post',
        data: {
            shortReviewNum : event.value,
            packageNum : $("#packagenum").val()
        },
        success : function(data) {

            alert("리뷰를 삭제하였습니다.");
            $('#shortReview').replaceWith(data);

        },
        error: function(xhr, status, error) {
            alert('error');
        }
    })

}