
function wishListCancel(event){

    let packageNum = event.value;
    let i = $("#recommendStatus").val();
    let wishListNum = $('#cancel'+packageNum).val();


    $.ajax({
        url: '/mypage/wishListCancel',
        type: 'post',
        data: {
            packageNum : wishListNum,
        },

        success : function(data) {
            if(confirm('위시리스트를 삭제하시겠습니까?')){
                alert("삭제되었습니다.");
                $('#wishListTable').replaceWith(data);
            }else{
                return;
            }



        },
        error: function(xhr, status, error) {
            alert('error');
        }
    })



}
