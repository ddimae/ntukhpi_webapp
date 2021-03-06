<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Institutes - ntukhpi</title>
</head>
<body>
	<center>
		<caption>
			<br>
			<h2>Institutes list (MySQL+HBN)</h2>
		</caption>
		<a href=".">to Start page</a> <br> <br>
		<table border="0">
			<tr>
				<td>
					<form action="institutes" method="GET">
						<input type="hidden" name="sortInst" value="1"> <input
							type="submit" value="Sort by Cod">
					</form>
				</td>
				<td>
					<form action="institutes" method="GET">
						<input type="hidden" name="sortInst" value="2"> <input
							type="submit" value="Sort by Name">
					</form>
				</td>
				<td>
					<form action="institutes" method="GET">
						<input type="hidden" name="sortInst" value="3"> <input
							type="submit" value="Sort by Date Create">
					</form>
				</td>
				<td>
					<form action="institute" method="GET">
						<input type="hidden" name="id" value="-1"> <input
							type="submit" value="New institute">
					</form>
				</td>
			</tr>
		</table>
		<br>
		<table border="2">
			<tr>
				<th>Назва</th>
				<th>Скорочення</th>
				<th>Код</th>
				<th>Дата створення</th>
				<th colspan="3">Дії</th>
			</tr>
			<c:forEach var="inst" items="${institutes}">
				<tr>
					<td><c:out value="${inst.nameInstitute}" /></td>
					<td><c:out value="${inst.shortNameInstitute}" /></td>
					<td><c:out value="${inst.codInstitute}" /></td>
					<td><c:out value="${inst.yearCreate}" /></td>
					<td><form method="get" action="institute">
							<input type="hidden" name="id" value="${inst.idInst}"> <input
								type="submit" value="Змінити">
						</form></td>
					<td><form method="get" action="institute_delete">
							<input type="hidden" name="id" value="${inst.idInst}"> <input
								type="submit" value="Вилучити">
						</form></td>
					<c:if test="${not empty inst.depsInst}">
						<td><form method="get" action="departments">
								<input type="hidden" name="send_select" value="${inst.shortNameInstitute}"> <input
									type="submit" value="Кафедри">
							</form></td>
					</c:if>
				</tr>
			</c:forEach>
		</table>
	</center>
</body>
<%-- <footer>
	<%@ include file="Footer2.jsp"%>
</footer> --%>

</html>