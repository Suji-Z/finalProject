
function bookingConfirm() {

    //비교할 데이터
    let chk = document.getElementsByName("agreeCheck");
    let username = $('#username').val();
    let userbirth = $('#userbirth').val();
    let usernumber = $('#usernumber').val();

//    //사용자 정보 확인
//    if(username==''||userbirth==''||usernumber==''){
//        alert('여행자 정보를 입력해주세요.')
//        return false;
//    }

    //정규식 검사
    const korean = /^[ㄱ-ㅎ|가-힣]+$/;
    const tel = /^[0-9]{2,3}[0-9]{3,4}[0-9]{4}$/;
    const birth = /^(19[0-9][0-9]|20\d{2})-(0[0-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/;

    if(!korean.test(username)){
        alert("한글 이름을 입력해주세요. (ex.홍길동)");
        return false;
    }else if(!birth.test(userbirth)){
        alert("생년월일을 정확히 입력해주세요. (ex.19990101)");
        return false;
    }else if(!tel.test(usernumber)){
        alert("정확한 휴대폰 번호를 입력해주세요. (ex.01012345678)");
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