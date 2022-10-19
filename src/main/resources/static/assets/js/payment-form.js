$(document).ready(function () {
    $('input[type="radio"]').on('click', (function () {
        var inputValue = $(this).attr("value");

        if (inputValue=='신용카드'){
            $("#payMethod").val("신용카드");
        }else if(inputValue=='계좌이체'){
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