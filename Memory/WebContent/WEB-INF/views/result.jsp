<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Game review</title>

<jsp:include page="../resources/header.jsp" />
<link rel="stylesheet" type="text/css" href="resources/css/result.css" />
<script type="text/javascript" src="resources/js/result.js"></script>


</head>
<body>
	<jsp:include page="navbar.jsp" />
	<div class="container" style="margin-top: 10px;">
		<h1>Game Review</h1>

		<div class="row">
			<section class="content">
				<div class="col-md-8 col-md-offset-2">
					<div id="lose" hidden="true">
						<h2 align="center">
							<i style="color: red;" class="fas fa-frown-open fa-7x"></i> <strong>
								You Lose </strong> <i style="color: red;" class="fas fa-frown-open fa-7x"></i>
						</h2>
					</div>

					<div id="win" hidden="true">
						<h2 align="center">
							<i style="color: green;" class="fas fa-smile-beam fa-7x"></i> <strong>
								You Win </strong> <i style="color: green;"
								class="fas fa-smile-beam fa-7x"></i>
						</h2>
					</div>

					<div class="panel panel-default">
						<div class="panel-body">
								<table id="reviewTable" class="table table-filter">
									<col width="40">
									<col width="80">
									<col width="80">
									<col width="80">
									<thead>
										<tr>
											<th colspan=2><span>Player <i class="far fa-user"></i></span>
											</th>
											<th><span>Time <i class="far fa-clock"></i></span></th>
											<th>Status</th>
										</tr>
									</thead>
									<tbody id="reviewbody">

										<tr>
											<td colspan=2>Player</td>
											<td>time</td>
											<td>win</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</section>
		</div>
		<div class="row">
			<div class="col-md-2 col-md-offset-2">
				<button type="button" id="exit" class="btn btn-primary">Return
					to Homepage</button>
			</div>
		</div>
	</div>

</body>
</html>