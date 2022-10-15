
$(function () {
    $('.btn-add,.btn-subtract').on('click touchstart', function () {

        const qadult = $('#f-qadult').val();
        const qchild = $('#f-qchild').val();
        const qbaby = $('#f-qbaby').val();

        $('.qstringa').text(` ${qadult} 성인`);
        $('.qstringc').text(` ${qchild} 어린이`);
        $('.qstringb').text(` ${qbaby} 유아`);
        event.stopPropagation();
        event.preventDefault();
    });
});



function addValuea(a) {
    const asum =+ a ;
    alert('a');
    $('.pcount').text(`${asum}`);
}

function addValuec(c) {
    const csum = c++;
    alert('c');
    $('.ccount').text(`${csum}`);

}

function addValueb(b) {
    const bsum = b++;
    alert('b');
    $('.incount').text(`${bsum}`);
}



$(function () {

    var i = 0;

    $('.btn-add').on('click touchstart', function () {
        const avalue = ++i;
        $('.pcount').text(`${avalue}`);
        addValuea(avalue);
        event.stopPropagation();
        event.preventDefault();
    });


    $('.btn-subtract').on('click touchstart', function () {
        if (i == 1) {
            $('.pcount').text(1);
            addValuea(1);

        } else {
            const avalue = --i;
            $('.pcount').text(`${avalue}`);
            addValuea(avalue);
        }
        event.stopPropagation();
        event.preventDefault();
    });


    $('.btn-add-c').on('click touchstart', function () {
        const cvalue = ++i;
        $('.ccount').text(`${cvalue}`);
        addValuec(cvalue);
        event.stopPropagation();
        event.preventDefault();
    });


    $('.btn-subtract-c').on('click touchstart', function () {
        if (i == 1) {
            $('.ccount').text(1);
            addValuec(1);
        } else {
            const cvalue = --i;
            $('.ccount').text(`${cvalue}`);
            addValuec(cvalue);
        }
        event.stopPropagation();
        event.preventDefault();
    });


    $('.btn-add-in').on('click touchstart', function () {
        const bvalue = ++i;
        $('.incount').text(`${bvalue}`);
        addValueb(bvalue);
        event.stopPropagation();
        event.preventDefault();
    });


    $('.btn-subtract-in').on('click touchstart', function () {
        if (i == 1) {
            $('.incount').text(1);
            addValueb(1);
        } else {
            const bvalue = --i;
            $('.incount').text(`${bvalue}`);
            addValueb(bvalue);
        }
        event.stopPropagation();
        event.preventDefault();
    });

});

$(document).ready(function () {
    $('.cabin-list button').click(function () {
        event.stopPropagation();
        event.preventDefault();
        $('.cabin-list button.active').removeClass("active");
        $(this).addClass("active");
    });
});