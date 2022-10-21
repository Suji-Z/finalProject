$(function () {

var i = 0;
        $('img').on('click',function(){
            if(i==0){
                $(this).attr('src','/assets/img/icon/hand-thumbs-up-fill.svg');
                i++;
            }else if(i==1){
                $(this).attr('src','/assets/img/icon/hand-thumbs-up.svg');
                i--;
            }

        });
    });