<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Delete confirmation - ntukhpi</title>
</head>
<body>
	<center>
		<caption>
			<br>
			<h2>Delete Institute (MySQL+HBN)</h2>
		</caption>
		<br>
		<p style="color: red;">${errorString}</p>
		<br>
		<br>
		<p style="color: blue;">${textToConfirm}</p>
        <form action="institute_delete" method="POST">
		    <input type="submit" value="Delete">
		    <a href="institutes">To Institutes</a>
		    <input type="hidden" name="id" value="${id}">
		</form>
	</center>
</body>
</html>