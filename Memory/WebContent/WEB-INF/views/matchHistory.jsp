<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Match History</title>
<jsp:include page="../resources/header.jsp" />
<script>
	function getEventsFromServer() {
		$.ajax({
			url : "getEvents",
			success : function(result) {
				$("#message").html(result);
				getEventsFromServer();
			},
			error : function() {
				//call events again after some time
				setTimeout(function() {
					getEventsFromServer();
				}, 5000);
			}
		});
	}
	$(document).ready(getEventsFromServer());
</script>
</head>
<body>
	<jsp:include page="navbar.jsp" />
	<h1>Match History</h1>
	
		<h2>Server async message</h2>
		<h2 id="message"></h2>
		<h2>Lobbies</h2>
		<h2 id="message"></h2>
		
		<c:forEach items="${lobbies}" var="lobby">
		<div class="product-preview-container" id="message">
			<ul>
				<li>Lobby: ${lobby.name}</li>
				<li>Size: ${lobby.players.size()}</li>		
				<li>Players:		
				<c:forEach items="${lobby.players}" var="player">
				 ${player.username}
				</c:forEach>
				</li>
				<li><a href="${pageContext.request.contextPath}/joinLobby?lobby=${lobby.name}">
					Join Lobby</a></li>
				<c:if test="${lobby.players.size() gt 1}">
				<li><a href="${pageContext.request.contextPath}/leaveLobby?lobby=${lobby.name}">
					Leave lobby</a></li> 
				</c:if>
				<c:if test="${lobby.players.size() gt 1  }"> <!-- and player.creator -->
				<li><a href="${pageContext.request.contextPath}/startGame">
					Start Game</a></li> 
				</c:if>
			</ul> 
		</div>
	</c:forEach>
	<div class="product-preview-container" id="message">
	${userName1}
	</div>
</body>
</html>