function updateResult() {
	$.ajax({
		type : "GET",
		url : "getResult",
		contentType : 'application/json; charset=utf-8',
		dataType : 'json',
		success : function(data) {
			console.log("success result");
			console.log(data);

			var str = "";
			for ( var o in data.players) {
				if (data.players[o].status == "win")
					str += "<tr><td colspan=2>" + data.players[o].user
							+ "</td><td>" + data.time + "</td><td>"
							+ data.players[o].status + "</td></tr>";
				else
					str += "<tr><td colspan=2>" + data.players[o].user
					+ "</td><td> -- </td><td>"
					+ data.players[o].status + "</td></tr>";
				
			}
			$("#reviewbody").html(str);
		},
		error : function(jqXHR, exception) {
			console.log("ERRORE getResult");
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
	updateResult();
});
