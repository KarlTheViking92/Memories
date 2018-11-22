<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
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
	
	<h1>ciccio</h1>
	<c:forEach items="${games}" var="i">
		<h1>ciccciop</h1>
		"${i.getName()}"
	</c:forEach>
	<h1>ciccio2</h1>
</body>
</html>