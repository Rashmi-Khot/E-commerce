<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h1>merchant home</h1>
<h1 style="color:green">${pos}</h1>
<h1 style="color:red">${neg}</h1>

<a href="/merchant/add-product"><button>Add product</button></a>
<a href="/merchant/fetch-products"><button>fetch product</button></a>
<a href="/logout"><button>Logout</button></a>

</body>
</html>