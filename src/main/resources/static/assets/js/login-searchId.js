function searchIdBtn(){

        alert("아작스전");

    $.ajax({
        url: '/login/findId',
        type: 'GET',
        data: {
            inputName: $('#inputName').val(),
            inputPhone: $('#inputPhone').val()
        },

        success: function (data) {

            alert("헤이")
            // 성공 시 실패 메시지 hide, 성공 메시지 show
            if ($('#inputPhone').val() != '') {

                $('#id_value').show().text('회원님의 아이디는 '+data+' 입니다.').append($('<br />'));
            } else {
                alert("회원 정보를 입력해주세요.")
            }
        }, error: function (error) {

            alert("ㅠㅠ")
            // 실패 시 실패 메시지 show, 성공 메시지 hide
            $('#emailAlert').hide();

            $('#id_value').show().text('사용자정보를 찾을 수 없습니다.').append($('<br />'));
        }
    });

};
