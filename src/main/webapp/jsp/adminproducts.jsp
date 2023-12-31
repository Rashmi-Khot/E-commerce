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
	<h1>adminproducts</h1>
	<table border="1">
		<thead>
			<tr>
				<th>Product Name</th>
				<th>Picture</th>
				<th>Category</th>
				<th>Price</th>
				<th>Stock</th>
				<th>Status</th>
				<th>Change Status</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="product" items="${list}">
				<tr>
					<td>${product.getName()}</td>
					<td>
					<c:set var="base64"
							value="${Base64.encodeBase64String(product.getPicture())}"></c:set>
				    <img
						height="100px" width="100px" alt="unknown"
						src="data:image/jpeg;base64,${base64}">
					</td>
					<td>${product.getCategory()}</td>
					<td>${product.getPrice()}</td>
					<td>${product.getStock()}</td>
					<td>
					${product.isApproved()}
					</td>
					<td><a href="/admin/change-status/${product.getId()}"><button>Change</button></a>
					</tr>
			</c:forEach>
		</tbody>
	</table>
	<br />
	<a href="/admin/home"><button>Back</button></a>

</body>
</html>