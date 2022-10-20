function profileUpdateConfirm() {

    let keyword = document.getElementsByName("keyword")

    let keyCnt=0;

    keyword.forEach(element => {

        if(element.checked) keyCnt++;
    });

    if(keyCnt<1 || keyCnt>3) {
        alert('선호항목을 1개이상 또는 3개이하 선택해주세요.')
        return false;
    }

    //
    // if($("input[name=checked_phone]").val()==''){
    //     alert('인증번호 본인확인이 필요합니다.');
    //
    //     return false;
    // }


};