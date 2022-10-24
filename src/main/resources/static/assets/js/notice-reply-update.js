//function noticeUpdateReply1(id) => {
//
//  let tdReplyUpdate = document.querySelector(`.update1(${comment.id})`);
//  let tdReplyDelete = document.querySelector(`.update2(${comment.id})`);
//  let tdReplySubmit = document.querySelector(`.cancle(${comment.id})`);
//  let tdReplyUpdateCancel = document.querySelector(`delete(${comment.id})`);
//
//    tdReplyUpdate.style.display = 'none';
//    tdReplyDelete.style.display = 'none';
//    tdReplySubmit.style.display = 'inline-block';
//    tdReplyUpdateCancel.style.display = 'inline-block';
//
//}


function noticeUpdateReply1(event){
//  let tdReplyUpdate = document.querySelector(`.updateA${comment.id}`); //수정
//  let tdReplyDelete = document.querySelector(`.delete${comment.id}`); //삭제
//  let tdReplySubmit = document.querySelector(`.updateB${comment.id}`); //확인
//  let tdReplyUpdateCancel = document.querySelector(`.cancle${comment.id}`); //취소
//
//  console.log(tdReplyUpdate);
//  tdReplyUpdate.style.display = 'none';
//  tdReplyDelete.style.display = 'none';
//  tdReplySubmit.style.display = 'inline-block';
//  tdReplyUpdateCancel.style.display = 'inline-block';
//
//$('#updateA'+event.value).html('확인');
this.$('#updateA'+event.value).attr('확인');
alert(event.value);
}



function noticeUpdateReply2(event){

alert(event.value);

    let commentBox = $("#commentBox").val();
    if(commentBox==""){
        alert("댓글 내용을 입력해주세요.");
        return false;
    }else{
        alert(commentBox);

         $.ajax({
                      url: '/noticeReply/update',
                      type: 'post',
                      data: {
                          replyNum : event.value,
                          noticeNum : $("#noticeNum").val(),
                          content : $("#commentBox").val()
                      },
                      success : function(data) {

                          alert("댓글을 수정하였습니다.");
                          $('#commentTable').replaceWith(data);

                      },
                      error: function(xhr, status, error) {
                          alert('error');
                      }
                  })
    }



}