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

    //핸드폰 번호 변경하지 않은 경우 본인확인없이 바로 수정가능하도록
    if($("input[name=phone_num]").val()==$("input[name=phone_num2]").val()){
       return true;

    }else{
        alert('인증번호 본인확인이 필요합니다.');
        return false;
    }

};