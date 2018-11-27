<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Lobby</title>
<jsp:include page="../resources/header.jsp" />
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
								<h3>${lobby.getName()}</h3>
							</div>
							<ul class="chat-list">
								<li class=""><a class="lobby" href="#lobby.html">
										<h4>
											<i class="fa fa-list"></i> Lobby
										</h4>
								</a></li>
								<c:if
									test="${lobby.players.size() gt 1 and lobby.creator eq user}">
									<a href="${pageContext.request.contextPath}/startGame"
										class="table-link success">
										<button style="font-size: 10px">
											Start Game<span class="fa-stack"> <i
												class="fa fa-square fa-stack-2x" style="color: green"></i> <i
												class="fa fa-gamepad fa-stack-1x fa-inverse"></i>
											</span>
										</button>
									</a>
								</c:if>

								<li><a
									href="${pageContext.request.contextPath}/leaveLobby?lobby=${lobby.name}"
									class="table-link danger"><button style="font-size: 10px">
											Leave Lobby <span class="fa-stack"> <i
												class="fa fa-square fa-stack-2x"></i> <i
												class="fa fa-trash-o fa-stack-1x fa-inverse"></i>
											</span>
										</button> </a></li>

								<!-- <li class="active"><a href="#chat_room.html"> <i
										class="fa fa-rocket"></i> <span>Water Cooler</span> <i
										class="fa fa-times pull-right"></i>
								</a></li>
								<li><a href="#chat_room.html"> <i class="fa fa-rocket"></i>
										<span>Design Lounge</span> <i class="fa fa-times pull-right"></i>
								</a></li>
								<li><a href="#chat_room.html"> <i class="fa fa-rocket"></i>
										<span>Development</span> <i class="fa fa-times pull-right"></i>
								</a></li>
							</ul>
							<ul class="chat-list chat-user">
								<li><a href="##"> <i class="fa fa-circle text-success"></i>
										<span>Jonathan Smith</span> <i class="fa fa-times pull-right"></i>
								</a></li>
								<li><a href="##"> <i class="fa fa-circle text-success"></i>
										<span>Jhon Doe</span> <i class="fa fa-times pull-right"></i>
								</a></li>
								<li><a href="##"> <i class="fa fa-circle text-muted"></i>
										<span>Cendy Andrianto</span> <i class="fa fa-times pull-right"></i>
								</a></li>
								<li><a href="##"> <i class="fa fa-circle text-danger"></i>
										<span>Anjelina Joe</span> <i class="fa fa-times pull-right"></i>
								</a></li> -->
							</ul>
							<footer>
								<a class="chat-avatar" href="#javascript:;"> <img alt=""
									src="http://bootemplates.com/themes/kentut/assets/img/avatar/avatar-19.jpg">
								</a>
								<div class="user-status">
									<i class="fa fa-circle text-success"></i> Available
								</div>
								<a class="chat-dropdown pull-right" href="#javascript:;"> <i
									class="fa fa-chevron-down"></i>
								</a>
							</footer>
						</aside>
						<!-- end:aside kiri chat room -->

						<!-- start:aside tengah chat room -->
						<aside class="tengah-side">
							<div class="chat-room-head">
								<h3>Air Koler</h3>
								<form action="#" class="pull-right position">
									<input type="text" placeholder="Search"
										class="form-control search-btn ">
								</form>
							</div>
							<div class="group-rom">
								<div class="first-part odd">Jonathan Smith</div>
								<div class="second-part">Hello Cendy are you there?</div>
								<div class="third-part">12:30</div>
							</div>
							<div class="group-rom">
								<div class="first-part">Cendy Andrianto</div>
								<div class="second-part">Yoman Smith. Please proceed</div>
								<div class="third-part">12:31</div>
							</div>
							<div class="group-rom">
								<div class="first-part odd">Jonathan Smith</div>
								<div class="second-part">I want to share a file using
									chatroom</div>
								<div class="third-part">12:32</div>
							</div>
							<div class="group-rom">
								<div class="first-part">Cendy Andrianto</div>
								<div class="second-part">oh sure. please send</div>
								<div class="third-part">12:32</div>
							</div>
							<div class="group-rom">
								<div class="first-part odd">Jonathan Smith</div>
								<div class="second-part">
									<a href="##">search_scb_dialog.jpg</a> <span class="text-muted">46.8KB</span>
									<p>
										<img
											src="http://bootemplates.com/themes/kentut/assets/img/avatar/avatar-2.jpg"
											alt="" class="img-responsive">
									</p>
								</div>
								<div class="third-part">12:32</div>
							</div>
							<div class="group-rom">
								<div class="first-part">Cendy Andrianto</div>
								<div class="second-part">Fantastic job, love it :)</div>
								<div class="third-part">12:32</div>
							</div>
							<div class="group-rom">
								<div class="first-part odd">Jonathan Smith</div>
								<div class="second-part">Thanks</div>
								<div class="third-part">12:33</div>
							</div>
							<footer>
								<div class="chat-txt">
									<input type="text" class="form-control">
								</div>
								<div class="btn-group">
									<button type="button" class="btn btn-white"
										data-original-title="" title="">
										<i class="fa fa-meh-o"></i>
									</button>
									<button type="button" class="btn btn-white"
										data-original-title="" title="">
										<i class=" fa fa-paperclip"></i>
									</button>
								</div>
								<button class="btn btn-danger" data-original-title="" title="">Send</button>
							</footer>
						</aside>
						<!-- end:aside tengah chat room -->

						<!-- start:aside kanan chat room -->
						<aside class="kanan-side">
							<div class="user-head">
								<a href="##" class="chat-tools btn-success"><i
									class="fa fa-cog"></i> </a> <a href="##" class="chat-tools btn-key"><i
									class="fa fa-key"></i> </a>
							</div>
							<div class="invite-row">
								<h4 class="pull-left">People</h4>
							</div>
							<ul class="chat-available-user">
								<c:forEach items="${lobby.getPlayers()}" var="player">
									<li><a href="#chat-room.html" class="player"> <i
											class="fa fa-circle text-success"></i>${player.username}</a></li>
								</c:forEach>

							</ul>
							<footer>
								<a href="##" class="guest-on"> <i class="fa fa-check"></i>
									Guest Access On
								</a>
							</footer>
						</aside>
						<!-- end:aside kanan chat room -->

					</div>
				</div>
				<!-- end:chat room -->
			</div>
		</div>
		<form action="createGame" method="POST">
			Difficulty: <input type="text" name="difficulty">
			<input type="submit" value="Submit">
		</form>
		<%-- <a href="${pageContext.request.contextPath}/createGame">
				<button class="btn btn-success">Start
					Game</button>
		</a> --%>
	</div>
</body>
</html>