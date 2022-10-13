
$(document).ready(function(){

    var usernameOri = $('#username').val();
    var userbirthOri = $('#userbirth').val();
    var usernumberOri = $('#usernumber').val();

    $('#userInfoCheck').removeAttr('checked');
    $('#username').val("");
    $('#userbirth').val("");
    $('#usernumber').val("");

    $('#userInfoCheck').change(function(){
        if(this.checked){
            $('#username').val(usernameOri);
            $('#userbirth').val(userbirthOri);
            $('#usernumber').val(usernumberOri);
        }else{
            $('#username').val("");
            $('#userbirth').val("");
            $('#usernumber').val("");
         }

        });
});




