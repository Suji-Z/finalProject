
    let pgName =  "";
    let pgMethod = "";

function requestPay() {

    //결제 선택 확인
    let payMethod = $("#payMethod").val();
    if(payMethod ===""){
    alert("결제방식을 선택해주세요.")
    return false;
    }

    //약관 체크 확인
    let chk = document.getElementsByName("agreeCheck");
    let cnt=0;
    chk.forEach(element => {
        if(element.checked) cnt++;
    });
    if(cnt !=2 ) {
       alert('항목을 모두 동의해야만 결제가능합니다.')
       return false;
    }

    //결제 방법 확인
    if(payMethod==='카카오페이'){
        pgName =  "kakaopay";
        pgMethod = "card";

    }else if(payMethod==='가상계좌'){
        pgName =  "html5_inicis";
        pgMethod = "vbank";

    }else if(payMethod==='신용카드'){
        pgName =  "html5_inicis";
        pgMethod = "card";

    }else {
        pgName =  "html5_inicis";
        pgMethod = "phone";

    }

    payConfirm();



}

//결제 방식
function payConfirm() {

    var IMP = window.IMP;
        IMP.init('imp40260603'); // 팝업창으로 뜨게 아임포트 가맹점 식별코드

        let payTotalPrice1 = parseInt($("#payTotalPrice").text().replace(/,/g,'')); //결제금액
    alert(payTotalPrice1);

    IMP.request_pay({ //요청하는 것들
        pg : pgName, //카카오페이 API래핑
        pay_method : pgMethod,
        merchant_uid: 'packageorder'+$("#bookingNum").val(),  // 상점에서관리하는 상품번호
        name : $("#packageName").val(), //상품명
        amount : payTotalPrice1, //가격
        buyer_email : $("#kakaoemail").val(), //구매자 이메일
        buyer_name : $("#kakaoname").val(), //구매자 이름
        buyer_tel : $("#kakaotel").val() //구매자 번호
        /* m_redirect_url : 'https://www.yourdomain.com/payments/complete' */

    }, function (rsp) {

        if (rsp.success) { // 결제 성공 시: 결제 승인 또는 가상계좌 발급에 성공한 경우
            //alert(rsp.imp_uid +" / " + rsp.merchant_uid);

        $.ajax({
            url: "/pay/payments/complete/", // 예: https://www.myservice.com/payments/complete
            type: "Get",
            headers: { "Content-Type": "application/json" },
            data: {
                impUid: rsp.imp_uid,
                merchantUid: rsp.merchant_uid,
                payMethod: $("#payMethod").val(),
                payTotalPrice :payTotalPrice1,
                bookingNum : $("#bookingNum").val(),

            },
            success : function(url){
                location.href=url;
            }

        });
        } else {

            alert("결제에 실패하였습니다. 에러 내용: " +  rsp.error_msg);

            let url = '/payments/fail';

        }
    });

}


