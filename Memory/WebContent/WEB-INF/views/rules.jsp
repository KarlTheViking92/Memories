<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Rules</title>
<jsp:include page="../resources/header.jsp" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/rules.css"
	type="text/css">
</head>
<body>
	<jsp:include page="navbar.jsp" />
	<div id="one">
	<h1 class="text-center">THE RULES FOR PLAYING "MEMORY"</h1>
	<ul>
		<li>The cards are mixed.</li>
		<li>The cards are placed in rows, face down.</li>
		<li>Turn over any two cards.</li>
		<li>If the two cards match, the cards remain face up.</li>
		<li>If they don't match, the cards are put face down.</li>
		<li>The game is over when all the cards have been matched.</li>
		<li>The player who completes the game in the least time wins.</li>
	</ul>
	</div>
</body>
</html>