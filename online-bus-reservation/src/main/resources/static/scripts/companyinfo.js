$(function () {

    loadCompanyDetails();
});

function loadCompanyDetails() {

    $.ajax({
        type: 'GET',
        url: '/info/info/view',
        dataType: 'json',
        contentType: 'application/json',
        success: function (companyInfo) {
            loadDOMformCompanyInfo(companyInfo);
        }
    })
}

function loadDOMformCompanyInfo(companyInfo) {

    let id = companyInfo.id;
    let companyName = companyInfo.companyName;
    let town = companyInfo.town;
    let address = companyInfo.address;
    let phoneNumber = companyInfo.phoneNumber;

    $('#company-details')
        .append($('<tr></tr>')
            .attr('id', 'last-row')
            .attr('itemid', id)
            .css('background-color', 'lightyellow')
            .append($('<td></td>').append($('<span></span>').text(companyName)))
            .append($('<td></td>').append($('<span></span>').text(town)))
            .append($('<td></td>').append($('<span></span>').text(address)))
            .append($('<td></td>').append($('<span></span>').text(phoneNumber)))
            .append($('<td></td>')
                .append($('<button>Edit</button>')
                    .attr('class', 'btn btn-default')
                    .on('click', function () {
                        let currDom = $(this).parent();
                        let itemId = currDom.attr('itemid');
                        $('.table-responsive').remove();
                        goToEditCompanyInfo(id);
                    })
                )
            ))
}

function goToEditCompanyInfo(id) {

    $.ajax({
        type: 'GET',
        url: '/info/info/edit/' + id,
        dataType: 'json',
        contentType: 'application/json',
        success: function (infoEditModel) {
            loadEditForm(infoEditModel);
        }
    })
}

function loadEditForm(infoEditModel) {

    let id = infoEditModel.id;
    let companyName = infoEditModel.companyName;
    let town = infoEditModel.town;
    let address = infoEditModel.address;
    let phoneNumber = infoEditModel.phoneNumber;
    $('#size-change').attr('class', 'col-lg-6 col-sm-8 col-xs-12');

    $('.jumbotron')
        .css('border-radius', '0')
        .append($('<div></div>')
            .addClass('container')
            .append($('<div></div>')
                .addClass('row')
                .append($('<div></div>')
                    .append($('<div></div>')
                        .attr('class', 'form-group row')
                        .append($('<h2></h2>')
                            .addClass('display-3')
                            .text('Edit Company Info')))
                    .append($('<br/>'))
                    .append($('<form></form>')
                        // .attr('method', 'post')
                        .append($('<div></div>')
                            .attr('class', 'form-group row required')
                            .append($('<label></label>')
                                .attr('class', 'form-control-label')
                                .attr('for', 'companyName')
                                .text('Company Name'))
                            .append($('<input/>')
                                .addClass('form-control')
                                .attr('type', 'text')
                                .attr('id', 'companyName')
                                .val(companyName))
                        )
                        .append($('<div></div>')
                            .attr('class', 'form-group row required')
                            .append($('<label></label>')
                                .attr('class', 'form-control-label')
                                .attr('for', 'address')
                                .text('Address'))
                            .append($('<input/>')
                                .addClass('form-control')
                                .attr('type', 'text')
                                .attr('id', 'address')
                                .val(address))
                        )
                        .append($('<div></div>')
                                .attr('class', 'form-group row required')
                                .append($('<label></label>')
                                    .attr('class', 'form-control-label')
                                    .attr('for', 'town')
                                    .text('Town'))
                                .append($('<input/>')
                                    .addClass('form-control')
                                    .attr('type', 'text')
                                    .attr('id', 'town')
                                    .val(town))
                        )
                        .append($('<div></div>')
                                .attr('class', 'form-group row required')
                                .append($('<label></label>')
                                    .attr('class', 'form-control-label')
                                    .attr('for', 'phoneNumber')
                                    .text('Phone Number'))
                                .append($('<input/>')
                                    .addClass('form-control')
                                    .attr('type', 'text')
                                    .attr('id', 'phoneNumber')
                                    .val(phoneNumber))
                        )
                        .append($('<div></div>')
                            .attr('class', 'form-group row')
                            .append($('<button>Edit</button>')
                                .addClass('btn btn-danger')
                                .on('click', function () {
                                     changeCompanyInfo(id);
                                })
                            )
                            .append($('<button>Cancel</button>')
                                .addClass('btn btn-default')
                                .css('margin-left', '5px')
                                .on('click', function () {
                                    window.location = '/info/info'
                                })
                            )
                        )
                    )
                )
            )
        )
}

function changeCompanyInfo(id) {

    let companyInfo = {};
    companyInfo.id = id;
    companyInfo.companyName = $('#companyName').val();
    companyInfo.town = $('#town').val();
    companyInfo.address = $('#address').val();
    companyInfo.phoneNumber = $('#phoneNumber').val();

    $.ajax({
        type: 'POST',
        url: '/info/info/edit',
        data: JSON.stringify(companyInfo),
        contentType: 'application/json',
        dateType: 'json',
        success: function () {
            window.location = '/info/info'
        },
        error: function (jqXHR) {

            let errors = jQuery.parseJSON(jqXHR.responseText);
            let arr = [];
            let c = 0;

            $.each(errors, function (i, error) {

                // alert(error.indexOf(",") >= 0);
                // if (error.indexOf(',') >= 0) {

                    // arr = String(error).split(',');
                    // arr.push(e);
                    // alert(e);
                // }

                if (c > 0) {
                    return
                }

                alert(error.errorMessage);
                c++;
            });

            //
            // for(let i = 0; i < arr.length; i++) {
            //     alert(arr[i]);
            // }
        }
    })
}