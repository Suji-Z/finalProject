$(function() {
		$('#datePicker').datepicker({
		    format: "yyyy-mm-dd",	//데이터 포맷 형식(yyyy : 년 mm : 월 dd : 일 )
		    startDate: '0d',	//달력에서 선택 할 수 있는 가장 빠른 날짜. 이전으로는 선택 불가능 ( d : 일 m : 달 y : 년 w : 주)
		    autoclose : true,	//사용자가 날짜를 클릭하면 자동 캘린더가 닫히는 옵션
		    templates : {
		        leftArrow: '&laquo;',
		        rightArrow: '&raquo;'
		    }, //다음달 이전달로 넘어가는 화살표 모양 커스텀 마이징
		    title: "출발일 선택",	//캘린더 상단에 보여주는 타이틀
		    todayHighlight : true ,	//오늘 날짜에 하이라이팅 기능 기본값 :false
		    weekStart : 0 ,//달력 시작 요일 선택하는 것 기본값은 0인 일요일
		    language : "ko"	//달력의 언어 선택
		});//datepicker end
	});//ready end

$(function() {
    $('#fromDate').datepicker({
        format: "yyyy-mm-dd",	//데이터 포맷 형식(yyyy : 년 mm : 월 dd : 일 )
        startDate: '0d',	//달력에서 선택 할 수 있는 가장 빠른 날짜. 이전으로는 선택 불가능 ( d : 일 m : 달 y : 년 w : 주)
        autoclose : true,	//사용자가 날짜를 클릭하면 자동 캘린더가 닫히는 옵션
       templates : {
            leftArrow: '&laquo;',
            rightArrow: '&raquo;'
        }, //다음달 이전달로 넘어가는 화살표 모양 커스텀 마이징
        title: "출발일 선택",	//캘린더 상단에 보여주는 타이틀
        todayHighlight : true ,	//오늘 날짜에 하이라이팅 기능 기본값 :false
        weekStart : 0 ,//달력 시작 요일 선택하는 것 기본값은 0인 일요일
        language : "ko"	//달력의 언어 선택, 그에 맞는 js로 교체해줘야한다.
    }).on("changeDate", function(){
         $("#toDate").datepicker("setStartDate", new Date($("#fromDate").val()));
    });


    $('#toDate').datepicker({
        format: "yyyy-mm-dd",	//데이터 포맷 형식(yyyy : 년 mm : 월 dd : 일 )
        startDate: '0d',	//달력에서 선택 할 수 있는 가장 빠른 날짜. 이전으로는 선택 불가능 ( d : 일 m : 달 y : 년 w : 주)
        autoclose : true,	//사용자가 날짜를 클릭하면 자동 캘린더가 닫히는 옵션
        templates : {
            leftArrow: '&laquo;',
            rightArrow: '&raquo;'
        }, //다음달 이전달로 넘어가는 화살표 모양 커스텀 마이징
        title: "도착일 선택",	//캘린더 상단에 보여주는 타이틀
        todayHighlight : true ,	//오늘 날짜에 하이라이팅 기능 기본값 :false
         weekStart : 0 ,//달력 시작 요일 선택하는 것 기본값은 0인 일요일
        language : "ko"	//달력의 언어 선택, 그에 맞는 js로 교체해줘야한다.
    }).on("changeDate", function(){
      $("#fromDate").datepicker("setEndDate", new Date($("#toDate").val()));
    });

});