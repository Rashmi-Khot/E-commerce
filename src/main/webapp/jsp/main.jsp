
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<style type="text/css">
 body{
 margin: 0;
 padding: 0;
 }
 h2,h2{
 margin: 0;
 padding: 0;
 }
 
 body {
	font-family: Arial, sans-serif;
}
 
 header{
 background-color: #007BFF;
 padding: 10px 0;
 text-align: center;
 }
 .container{
 max-width: 800px;
 margin: 0 auto;
 padding: 20px;
 box-shadow: 0 0 10px rgba(0,0,0,0.1);
 border-radius: 5px;
 margin-top: 20px;
 }
 h2 {
	color: red;
	font-size: 28px;
}

h3 {
	color: green;
	font-size: 28px;
}
.abc {
	display: inline-block;
	padding: 10px 20px;
	background-color: #007BFF;
	color: #fff;
	text-decoration: none;
	border-radius: 5px;
	font-weight: bold;
	transition: background-color 0.3s ease;
}
.btn:hover {
	background-color: #0056b3;
}

</style>
</head>
<body>
<header>
<h1>this is home page</h1>
</header>
<div class="container">
<h1>${pos}</h1>
<h3>${neg}</h3>
<a href="/admin" class="abc"><button>admin</button></a>
<a href="/merchant" class="abc"><button>Merchant</button></a>
<a href="/customer" class="abc"><button>Customer</button></a>
</div>


</body>
</html>