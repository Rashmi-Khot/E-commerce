<%@page import="org.apache.commons.codec.binary.Base64"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ taglib prefix="mvc" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h1 style="color:green">${pos}</h1>
<h1 style="color:red">${neg}</h1>
<h1>edit product</h1>
<mvc:form action="/merchant/update-product" method="post" modelAttribute="product" enctype="multipart/form-data">
 <mvc:hidden path="id"/> 
 <table>

 <tr>
	<th>Name:</th>
	<th><mvc:input path="name"/></th>
	<th><mvc:errors path="name"/></th>
</tr>
<tr>
	<th>stock:</th>
	<th><mvc:input path="stock"/></th>
	<th><mvc:errors path="stock" /></th>
</tr>
<tr>
	<th>price:</th>
	<th><mvc:input path="price"/></th>
	<th><mvc:errors path="price" /></th>
</tr>
<tr>
	<th>category:</th>
	<th>
	<mvc:select path="category">
	<mvc:option value="">select one option</mvc:option>
	<mvc:option value="clothing">Clothing</mvc:option>
	<mvc:option value="electronics">Electronics</mvc:option>
	<mvc:option value="provisions">Provisions</mvc:option>
	</mvc:select></th> 
	<th><mvc:errors path="category"/></th>
</tr>
<tr>
	<th>picture:</th>
	<th>
	         <c:set var="base64"
			value="${Base64.encodeBase64String(product.getPicture())}"></c:set>
		    <img
						height="50px" width="50px" alt="unknown"
						src="data:image/jpeg;base64,${base64}">
	<input type="file" name="pic">
	</th>
	<th></th>

	</tr>
	<tr>
	 <th><button>update</button></th>
	 <th><button>cancel</button></th>
	</tr>
</table>

</mvc:form>
<br>
<a href="/merchant/home"><button>Back</button></a>


</body>
</html>