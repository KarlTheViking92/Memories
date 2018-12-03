var pause = false;

function getEventsFromServer() {
	$.ajax({
		type : "GET",
		url : "getEvents",
		data : {
			eventSource : "game"
		},
		success : function(result) {
			setTimeout(function() {
				if (result == "finishGame") {
					$.ajax({
						url:"saveResults",
						type : "GET",
					});
					
					setTimeout(() => {
						window.location.href = "./result";
					}, 3000);
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
	var sec = 0;
	$(".card").on("click", function() {
		var $card = $(this);
		var clickedCard = $(".card").find(".picked:not(.matched)").length;
		var selected = $(".card").find(".selected").parent().attr("data-counter");
		if (selected != $card.attr("data-counter") && !pause) {
			$.ajax({
				type : "POST",
				url : "pickCard",
				data : {
					"imageId" : $card.attr("data-id"),
					"position" : $card.attr("data-counter")
				},
				success : function(data) {
					switch (data) {
					case "selected":
						$card.find(".inside").addClass("picked");
						$card.find(".inside").addClass("selected");
						break;
					case "found-pair":
						$card.find(".inside").addClass("picked");
						pause = true;
						setTimeout(function() {
							$(".card").find(".picked").addClass("matched");
							pause = false;
						}, 500);
						break;
					case "wrong-pair":
						$card.find(".inside").addClass("picked");
						pause = true;
						setTimeout(function() {
							$(".card").find(".inside").removeClass("picked");
							$(".card").find(".inside").removeClass("selected");
							pause = false;
						}, 1000);
						break;
					case "win":
						$card.find(".inside").addClass("picked");
						setTimeout(function() {
							$(".card").find(".picked").addClass("matched");
						}, 500);
						break;
					case "not-permitted":
						break;
					}
				},
				error : function() {
					console.log("Ajax Error");
				}
			});
		}
	});
	getEventsFromServer();
	setInterval(function time() {
		jQuery('#countdown #sec').html(sec);
		sec++;
	}, 1000);
	$("#timer-row").removeAttr('hidden');
});
