

function noticeSearch(){

  let searchKeyword = $("#searchKeyword").val();
  let category = $("#noticeFilter").val();

      $.ajax({
          url: '/notice/search',
          type: 'post',
          data: {
              searchKeyword : searchKeyword,
              category : category
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