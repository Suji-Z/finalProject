$(function () {


    let i = $("#recommendStatus").val();
    let packageId = $("#packageId").val();

    $('i').on('click',function(){
        if(i==0){ //비어있다가 채우는 행위
            $(this).attr('class','fas fa-heart fa-2x');
            i++; // i=1
        }else if(i==1){ //채웠다가 비우는 행위
            $(this).attr('class','fa-regular fa-heart fa-2x');
            i--; // i=0
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