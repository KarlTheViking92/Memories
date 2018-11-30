<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Memory</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/memory.css"
	type="text/css">
<jsp:include page="../resources/header.jsp" />
<script type="text/javascript" src="resources/js/memory.js"></script>
</head>
<body>
	<jsp:include page="navbar.jsp" />

	<div class="wrap">
		<div class="game">
			<c:set var="count" value="0" scope="page" />
			<c:forEach items="${cards}" var="card">
				<div class="card" data-id="${card.id}" data-counter="${count}">
					<div class="inside">

						<div class="front">
							<img src="${card.img}" alt="${card.name}" />
						</div>

						<div class="back">
							<img
								src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/74196/codepen-logo.png"
								alt="Codepen" />
						</div>
					</div>
				</div>
				<c:set var="count" value="${count + 1}" scope="page" />
			</c:forEach>
		</div>
	</div>
	<!-- End Wrap -->
	<h1>${gameId}</h1>
</body>
</html>