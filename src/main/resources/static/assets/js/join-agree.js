$(function () {

    $('#more1').on("click",function (){
        if($('#text1').css("display") =="none"){
            $('#text1').show();
            $('#more1').hide();
            $('#hide1').show();
        }
    });

    $('#hide1').on("click",function (){
         $('#text1').hide();
         $('#more1').show();
         $('#hide1').hide();
    });


    $('#more2').on("click",function (){
        if($('#text2').css("display") =="none"){
            $('#text2').show();
            $('#more2').hide();
            $('#hide2').show();
        }
    });

    $('#hide2').on("click",function (){
        $('#text2').hide();
        $('#more2').show();
        $('#hide2').hide();
    });

    $('#more3').on("click",function (){
        if($('#text3').css("display") =="none"){
            $('#text3').show();
            $('#more3').hide();
            $('#hide3').show();
        }
    });

    $('#hide3').on("click",function (){
        $('#text3').hide();
        $('#more3').show();
        $('#hide3').hide();
    });

});