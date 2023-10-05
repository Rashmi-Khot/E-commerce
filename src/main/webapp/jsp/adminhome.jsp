<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h1>this is adminhome page</h1>
<h1 style="color:green">${pos}</h1>
<h1 style="color:red">${neg}</h1>
<a href="/admin/fetch-products"><button>Approve product</button></a>
<a href="/admin/fetch-merchants"><button>View merchant</button></a>
<a href="/admin/fetch-customers"><button>View customer</button></a>
<a href="/logout"><button>logout</button></a>
</body>
</html>