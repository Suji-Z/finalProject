function updateReply(){

    let content = $("#content").val();
    let noticeNum = $("#noticeNum").val();
    let memberNum = $("#memberNum").val();

    if(content==""){
        alert("내용을 비워둘 수 없습니다.");
        return false;
    }

    $.ajax({
        url: '/noticeReply/write',
        type: 'post',
        data: {
            content : $("#content").val(),
            noticeNum : $("#noticeNum").val(),
            memberNum : $("#memberNum").val()
        },
        success : function(data) {

            $('#commentTable').replaceWith(data);

        },
        error: function(xhr, status, error) {
            alert('error');
        }
    })

}