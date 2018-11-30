<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Memory Game</title>
<jsp:include page="../resources/header.jsp" />
<script src="resources/js/index.js"></script>
</head>
<body>
	<jsp:include page="navbar.jsp" />
	<c:if test="${not empty errorRegistration}">
		<div class="alert alert-danger alert-dismissible" role="alert">
			<button type="button" class="close" data-dismiss="alert"
				aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
			<strong>${errorRegistration}</strong>
		</div>
	</c:if>
	<c:if test="${not empty errorLogin}">
		<div class="alert alert-danger alert-dismissible" role="alert">
			<button type="button" class="close" data-dismiss="alert"
				aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
			<strong>${errorLogin}</strong>
		</div>
	</c:if>
</body>
</html>