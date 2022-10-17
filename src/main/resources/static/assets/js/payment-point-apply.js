$(document).ready(function() {

$("#pointApply").on("propertychange change paste input", function() {

    let pointApply = fnReplace($("#pointApply").val());
    let bookingPrice = fnReplace($("#bookingPrice").text());
    let point = fnReplace($("#point").val());

    if(pointApply > point){
        alert ("보유 포인트보다 더 많은 포인트는 사용할 수 없습니다.");
        return;
    };

    $("#payTotalPrice").text(bookingPrice-pointApply);

});


});

function fnReplace(val) {
    var ret = 0;
    if(typeof val != "undefined" && val != null && val != ""){
        ret = Number(val.replace(/,/gi,''));
    }
    return ret;
}
