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
	href="${pageContext.request.contextPath}/resources/listLobbies.css"
	type="text/css">


<script type="text/javascript">
	/*function ola(id){
	 //console.log(location.href + " #" + id_div + ">*");
	 $("#" + id_div).load(location.href + " #" + id_div + ">*", "");
	 });*/

	function updateLobbyList() {
		$
				.ajax({
					type : "POST",
					url : "lobbyList",
					contentType : 'application/json; charset=utf-8',
					dataType : 'json',
					success : function(data) {
						console.log("AGGIORNO LA LISTA DI LOBBY");
						console.log(data);
						//var obj = JSON.parse(data);
						var str = "";
						for ( var o in data) {
							var creator = data[o].userList[0].player;
							var isCreator = data[o].userList[0].creator;
							var lobbySize = data[o].playerSize;
							var guest = null;
							if (data[o].playerSize > 1) {
								guest = data[o].userList[1].player;
								var isGuest = data[o].userList[1].creator;

							}
							var currentLobby = data[o].name;
							str += "<tr><td><img src=\"https://bootdey.com/img/Content/avatar/avatar1.png\"><span>Admin:"
									+ creator
									+ "</span></td><td>"
									+ data[o].name
									+ "</td><td class=\"text-center\">";
							if (lobbySize < 2) {
								str += "<span class=\"label label-success\">Players:"
										+ lobbySize
										+ "/2</span></td><td style=\"width: 20%;\">";
								str += "<a href=\"${pageContext.request.contextPath}/joinLobby?lobby="
										+ currentLobby
										+ "\" class=\"table-link\"><button style=\"font-size: 10px\">Join Lobby <span class=\"fa-stack\"> <i class=\"fa fa-square fa-stack-2x\"></i> <i	class=\"fa fa-arrow-circle-up fa-stack-1x fa-inverse\"></i></span></button></a></td>";
							} else {
								str += "<span class=\"label label-danger\">Players:"
										+ lobbySize
										+ "/2</span></td><td style=\"width: 20%;\">";
							}

							str + "</tr>";
						}
						$("#lobby-container").html(str);

					},
					error : function(jqXHR, exception) {
						console.log("ERRORE");
						var msg = '';
						if (jqXHR.status === 0) {
							msg = 'Not connect.\n Verify Network.';
						} else if (jqXHR.status == 404) {
							msg = 'Requested page not found. [404]';
						} else if (jqXHR.status == 500) {
							msg = 'Internal Server Error [500].';
						} else if (exception === 'parsererror') {
							msg = 'Requested JSON parse failed.';
						} else if (exception === 'timeout') {
							msg = 'Time out error.';
						} else if (exception === 'abort') {
							msg = 'Ajax request aborted.';
						} else {
							msg = 'Uncaught Error.\n' + jqXHR.responseText;
						}
						console.log(msg);
					}
				});
	}

	function getEventsFromServer() {
		console.log("lobby get events");
		$.ajax({
			url : "getEvents",
			data : {
				eventSource : "lobby"
			},
			success : function(result) {
				console.log("result ajax update lobby list");
				console.log(result);
				if (result == "newLobby" || result == "removedLobby") {
					updateLobbyList();
				}
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
	$(document).ready(function() {
		updateLobbyList();
		getEventsFromServer();
	});
</script>


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