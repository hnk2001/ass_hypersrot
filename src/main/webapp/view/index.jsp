<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Shopping App</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
    <h1>Welcome to Shopping App</h1>
    
    <!-- Fetch coupons on page load -->
    <script>
        $(document).ready(function() {
            $.get("/fetch-Coupons", function(coupons) {
                coupons.forEach(function(coupon) {
                    // Display coupon codes as options
                    $("#coupons").append($("<option>").text(coupon.code));
                });
            });
            
            // Fetch inventory
            $.get("/inventory", function(product) {
                // Display product details
                $("#product-details").html("Product Price: $" + product.price);
            });
        });
    </script>

    <form action="/placeOrder/1" method="POST">
        <!-- Enter userId -->
        <label for="userId">User ID:</label>
        <input type="text" id="userId" name="userId">
        <br>
        
        <!-- Select coupon -->
        <label for="coupon">Select Coupon:</label>
        <select id="coupons" name="coupon"></select>
        <p>${couponAppliedError}</p>

        <!-- Enter quantity -->
        <label for="qty">Quantity:</label>
        <input type="number" id="qty" name="qty" min="1">
        <p>${qtyError}</p>

        <!-- Place order button -->
        <button type="submit">Place Order</button>
    </form>

    <div id="product-details"></div>
</body>
</html>
