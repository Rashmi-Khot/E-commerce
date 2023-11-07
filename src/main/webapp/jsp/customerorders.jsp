<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h1>Customer Orders</h1>
		<h3>${pos}</h3>
		<h2>${neg}</h2>
		<table>
			<thead>
				<tr>
					<th>Order Id</th>
					<th>Time</th>
					<th>Payment Id</th>
					<th>Amount</th>
					<th>Items Ordered</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="order" items="${orders}">
					<tr>
						<td>${order.getId()}</td>
						<td>${order.getDateTime()}</td>
						<td>${order.getPayment_id()}</td>
						<td>${order.getPrice()}</td>
						<td>
						<c:forEach var="product" items="${order.getCustomerProducts()}">
						|${product.getName()}-${product.getQuantity()}*${product.getPrice()/product.getQuantity()}=${product.getPrice()}|<br>
						</c:forEach>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

		
			<a href="/customer/home"><button>Back</button></a>
		
</body>
</html>