<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
 <h1>this is merchant page</h1>
 <h1 style="color:green">${pos}</h1>
<h1 style="color:red">${neg}</h1>
<form action="/merchant/login" method="post">
<fieldset style="width: 50px">
<legend>Login here</legend>
<table>
 <tr>
 <th>email:</th>
 <th><input type="email" name="email"></th>
 </tr>
 <tr>
 <th>password:</th>
 <th><input type="number" name="password"></th>
 </tr>
 <tr>
 <th><button>Login</button></th>
 <th><button type="reset">Cancel</button></th>
 </tr>
 </table>
<th><a href="/merchant/signup">New?click here to signup</a></th>
</fieldset>
</form>
 <br>
 <a href="/"><button>Back</button></a>
</body>
</html>