function checkConfirm() {

    let chk = document.getElementsByName("checkRow")
    let keyword = document.getElementsByName("keyword")

    let cnt=0;
    let keyCnt=0;

    chk.forEach(element => {       //element는 name이 hobby인 input 요소 입니다.
                                             //alert(`배열요소 ${element.value} : ${element.checked}`)
        if(element.checked) cnt++;
    });

    if(cnt !=3 ) {
        alert('필수 항목을 모두 동의해주세요.')
        isValid=false;
    }

    keyword.forEach(element => {

        if(element.checked) keyCnt++;
    });

    if(keyCnt<1 || keyCnt>3) {
        alert('선호항목을 1개이상 또는 3개이하 선택해주세요.')
        isValid=false;
    }

    if($("input[name=checked_id]").val()==''){
        alert('이메일 중복확인을 해주세요.');

        return false;
    }

    if($("input[name=checked_phone]").val()==''){
        alert('인증번호 본인확인이 필요합니다.');

        return false;
    }


};