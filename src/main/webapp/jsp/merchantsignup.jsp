<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="x" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
${neg}
	<h1>merchant signup</h1>
	<h1 style="color:green">${pos}</h1>
<h1 style="color:red">${neg}</h1>
	<x:form action="/merchant/signup" method="post"
		modelAttribute="merchantDto" style="width: 700px ">
		<fieldset>
			<legend>signup here</legend>

			<table>
				<tr>
					<th>Name:</th>
					<th><x:input path="name" required="required" /></th>
					<th><x:errors path="name" /></th>
				</tr>
				<tr>
					<th>Email:</th>
					<th><x:input type="email" path="email" required="required" /></th>
					<th><x:errors path="email" /></th>
				</tr>
				<tr>
					<th>Password:</th>
					<th><x:password path="password" required="required" /></th>
					<th><x:errors path="password" /></th>
				</tr>
				<tr>
					<th>Mobile:</th>
					<th><x:input type="tel" path="mobile" pattern="{0-9}{10}"
							required="required" /></th>
					<th><x:errors path="mobile" /></th>
				</tr>
				<tr>
					<th>DOB:</th>
					<th><x:input type="date" path="dob" required="required" /></th>
					<th><x:errors path="dob" /></th>
				</tr>
				<tr>
					<th>Gender</th>
					<th><x:radiobutton path="gender" value="male" />male</th>
					<th><x:radiobutton path="gender" value="female" />female</th>
					<th><x:errors path="gender" /></th>
				</tr>
				<tr>
					<th><button type="submit">Signup</button></th>
					<th><button type="reset">Cancel</button>
					<th></th>
				</tr>
			</table>
		</fieldset>
	</x:form>
	<br>
	<a href="/merchant"><button>Back</button></a>

</body>
</html>