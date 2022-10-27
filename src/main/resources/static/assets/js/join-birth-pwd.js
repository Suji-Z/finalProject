$(function () {

    var birthExcept = /^(19[0-9][0-9]|20[0-1][0-9])(0[0-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])$/;
    var pwdExcept = /^(?=.*[a-zA-Z])((?=.*\d)(?=.*\W)).{8,16}$/

    $('#birth').on("change",function (){

        var birth = $('#birth').val();

        if(!birthExcept.test(birth)){
            $('#birthAvailable').hide();
            $('#birthNotAvailable').show().text('생년월일 형식이 올바르지 않습니다.');

            return false;
        }else{
            $('#birthNotAvailable').hide();
        }
    });

    $('#password1').on("keyup",function (){

        var pwd1 = $('#password1').val();

        if(!pwdExcept.test(pwd1)){
            $('#pwdAvailable').hide();
            $('#pwdNotAvailable').show().text('비밀번호는 영문,숫자,특수문자를 포함하여 8-16자리로 입력해주세요');

            return false;
        }else {
            $('#pwdNotAvailable').hide();
        }

    });

    $('#password2').on("keyup",function (){

        var pwd1 = $('#password1').val();
        var pwd2 = $('#password2').val();

        if(pwd1!=pwd2){

            $('#repwdAvailable').hide();
            $('#repwdNotAvailable').show().text('비밀번호가 일치하지 않습니다.');

            return false;
        }else {

            $('#repwdNotAvailable').hide();
            $('#repwdAvailable').show().text('비밀번호가 일치합니다.');

        }

    });
    /*  정규식 외 방법. (윤달까지 check)
          var year = Number(birth.substring(0,4)); // 입력한 값의 0~4자리까지 (연)
          var month = Number(birth.substring(4,6)); // 입력한 값의 4번째 자리부터 2자리 숫자 (월)
          var day = Number(birth.substring(6,8));

                  if (birth.length <=8) {

                      if(1990>year || year > 2019) {
                          $('#birthAvailable').hide();
                          $('#birthNotAvailable').show().text('생년월일 형식이 올바르지 않습니다.');

                          return false;

                      }else if (month<1 || month >12){

                          $('#birthAvailable').hide();
                          $('#birthNotAvailable').show().text('생년월일 형식이 올바르지 않습니다.');

                          return false;

                      }else if (day <1 || day>31){

                          $('#birthAvailable').hide();
                          $('#birthNotAvailable').show().text('생년월일 형식이 올바르지 않습니다.');

                          return false;
                      }else if((month==4 || month==6 || month==9 || month==11) && day==31){

                          $('#birthAvailable').hide();
                          $('#birthNotAvailable').show().text('생년월일 형식이 올바르지 않습니다.');

                          return false;

                      }else if (month==2){

                          var isleap = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));

                          if (day>29 || (day==29 && !isleap)) {

                              $('#birthAvailable').hide();
                              $('#birthNotAvailable').show().text('생년월일 형식이 올바르지 않습니다.');

                              return false;

                          }else{
                              $('#birthAvailable').hide();
                              $('#birthNotAvailable').show().text('');
                          }
                      }else {
                          $('#birthAvailable').hide();
                          $('#birthNotAvailable').hide();
                      }

                  }else{
                      $('#birthAvailable').hide();
                      $('#birthNotAvailable').hide();
                  }
          */






});