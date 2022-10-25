$(function () {

    let reviewId = $("#reviewId").val();
    let i = $("#recommendStatus").val();

    $('i').on('click',function(){
        if(i==0){ //비어있다가 채우는 행위
            $(this).attr('class','fas fa-heart');
            i++; // i=1
        }else if(i==1){ //채웠다가 비우는 행위
            $(this).attr('class','fa-regular fa-heart');
            i--; // i=0
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