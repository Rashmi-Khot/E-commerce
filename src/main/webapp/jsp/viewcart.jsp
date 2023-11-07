<%@page import="org.apache.commons.codec.binary.Base64"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h1 style="color: green">${pos}</h1>
	<h1 style="color: red">${neg}</h1>
	<h1>cart</h1>
	<table border="1">
		<thead>
			<tr>
				<th>Product Name</th>
				<th>Picture</th>
				<th>Category</th>
				<th>Price</th>
				<th>quantity</th>
				<th>Total</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="product" items="${items}">
				<tr>
					<td>${product.getName()}</td>
					<td>
					<img class="product-image" alt="Product Image"
							src="data:image/jpeg;base64,${Base64.encodeBase64String(product.getPicture())}">
					</td>
					<td>${product.getCategory()}</td>
					<td>${product.getPrice()/product.getQuantity()}</td>
						<td>${product.getQuantity()}</td>
					<td>${product.getPrice()}&#8377</td>
					</tr>
			</c:forEach>
			<tr>
				<th colspan="5">Total Price</th>
				<td>${details.getAmount()/100}&#8377</td>
				</tr>
		</tbody>
	</table>
	<br />
	<a href="/" style="text-decoration: none;"><button id="rzp-button1" style="width: 3.5%;height: 125%;position: relative;font-weight: 700;border-radius: 5px;">Pay</button></a>
	
	<a href="/customer/fetch-products"><button>Back</button></a>

<script src="https://checkout.razorpay.com/v1/checkout.js"></script>
<script>
var options = {
    "key": "${details.getKeyDetails()}", // Enter the Key ID generated from the Dashboard
    "amount": "${details.getAmount()}", // Amount is in currency subunits. Default currency is INR. Hence, 50000 refers to 50000 paise
    "currency": "${details.getCurrency()}",
    "name": "shopping cart", //your business name
    "description": "Test Transaction",
    "image": "https://example.com/your_logo",
    "order_id": "${details.getOrderId()}", //This is a sample Order ID. Pass the `id` obtained in the response of Step 1
    "callback_url": "/customer/payment/${details.getId()}",
    "prefill": { //We recommend using the prefill parameter to auto-fill customer's contact information especially their phone number
        "name": "${customer.getName()}", //your customer's name
        "email": "${customer.getEmail()}",
        "contact": "+91${customer.getMobile()}" //Provide the customer's phone number for better conversion rates 
    },
    "notes": {
        "address": "Razorpay Corporate Office"
    },
    "theme": {
        "color": "#3399cc"
    }
};
var rzp1 = new Razorpay(options);
document.getElementById('rzp-button1').onclick = function(e){
    rzp1.open();
    e.preventDefault();
}
</script>
</body>
</html>