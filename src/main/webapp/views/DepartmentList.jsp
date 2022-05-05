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
		<a href=".">to Start page</a> <a href="institutes">to Institute page</a><br> <br>
		<form action="departments" method="GET">
			<input type="submit" value="Select by institute"> 
			<select name="send_select">
				<option>any</option>
				<c:forEach var="inst" items="${institutes_list}">
					<c:choose>
						<c:when test="${inst != inst_select}">
							<option>${inst}</option>
						</c:when>
						<c:otherwise>
							<option selected>${inst}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select>
		</form>
		<br> <br>
		<form action="department" method="GET">
			<input type="hidden" name="id" value="-1">
			<input type="hidden" name="send_select" value="${inst_select}"> 
			<input type="submit" value="New department">
		</form>
		<table border='2'>
			<tr>
				<th>Назва</th>
				<th>Дата створення</th>
				<th>Скорочення</th>
				<th>Код</th>
				<th>У складі інституту</th>
				<th colspan="2">Дії</th>
			</tr>
			<c:forEach var="dep" items="${departments}">
				<tr>
					<td><c:out value="${dep.nameDepartment}" /></td>
					<td><c:out value="${dep.yearCreate}" /></td>
					<td><c:out value="${dep.shortNameDepartment}" /></td>
					<td><c:out value="${dep.codDepartment}" /></td>
					<td><c:out
							value="${dep.getLastInstitute().getKey().shortNameInstitute}" /></td>
					<td>
						<form method="get" action="department">
							<input type="hidden" name="id" value="${dep.idDep}"> <input
								type="submit" value="Змінити">
						</form>
					</td>
					<td>
						<form method="get" action="department_delete">
							<input type="hidden" name="id" value="${dep.idDep}"> <input
								type="submit" value="Вилучити">
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