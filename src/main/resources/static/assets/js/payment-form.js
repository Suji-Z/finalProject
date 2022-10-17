$(document).ready(function () {
    $('input[type="radio"]').on('click', (function () {
        var inputValue = $(this).attr("value");
        var targetBox = $("." + inputValue);
        $(".payment_toggle").not(targetBox).hide();
        $(targetBox).show();

        if (inputValue=='red'){
            $("#payMethod").val("신용카드");
        }else if(inputValue=='green'){
            $("#payMethod").val("계좌이체");
        }else{
            $("#payMethod").val("카카오페이");
        }
    })
    )
});

/*
inputValue
red : 신용카드
green : 계좌이체
white : 카카오페이
*/