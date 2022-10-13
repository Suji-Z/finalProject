
function bookingConfirm() {

    let chk = document.getElementsByName("agreeCheck");

    let cnt=0;
    chk.forEach(element => {
        if(element.checked) cnt++;
    });

    if(cnt !=3 ) {
       alert('동의 항목을 모두 동의해주세요.')
       isValid=false;
    }
};