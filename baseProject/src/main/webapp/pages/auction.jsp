<%@ page import="ch.unibe.ese.team8.model.Ad"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>

<!-- check if user is logged in -->
<security:authorize var="loggedIn" url="/profile" />

<c:import url="template/header.jsp" />

<pre><a href="/">Home</a>   &gt;   <a href="/profile/myRooms">My Rooms</a>   &gt;   Ad Description</pre>

<script src="/js/jquery.ui.widget.js"></script>
<script src="/js/jquery.iframe-transport.js"></script>
<script src="/js/image_slider.js"></script>
<script src="/js/adDescription.js"></script>

<script language="javascript" type="text/javascript">

var maxBidderProfile = ${shownAd.maxBidder.id};
openPage = function() {
location.href = "user?id="+maxBidderProfile;
}

noticeLastBidder = function(){
	var subject = "${shownAd.title}";
	var text = "Hello ${shownAd.maxBidder.firstName} I just overbid you for ${shownAd.title}.";
	var recipientEmail = "${shownAd.maxBidder.username}";
	$.post("profile/messages/sendMessage", {subject : subject, text: text, recipientEmail : recipientEmail});
}

</script>
<script>
	var shownAdvertisementID = "${shownAd.id}";
	var shownAdvertisement = "${shownAd}";


	$(document).ready(function() {
		console.log(document.getElementById('bid').value);
		$("#makeBid").click(function() {
			var offer = document.getElementById('bid').value;
			console.log(offer);

			if(document.getElementById('bid').value < ${shownAd.prizePerMonth}){
				alert("you have to bid higher");
			}else{
			$.post("/auctionBid", {id: shownAdvertisementID, screening: true, bid: offer}, function(data) {

				switch(data) {
				case 0:
					alert("The bid must be higher than the current one!");
					break;
				case 1:
					alert("Everything worked fine!");
					noticeLastBidder();
					window.location.reload();
					break;
				case 3:
					break;
				default:
					alert("Default error. Please contact the WebAdmin.");
					window.location.reload();
				}
			});
		}
		});

		$.post("/bookmark", {id: shownAdvertisementID, screening: true, bookmarked: true}, function(data) {
			if(data == 3) {
			}
			if(data == 4) {
			}
		});

		$("#newMsg").click(function(){
			$("#content").children().animate({opacity: 0.4}, 300, function(){
				$("#msgDiv").css("display", "block");
				$("#msgDiv").css("opacity", "1");
			});
		});

		$("#messageCancel").click(function(){
			$("#msgDiv").css("display", "none");
			$("#msgDiv").css("opacity", "0");
			$("#content").children().animate({opacity: 1}, 300);
		});

		$("#messageSend").click(function (){
			if($("#msgSubject").val() != "" && $("#msgTextarea").val() != ""){
				var subject = $("#msgSubject").val();
				var text = $("#msgTextarea").val();
				var recipientEmail = "${shownAd.user.username}";
				$.post("profile/messages/sendMessage", {subject : subject, text: text, recipientEmail : recipientEmail}, function(){
					$("#msgDiv").css("display", "none");
					$("#msgDiv").css("opacity", "0");
					$("#msgSubject").val("");
					$("#msgTextarea").val("");
					$("#content").children().animate({opacity: 1}, 300);
				})
			}
		});
	});

</script>

    <jsp:useBean id="today" class="java.util.Date" />



<!-- format the dates -->
<fmt:formatDate value="${shownAd.moveInDate}" var="formattedMoveInDate"
	type="date" pattern="dd.MM.yyyy" />
<fmt:formatDate value="${shownAd.creationDate}" var="formattedCreationDate"
	type="date" pattern="dd.MM.yyyy" />
	<fmt:formatDate value="${today}" var="formattedToday"
		type="date" pattern="dd.MM.yyyy" />

<c:choose>
	<c:when test="${empty shownAd.moveOutDate }">
		<c:set var="formattedMoveOutDate" value="unlimited" />
	</c:when>
	<c:otherwise>
		<fmt:formatDate value="${shownAd.moveOutDate}"
			var="formattedMoveOutDate" type="date" pattern="dd.MM.yyyy" />
	</c:otherwise>
</c:choose>

<h1 id="shownAdTitle">${shownAd.title}</h1>

<hr />

<section>
	<c:choose>
		<c:when test="${loggedIn}">
			<c:if test="${loggedInUserEmail == shownAd.user.username }">
				<a href="<c:url value='/profile/editAd?id=${shownAd.id}' />">
					<button type="button">Edit Ad</button>
				</a>
			</c:if>
		</c:when>
	</c:choose>
	<br>
	<br>

	<table id="adDescTable" class="adDescDiv">
		<tr>
			<td><h2>Type</h2></td>
			<td>
				<c:choose>
					<c:when test="${shownAd.category == 'studio' }">Studio</c:when>
					<c:when test="${shownAd.category == 'room' }">Room</c:when>
					<c:when test="${shownAd.category == 'house' }">House</c:when>
				</c:choose>
			</td>
		</tr>
		<tr>
			<td><h2>Tenure</h2></td>
			<td>
				<c:choose>
					<c:when test="${shownAd.sale}">Auction</c:when>
					<c:otherwise>Something is messed up here!</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
			<td><h2>Address</h2></td>
			<td>
				<a class="link" href="http://maps.google.com/?q=${shownAd.street}, ${shownAd.zipcode}, ${shownAd.city}">${shownAd.street},
						${shownAd.zipcode} ${shownAd.city}</a>
			</td>
		</tr>

		<tr>
			<td><h2>Available from</h2></td>
			<td>${formattedMoveInDate}</td>
		</tr>

		<tr>
			<td><h2>Auction ends on</h2></td>
			<td>${formattedMoveOutDate}</td>
		</tr>

		<tr>
			<td><h2>
				<c:choose>
					<c:when test="${shownAd.sale}">Current price</c:when>
					<c:otherwise>Something is meesed up here!</c:otherwise>
				</c:choose></h2></td>
			<td>${shownAd.prizePerMonth}&#32;CHF</td>
		</tr>

		<tr>
			<td><h2>Square Meters</h2></td>
			<td>${shownAd.squareFootage}&#32;m²</td>
		</tr>
		<tr>
			<td><h2>Ad created on</h2></td>
			<td>${formattedCreationDate}</td>
		</tr>
		<tr>
			<c:choose>
				<c:when test="${loggedIn}">
			<td><h2>Current max. bidder </h2></td>
			<td><p id="highestBidder"></p></td>
		</c:when></c:choose>
		</tr>
	</table>
</section>


<div id="image-slider">
	<div id="left-arrow">
		<img src="/img/left-arrow.png" />
	</div>
	<div id="images">
		<c:forEach items="${shownAd.pictures}" var="picture">
			<img src="${picture.filePath}" />
		</c:forEach>
	</div>
	<div id="right-arrow">
		<img src="/img/right-arrow.png" />
	</div>
</div>
<div id="bidDiv">
<c:choose>
	<c:when test="${formattedMoveOutDate lt formattedToday}">
<a class="right" id="makeBidDisabled">Auction over</a>
	</c:when>
	<c:otherwise>
	<c:choose>
		<c:when test="${loggedIn}">
			<table>
			<tr height="44px">
			<td width="50%" style="vertical-align:top"><a class="right"  id="makeBid">Place bid</a></td>
			<td width="50%" style="vertical-align:middle"><input style="float:right; margin-left: 10px;"
			align="right" type="number" step="50" name="bid" id="bid" min="${ad.prizePerMonth}" value="${shownAd.prizePerMonth}">
			</td></tr></table>
		</c:when>
	</c:choose>
</c:otherwise>
</c:choose>
</div>

<hr class="clearBoth" />

<section>
	<div id="descriptionTexts">
		<div class="adDescDiv">
			<h2>Room Description</h2>
			<p>${shownAd.roomDescription}</p>
		</div>
		<br />

		<div id="visitList" class="adDescDiv">
			<h2>Visiting times</h2>
			<table>
				<c:forEach items="${visits }" var="visit">
					<tr>
						<td>
							<fmt:formatDate value="${visit.startTimestamp}" pattern="dd-MM-yyyy " />
							&nbsp; from
							<fmt:formatDate value="${visit.startTimestamp}" pattern=" HH:mm " />
							until
							<fmt:formatDate value="${visit.endTimestamp}" pattern=" HH:mm" />
						</td>
						<td><c:choose>
								<c:when test="${loggedIn}">
									<c:if test="${loggedInUserEmail != shownAd.user.username}">
										<button class="thinButton" type="button" data-id="${visit.id}">Send
											enquiry to advertiser</button>
									</c:if>
								</c:when>
								<c:otherwise>
									<a href="/login"><button class="thinInactiveButton" type="button"
										data-id="${visit.id}">Login to send enquiries</button></a>
								</c:otherwise>
							</c:choose></td>
					</tr>
				</c:forEach>
			</table>
		</div>

	</div>

	<table id="checkBoxTable" class="adDescDiv">
		<tr>
			<td><h2>Smoking inside allowed</h2></td>
			<td>
				<c:choose>
					<c:when test="${shownAd.smokers}"><img src="/img/check-mark.png"></c:when>
					<c:otherwise><img src="/img/check-mark-negative.png"></c:otherwise>
				</c:choose>
			</td>
		</tr>

		<tr>
			<td><h2>Animals allowed</h2></td>
			<td>
				<c:choose>
					<c:when test="${shownAd.animals}"><img src="/img/check-mark.png"></c:when>
					<c:otherwise><img src="/img/check-mark-negative.png"></c:otherwise>
				</c:choose>
			</td>
		</tr>

		<tr>
			<td><h2>Furnished Room</h2></td>
			<td>
				<c:choose>
					<c:when test="${shownAd.furnished}"><img src="/img/check-mark.png"></c:when>
					<c:otherwise><img src="/img/check-mark-negative.png"></c:otherwise>
				</c:choose>
			</td>
		</tr>

		<tr>
			<td><h2>WiFi available</h2></td>
			<td>
				<c:choose>
					<c:when test="${shownAd.internet}"><img src="/img/check-mark.png"></c:when>
					<c:otherwise><img src="/img/check-mark-negative.png"></c:otherwise>
				</c:choose>
			</td>
		</tr>

		<tr>
			<td><h2>Cable TV</h2></td>
			<td>
				<c:choose>
					<c:when test="${shownAd.cable}"><img src="/img/check-mark.png"></c:when>
					<c:otherwise><img src="/img/check-mark-negative.png"></c:otherwise>
				</c:choose>
			</td>
		</tr>

		<tr>
			<td><h2>Garage</h2></td>
			<td>
				<c:choose>
					<c:when test="${shownAd.garage}"><img src="/img/check-mark.png"></c:when>
					<c:otherwise><img src="/img/check-mark-negative.png"></c:otherwise>
				</c:choose>
			</td>
		</tr>

		<tr>
			<td><h2>Cellar</h2></td>
			<td>
				<c:choose>
					<c:when test="${shownAd.cellar}"><img src="/img/check-mark.png"></c:when>
					<c:otherwise><img src="/img/check-mark-negative.png"></c:otherwise>
				</c:choose>
			</td>
		</tr>

		<tr>
			<td><h2>Balcony</h2></td>
			<td>
				<c:choose>
					<c:when test="${shownAd.balcony}"><img src="/img/check-mark.png"></c:when>
					<c:otherwise><img src="/img/check-mark-negative.png"></c:otherwise>
				</c:choose>
			</td>
		</tr>

		<tr>
			<td><h2>Garden</h2></td>
			<td>
				<c:choose>
					<c:when test="${shownAd.garden}"><img src="/img/check-mark.png"></c:when>
					<c:otherwise><img src="/img/check-mark-negative.png"></c:otherwise>
				</c:choose>
			</td>
		</tr>

	</table>
</section>

<div class="clearBoth"></div>
<br>

<table id="advertiserTable" class="adDescDiv">
	<tr>
	<td><h2>Advertiser</h2><br /></td>
	</tr>

	<tr>
		<td><c:choose>
				<c:when test="${shownAd.user.picture.filePath != null}">
					<img src="${shownAd.user.picture.filePath}">
				</c:when>
				<c:otherwise>
					<img src="/img/avatar.png">
				</c:otherwise>
			</c:choose></td>

		<td>${shownAd.user.username}</td>

		<td id="advertiserEmail">
		<c:choose>
			<c:when test="${loggedIn}">
				<%@include file='/pages/getUserPicture.jsp' %>
				<a id="currentUserId" style=" display: none" ><% out.print( realUser.getId() ); %></a>

				<a href="/user?id=${shownAd.user.id}"><button type="button">Visit profile</button></a>
			</c:when>
			<c:otherwise>
				<a href="/login"><button class="thinInactiveButton" type="button">Login to visit profile</button></a>
			</c:otherwise>
		</c:choose>

		<td>
			<form>
				<c:choose>
					<c:when test="${loggedIn}">
						<c:if test="${loggedInUserEmail != shownAd.user.username }">
							<button id="newMsg" type="button">Contact Advertiser</button>
						</c:if>
					</c:when>
					<c:otherwise>
						<a href="/login"><button class="thinInactiveButton" type="button">Login to contact advertiser</button></a>
					</c:otherwise>
				</c:choose>
			</form>
		</td>
	</tr>
</table>

<script>
$(window).ready(amIMaxBidder());
function amIMaxBidder(){
var currentUserId1 = document.getElementById('currentUserId').innerHTML;
var currentUserId = parseInt(currentUserId1);
var maxBidderID = ${shownAd.maxBidder.id};
if(currentUserId - maxBidderID == 0){
	document.getElementById('highestBidder').innerHTML="You are the highest bidder";
}else{
	document.getElementById('highestBidder').innerHTML="Someone else placed a higher bid";
}}
</script>
<script>
//contacts the advertiser and maxBidder when the auction is over.
$(document).ready(hasEnded());
function hasEnded(){

	if(${formattedMoveOutDate lt formattedToday}&& !${shownAd.hasEnded}){
		//maxBidder
		var subject = "${shownAd.title}";
		var text = "Hello ${shownAd.maxBidder.firstName} the auction for ${shownAd.title} has ended and you are the winner. You will be contacted by: ${shownAd.user.firstName} ${shownAd.user.lastName} as soon as possible.";
		var recipientEmail = "${shownAd.maxBidder.username}";
		$.post("profile/messages/sendMessage", {subject : subject, text: text, recipientEmail : recipientEmail});
		//advertiser
		var subject1 = "${shownAd.title}";
		var text1 = "Hello ${shownAd.user.firstName} the auction for ${shownAd.title} has ended and ${shownAd.maxBidder.firstName} ${shownAd.maxBidder.lastName} is the winner. Contact him: ${shownAd.maxBidder.username}";
		var recipientEmail1 = "${shownAd.user.username}";
		$.post("profile/messages/sendMessage", {subject : subject1, text: text1, recipientEmail : recipientEmail1});
		// to be added: hasEnded has to be updated on the ad


}}
</script>
<div id="msgDiv">
<form class="msgForm">
	<h2>Contact the advertiser</h2>
	<br>
	<br>
	<label>Subject: <span>*</span></label>
	<input  class="msgInput" type="text" id="msgSubject" placeholder="Subject" />
	<br><br>
	<label>Message: </label>
	<textarea id="msgTextarea" placeholder="Message" ></textarea>
	<br/>
	<button type="button" id="messageSend">Send</button>
	<button type="button" id="messageCancel">Cancel</button>
	</form>
</div>
<div id="confirmationDialog">
	<form>
	<p>Send enquiry to advertiser?</p>
	<button type="button" id="confirmationDialogSend">Send</button>
	<button type="button" id="confirmationDialogCancel">Cancel</button>
	</form>
</div>


<c:import url="template/footer.jsp" />
