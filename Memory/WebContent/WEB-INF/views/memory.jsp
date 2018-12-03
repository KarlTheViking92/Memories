<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Memory</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/memory.css"
	type="text/css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/timer.css"
	type="text/css">
<jsp:include page="../resources/header.jsp" />
<script type="text/javascript" src="resources/js/memory.js"></script>
<script type="text/javascript" src="resources/js/timer.js"></script>
</head>
<body>
	<jsp:include page="navbar.jsp" />
	<div class="container">
		<section id="timer" >
			<div id="timer-row" class="row" hidden="true">
				<div
					class="col-xs-12 col-sm-12 col-md-12 countdown-wrapper text-center mb20">
					<div class="timer">
						<div class="card-block">
							<div id="countdown">
							<!-- 	<span id="hour" class="timer bg-success"></span> 
								<span id="min"
									class="timer bg-info">
								</span> --> <span id="sec"
									class="timer bg-primary"></span>
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>
	</div>

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
	<%-- <h1>${gameId}</h1> --%>
</body>
</html>