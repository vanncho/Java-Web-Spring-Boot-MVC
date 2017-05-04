// SUM TOTAL COST OF RESERVED SEATS BY TICKET PRICE

let price = document.getElementById('totalCost').value = 0;

function calc(checkbox) {

    if (checkbox.checked) {

        let a = document.getElementById('ticketPrice').value;
        let intA = parseFloat(a);

        let b = document.getElementById('totalCost').value;
        let intB = parseFloat(b);

        document.getElementById('totalCost').value = (intA + intB).toFixed(2);
    } else {

        let a = document.getElementById('totalCost').value;
        let intA = parseFloat(a);

        let b = document.getElementById('ticketPrice').value;
        let intB = parseFloat(b);

        document.getElementById('totalCost').value = (intA - intB).toFixed(2);
    }
};

function checkDate() {

    let selectedText = document.getElementById('datepicker').value;
    let selectedDate = new Date(selectedText);
    let now = new Date();

    // if (selectedText == "" || selectedText == null) {
    //     document.getElementById('datepicker').setAttribute("style", "background-color: red")
    // }

    if (selectedDate <= now) {
        document.getElementById('datepicker').setAttribute("style", "background-color: red")
    }
};