<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Match History</title>
<jsp:include page="../resources/header.jsp" />
</head>
<body>
	<jsp:include page="navbar.jsp" />
	<h1>Match History</h1>
	<br>
	<div class="container" style="background-color: lightblue">
		<div class="row">
			<div class="col-lg-12">
				<div class="main-box clearfix">
					<div class="table-responsive">
						<table class="table user-list">
							<thead>
								<tr>
									<th class="text-center">Game ID</th>
									<th class="text-center">Players</th>
									<th class="text-center">Won or Lost</th>
									<th class="text-center">Seconds</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${gamesOfUser}" var="i">
									<tr>
										<td class="text-center"><span>${i.getGameID()}</span></td>
										<td class="text-center"><span> <c:forEach
													items="${i.getPlayers()}" var="p">
           ${p.getUsername()}
          </c:forEach>
										</span></td>
										<td class="text-center"><span> <c:choose>
													<c:when test="${i.getWinner() eq user.id }">
             WON
            </c:when>
													<c:otherwise>
             LOST
            </c:otherwise>
												</c:choose>
										</span></td>
										<td class="text-center"><span>${i.getTime()}</span></td>
									</tr>
								</c:forEach>
							</tbody>
							<tbody id="lobby-container">
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>