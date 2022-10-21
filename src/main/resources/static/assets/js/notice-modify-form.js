function noticeModify(){

    let subject = $("#subject").val();
    let content = $("#content").val();
    let category = $("#categorychk option:selected").val();

    if(subject==$("#subject").val() && content==$("#content").val() && category==$("#categorychk option:selected").val()){
        alert("바뀐 내용이 없습니다. 등록하시겠습니까");
        return false;
    }



}