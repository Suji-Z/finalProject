$('.nav-link').on('click',(e)=> {

    $.ajax({
        url: '/view',
        type: 'GET',
        data: {
            keyword: e.target.value,
        },
        success : function(data) {

            $('#viewTable').replaceWith(data);

        },
        error: function(xhr, status, error) {
            alert('error');
        }
    })


});


