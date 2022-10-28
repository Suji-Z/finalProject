$(function () {

    let reviewId = $("#reviewId").val();
    let i = $("#recommendStatus").val();
    let loginStatus1 = $("#loginStatus1").val();

    $('.reviewHeart').on('click',function(){

        if(loginStatus1=='n'){
            alert('로그인 후 가능한 서비스입니다.');
            location.href="http://localhost:8088/member/login";
        }else{

            if(i==0){ //비어있다가 채우는 행위
                $(this).attr('src','/assets/img/icon/ReviewHeart2.png');
                i++; // i=1
            }else if(i==1){ //채웠다가 비우는 행위
                $(this).attr('src','/assets/img/icon/ReviewHeart1.png');
                i--; // i=0
            }
        }


        // alert("추천"+i);
        // alert("글번호"+reviewId);

        $.ajax({
            url: '/review/vote',
            type: 'GET',
            data: {
                recommendStatus: i,
                id : reviewId

            },
            contentType: "application/json",

            success: function(result) {
            }

        })


    });
});