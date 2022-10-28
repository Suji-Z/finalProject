$(function () {

    let replyId = $("#replyId").val();
    let likeStatus = $("#likeStatus").val();
    let loginStatus1 = $("#loginStatus1").val();

    $('.replyHeart').on('click',function(){

        if(loginStatus1=='n'){
            alert('로그인 후 가능한 서비스입니다.');
            location.href="http://localhost:8088/member/login";
        }else{

            if(likeStatus==0){ //비어있다가 채우는 행위
                $('#likeOn').show();
            }else if(likeStatus==1){ //채웠다가 비우는 행위
                $('#likeOff').show();
            }
        }


        // alert("추천"+i);
        // alert("글번호"+reviewId);

        $.ajax({
            url: '/review/replyVote',
            type: 'GET',
            data: {
                likeStatus : likeStatus,
                id : replyId

            },
            contentType: "application/json",

            success: function(result) {
            }

        })


    });
});