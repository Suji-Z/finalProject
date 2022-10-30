$(function () {
    let voicePageNum = $("#voicePageNum").val();

    $('#typeValue').change(function (){

        let types = $('#typeValue').val();

        location.href = "searching?page=" + voicePageNum + "&types=" + types;

    });

});