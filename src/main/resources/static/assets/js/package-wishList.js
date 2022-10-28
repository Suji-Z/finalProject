$(function () {


    let i = $("#recommendStatus").val();
    let packageId = $("#packageId").val();
    let loginStatus = $("#loginStatus").val();

    $('.wishListHeart').on('click',function(){

        if(loginStatus=='n'){
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




        $.ajax({
            url: '/package/jeju/wishList',
            type: 'GET',
            data: {
                recommendStatus: i,
                id : packageId

            },
            contentType: "application/json",

            success: function(result) {
            }

        })


    });
});