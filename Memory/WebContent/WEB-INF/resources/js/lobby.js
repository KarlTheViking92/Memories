function updateLobby() {
	$
			.ajax({
				type : "POST",
				url : "getLobby",
				contentType : 'application/json; charset=utf-8',
				dataType : 'json',
				success : function(data) {
					console.log("AGGIORNO LOBBY");
					console.log(data);
				
					var str = "";
					var lobbySize = data.playerSize;
					var game_button = $("#g_button");
					var difficulty = $("#difficulty");
					if (data.creator == data.user) {
						difficulty.css("display", "");
						if (lobbySize == 2) {
							game_button.css("display", "");
						}else {
							game_button.css("display", "none");
						}
					}
					console.log(data.players);
					for (var i = 0; i < data.players.length; i++) {
						var p = data.players[i].player;
						console.log(p);
						str += "<a href=\"#chat-room.html\"><i class=\"fa fa-circle text-success\"></i>"
								+ p + "</a>";
					}
					// $(".chat-list").append(game_button);
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

function checkGameStarted() {
	$.ajax({
		type : "GET",
		url : "checkGameStarted",
		success : function(data) {
			console.log("Return checkGameStarted");
			console.log(data);
			// var str = "";
			if (data == "true") {
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
	console.log("lobby get events from server");
	$.ajax({
		type : "GET",
		url : "getEvents",
		data : {
			eventSource : "lobby"
		},
		success : function(result) {
			setTimeout(function() {
//				console.log("result ajax lobby getevents");
				console.log(result);
				if (result == "joined" || result == "leftLobby") {
					updateLobby();
				}
				if(result=="removedLobby"){
					console.log(myContextPath+"/refreshLists");
					var controller = myContextPath+"/refreshLists";
					window.location = controller ;

				}
				if (result == "gameStarted") {
					console.log("YEAH");
					checkGameStarted();
				}
				
				getEventsFromServer();
			}, 1000);
		},
		error : function(jqXHR, exception) {
			console.log("ERRORE GET EVENTS LOBBYJS");
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