$(function () {

    loadFeedbacks();
});

function loadFeedbacks() {

    $.ajax({
        type: 'GET',
        url: '/feedback/feedbacks/all',
        dataType: 'json',
        contentType: 'application/json',
        success: function (feedbacks) {
            loadDOMformFeedbacks(feedbacks);
        }
    })
}

function loadDOMformFeedbacks(feedbacks) {

    $.each(feedbacks, function (i, feedback) {

        let id = feedback.id;
        let username = feedback.user.username;
        let msg = cutMessageLength(feedback.message);
        let date = formatDate(feedback.date);
        let isAnswered = feedback.isAnswered;

        $('#feedback-details')
            .append($('<tr></tr>')
                .attr('itemid', id)
                .css('background-color', 'lightyellow')
                .append($('<td></td>').append($('<span></span>').append($(isAnswered === false ? '<b></b>' : '<h5></h5>').text(username))))
                .append($('<td></td>').append($('<span></span>').append($(isAnswered === false ? '<b></b>' : '<h5></h5>').text(date))))
                .append($('<td></td>').append($('<span></span>').append($(isAnswered === false ? '<b></b>' : '<h5></h5>').text(msg))))
                .append($('<td></td>')
                    .append($('<button>Delete</button>')
                        .addClass('btn btn-danger')
                        .css('margin-right', '5px'))
                    .on('click', function () {
                        let currDom = $(this).parent();
                        let itemId = currDom.attr('itemid');
                        currDom.remove();
                        deleteFeedback(itemId);
                    }))
                .append($('<td></td>')
                    .append($(isAnswered === false ? '<button>Reply</button>' : '')
                        .addClass('btn btn-success')
                        .on('click', function () {
                            let currDom = $(this).parent();
                            let itemId = currDom.attr('itemid');
                            $('.table-responsive').remove();
                            goToReply(id);
                        })))
            )
    });
}

function cutMessageLength(msg) {

    if (msg.length > 35) {

        return msg.substring(0, 35) + '...';
    }

    return msg;
}

function formatDate(date) {

    let d = new Date(date);
    let day = d.getDate();
    let month = d.getMonth() + 1;
    let year = d.getFullYear();
    return year + '/' + month + '/' + day;
}

function deleteFeedback(id) {

    $.ajax({
        type: 'DELETE',
        url: '/feedback/feedbacks/delete/' + id
    })
}

function goToReply(itemId) {

    $.ajax({
        type: 'GET',
        url: '/feedback/feedbacks/reply/' + itemId,
        dataType: 'json',
        contentType: 'application/json',
        success: function (feedback) {
            loadReplyForm(feedback);
        }
    })
}

function loadReplyForm(feedback) {

    let feedbackId = feedback.id;
    let username = feedback.user.username;
    let userId = feedback.user.id;
    let date = formatDate(feedback.date);
    let message = feedback.message;
    let isAnswered = feedback.isAnswered;
    $('#size-change').attr('class', 'col-lg-6 col-sm-8 col-xs-12');

    $('.jumbotron')
        .css('background-color', 'white')
        .append($('<label></label>')
            .addClass('form-control-label')
            .css('padding-right', '5px')
            .text('Message details: '))
        .append($('<div></div>')
            .append($('<h5></h5>')
                .text('User: ' + username))
            .append($('<h5></h5>')
                .text('Date: ' + date))
            .append($('<h5></h5>')
                .text('Message: ' + message))
            .append($('<h5></h5>')
                .text('Replied: ' + isAnswered)))
        .append($('<br/>'))
        .append($('<form></form>')
            .attr('method', 'post'))
        .append($('<div></div>')
            .append($('<input/>')
                .addClass('form-control-label')
                .attr('type', 'number')
                .attr('id', 'user')
                .attr('hidden', 'hidden')
                .val(userId)))
        .append($('<div></div>')
            .addClass('form-group row')
            .append($('<label></label>')
                .addClass('form-control-label')
                .css('padding-right', '5px')
                .text('Reply to user: '))
            .append($('<textarea></textarea>')
                .css('height', '150px')
                .attr('id', 'message')
                .attr('type', 'text')
                .addClass('form-control')))
        .append($('<div></div>')
            .addClass('form-group row')
            .append($('<button>Send</button>')
                .addClass('btn btn-primary')
                .css('margin-right', '5px')
                .on('click', function () {
                    sendMessage(feedbackId);
                }))
            .append($('<button>Cancel</button>')
                .addClass('btn btn-default')
                .css('margin-right', '5px')
                .on('click', function () {
                    window.location = '/feedback/feedbacks'
                })))
}

function sendMessage(feedbackId) {

    let messageToSend = {};
    messageToSend.userId = $('#user').val();
    messageToSend.feedbackId = feedbackId;
    messageToSend.message = $('#message').val();

    $.ajax({
        type: 'POST',
        url: '/feedback/feedbacks/send',
        data: JSON.stringify(messageToSend),
        contentType: 'application/json',
        dateType: 'json',
        success: function () {
            window.location = '/feedback/feedbacks'
        }
    })
}