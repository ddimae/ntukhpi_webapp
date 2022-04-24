<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>${titleInstitute} - ntukhpi</title>
</head>
<body>
	<center>
		<caption>
			<br>
			<h2>${titleInstitute} (MySQL+HBN)</h2>
		</caption>
		<br>
		<p style="color: red;">${errorString}</p>
		<br>
		<form action="institute" method="POST">
			<p>Name</p>
			<p>
				<input name="nameI" placeholder="Full name institute" size="100"
					value="${institute.nameInstitute}" required >
			</p>
			<p>Short Name</p>
			<p>
				<input name="nameSI" placeholder="Short name institute"
					value="${institute.shortNameInstitute}" required >
			</p>
			<p>Cod</p>
			<p>
				<input name="codI" placeholder="Cod institute - NNN"
					pattern="[0-9]{3}" value="${institute.codInstitute}"  required >
			</p>
			<p>Date of Establishment</p>
			<p>
				<input name="datecreateI" type="date"
					placeholder="Date of institute establichment"
					value="${institute.yearCreate}">
			</p>
			<br> <br> <input type="submit" value="Save"><a
				href="institutes">To Institutes</a>
			<input type="hidden" name="id" value="${institute.idInst}">
		</form>
	</center>

</body>
</html>