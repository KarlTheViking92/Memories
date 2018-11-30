<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Lobby</title>
<jsp:include page="../resources/header.jsp" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/checkbox.css"
	type="text/css">
<script type="text/javascript">
	function updateLobby() {
		$
				.ajax({
					type : "POST",
					url : "getLobby",
					contentType : 'application/json; charset=utf-8',
					dataType : 'json',
					success : function(data) {
						console.log("SUCCESSO NELLA LOBBY");
						console.log(data);
						var str = "";
						var lobbySize = data.playerSize;
						var game_button = $("#g_button");
						var difficulty = $("#difficulty");
						if (data.creator == data.user) {
							difficulty.css("display", "");
							if (lobbySize == 2) {
								game_button.css("display", "");
							}
						}
						console.log(data.players);
						for (var i = 0; i < data.players.length; i++) {
							var p = data.players[i].player;
							console.log(p);
							str += "<a href=\"#chat-room.html\"><i class=\"fa fa-circle text-success\"></i>"
									+ p + "</a>";
						}
						//$(".chat-list").append(game_button);
						$("#lobby-container").html(str);
					},
					error : function(jqXHR, exception) {
						console.log("ERRORE getLobby");
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
	
	function checkGameStarted(){
		$.ajax({
			type : "GET",
			url : "checkGameStarted",
			success : function(data) {
				console.log("Return checkGameStarted");
				console.log(data);
				//var str = "";
				if(data == "true"){
					window.location.href = "./getGame";
				}
				
			},
			error : function(jqXHR, exception) {
				console.log("ERRORE checkGameStarted");
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
				console.log("result ajax update lobby");
				console.log(result);
				if (result == "joined") {
					updateLobby();
				}else if(result == "gameStarted"){
					console.log("YEAH");
					checkGameStarted();
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
		$("#createGame").on("click", function() {
			console.log("call CREATE GAME");
			var lobby = $("#lobby-name").text();
			var difficulty = $('#difficulty input:radio:checked').val();
			$.ajax({
				type : "POST",
				url : "createGame",
				data : {
					"difficulty" : difficulty
				},
				success : function(response) {
					console.log("response " + response);
					window.location.href = "./" + response;
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
		});
		updateLobby();
		getEventsFromServer();
	});
</script>

</head>
<body>
	<jsp:include page="navbar.jsp" />
	<div class="container bootstrap snippets">
		<div class="row">
			<div class="col-md-12">
				<!-- start:chat room -->
				<div class="box">
					<div class="chat-room">
						<!-- start:aside kiri chat room -->
						<aside class="kiri-side">
							<div class="user-head">
								<!-- 								<i class="fa fa-comments-o"></i>
 -->

							</div>
							<ul class="chat-list">
								<li style="text-align: center"><h3>Utenti nalla Lobby</h3></li>
								<li>
									<div id="lobby-container"></div>
								</li>
								<li><a id="g_button" style="display: none"><button
											id="createGame" style="font-size: 10px">
											Start Game<span class="fa-stack"> <i
												class="fa fa-square fa-stack-2x" style="color: green"></i> <i
												class="fa fa-gamepad fa-stack-1x fa-inverse"></i>
											</span>
										</button> </a></li>
								<li><a
									href="${pageContext.request.contextPath}/leaveLobby?lobby=${lobby.name}"
									class="table-link danger"><button style="font-size: 10px">
											Leave Lobby <span class="fa-stack"> <i
												class="fa fa-square fa-stack-2x"></i> <i
												class="fa fa-trash-o fa-stack-1x fa-inverse"></i>
											</span>
										</button> </a></li>
								<li><div class="col-sm-12" id="difficulty" style="display: none">
										<h5>Select Game Difficult</h5>
										<div class="col-sm-12" >
											<div class="radio">
												<label> <input type="radio" name="o3" value="EASY">
													<span class="cr"><i class="cr-icon fa fa-circle"></i></span>Easy
												</label>
											</div>
											<div class="radio">
												<label> <input type="radio" name="o3" value="NORMAL"
													checked> <span class="cr"><i
														class="cr-icon fa fa-circle"></i></span>Normal
												</label>
											</div>
											<div class="radio">
												<label> <input type="radio" name="o3" value="HARD">
													<span class="cr"><i class="cr-icon fa fa-circle"></i></span>Hard
												</label>
											</div>
										</div>
									</div></li>

							</ul>
						</aside>
						<!-- end:aside kiri chat room -->

						<!-- start:aside tengah chat room -->
						<aside class="tengah-side">
							<div class="chat-room-head" align="center">

								<h3 style="text-align: center">${lobby.getName()}</h3>
							</div>
							<div class="group-rom">
								<div class="first-part odd">Jonathan Smith</div>
								<div class="second-part">Hello Cendy are you there?</div>
								<div class="third-part">12:30</div>
							</div>

							<footer>
								<div class="chat-txt">
									<input type="text" class="form-control">
								</div>
								<button class="btn btn-danger" data-original-title="" title="">Send</button>
							</footer>
						</aside>
						<!-- end:aside tengah chat room -->



					</div>
				</div>
				<!-- end:chat room -->
			</div>
		</div>
	</div>
</body>
</html>