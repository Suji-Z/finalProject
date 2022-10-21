
function pinCheck(event){

    let pinCount = $('#pinCount').val();

    if(pinCount>=3){
        alert("이미 고정된 게시글이 3개입니다.");
        event.checked = false;
    }

}
