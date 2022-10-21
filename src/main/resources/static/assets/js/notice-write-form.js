function noticeConfirm(){

    if($("#subject").val()===""){
        alert("제목은 비워둘 수 없습니다.");
        return false;
    }else if($("#categorychk option:selected").val()==="nochoice"){
        alert("카테고리를 선택해주세요.");
        return false;
    }else if($("#content").val()===""){
        alert("내용은 비워둘 수 없습니다.");
        return false;
    }



}