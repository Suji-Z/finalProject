
function wishListCancel(event){

    let packageNum = event.value;
    let i = $("#recommendStatus").val();
    let wishListNum = $('#cancel'+packageNum).val();

    if(!confirm('위시리스트를 삭제하시겠습니까?')){
        return;
    }else{

    }


    $.ajax({
        url: '/mypage/wishListCancel',
        type: 'post',
        data: {
            packageNum : wishListNum,
        },

        success : function(data) {
            alert('삭제되었습니다.');
            $('#wishListTable').replaceWith(data);

        },
        error: function(xhr, status, error) {
            alert('error');
        }
    })



}
