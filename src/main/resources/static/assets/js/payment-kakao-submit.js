
function kakaoSubmit() {


        // 필수입력값을 확인. 나는 필수가 아닐수도?
        var name = $("#kakaoname").val();
        var tel = $("#kakaotel").val();
        var email = $("#kakaoemail").val();

        // 결제 정보를 form에 저장한다.
        let payTotalPrice = parseInt($("#payTotalPrice").text().replace(/,/g,''));
        let bookingPrice = parseInt($("#bookingPrice").text().replace(/,/g,''));
        let discountPrice = bookingPrice - payTotalPrice;
        let pointApply = $("#pointApply").val();

            // 카카오페이 결제전송
            $.ajax({
                type:'get',
                url:'/pay/kakaopay',
                data:{
                    total_amount: payTotalPrice,
                    payUserName: name,
                    sumPrice:bookingPrice,
                    discountPrice:discountPrice,
                    totalPrice:payTotalPrice,
                    tel:tel,
                    email:email,
                    usePoint:pointApply
                },

                success: function(result) {
                    if (result) {
                    location.href = result.next_redirect_pc_url;

                        } else {
                            alert("전송된 값 없음");
                        }
                    },
                   error: function() {
                       alert("에러 발생");
                   }
            })

        };


