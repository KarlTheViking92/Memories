<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Lobby</title>
<jsp:include page="../resources/header.jsp" />
<link
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css"
	rel="stylesheet">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/listLobbies.css"
	type="text/css">

<script type="text/javascript">var ctx = "${pageContext.request.contextPath}"</script>	
<script type="text/javascript" src="resources/js/listLobbies.js"> </script>
</head>
<body>
	<jsp:include page="navbar.jsp" />
	<div class="page-title">
		<h1 align="center">LOBBIES</h1>
	</div>


	<div class="container" style="background-color: lightgrey">
		<div class="row">
			<div class="col-lg-12">
				<div class="main-box clearfix">
					<div class="table-responsive">
						<table class="table user-list">
							<thead>
								<tr>
									<th><span>User</span></th>
									<th><span>Lobby</span></th>
									<th class="text-center"><span>Status Lobby</span></th>

									<th>&nbsp;</th>
								</tr>
							</thead>
							<tbody id="lobby-container">
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>



	<a href="${pageContext.request.contextPath}/refreshLobby">REFRESH</a>
</body>
</html>