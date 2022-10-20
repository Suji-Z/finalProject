
$(document).ready(function() {

$("#pointApply").on("property change paste input", function() {

    let pointApply = fnReplace($("#pointApply").val());
    let bookingPrice = fnReplace($("#bookingPrice").text());
    let point = fnReplace($("#point").val());
    let payTotalPrice = fnReplace($("#bookingPrice").val());

    let remainPoint = point - pointApply;
     if(bookingPrice-pointApply <0){
        $("#remainPoint").text("(보유포인트 0)");
     }else{
        $("#remainPoint").text("(보유포인트 "+remainPoint+")");
     }

    if(pointApply > point){
        $("#pointMessage").text("보유 포인트보다 더 많은 포인트는 사용할 수 없습니다.");
        return false;
    }else
    if(bookingPrice-pointApply <0){
        $("#pointMessage").text("상품금액보다 더 많은 포인트는 사용할 수 없습니다.");
        $("#remainPoint").text("(보유포인트 0)");
        return false;
    }else{
    $("#pointMessage").text("");
    }

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
