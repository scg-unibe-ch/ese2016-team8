<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:import url="template/header.jsp" />

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome to flatbook</title>
</head>
<body>

<pre>Home</pre>

<h1>Welcome to flatbook!</h1>

<c:choose>
	<c:when test="${empty newest}">
		<h2>No ads placed yet</h2>
	</c:when>
	<c:otherwise>
		<div id="resultsDiv" class="resultsDiv">
			<h2>Our newest ads:</h2>
			<c:forEach var="ad" items="${newest}">
				<div class="resultAd">
					<div class="resultLeft">
						<c:choose>
							<c:when test="${!ad.auction}"><a href="<c:url value='/ad?id=${ad.id}' />"><img src="${ad.pictures[0].filePath}" /></a></c:when>
							<c:otherwise><a href="<c:url value='/auction?id=${ad.id}' />"><img src="${ad.pictures[0].filePath}" /></a></c:otherwise>
						</c:choose>
						<h2>
						<c:choose>
							<c:when test="${!ad.auction}"><a class="link" href="<c:url value='/ad?id=${ad.id}' />">${ad.title}</a></c:when>
							<c:otherwise><a class="link" href="<c:url value='/auction?id=${ad.id}' />">${ad.title}</a></c:otherwise>
						</c:choose>
						</h2>
						<p>${ad.street}, ${ad.zipcode} ${ad.city}</p>
						<br />
						<p>
							<i><c:choose>
									<c:when test="${ad.category == 'house'}">House</c:when>
									<c:when test="${ad.category == 'studio'}">Studio</c:when>
									<c:otherwise>Room</c:otherwise>
								</c:choose> to
								<c:choose>
									<c:when test="${ad.sale}">Buy</c:when>
									<c:otherwise>Rent</c:otherwise>
								</c:choose></i>
						</p>
					</div>
					<div class="resultRight">
						<c:choose>
							<c:when test="${ad.user.premium}"><img style="width: 40px; height: 40px;" src="/img/Star.png"></c:when>
						</c:choose>
						<h2>CHF ${ad.prizePerMonth }</h2>
						<br /> <br />

						<fmt:formatDate value="${ad.moveInDate}" var="formattedMoveInDate"
							type="date" pattern="dd.MM.yyyy" />

						<p>Move-in date: ${formattedMoveInDate }</p>
					</div>
				</div>
			</c:forEach>
		</div>
	</c:otherwise>
</c:choose>

<c:import url="template/footer.jsp" /><br />
