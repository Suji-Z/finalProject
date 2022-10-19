
function requestPay() {

    var IMP = window.IMP;
    IMP.init('imp40260603'); // 팝업창으로 뜨게 아임포트 가맹점 식별코드

    //필요한 정보 : 상품번호, 상품명, 가격, 구매자이메일, 구매자 이름, 구매자 휴대폰번호
        let name = $("#kakaoname").val(); //구매자이름
        let tel = $("#kakaotel").val(); //구매자번호
        let email = $("#kakaoemail").val(); //구매자이메일
        let packageNum = $("#packageNum").val(); //패키지번호
        let packageName = $("#packageName").val(); //패키지이름
        let payTotalPrice = parseInt($("#payTotalPrice").text().replace(/,/g,'')); //결제금액

        IMP.request_pay({ //요청하는 것들
            pg : 'kakaopay', //카카오페이 API래핑
            pay_method : 'kakaopay',
            merchant_uid: "packageNum",  // 상점에서관리하는 상품번호
            name : packageName, //상품명
            amount : '20', //가격
            buyer_email : email, //구매자 이메일
            buyer_name : name, //구매자 이름
            buyer_tel : tel //구매자 번호
            /* m_redirect_url : 'https://www.yourdomain.com/payments/complete' */

        }, function (rsp) { if (rsp.success) { // 결제 성공 시: 결제 승인 또는 가상계좌 발급에 성공한 경우
            // jQuery로 HTTP 요청
            jQuery.ajax({

                url: "http://localhost:8088/pay/payments/complete", // 예: https://www.myservice.com/payments/complete
                method: "POST",
                type: "POST",
                headers: { "Content-Type": "application/json" },
                data: {
                    imp_uid: rsp.imp_uid,
                    merchant_uid: rsp.merchant_uid
                }
            }).done(function (data) {
              alert("성공")
            })
          } else {
            alert("결제에 실패하였습니다. 에러 내용: " +  rsp.error_msg);
          }
        });
    }

