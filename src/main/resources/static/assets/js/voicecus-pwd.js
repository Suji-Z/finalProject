$(function () {
    $('#userPwdChk').on("click",function (){
        let pwdVal = $('#pwd').val();
        let userPwd = $('#userPwd').val();
        let articleNum = $('#articleNum').val();


        $.ajax({
            url: '/voiceCus/article/chkPwd',
            type: 'POST',
            data: {
                pwd: $('#pwd').val(),
                id: articleNum,
            },

            success: function (result) {

                if(result=="true"){
                    location.href = "/voiceCus/article/" + articleNum;
                }else if(result=="false"){
                    alert("비밀번호가 일치하지 않습니다.");
                    return false;
                }


            }, error: function (error) {
             alert("에러입니당 ㅜ");
            }
        });



    });


    $('#replyPwdChk').on("click",function (){
        let pwdVal = $('#pwd').val();
        let userPwd = $('#userPwd').val();
        let replyNum = $('#replyNum').val();

        console.log("입력패스워드: "+pwdVal);
        console.log("유저패스워드: "+userPwd);
        console.log("글번호:" + replyNum);

        $.ajax({
            url: '/reVoiceCus/article/chkPwd',
            type: 'POST',
            data: {
                pwd: $('#pwd').val(),
                id: replyNum,
            },

            success: function (result) {

                if(result=="true"){
                    location.href = "/reVoiceCus/article/" + replyNum;
                }else if(result=="false"){
                    alert("비밀번호가 일치하지 않습니다.");
                    return false;
                }


            }, error: function (error) {
                alert("에러입니당 ㅜ");
            }
        });



    });

});