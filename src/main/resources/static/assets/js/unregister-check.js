function unregisterConfirm() {

    //비교할 데이터
    let chk = document.getElementsByName("agreeClick");

    //동의사항 선택 확인
    let cnt=0;
    chk.forEach(element => {
        if(element.checked) cnt++;
    });

    if(cnt !=1 ) {
        alert('동의사항에 체크해주세요.')
        return false;
    }
};