<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Departments - ntukhpi</title>
</head>
<body>
	<center>
		<caption>
			<br>
			<h2>Department list (MySQL+HBN)</h2>
		</caption>
		<a href=".">to Start page</a>
		<br><br>
		<form action="department" method="GET">
		    <input type="hidden" name="id" value="-1">
			<input type="submit" value="New department">
		</form>
		<table border='2'>
			<tr>
				<th>Назва</th>
				<th>Скорочення</th>
				<th>Код</th>
				<th>Дата створення</th>
				<th>Дії</th>
			</tr>
			<c:forEach var="dep" items="${departments}">
				<tr>
					<td><c:out value="${dep.nameDepartment}" /></td>
					<td><c:out value="${dep.shortNameDepartment}" /></td>
					<td><c:out value="${dep.codDepartment}" /></td>
					<td><c:out value="${dep.yearCreate}" /></td>
					<td><form method="get" action="department">
							<input type="hidden" name="id" value="${dep.idDep}"> 
							<input type="submit" value="Змінити">
						</form>
						<form method="get" action="department_delete">
							<input type="hidden" name="id" value="${dep.idDep}"> 
							<input type="submit" value="Вилучити">
						</form>
					</td>
				</tr>
			</c:forEach>
		</table>
	</center>
</body>
<%-- <footer>
	<%@ include file="Footer2.jsp"%>
</footer> --%>

</html>