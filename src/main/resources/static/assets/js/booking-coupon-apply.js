
function couponApply() {

let couponNum = $("#couponNum option:selected").val();
let bookingPrice = $("#bookingPrice").val();

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
               $('#couponDiscountPrice').html(bookingPrice*result.couponRate*(-1));
               $('#bookingTotalPrice').html(bookingPrice*(1-result.couponRate));

                   } else {
                       alert("전송된 값 없음");
                   }
               },
               error: function() {
                   alert("에러 발생");
               }

    })
};