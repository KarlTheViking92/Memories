<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Lobby</title>
<jsp:include page="../resources/header.jsp" />
</head>
<body>
	<jsp:include page="navbar.jsp" />
	<div class="page-title">
		<h1>LOBBIES</h1>
	</div>
	<c:forEach items="${lobbies}" var="lobby">
		<div class="product-preview-container">
			<ul>
				<li>Lobby: ${lobby.name}</li>
				<li>Size: ${lobby.players.size()}</li>		
				<li>Players:		
					<c:forEach items="${lobby.players}" var="player">
					user: ${player.username}
					</c:forEach>
					<!-- NOT SURE ON THIS IF -->
					<c:if test="${lobby.alreadyInLobby(user) eq true}"> <!-- se sei il creatore elimina -->
					<li><a href="${pageContext.request.contextPath}/leaveLobby?lobby=${lobby.name}">
						Leave lobby</a></li> 
					</c:if>
					
				</li>
				<c:if test="${lobby.alreadyInLobby(user) eq false}">
				<li><a href="${pageContext.request.contextPath}/joinLobby?lobby=${lobby.name}">
					Join Lobby</a></li>
				</c:if>
				
				<c:if test="${lobby.players.size() gt 1  and lobby.creatorLobby(user) eq true }"> <!--  user.isCreator() eq true -->
				<li><a href="${pageContext.request.contextPath}/startGame">
					Start Game</a></li> 
				</c:if>
			</ul> 
		</div>
	</c:forEach>
	
	<c:if test="${not empty errorLobby}">
		<div class="alert alert-danger alert-dismissible" role="alert">
			<button type="button" class="close" data-dismiss="alert"
				aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
			<strong>${errorLobby}</strong>
		</div>
	</c:if>
	<div class="login-container">
		<h1>Create new Lobby</h1>
		<br>
		<form method="POST" action="createLobby"> <!-- ANd JOIN -->
			<table>
				<tr>
					<td>Name</td>
					<td><input name="name" /></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td><input type="submit" value="Create" /></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>