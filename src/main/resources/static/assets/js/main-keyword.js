$('.nav-link').on('click',(e)=>{
        console.log(e.target.value);


    console.log("찍히나요")

    $.ajax({
        url: '/',
        type: 'GET',
        data: {
            'keyword':  e.target.value,
        },

    }).done(function(result){

        alert("다녀옴");

        result = $('#keyword').target.value;


    }).fail(function(error){
        alert(JSON.stringify(error));
    })

     });


