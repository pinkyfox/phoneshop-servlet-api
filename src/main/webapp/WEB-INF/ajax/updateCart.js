let productsHash = new Map();
$(document).on("click", "#js-submitButton", updateCart);


function updateCart() {
    let productsQntity = document.getElementsByClassName('quantity');
    let productsId = document.getElementsByClassName('productId');
    [...productsId].forEach((id, i) => {
        productsHash.set(id.innerHTML, productsQntity[i].value);
    });
    let response = fetch('http://localhost:8080/phoneshop-servlet-api/cart', {
        method: 'POST',
        headers: {
            'Content-Type':'application/x-www-form-urlencoded'
        },
        body: 'cartUpdateValues=' + JSON.stringify([...productsHash])
    }).then(function(response) {
        response.text().then(function(text) {
            $("html").html(text);
        });
    });
}

