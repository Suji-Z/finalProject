function allselect(bool){
    var chks = document.getElementsByName("checkRow");
    for(var i = 0; i < chks.length; i++){
        chks[i].checked = bool;
    }
};

