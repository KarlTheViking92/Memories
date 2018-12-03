<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Match History</title>
<jsp:include page="../resources/header.jsp" />
<link
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css"
	rel="stylesheet">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/listLobbies.css"
	type="text/css">
<script type="text/javascript">var ctx = "${pageContext.request.contextPath}"</script>	
<script type="text/javascript" src="resources/js/matchHistory.js"> </script>
</head>
<body>
	<jsp:include page="navbar.jsp" />
		
	<div class="container" style="background-color: lightgrey">
		<div class="row">
			<div class="col-lg-12">
				<div class="main-box clearfix">
					<div class="table-responsive">
						<table class="table user-list">
							<thead>
								<tr>
									<th><h1 align="left">${user.username} Match History</h1></th>
								</tr>
							</thead>
							<thead>
								<tr>
									<th><span>Opponent</span></th>
									<th><span>Lobby</span></th>
									<th class="text-center"><span>Status game</span></th>
									<th><span>Time</span></th>
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
</body>
</html>