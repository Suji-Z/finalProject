$(document).ready(function () {
    $('input[type="radio"]').on('click', (function () {
        var inputValue = $(this).attr("value");

        if (inputValue=='creditCard'){
            $("#payMethod").val("신용카드");
        }else if(inputValue=='vbank'){
            $("#payMethod").val("가상계좌");
        }else if(inputValue=='kakaoPay'){
            $("#payMethod").val("카카오페이");
        }else{
            $("#payMethod").val("휴대폰결제");
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