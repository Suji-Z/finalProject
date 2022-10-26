


    var drawStar = function(target) {
        document.querySelector(`.star span`).style.width = `${target.value * 10}%`;
        document.querySelector(`#result`).innerText = target.value * 0.5;
        target.value = target.value * 0.5;



    }


    $(document).ready(function reviewstar() {

        let score = document.getElementById('score').value;
        let scorestar = ("★").repeat(score);
        document.getElementById('scorestar').innerHTML
            = `<p>${scorestar}</p>`;


    });

