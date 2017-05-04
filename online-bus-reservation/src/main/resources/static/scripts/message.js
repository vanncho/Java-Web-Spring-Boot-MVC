$(function () {

    loadAllUserMessages();
    blinkMessages();
});

function loadAllUserMessages() {

    $.ajax({
        type: 'GET',
        url: '/messages/messages/all',
        dataType: 'json',
        contentType: 'application/json',
        success: function (userMessages) {
            loadDOMMessages(userMessages);
        }
    })
}

function loadDOMMessages(userMessages) {

    $.each(userMessages, function (i, userMessage) {

        let id = userMessage.id;
        let date = formatDate(userMessage.date);
        let feedback = cutFeedbackLength(userMessage.feedbackMessage.message);
        let msg = cutMessageLength(userMessage.message);
        let isRead = userMessage.isRead;

        $('#message-details')
            .append($('<tr></tr>')
                .attr('id', 'last-row')
                .attr('itemid', id)
                .css('background-color', 'lightyellow')
                .append($('<td></td>').append($('<span></span>').append($(isRead === false ? '<b></b>' : '<h5></h5>').text(date))))
                .append($('<td></td>')
                    .append($('<span></span>').append($(isRead === false ? '<b></b>' : '<h5></h5>').text(feedback).css('display', 'inline-block')))
                    .append($('<span></span>').append($(isRead === false ? '<b></b>' : '<h5></h5>').text(msg).css({'font-style' : 'italic', 'display' : 'inline-block'}))))
                .append($('<td></td>')
                    .append($('<button>Read</button>')
                        .attr('class', 'btn btn-info')
                        .on('click', function () {
                            $('#message-details').remove();
                            getFullMessage(id);
                        })))
                .append($('<td></td>')
                    .append($('<button>Delete</button>')
                        .addClass('btn btn-danger')
                        .on('click', function () {
                            let currDom = $(this).parents().eq(1);
                            let itemId = currDom.attr('itemid');
                            currDom.remove();
                            deleteMessage(itemId);
                        })))
            )
    });
}

function cutFeedbackLength(feedback) {

    if (feedback.length > 10) {

        return 'Re: ' + feedback.substring(0, 10) + '...: ';
    }

    return feedback;
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

function blinkMessages() {

    let size = parseInt($('.message-size').text());

    if (size > 0) {
        $('.dropbtn').attr('id', 'blink');
    } else {
        $('.dropbtn').removeAttr('id', 'blink');
    }
}

function deleteMessage(id) {

    $.ajax({
        type: 'DELETE',
        url: '/messages/messages/delete/' + id
    })
}

function updateMessageStatus(id) {

    $.ajax({
        type: 'PUT',
        url: '/messages/messages/update/' + id,
        contentType: 'application/json',
        dateType: 'json',
    })
}

function getFullMessage(id) {

    $.ajax({
        type: 'GET',
        url: '/messages/messages/' + id,
        dataType: 'json',
        contentType: 'application/json',
        success: function (fullMessage) {
            getText(fullMessage);
            updateMessageStatus(id);
        }
    })
}

function getText(message) {

    $('#size-change').attr('class', 'col-lg-6 col-sm-8 col-xs-12');
    $('#infotext').text('Reading message...');

    $('#show-message')
        .append($('<span></span>').text('Feedback: ').css('font-weight', 'bold'))
        .append($('<span></span>').text(message.feedbackMessage.message).css('font-style', 'italic'))
        .append($('<br/>'))
        .append($('<br/>'))
        .append($('<span></span>').text('Admin reply: ').css('font-weight', 'bold'))
        .append($('<span></span>').text(message.message))
        .append($('<br/>'))
        .append($('<br/>'))
        .append($('<button>Back</button>')
            .addClass('btn btn-success')
            .on('click', function () {
                window.location = '/messages/messages'
            }));
}