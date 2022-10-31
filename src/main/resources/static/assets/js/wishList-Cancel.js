
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

            alert("위시리스트가 삭제되었습니다.");
            $('#wishListTable').replaceWith(data);

        },
        error: function(xhr, status, error) {
            alert('error');
        }
    })



}
