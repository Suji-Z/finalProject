
$(function () {
    $('.btn-add,.btn-subtract').on('click touchstart', function () {

        const qadult = $('#f-qadult').val();
        const qchild = $('#f-qchild').val();
        const qinfant = $('#f-qinfant').val();

        $('.qstring').text(` ${qadult} Adults - ${qchild} Childs - ${qinfant} Infants`);
        event.stopPropagation();
        event.preventDefault();
    });
});

$(function () {
    function addValue() {
        const sum = i + j + k;
        $('.final-count').text(`${sum}`);
    }

    var i = 0;
    var j = 0;
    var k = 0;

    $('.btn-add').on('click touchstart', function () {
        const value = ++i;
        $('.pcount').text(`${value}`);
        addValue();
        event.stopPropagation();
        event.preventDefault();
    });


    $('.btn-subtract').on('click touchstart', function () {
        if (i == 0) {
            $('.pcount').text(0);
            addValue();
        } else {
            const value = --i;
            $('.pcount').text(`${value}`);
            addValue();
        }
        event.stopPropagation();
        event.preventDefault();
    });


    $('.btn-add-c').on('click touchstart', function () {
        const value = ++j;
        $('.ccount').text(`${value}`);
        addValue();
        event.stopPropagation();
        event.preventDefault();
    });


    $('.btn-subtract-c').on('click touchstart', function () {
        if (j == 0) {
            $('.ccount').text(0);
             addValue();
        } else {
            const value = --j;
            $('.ccount').text(`${value}`);
            addValue();
        }
        event.stopPropagation();
        event.preventDefault();
    });


    $('.btn-add-in').on('click touchstart', function () {
        const value = ++k;
        $('.incount').text(`${value}`);
        addValue();
        event.stopPropagation();
        event.preventDefault();
    });


    $('.btn-subtract-in').on('click touchstart', function () {
        if (k == 0) {
            $('.incount').text(0);
             addValue();
        } else {
            const value = --k;
            $('.incount').text(`${value}`);
            addValue();
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