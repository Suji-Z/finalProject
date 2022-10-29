function mypagePhoneCheck(){

    var except = /^(01[016789]{1})([0-9]{4})([0-9]{4})$/;
    var phone = $('#phone_num').val();
    if(phone=='') {
        alert("휴대폰번호를 입력해주세요.")
        return false;

    }else if (!except.test(phone)&&phone.length!=11) {
        alert("올바른 휴대폰번호가 아닙니다.")
        return false;
    }else{
        $.ajax({
            url: "/join/phoneCheck",
            type: "GET",
            data: {
                phone_num:phone
            },

            success: function (data) {
                const checkNum = data;

                alert('checkNum:' + checkNum);

                //인증하기 버튼 클릭 이벤트

                $(function () {
                    $('#checkBtn').on("click", function () {
                        let userNum = $('#sms_num').val();

                        if (checkNum == userNum) {
                            $("input[name=checked_phone]").val('y');
                            //히든 값 바꿔주기
                            $("input[name=phone_num2]").val($('#phone_num').val());
                            alert('인증 성공하였습니다.');
                        } else if (userNum == '') {
                            alert('인증번호를 입력해주세요.');
                        } else {
                            alert('인증 실패하였습니다. 다시 입력해주세요.');
                        }
                    });
                });

            },

        });

    }
}