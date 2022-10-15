
function bookingConfirm() {

    //비교할 데이터
    let chk = document.getElementsByName("agreeCheck");
    let username = $('#username').val();
    let userbirth = $('#userbirth').val();
    let usernumber = $('#usernumber').val();

    //사용자 정보 확인
    if(username==''||userbirth==''||usernumber==''){
        alert('여행자 정보를 입력해주세요.')
        return false;
    }

    //동의사항 선택 확인
    let cnt=0;
    chk.forEach(element => {
        if(element.checked) cnt++;
    });

    if(cnt !=3 ) {
       alert('항목을 모두 동의해야만 예약가능합니다.')
       return false;
    }
};