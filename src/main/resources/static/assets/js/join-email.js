function emailCheck() {
    $("input[name=checked_id]").val('y');
    $.ajax({
        url: '/join/emailCheck',
        type: 'GET',
        contentType: 'application/json',
        headers: {
            // 스프링 시큐리티를 위한 헤더 설정
            "X-CSRF-TOKEN": $("meta[name='_csrf']").attr("content")
        },
        data: {
            email: $('#email').val()
        },

        success: function (result) {
            // 성공 시 실패 메시지 hide, 성공 메시지 show
            $('#emailAlert').hide();
            if($('#email').val()!=''){
            $('#emailNotAvailable').hide();
            $('#emailAvailable').show().text(result).append($('<br />'));
            } else{
                alert("이메일을 입력해주세요.")
            }
        }, error: function (error) {
            // 실패 시 실패 메시지 show, 성공 메시지 hide
            $('#emailAlert').hide();
            $('#emailAvailable').hide();
            $('#emailNotAvailable').show().text('이미 사용중인 아이디 입니다.').append($('<br />'));
        }
    });

};

