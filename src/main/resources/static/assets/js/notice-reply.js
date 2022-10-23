function replyConfirm(){


    let content = $("#content").val();
    let noticeNum = $("#noticeNum").val();

    var replyForm ={


        "content" : content,
    };

    if(content==""){
        alert("내용을 비워둘 수 없습니다.");
        return false;
    }

        $.ajax({
            anyne:true,
            url: '/noticeReply/write',
            type: 'post',
            data: JSON.stringify(replyForm),
            dataType: "json",
            contentType:'application/json',
        })
        .done(function (fragment) {
            $('#commentTable').replaceWith(fragment);
        });

}