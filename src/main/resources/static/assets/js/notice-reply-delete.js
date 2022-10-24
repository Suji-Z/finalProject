

function noticeDeleteReply(event){

  let noticeNum = $("#noticeNum").val();

      $.ajax({
          url: '/noticeReply/delete',
          type: 'post',
          data: {
              replyNum : event.value,
              noticeNum : $("#noticeNum").val()
          },
          success : function(data) {

              alert("댓글을 삭제하였습니다.");
              $('#commentTable').replaceWith(data);

          },
          error: function(xhr, status, error) {
              alert('error');
          }
      })

}