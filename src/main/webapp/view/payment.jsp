<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Payment</title>
</head>
<body>

    <h1>Payment</h1>
    <form id="paymentForm" action="/${userId}/${orderId}/pay" method="POST">
        <input type="hidden" id="userId" name="userId" value="${param.userId}">
        <input type="hidden" id="orderId" name="orderId" value="${param.orderId}">
        <!-- Display order details -->
        <label>Order ID: <span id="orderIdDisplay">${param.orderId}</span></label><br>
        <!-- <label>Amount: $<span id="amountDisplay">${amount}</span></label><br> -->
        <!-- Enter payment amount -->
        <!-- <label for="amount">Enter Amount:</label> -->
        <input type="text" id="amount" name="amount" min="1" value="${amount}" readonly>
        <!-- Pay button -->
        <button type="submit">Pay</button>
    </form>
</body>
</html>
