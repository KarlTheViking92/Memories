var pause = false;
$(document).ready(function() {
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
});