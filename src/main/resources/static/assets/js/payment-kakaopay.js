
function requestPay() {

    let payMethod = $("#payMethod").val();

    if(payMethod==='카카오페이'){

        kakaoPay();

    }else {

        alert(payMethod);

    }
}

function kakaoPay() {

    var IMP = window.IMP;
        IMP.init('imp40260603'); // 팝업창으로 뜨게 아임포트 가맹점 식별코드

        //필요한 정보 : 상품번호, 상품명, 가격, 구매자이메일, 구매자 이름, 구매자 휴대폰번호
        let name = $("#kakaoname").val(); //구매자이름
        let tel = $("#kakaotel").val(); //구매자번호
        let email = $("#kakaoemail").val(); //구매자이메일
        let packageNum = $("#packageNum").val(); //패키지번호
        let packageName = $("#packageName").val(); //패키지이름
        let payTotalPrice1 = parseInt($("#payTotalPrice").text().replace(/,/g,'')); //결제금액
        let bookingNum = $("#bookingNum").val(); //패키지번호


    IMP.request_pay({ //요청하는 것들
        pg : 'kakaopay', //카카오페이 API래핑
        pay_method : 'card',
        merchant_uid: 'p4445532111ㅇddㅇㅇsssaas',  // 상점에서관리하는 상품번호
        name : packageName, //상품명
        amount : '20', //가격
        buyer_email : email, //구매자 이메일
        buyer_name : name, //구매자 이름
        buyer_tel : tel //구매자 번호
        /* m_redirect_url : 'https://www.yourdomain.com/payments/complete' */

    }, function (rsp) {

        if (rsp.success) { // 결제 성공 시: 결제 승인 또는 가상계좌 발급에 성공한 경우
            alert(rsp.imp_uid +" / " + rsp.merchant_uid);

        $.ajax({
            url: "/pay/payments/complete", // 예: https://www.myservice.com/payments/complete
            type: "Get",
            headers: { "Content-Type": "application/json" },
            data: {
                impUid: rsp.imp_uid,
                merchantUid: rsp.merchant_uid,
                payMethod: '카카오페이',
                payTotalPrice :payTotalPrice1
            },

        }).done(function (data) {

          alert(payMethod.value);

            $.ajax({
                url: '/pay/confirmation/'+bookingNum, // 예: https://www.myservice.com/payments/complete
                type: "Get",
                headers: { "Content-Type": "application/json" },
                data: {
                    impUid: rsp.imp_uid,
                    payMethod: data.payMethod,
                    payTotalPrice : data.payTotalPrice

                },
                    success : function(url){
                    location.href=url;
                }
                });

//              $.post({
//                    url: '/pay/confirmation/'+bookingNum,
//                  data: {
//
//                    }
//                  }).done(function( data ) {
//
//                      $( "body" ).html(data);
//
//                  });
              });
//
//          let url = '/pay/confirmation/'+bookingNum;
//          location.href=url;


      } else {

        alert("결제에 실패하였습니다. 에러 내용: " +  rsp.error_msg);

        let url = '/payments/fail';

      }
    });

}




