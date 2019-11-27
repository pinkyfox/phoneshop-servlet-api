$(document).on("click", ".deleteBtn", function deleteCartItem() {
    setTimeout(deleteCartItem, 5000000);
    let response = fetch('http://localhost:8080/phoneshop-servlet-api/cart/deleteCartItem', {
        method: 'POST',
        headers: {
            'Content-Type':'application/x-www-form-urlencoded'
        },
        body: 'productId=' + $(this).val()
    }).then(function(response) {
        response.text().then(function(text) {
            $("html").html(text);
            $(".deleteLabel").hide(3200);
        });
    });
});