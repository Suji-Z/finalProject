
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
     $('.tour_select_offer_bar_bottom_sally').show();
     $('.tour_select_offer_bar_bottom_sally_x').hide();


    }else{
     $('#adult_count_price').hide();
     $('.tour_select_offer_bar_bottom_sally').hide();
     $('.tour_select_offer_bar_bottom_sally_x').show();
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
        url: '/package/dateprice',
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

                $('#aprice').html(data.aprice.toLocaleString()+'원');
                $('#cprice').html(data.cprice.toLocaleString()+'원');
                $('#bprice').html(data.bprice.toLocaleString()+'원');
                totalcount = data.aprice+data.cprice+data.bprice;
                $('#totalprice').html(totalcount.toLocaleString()+'원');
                }else {

                $('#bfaprice').html(data.aprice.toLocaleString()+'원');
                $('#bfcprice').html(data.cprice.toLocaleString()+'원');
                $('#bfbprice').html(data.bprice.toLocaleString()+'원')
                $('#aftaprice').html(data.dcaprice.toLocaleString()+'원');
                $('#aftbprice').html(data.dcbprice.toLocaleString()+'원');
                $('#aftcprice').html(data.dccprice.toLocaleString()+'원');
                 totalcount = data.dcaprice+data.dcbprice+data.dccprice;
                $('#totalprice').html(totalcount.toLocaleString()+'원');
                }

               } else {
                   alert("전송된 값 없음");
               }

          }
    })

}