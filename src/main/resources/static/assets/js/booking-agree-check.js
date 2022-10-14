
function bookingConfirm() {

    let chk = document.getElementsByName("agreeCheck");

    let cnt=0;
    chk.forEach(element => {
        if(element.checked) cnt++;
    });

    if(cnt !=3 ) {
       alert('항목을 모두 동의해야만 예약가능합니다.')
       return false;
    }
};