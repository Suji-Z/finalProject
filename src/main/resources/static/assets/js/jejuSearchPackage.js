
function selectdatePackage(){

    const date = $('#datePicker').val();
    const packagenum = $('#packagenum').val();
    const acount = $('#acount').html();
    const ccount = $('#ccount').html();
    const bcount = $('#bcount').html();

    if(date!=null){
        $('.select_person_side').show();
    }

    if(acount!=0){
     $('#adult_count_price').show();
    }else{
     $('#adult_count_price').hide();
    }

    if(bcount!=0){
    $('#baby_count_price').show();
    }else{
     $('#baby_count_price').hide();
    }

    if(ccount!=0){
    $('#child_count_price').show();
    }else{
    $('#child_count_price').hide();
    }
    $.ajax({
        type: 'GET',
        url: '/jeju/dateprice',
        data: {
            'date' : date,
            'acount' : acount,
            'ccount' : ccount,
            'bcount' : bcount,
            'packagenum' : packagenum
        },
        contentType: "application/json",
          success: function (data) {

            var totalcount;

                if (data) {

                $('#totalacount').html(data.acount);
                $('#totalbcount').html(data.bcount);
                $('#totalccount').html(data.ccount);

                $('#bookingacount').val(data.acount);
                $('#bookingbcount').val(data.bcount);
                $('#bookingccount').val(data.ccount);

                if(data.discount==null){

                $('#aprice').html(data.aprice);
                $('#cprice').html(data.cprice);
                $('#bprice').html(data.bprice);
                totalcount = data.aprice+data.cprice+data.bprice;
                $('#totalprice').html(totalcount);
                }else {

                $('#bfaprice').html(data.aprice);
                $('#bfcprice').html(data.cprice);
                $('#bfbprice').html(data.bprice)
                $('#aftaprice').html(data.dcaprice);
                $('#aftbprice').html(data.dcbprice);
                $('#aftcprice').html(data.dccprice);
                 totalcount = data.dcaprice+data.dcbprice+data.dccprice;
                $('#totalprice').html(totalcount);
                }

               } else {
                   alert("전송된 값 없음");
               }

          }
    })

}