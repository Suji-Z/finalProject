
function noticeUpdateReply1(event){

    let replyNum = event.value;
    alert(replyNum);

    $("#commentOri"+replyNum).toggle(); //원래 댓글 내용
    $('#commentBox'+replyNum).toggle(); //textarea
    $("#updateA"+replyNum).toggle(); //수정
    $("#delete"+replyNum).toggle(); //삭제
    $("#updateB"+replyNum).toggle(); //확인
    $("#cancle"+replyNum).toggle(); //취소

}



function noticeUpdateReply2(event){

    let replyNum = event.value;

    let commentBox = $('#commentBox'+replyNum).val();
    let commentOri = $('#commentOri'+replyNum).text();

    if(commentBox==""){
        alert("댓글 내용을 입력해주세요.");
        return false;

    }else if(commentBox == commentOri){
        alert("변경된 내용이 없습니다.");
        return false;
    }else{

         $.ajax({
                      url: '/noticeReply/update',
                      type: 'post',
                      data: {
                          replyNum : replyNum,
                          noticeNum : $("#noticeNum").val(),
                          content : commentBox
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


//function noticeUpdateReply1(event){
//
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
//this.$('#updateA'+event.value).attr('확인');
//alert(event.value);
//
//var comment = [[${comment.id}]]()
//
//
//}