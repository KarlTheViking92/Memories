var count = 0;
function updateLobbyList() {
	console.log("count : " +count);
	$
			.ajax({
				type : "GET",
				url : "lobbyList",
				contentType : 'application/json; charset=utf-8',
				dataType : 'json',
				success : function(data) {
					console.log("AGGIORNO LA LISTA DI LOBBY");
					console.log(data);
					// var obj = JSON.parse(data);
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
						str += "<tr><td><img src=\"https://bootdey.com/img/Content/avatar/avatar1.png\"><span>Opponent: "
								+ creator
								+ "</span></td><td>"
								+ data[o].name
								+ "</td><td class=\"text-center\">";
						if (lobbySize < 2) {
							str += "<span class=\"label label-success\">Won</span></td><td style=\"width: 20%;\">";							
							str += "Time</td>";
						} else {
							str += "<span class=\"label label-danger\">Lost</span></td><td style=\"width: 20%;\">";
							str += "Time";
						}

						str + "</tr>";
					}
					$("#lobby-container").html(str);
					count++;

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
/*
function getEventsFromServer() {
	console.log("lobby get events");
	$.ajax({
		url : "getEvents",
		data : {
			eventSource : "lobby"
		},
		success : function(result) {
			setTimeout(function(){
//				console.log("result ajax update lobby list");
				console.log(result);
				if (result == "newLobby" || result == "removedLobby") {
					console.log("update lobby");
					updateLobbyList();
				}
				getEventsFromServer();
			}, 1000);
		},
		error : function() {
			// call events again after some time
			setTimeout(function() {
				getEventsFromServer();
			}, 5000);
		}
	});
//	console.log("end get events");
}*/

function diocane(){
	setInterval(function (){
		updateLobbyList();
	}, 2000);
}

$(document).ready(function() {
	updateLobbyList();
	diocane();
	console.log("on ready listlobby js");
	console.log("end ready");
});