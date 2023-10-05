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
	<h1>merchants</h1>
	<table border="1">
		<thead>
			<tr>
				<th>Id</th>
				<th>Merchant name</th>
				<th>Email</th>
				<th>Mobile</th>
				<th>Gender</th>
				<th>Date of birth</th>
				<th> Status</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="merchant" items="${list}">
				<tr>
				<td>${merchant.getId()}</td>
				<td>${merchant.getName()}</td>
				<td>${merchant.getEmail()}</td>
				<td>${merchant.getMobile()}</td>
				<td>${merchant.getGender()}</td>
				<td>${merchant.getDob()}</td>
				<td>${merchant.isStatus()}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<br />
	<a href="/admin/home"><button>Back</button></a>

</body>
</html>