<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Memory</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/memory.css"
	type="text/css">
<jsp:include page="../resources/header.jsp" />
<script type="text/javascript">
	var pause = false;
	$(document).ready(
			function() {
				$(".card").on(
						"click",
						function() {
							console.log("clicco la carta");
							var $card = $(this);
							console.log($card);
							var clickedCard = $(".card").find(
									".picked:not(.matched)").length;
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
											$card.find(".inside").addClass(
													"picked");
											break;
										case "found-pair":
											$card.find(".inside").addClass(
													"picked");
											pause = true;
											setTimeout(function() {
												$(".card").find(".picked")
														.addClass("matched");
												pause = false;
											}, 500);
											break;
										case "wrong-pair":
											$card.find(".inside").addClass(
													"picked");
											pause = true;
											setTimeout(function() {
												$(".card").find(".inside")
														.removeClass("picked");
												pause = false;
											}, 1000);
											break;
										case "win":
											$card.find(".inside").addClass(
													"picked");
											setTimeout(function() {
												$(".card").find(".picked")
														.addClass("matched");
											}, 500);
											break;
										case "not-permitted":
											break;
										}
										//$card.find(".inside").addClass("picked");
									},
									error : function() {
										console.log("Ajax Error");
									}
								});
							}
							/*	if($card.find(".inside").hasClass("picked")){
									console.log("ciao ciao");
									$card.find(".inside").removeClass("picked");
								}
								else{
									console.log("ciao");
									$card.find(".inside").addClass("picked");
								}*/
						});
			});
</script>
</head>
<body>
	<jsp:include page="navbar.jsp" />

	<div class="wrap">
		<div class="game">
			<c:set var="count" value="0" scope="page" />
			<c:forEach items="${cards}" var="card">
				<div class="card" data-id="${card.id}" data-counter="${count}">
					<div class="inside">

						<div class="front">
							<img src="${card.img}" alt="${card.name}" />
						</div>

						<div class="back">
							<img
								src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/74196/codepen-logo.png"
								alt="Codepen" />
						</div>
					</div>
				</div>
				<c:set var="count" value="${count + 1}" scope="page" />
			</c:forEach>
		</div>
	</div>
	<!-- End Wrap -->
	<h1>${gameId}</h1>
</body>
</html>