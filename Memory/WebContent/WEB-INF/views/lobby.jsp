<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Lobby</title>
<jsp:include page="../resources/header.jsp" />
<script type="text/javascript">
	/*function ola(id){
	 //console.log(location.href + " #" + id_div + ">*");
	 $("#" + id_div).load(location.href + " #" + id_div + ">*", "");
	 });*/
	function updateLobby() {
		$
				.ajax({
					type : "POST",
					url : "getLobby",
					contentType : 'application/json; charset=utf-8',
					dataType : 'json',
					success : function(data) {
						console.log("SUCCESSO NELLA LOBBY DI CRISTO");
						/*  var obj = JSON.parse(data);*/

						var str = "";
						var tmp = "";

						for ( var o in data) {
							/*console.log(data[o].name);
							console.log(data[o].currentUser);
							console.log(data[o].currentUser);
							console.log(data[o].userList);*/
							/* var creator = data[o].userList[0].player;
							var isCreator = data[o].userList[0].creator; */
							var lobbySize = data[o].playerSize;
							var guest = null;
							if (data[o].playerSize > 1) {
								guest = data[o].userList[1].player;
								var isGuest = data[o].userList[1].creator;

							}
							var currentLobby = data[o].name;
							str += "<a href=\"#chat-room.html\"><i class=\"fa fa-circle text-success\"></i>"
									+ data[o].player + "</a>";
							if (lobbySize == 2
									&& data[o].creator == data[o].cUser) {

								tmp += "<a href=\"${pageContext.request.contextPath}/startGame\" class=\"table-link success\"> <button style=\"font-size: 10px\">Start Game<span class=\"fa-stack\"> <i class=\"fa fa-square fa-stack-2x\" style=\"color: green\"></i> <i	class=\"fa fa-gamepad fa-stack-1x fa-inverse\"></i></span></button></a>";
							}
						}

						/* $("#lobby-container").html(str); */
						$("#start-button").html(tmp);
					},
					error : function(jqXHR, exception) {
						console.log("ERRORE");
						console.log(msg);
					}
				});
		setTimeout(updateLobby, 5000);
	}
	$(document).ready(updateLobby());
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
								<li id="start-button"></li>

								<li><a
									href="${pageContext.request.contextPath}/leaveLobby?lobby=${lobby.name}"
									class="table-link danger"><button style="font-size: 10px">
											Leave Lobby <span class="fa-stack"> <i
												class="fa fa-square fa-stack-2x"></i> <i
												class="fa fa-trash-o fa-stack-1x fa-inverse"></i>
											</span>
										</button> </a></li>

								<li style="text-align: center">Utenti nalla Lobby</li>
								<li>
									<div id="lobby-container"></div>
								</li>
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
		<a href="${pageContext.request.contextPath}/createGame">
			<button class="btn btn-success">Start Game</button>
		</a>
	</div>
</body>
</html>