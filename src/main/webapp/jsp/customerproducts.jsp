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
	<h1>customer products</h1>
	<table border="1">
		<thead>
			<tr>
				<th>Product Name</th>
				<th>Picture</th>
				<th>Category</th>
				<th>Price</th>
				<th>Stock</th>
				<th>remove</th>
				<th>quantity</th>
				<th>Add</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="product" items="${list}">
				<tr>
					<td>${product.getName()}</td>
					<td>
					<img class="product-image" alt="Product Image"
							src="data:image/jpeg;base64,${Base64.encodeBase64String(product.getPicture())}">
					</td>
					<td>${product.getCategory()}</td>
					<td>${product.getPrice()}</td>
					<td>${product.getStock()}</td>
					<th><a href="/customer/cart-remove/${product.getId()}"><button>-</button></a></th>
					<td>
						<c:if test="${cartitems==null}">
						0
						</c:if> 
						<c:if test="${cartitems!=null}">
						<c:set var="flag" value="true"></c:set>
						<c:forEach var="item" items="${cartitems}">
						<c:if test="${item.getName().equals(product.getName())}">
						${item.getQuantity()}
						<c:set var="flag" value="false"></c:set>
						</c:if>
						</c:forEach>
						<c:if test="${flag==true}">
						0
						</c:if>
						</c:if>
						</td>
					<th><a href="/customer/cart-add/${product.getId()}"><button>+</button></a></th>
					
					
					</tr>
			</c:forEach>
		</tbody>
	</table>
	<br />
	<a href="/customer/cart-view"><button>view cart</button></a>
	<a href="/customer/home"><button>Back</button></a>

</body>
</html>