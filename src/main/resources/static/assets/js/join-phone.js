function phoneCheck(){

    if($('#phone_num').val()=='') {
        alert("휴대폰번호를 입력해주세요.")
        return false;
    }else {
        $.ajax({
            url: "/join/phoneCheck",
            type: "GET",
            data: {
                phone_num:$('#phone_num').val()
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