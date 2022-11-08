function searchEmailBtn() {

    $.ajax({
        url: '/member/login/findId',
        type: 'GET',
        data: {
            inputName: $('#inputName').val(),
            inputPhone: $('#inputPhone').val()
        },

        success: function (data) {
            // 성공 시 실패 메시지 hide, 성공 메시지 show
            if (data!=null&&data!='') {

                $('#id_value').show().text('회원님의 아이디는 ' + data + ' 입니다.').append($('<br />'));

            } else {
                alert("올바른 회원 정보를 입력해주세요.")
            }
        }, error: function (error) {

            // 실패 시 실패 메시지 show, 성공 메시지 hide
            $('#emailAlert').hide();

            $('#id_value').show().text('사용자정보를 찾을 수 없습니다.').append($('<br />'));
        }
    });


    };


