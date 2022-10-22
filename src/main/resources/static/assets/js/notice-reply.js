function replyConfirm(){


    let content = $("#content").val();
    let noticeNum = $("#noticeNum").val();

    var replyForm ={
        id : "0",
        created : "0",
        content : $("#content").val(),
        memberNum : $("#memberNum").val(),
        noticeNum : $("#noticeNum").val(),
    };

    alert(JSON.stringify(replyForm));


    if(content==""){
        alert("내용을 비워둘 수 없습니다.");
        return false;
    }

        $.ajax({
            url: '/noticeReply/write',
            type: 'post',
            data: replyForm,
            contentType: "json",
        })
        .done(function (fragment) {
            $('#commentTable').replaceWith(fragment);
        });


}