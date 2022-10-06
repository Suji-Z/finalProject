function emailCheck() {
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
            $('#emailNotAvailable').hide();
            $('#emailAvailable').show().text(result).append($('<br />'));
        }, error: function (error) {
            // 실패 시 실패 메시지 show, 성공 메시지 hide
            $('#emailAvailable').hide();
            $('#emailNotAvailable').show().text('이미 사용중인 아이디 입니다.').append($('<br />'));
        }
    });
}