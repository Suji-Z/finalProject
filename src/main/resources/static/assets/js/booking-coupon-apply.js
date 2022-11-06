
function couponApply() {

let couponNum = $("#couponNum option:selected").val();
let bookingPrice = $("#bookingPrice").val();

    $.ajax({
        url: '/booking/detail/applyCoupon',
        type: 'GET',
        data: {
            chkCoupon: $('#couponNum').val(),
            bookingPrice : $("#bookingPrice").val()
        },
        contentType: "application/json",

        success: function(result) {
            if (result) {
               $('#chkCouponName').html(result.couponName);
               $('#couponDiscountPrice').html(result.couponDiscountPrice);
               $('#bookingTotalPrice').html(result.bookingTotalPrice);

                   } else {
                       alert("전송된 값 없음");
                   }
               },
               error: function() {
               }

    })
};