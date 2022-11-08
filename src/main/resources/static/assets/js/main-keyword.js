$('.nav-link').on('click',(e)=> {
    console.log(e.target.value);
    console.log("찍히나요")

    let values = [];

    $.ajax({
        url: '/main/keyword',
        type: 'POST',
        dataType: 'json',
        data: {
            keyword: e.target.value,
        },  success : function(result, status, xhr) {    // 정상적으로 응답 받았을 경우 파라미터는 응답 바디, 응답 코드 그리고 XHR 헤더  }
            alert('success');
            console.log("result >>>" + JSON.stringify(result)); //json을 string으로 출력

            console.log("success 키워드 >>>" + result.returnKeyword); // OK
            console.log("success 패키지이름 >>>" + result.packageName);

            values = result.theme;

            $.each(values,function (index,item) {

                console.log(index+" : "+item.packageName);


            });

        },
        error: function(xhr, status, error) {
            alert('error');
        }
    })


});


