function updateLobby() {
	$
			.ajax({
				type : "POST",
				url : "getLobby",
				contentType : 'application/json; charset=utf-8',
				dataType : 'json',
				success : function(data) {

					var str = "";
					var lobbySize = data.playerSize;
					var game_button = $("#g_button");
					var difficulty = $("#difficulty");
					if (data.creator == data.user) {
						difficulty.css("display", "");
						if (lobbySize == 2) {
							game_button.css("display", "");
						} else {
							game_button.css("display", "none");
						}
					}
					for (var i = 0; i < data.players.length; i++) {
						var p = data.players[i].player;
						str += "<a href=\"#chat-room.html\"><i class=\"fa fa-circle text-success\"></i> "
								+ p + "</a>";
					}
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
	$.ajax({
		type : "GET",
		url : "getEvents",
		data : {
			eventSource : "lobby"
		},
		success : function(result) {
			setTimeout(function() {
				if (result == "joined" || result == "leftLobby") {
					updateLobby();
				}
				if (result == "removedLobby") {
					var controller = myContextPath + "/refreshLists";
					window.location = controller;

				}
				if (result == "gameStarted") {
					checkGameStarted();
				}
				if (result == "chat") {
					getMessage();
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

function getMessage() {
	$.ajax({
		type : "POST",
		url : "getMessage",
		contentType : 'application/json; charset=utf-8',
		dataType : 'json',
		success : function(response) {
			var str = "";
			var cUser = response.user;
			str += "<div class=\"first-part odd\" >" + cUser
					+ "</div> <div class=\"second-part\">" + response.message
					+ "</div>";
			$("#messageUpdate").append(str);
		},
		error : function(jqXHR, exception) {
			console.log("ERRORE NEL GET MESSAGE");
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

$(document).ready(function() {
	$("#buttonMessage").on("click", function() {
		$.ajax({
			type : "GET",
			url : "sendMessage",
			data : {

				"idMessage" : $("#sendMessage").val()
			},
			success : function(data) {
				txt = data;
				$("#sendMessage").val('');
				// str+="<div class=\"first-part odd\">"++"</div><div
				// class=\"second-part\" id=\"getMessage\"></div>";
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
			}

		});
	});

	$("#createGame").on("click", function() {
		var lobby = $("#lobby-name").text();
		var difficulty = $('#difficulty input:radio:checked').val();
		$.ajax({
			type : "POST",
			url : "createGame",
			data : {
				"difficulty" : difficulty
			},
			success : function(response) {
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