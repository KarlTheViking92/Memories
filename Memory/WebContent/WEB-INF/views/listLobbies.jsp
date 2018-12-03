<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Lobby</title>
<jsp:include page="../resources/header.jsp" />

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/animate/animate.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/select2/select2.min.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/perfect-scrollbar/perfect-scrollbar.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/util.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/main.css">


<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/listLobbies.css"
	type="text/css">

<script type="text/javascript">
	var ctx = "${pageContext.request.contextPath}"
</script>
<script type="text/javascript" src="resources/js/listLobbies.js">
	
</script>
</head>
<body>
	<jsp:include page="navbar.jsp" />
	<div class="page-title">
		<h1 align="center">Join a Lobby</h1>
	</div>


	<div class="limiter">
		<div class="container-table100">

			<div class="wrap-table100">
				<div id="empty-lobby" hidden="true" class="alert alert-danger" role="alert" align="center">There is no available Lobby. Please, create your own.</div>
				<div class="table" id="lobbylist-container" hidden="true">

					<div class="row header">
						<div class="cell">User</div>
						<div class="cell">Lobby</div>
						<div class="cell">Status</div>
						<div class="cell"></div>
					</div>
					<div id="table-body">
						<div class="row">
							<div class="cell" data-title="Full Name">
								<img class="avatar"
									src="https://bootdey.com/img/Content/avatar/avatar1.png">
								<span>Admin:qwe</span>
							</div>
							<div class="cell" data-title="Lobby Name">asd</div>
							<div class="cell" data-title="Status">
								<span class="label label-success">Players:1/2</span>
							</div>
							<div class="cell" data-title="Join">
								<a href="/Memory/joinLobby?lobby=asd" class="table-link"><button
										class="joinButton">
										Join Lobby <span class="fa-stack"> <i
											class="fa fa-square fa-stack-2x"></i> <i
											class="fa fa-arrow-circle-up fa-stack-1x fa-inverse"></i></span>
									</button></a>
							</div>
						</div>
					</div>

				</div>
			</div>
		</div>
		<div class="container-table100">
			<div class="wrap-table100">
				<h1 align="center">Create your Lobby</h1>
				<br>
				<div class="md-form create-form" class="form-control">
					<form method="POST" action="createNewLobby" class="form-inline">
						<!-- ANd JOIN -->
						<input class="form-control" type="text" placeholder="Lobby Name"
							name="name">
						<button class="btn btn-primary" type="submit">CREATE</button>
					</form>
				</div>
			</div>
		</div>
	</div>



</body>
</html>