var count = 0;
function updateLobbyList() {
	$
			.ajax({
				type : "GET",
				url : "lobbyList",
				contentType : 'application/json; charset=utf-8',
				dataType : 'json',
				success : function(data) {
					var str = "";
					if(data.length == 0){
						$("#empty-lobby").removeAttr('hidden');
						$("#lobbylist-container").attr("hidden","true");
					}
					for ( var o in data) {
						$("#empty-lobby").attr("hidden","true");
						$("#lobbylist-container").removeAttr('hidden');
						var newStr = "<div class=\"row\">";
						var creator = data[o].userList[0].player;
						var isCreator = data[o].userList[0].creator;
						var lobbySize = data[o].playerSize;
						var guest = null;
						if (data[o].playerSize > 1) {
							guest = data[o].userList[1].player;
							var isGuest = data[o].userList[1].creator;
						}
						var currentLobby = data[o].name;
						newStr += "<div class=\"cell\" data-title=\"Full Name\"><img class=\"avatar\" src=\"https://bootdey.com/img/Content/avatar/avatar1.png\"><span> <strong>Admin</strong> : "
								+ creator + " </span></div>";
						newStr += "<div class=\"cell\" data-title=\"Lobby Name\"> "
								+ data[o].name + " </div>";
						if (lobbySize < 2) {

							newStr += "<div class=\"cell\" data-title=\"Status\"><span class=\"label label-success\">Players:"
									+ lobbySize + "/2</span></div>";
							newStr += "<div class=\"cell\" data-title=\"Join\"><a href=\"/Memory/joinLobby?lobby="
									+ currentLobby
									+ "\" class=\"table-link\"><button class=\"joinButton\">Join Lobby<span class=\"fa-stack\"> <i class=\"fa fa-square fa-stack-2x\"></i> <i class=\"fa fa-arrow-circle-up fa-stack-1x fa-inverse\"></i></span></button></a></div>";
						} else {
							newStr += "<div class=\"cell\" data-title=\"Status\"><span class=\"label label-danger\">Players:"
									+ lobbySize + "/2</span></div>";
							newStr += "<div class=\"cell\" data-title=\"Join\"></div>";
						}
						newStr += "</div>";
						str += newStr;
					}
					$("#table-body").html(str);
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

function update() {
	setInterval(function() {
		updateLobbyList();
	}, 2000);
}

$(document).ready(function() {
	update();
});