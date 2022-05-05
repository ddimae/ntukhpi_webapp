<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>${titleDepartment} - ntukhpi</title>
</head>
<body>
	<center>
		<caption>
			<br>
			<h2>${titleDepartment} (MySQL+HBN)</h2>
		</caption>
		<br>
		<p style="color: red;">${errorString}</p>
		<br>
		<form action="department" method="POST">
			<p>Name</p>
			<p>
				<input name="nameD" placeholder="Full name department" size="100"
					value="${department.nameDepartment}" required >
			</p>
			<p>Short Name</p>
			<p>
				<input name="nameSD" placeholder="Short name department"
					value="${department.shortNameDepartment}" required >
			</p>
			<p>Cod</p>
			<p>
				<input name="codD" placeholder="Cod department - NNN"
					pattern="[0-9]{3}" value="${department.codDepartment}"  required >
			</p>
			<p>Date of Establishment</p>
			<p>
				<input name="datecreateD" type="date"
					placeholder="Date of department establichment"
					value="${department.yearCreate}">
			</p>
			<br>
			<br>
			<p><b>About Institute in</b></p>
			<p>Shortname of Institute</p>
			<select name="send_select">
				<option>any</option>
				<c:forEach var="inst" items="${institutes_list}">
					<c:choose>
						<c:when test="${inst != nameInstIn}">
							<option>${inst}</option>
						</c:when>
						<c:otherwise>
							<option selected>${inst}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select>
			<p>Date of include in</p>
			<p>
				<input name="dateStartIn" type="date"
					placeholder="Date of department establichment"
					value="${dateStartIn}">
			</p>
			<br><br>
			<input type="submit" value="Save">
			<a href="departments?send_select=${inst_select}">To Departments</a>
			<input type="hidden" name="id" value="${department.idDep}">
		</form>
	</center>

</body>
</html>