


    var drawStar = function(target) {
        document.querySelector(`.star span`).style.width = `${target.value * 10}%`;
        document.querySelector(`#result`).innerText = target.value;
    }
