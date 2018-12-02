var pause = false;

function getEventsFromServer() {
	console.log("lobby get events from server");
	$.ajax({
		type : "GET",
		url : "getEvents",
		data : {
			eventSource : "game"
		},
		success : function(result) {
			setTimeout(function() {
				// console.log("result ajax lobby getevents");
				console.log(result);
				if (result == "finishGame") {
					console.log("this game is over");
					window.location.href = "./result"
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
		console.log("clicco la carta");
		var $card = $(this);
		console.log($card);
		var clickedCard = $(".card").find(".picked:not(.matched)").length;
		console.log("pause var " + pause);
		if (!pause) {
			$.ajax({
				type : "POST",
				url : "pickCard",
				data : {
					"imageId" : $card.attr("data-id"),
					"position" : $card.attr("data-counter")
				},
				success : function(data) {
					console.log("data is " + data);
					switch (data) {
					case "selected":
						$card.find(".inside").addClass("picked");
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
					// $card.find(".inside").addClass("picked");
				},
				error : function() {
					console.log("Ajax Error");
				}
			});
		}
		/*
		 * if($card.find(".inside").hasClass("picked")){ console.log("ciao
		 * ciao"); $card.find(".inside").removeClass("picked"); } else{
		 * console.log("ciao"); $card.find(".inside").addClass("picked"); }
		 */
	});
	getEventsFromServer();
	setInterval(function time() {
		/*
		 * var d = new Date(); var hours =d.getHours(); var min =
		 * d.getMinutes(); if ((min + '').length == 1) { min = '0' + min; }
		 * 
		 * if ((sec + '').length == 1) { sec = '0' + sec; }
		 */
		// jQuery('#countdown #hour').html(hours);
		// jQuery('#countdown #min').html(min);
		jQuery('#countdown #sec').html(sec);
		sec++;
	}, 1000);
	$("#timer-row").removeAttr('hidden');
});
