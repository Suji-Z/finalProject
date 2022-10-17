
function searchPwdBtn(){
    const pwdEmail = $('#pwdEmail').val();
    
    alert("오나요")

    if(!pwdEmail || pwdEmail.trim() === ""){
        alert("이메일을 입력하세요.");
    } else {
        $.ajax({
            type: 'GET',
            url: '/member/login/findPwd',
            data: {
                'pwdEmail': pwdEmail
            },
        }).done(function(result){
            console.log("result :" + result);
            console.log(typeof result);

            if (result==true) {
                console.log("on");
                sendEmail();
                alert('임시비밀번호를 전송 했습니다.');

            } else if (result ==false) {
                alert('존재하지 않는 사용자 정보입니다.');
            }
        }).fail(function(error){
            alert(JSON.stringify(error));
        })
    }
};

function sendEmail(){
    const pwdEmail = $('#pwdEmail').val();

    $.ajax({
        type: 'POST',
        url: '/member/login/findPwd/sendEmail',
        data: {
            'pwdEmail' : pwdEmail
        },
        error: function(error){
            alert(JSON.stringify(error));
        }
    })
}