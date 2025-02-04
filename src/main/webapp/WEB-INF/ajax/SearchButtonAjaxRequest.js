$("#textBox").keyup(function(event) {
    if (event.keyCode === 13) {
        $("#myButton").click();
    }
});

$(document).on("click", "#myButton", function() {
    $.get("/phoneshop-servlet-api/products?" + $("#MyForm").serialize(), function(responseText) {
        $("body").html(responseText);
        var stateObj = { products : "products" }
        window.history.pushState(stateObj, "", "products?" + $("#MyForm").serialize());
    });
});

$(function() {
    $('a#pasc').click( function() {
         $.get("/phoneshop-servlet-api/products?sort=price&order=asc&" + $("#MyForm").serialize(), function(responseText) {
              $("body").html(responseText);
         });
         var stateObj = { products : "products" }
         window.history.pushState(stateObj, "", "products?sort=price&order=asc&" + $("#MyForm").serialize());
         return false;
    });
});

$(function() {
    $('a#pdesc').click( function() {
         $.get("/phoneshop-servlet-api/products?sort=price&order=desc&" + $("#MyForm").serialize(), function(responseText) {
              $("body").html(responseText);
         });
         var stateObj = { products : "products" }
         window.history.pushState(stateObj, "", "products?sort=price&order=desc&" + $("#MyForm").serialize());
         return false;
    });
});

$(function() {
    $('a#dasc').click( function() {
         $.get("/phoneshop-servlet-api/products?sort=description&order=asc&" + $("#MyForm").serialize(), function(responseText) {
              $("body").html(responseText);
         });
         var stateObj = { products : "products" }
         window.history.pushState(stateObj, "", "products?sort=description&order=asc&" + $("#MyForm").serialize());
         return false;
    });
});

$(function() {
    $('a#ddesc').click( function() {
         $.get("/phoneshop-servlet-api/products?sort=description&order=desc&" + $("#MyForm").serialize(), function(responseText) {
              $("body").html(responseText);
         });
         var stateObj = { products : "products" }
         window.history.pushState(stateObj, "", "products?sort=description&order=desc&" + $("#MyForm").serialize());
         return false;
    });
});
