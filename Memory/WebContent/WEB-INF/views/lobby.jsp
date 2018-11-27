<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
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
			$.ajax({
				type : "POST",
				url : "lobbyList",
				contentType : 'application/json; charset=utf-8',
				dataType : 'json',
				success : function(data) {
					//var obj = JSON.parse(data);
					
					var str = "";
					for(var o in data) {
						//console.log(data[o].name);
						//console.log(data[o].currentUser);
						//console.log(data[o].currentUser);
						//console.log(data[o].userList);
						var creator = data[o].userList[0].player;
						var isCreator = data[o].userList[0].creator;
						var guest = null;
						if(data[o].playerSize > 1) {
							guest = data[o].userList[1].player;
							var isGuest = data[o].userList[1].creator;
							
						}
				
						str += "<div class= \"product-preview-container\" id=\"id_div\"><ul><li>Lobby: "+data[o].name+"</li><li>Players: "+data[o].playerSize+"</li><li>Creator: "+creator+" "+isCreator+"</li><li>Guest:"+guest+"</li>";
						if( (creator == data[o].currentUser) || (guest == data[o].currentUser) ) {
							var currentLobby = data[o].name;
							console.log(currentLobby);
							//var location = window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
							//console.log(location);
							//if he leaves and he is the creator bye bye lobby
						str+= "<li><a href=\"${pageContext.request.contextPath}/leaveLobby?lobby="+currentLobby+"\">Leave lobby</a></li>";
						}	 //lobby=${lobby.name}
						if( (data[o].playerSize < 2) && ( (creator != data[o].currentUser) && (guest != data[o].currentUser) ) ) { 
							var currentLobby = data[o].name;
							console.log(currentLobby);
							str+= "<li><a href=\"${pageContext.request.contextPath}/joinLobby?lobby="+currentLobby+"\">Join Lobby</a></li>";
						}					
						if( (data[o].playerSize > 1) && (creator == data[o].currentUser) ) { 
							var currentLobby = data[o].name;
							console.log(currentLobby);
							str+= "<li><a href=\"${pageContext.request.contextPath}/startGame\">Start Game</a></li>";
						}
						str+= "</ul></div>";
					}

					$("#lobby-container").html(str);

					},
					error : function() {
						console.log("ERRORE");
					}
				});
			setTimeout(updateLobby, 5000);
	}
	$(document).ready(updateLobby());
</script>
</head>
<body>
	<jsp:include page="navbar.jsp" />
	<div class="page-title">
		<h1>LOBBIES</h1>
	</div>
	<div id="lobby-container"></div>
	<c:if test="${not empty errorLobby}">
		<div class="alert alert-danger alert-dismissible" role="alert">
			<button type="button" class="close" data-dismiss="alert"
				aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
			<strong>${errorLobby}</strong>
		</div>
	</c:if>
	<a href="${pageContext.request.contextPath}/refreshLobby">REFRESH</a>
	<!-- <button class="btn btn-primary mb-0" type="submit" onclick="ola('newServices')">NEW</button> -->
</body>
</html>