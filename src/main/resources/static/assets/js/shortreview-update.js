

function shortReviewUpdate1(event){

    let shortReviewNum = event.value;

    $("#shortReviewOri"+shortReviewNum).toggle(); //원래 댓글 내용
    $('#shortReviewBox'+shortReviewNum).toggle(); //textarea
    $("#updateA"+shortReviewNum).toggle(); //수정
    $("#delete"+shortReviewNum).toggle(); //삭제
    $("#updateB"+shortReviewNum).toggle(); //확인
    $("#cancle"+shortReviewNum).toggle(); //취소

}



function shortReviewUpdate2(event){

    let shortReviewNum = event.value;

    let shortReviewBox = $('#shortReviewBox'+shortReviewNum).val();
    let shortReviewOri = $('#shortReviewOri'+shortReviewNum).text();

    if(shortReviewBox==""){
        alert("댓글 내용을 입력해주세요.");
        return false;

    }else if(shortReviewBox == shortReviewOri){
        alert("변경된 내용이 없습니다.");
        return false;
    }else{

        $.ajax({
            url: '/package/shortReview/update',
            type: 'post',
            data: {
                shortReviewNum : shortReviewNum,
                packageNum : $("#packagenum").val(),
                content : shortReviewBox,
            },
            success : function(data) {

                alert("리뷰를 수정하였습니다.");
                $('#shortReview').replaceWith(data);
                location.reload();

            },
            error: function(xhr, status, error) {
                alert('error');
            }
        })
    }



}