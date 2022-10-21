function likeBtn(){

    var reviewName = $('#reviewId').val();
    var replyName = $('#replyId').val();

    console.log(replyName);

    $.ajax({
        url: reviewName+'/?replyName='+replyName,
        type: 'GET',
        data: {
            replyLike : true
        },

        success: function (data) {
            $('#likeOff').hide();
            $('#likeOn').show();



        },
        error: function (error) {
            alert("실패")
        }
    });

};

function dislikeBtn(){

    var reviewName = $('#reviewId').val();
    var replyName = $('#replyId').val();

    console.log(replyName);

    $.ajax({
        url: reviewName+'?replyName='+replyName,
        type: 'GET',
        data: {
            replyLike : false
        },
        success: function (data) {


            $('#likeOn').hide();
            $('#likeOff').show();

        },
        error: function (error) {
            alert("실패")
        }
    });

};