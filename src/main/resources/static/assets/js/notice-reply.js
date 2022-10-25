function replyConfirm(){

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

            document.getElementById("content").value='';
            $('#commentTable').replaceWith(data);

        },
        error: function(xhr, status, error) {
            alert('error');
        }
    })

}


// function replyConfirm(){
//
//     let content = $("#content").val();
//     let noticeNum = $("#noticeNum").val();
//     let memberNum = $("#memberNum").val();
//
//     var replyForm ={
//
//         "memberNum" : memberNum,
//         "noticeNum" : noticeNum,
//         "content" : content,
//     };
//
//     console.log(replyForm);
//
//     if(content==""){
//         alert("내용을 비워둘 수 없습니다.");
//         return false;
//     }
//
//         $.ajax({
//             anyne:true,
//             url: '/noticeReply/write',
//             type: 'post',
//             data: JSON.stringify(replyForm),
//             dataType: "json",
//             contentType:'application/json',
//             success : function(data) {
//
//                 $('#viewTable').replaceWith(data);
//
//             },
//             error: function(xhr, status, error) {
//                 alert('error');
//             }
//         })
//
// }