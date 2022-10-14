
function couponApply() {

var couponNum = $("#couponNum option:selected").val();


    $.ajax({
        url: '/booking/detail/applyCoupon',
        type: 'GET',
        data: {
            chkCoupon: $('#couponNum').val()
        },
        contentType: "application/json",

        success: function(result) {
            if (result) {
               $('#chkCouponName').html(result.couponName);
               $('#chkCouponRate').html(result.couponRate);

                   } else {
                       alert("전송된 값 없음");
                   }
               },
               error: function() {
                   alert("에러 발생");
               }

    })
};