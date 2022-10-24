function deleteReply(){

    let replyNum = $("#replyNum").val();
    alert(replyNum);

    $.ajax({
        url: '/noticeReply/delete',
        type: 'post',
        data: {
            replyNum : $("#replyNum").val(),
            noticeNum : $("#noticeNum").val()
        },
        success : function(data) {

            alert("삭제성공");

            $('#commentTable').replaceWith(data);

        },
        error: function(xhr, status, error) {
            alert('error');
        }
    })

}