$(function () {
    let voicePageNum = $("#voicePageNum").val();

    alert("페이지넘버:"+voicePageNum);
    
    $('#typeValue').change(function (){

        let types = $('#typeValue').val();

        location.href = "searching?page=" + voicePageNum + "&types=" + types;

    });

});