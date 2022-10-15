
$(document).ready(function(){

    let usernameOri = $('#username').val();
    let userbirthOri = $('#userbirth').val();
    let usernumberOri = $('#usernumber').val();

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




