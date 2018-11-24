<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Create Lobby</title>
<jsp:include page="../resources/header.jsp" />
</head>
<body>
	<jsp:include page="navbar.jsp" />
	<div class="md-form" class="form-control">
		<h1>Create Lobby</h1>
		<br>
		<form method="POST" action="createNewLobby">
			<!-- ANd JOIN -->
			<table>
				<tr>
					<td><input class="form-control form-control-lg" type="text"
						placeholder="Lobby Name" name="name"></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>
						<button class="btn btn-primary mb-0" type="submit">CREATE</button>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<br>
	<c:if test="${not empty createdLobby}">
		<div class="alert alert-danger alert-dismissible" role="alert">
			<button type="button" class="close" data-dismiss="alert"
				aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
			<strong>${createdLobby}</strong>
		</div>
	</c:if>
</body>
</html>