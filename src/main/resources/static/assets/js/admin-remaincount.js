
function remainApply(event) {

    let bookingNum = event.value;

    $("#check" + bookingNum).toggle();

    let departure = $("#checkdeparture" + bookingNum).val();
   let packagenum =  $("#checkpackagenum" + bookingNum).val();
   let count =  $("#checkcount" + bookingNum).val();


    $.ajax({
        url: '/admin/package/bookingCheck',//컨트롤러 주소
        type: 'GET',
        data: {//보낼 데이터
            bookingNum : bookingNum,
            packageNum :  packagenum,
            departure :departure,
            bookingcount : count

        },
        contentType: "application/json",

        success: function(result) {//성공했을시

            console.log("넘어온 msg >>>" + result.msg);

            if (result.msg=="none") {

                alert("예약 승인되었습니다.");
               location.reload();



            } else if(result.msg=="마감"){
                alert("예약 인원이 마감된 일자입니다.");
                location.reload();

            }else{
                alert("왜왔냐")
            }
        },
        error: function() {
            alert("에러 발생");
        }

    })
};
