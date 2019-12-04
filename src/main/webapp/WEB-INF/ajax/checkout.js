$(document).on("click", "#js-checkout", checkout);

function checkout() {
    updateCart();
    const cartErrors = document.getElementById('js-cartErrors').innerText;
    debugger;
    if (cartErrors === 'false') {
        var req = new XMLHttpRequest();
        req.open("GET", "http://localhost:8080/phoneshop-servlet-api/checkout", false);
        req.onload = function (){
            $("html").html(req.responseText);
        }
        req.send(null)
    }
};