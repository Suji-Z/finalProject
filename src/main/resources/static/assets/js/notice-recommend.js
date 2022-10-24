$(function () {

    let noticeNum = $("#noticeNum").val();
    let i = $("#recommendStatus").val();

        $('img').on('click',function(){
            if(i==0){ //비어있다가 채우는 행위
                $(this).attr('src','/assets/img/icon/hand-thumbs-up-fill.svg');
                i++; // i=1
            }else if(i==1){ //채웠다가 비우는 행위
                $(this).attr('src','/assets/img/icon/hand-thumbs-up.svg');
                i--; // i=0
            }

            $.ajax({
                url: '/notice/recommend',
                type: 'GET',
                data: {
                    recommendStatus: i,
                    id : noticeNum

                },
                contentType: "application/json",

                success: function(result) {
                }

            })


        });
    });