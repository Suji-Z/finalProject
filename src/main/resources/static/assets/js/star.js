


    var drawStar = function(target) {
        document.querySelector(`.star span`).style.width = `${target.value * 10}%`;
        document.querySelector(`#result`).innerText = target.value * 0.5;
        target.value = target.value * 0.5;



    }

    var reviewstar = function(target){

        for(var i = 0; i <= target.value; i++){
            documnet.write("★");
        }

    }

