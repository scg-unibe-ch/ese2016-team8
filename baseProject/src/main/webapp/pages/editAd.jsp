<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:import url="template/header.jsp" />

<script src="/js/jquery.ui.widget.js"></script>
<script src="/js/jquery.iframe-transport.js"></script>
<script src="/js/jquery.fileupload.js"></script>

<script src="/js/pictureUploadEditAd.js"></script>

<script src="/js/editAd.js"></script>


<script>
	$(document).ready(function() {
	
		//changes between Sale prize/ Prize per month
    $("#type-sale").on("click", function(){
		document.getElementById('type-rent').checked="";
		document.getElementById('type-auction').checked="";
        document.getElementById('month-Prize').innerHTML="Sale prize";
        document.getElementById('field-moveOutDate').style.display="none";
        document.getElementById('moveOutDate').innerHTML="";
    });
    $("#type-rent").on("click", function(){
		document.getElementById('type-sale').checked="";
		document.getElementById('type-auction').checked="";
        document.getElementById('month-Prize').innerHTML="Prize per Month";
				document.getElementById('month-Prize').innerHTML="Prize per Month";
        document.getElementById('field-moveOutDate').style.display="block";
        document.getElementById('moveOutDate').innerHTML="Move-out date (optional)";
    });

    $("#type-auction").on("click", function(){
    	document.getElementById('type-rent').checked="";
		document.getElementById('type-sale').checked="checked";
		document.getElementById('type-auction').checked="checked";
        document.getElementById('month-Prize').innerHTML="Start Prize";
        document.getElementById('field-Prize').path="startPrize";
        document.getElementById('field-Prize').placeholder="Start Prize";
		document.getElementById('field-moveOutDate').style.display="block";
        document.getElementById('moveOutDate').innerHTML="Auction end date";
    });
		
		$("#field-city").autocomplete({
			minLength : 2
		});
		$("#field-city").autocomplete({
			source : <c:import url="getzipcodes.jsp" />
		});
		$("#field-city").autocomplete("option", {
			enabled : true,
			autoFocus : true
		});
		$("#field-moveInDate").datepicker({
			dateFormat : 'dd-mm-yy'
		});
		$("#field-moveOutDate").datepicker({
			dateFormat : 'dd-mm-yy'
		});
		
		$("#field-visitDay").datepicker({
			dateFormat : 'dd-mm-yy'
		});
		

		
		$("#addbutton").click(function() {
			var text = $("#roomFriends").val();
			var alreadyAdded = $("#addedRoommates").html();
			if(validateForm(text)) {
				$.post("/profile/placeAd/validateEmail",{email: text, alreadyIn: alreadyAdded}, function(data) {
					if(validateForm(data)) {
						// length gibt die Anzahl der Elemente im input.roommateInput an. Dieser wird in index geschrieben und iteriert.
						var index = $("#roommateCell input.roommateInput").length;
						$("#roommateCell").append("<input class='roommateInput' type='hidden' name='registeredRoommateEmails[" + index + "]' value='" + data + "' />");
						$("#addedRoommates").append(data + "; ");
					} else {
						alert(data);
					}});
			}
			else {
				alert("Please enter an e-mail adress");
			}
			 
			// Validates the input for Email Syntax
			function validateForm(text) {
			    var positionAt = text.indexOf("@");
			    var positionDot = text.lastIndexOf(".");
			    if (positionAt< 1 || positionDot<positionAt+2 || positionDot+2>=text.length) {
			        return false;
			    } else {
			    	return true;
			    }
			}
		});
		
		$("#addVisitButton").click(function() {
			var date = $("#field-visitDay").val();
			if(date == ""){
				return;
			}
			
			var startHour = $("#startHour").val();
			var startMinutes = $("#startMinutes").val();
			var endHour = $("#endHour").val();
			var endMinutes = $("#endMinutes").val();
			
			if (startHour > endHour) {
				alert("Invalid times. The visit can't end before being started.");
				return;
			} else if (startHour == endHour && startMinutes >= endMinutes) {
				alert("Invalid times. The visit can't end before being started.");
				return;
			}
			
			var newVisit = date + ";" + startHour + ":" + startMinutes + 
				";" + endHour + ":" + endMinutes; 
			var newVisitLabel = date + " " + startHour + ":" + startMinutes + 
			" to " + endHour + ":" + endMinutes; 
			
			var index = $("#addedVisits input").length;
			
			var label = "<p>" + newVisitLabel + "</p>";
			var input = "<input type='hidden' value='" + newVisit + "' name='visits[" + index + "]' />";
			
			$("#addedVisits").append(label + input);
		});
		
		$(".deleteRoommateButton").click(function()  {
			var userId = $(this).attr("data-user-id");
			var adId = $(this).attr("data-ad-id");
			var row = $(this).parent().parent();
			$.post("/profile/editAd/deleteRoommate", {userId: userId, adId: adId}, function() {
				$(row).animate({opacity: 0}, 300, function() {$(row).remove(); } );
			});
		
		});
	});
	
	function checkDates(){
			
			var moveInDateString = $("#field-moveInDate").val();
			var moveOutDateString = $("#field-moveOutDate").val();
			var moveInDate = new Date();
			var moveOutDate = new Date();
			
			moveInDate.setFullYear(moveInDateString.substring(6), moveInDateString.substring(3,5), moveInDateString.substring(0,2));
			moveOutDate.setFullYear(moveOutDateString.substring(6), moveOutDateString.substring(3,5), moveOutDateString.substring(0,2));
			
			if(!moveOutDateString || moveOutDateString.length == 0){
				return true;
			}else if(moveOutDate.getTime() < moveInDate.getTime()){
				alert("Invalid dates. The move out date can't be before the move in.");
				return false;
			}else{
				return true;
			}
		};
	
	
	
</script>

<!-- format the dates -->
<fmt:formatDate value="${ad.moveInDate}" var="formattedMoveInDate"
	type="date" pattern="dd-MM-yyyy" />
<fmt:formatDate value="${ad.moveOutDate}" var="formattedMoveOutDate"
	type="date" pattern="dd-MM-yyyy" />
<c:set var="formattedAuctionEndDate" value="0"/>
<c:choose>
<c:when test="${ad.auction}">
	<fmt:formatDate value="${ad.moveOutDate}" var="formattedAuctionEndDate"
	type="date" pattern="dd-MM-yyyy" />
</c:when>
</c:choose>
	
<pre><a href="/">Home</a>   &gt;   <a href="/profile/myRooms">My Rooms</a>   &gt;   <a href="/ad?id=${ad.id}">Ad Description</a>   &gt;   Edit Ad</pre>


<h1>Edit Ad</h1>
<hr />

<form:form method="post" modelAttribute="placeAdForm"
	action="/profile/editAd" id="placeAdForm" autocomplete="off"
	enctype="multipart/form-data">

<input type="hidden" name="adId" value="${ad.id }" />

	<fieldset>
		<legend>Change General info</legend>
		<table class="placeAdTable">
			<tr>
				<td><label for="field-title">Ad Title</label></td>
				<td><label for="type-room">Type:</label></td>
			</tr>
			<tr height="40px">
				<td><form:input id="field-title" path="title"
						value="${ad.title}"/></td>
				<td>
				<c:choose>
				<c:when test="${ad.category == 'room' }">
					<form:radiobutton id="type-room" path="category" value="room" checked="checked"/>Room 
					<form:radiobutton id="type-studio" path="category" value="studio" />Studio 
					<form:radiobutton id="type-house" path="category" value="house" />House
				</c:when>
				<c:when test="${ad.category == 'studio'}">
					<form:radiobutton id="type-room" path="category" value="room" />Room 
					<form:radiobutton id="type-studio" path="category" value="studio" checked="checked"/>Studio 
					<form:radiobutton id="type-house" path="category" value="house" />House
				</c:when>
				<c:when test="${ad.category == 'house'}">
					<form:radiobutton id="type-room" path="category" value="room" />Room 
					<form:radiobutton id="type-studio" path="category" value="studio" />Studio 
					<form:radiobutton id="type-house" path="category" value="house" checked="checked"/>House
				</c:when>
				</c:choose>
				</td>
				<td>
				<c:choose>
				<c:when test="${!ad.auction}">
					<c:choose>
					<c:when test="${ad.sale}">
						<form:radiobutton id="type-rent" path="sale" value="0" />Rent 
						<form:radiobutton id="type-sale" path="sale" value="1" checked="checked" />Sale
					</c:when>
					<c:otherwise>
						<form:radiobutton id="type-rent" path="sale" value="0" checked="checked"/>Rent 
						<form:radiobutton id="type-sale" path="sale" value="1" />Sale
					</c:otherwise>
					</c:choose>
					</td>
					<td>
					<form:radiobutton id="type-auction" path="auction" value="1"/>Auction
					</td>
				</c:when>
				<c:otherwise>
				With auctions, type can't be changed
				<div style="display:none;">
				<form:radiobutton id="type-sale" path="sale" value="1" checked="checked" />
				<form:radiobutton id="type-auction" path="auction" value="1" checked="checked"/>Auction
				</div>
				</td>
				</c:otherwise>
				</c:choose>
			</tr>

			<tr>
				<td><label for="field-street">Street</label></td>
				<td><label for="field-city">City / Zip code</label></td>
			</tr>

			<tr>
				<td><form:input id="field-street" path="street"
						value="${ad.street}" /></td>
				<td>
					<form:input id="field-city" path="city" value="${ad.zipcode} - ${ad.city}" />
					<form:errors path="city" cssClass="validationErrorText" />
				</td>
			</tr>

			<tr>
				<td><label for="moveInDate">Move-in date</label></td>
				<c:choose>
				<c:when test="${!ad.sale}">
				<td><label id="moveOutDate" for="moveOutDate">Move-out date (optional)</label></td>
				</c:when>
				<c:otherwise>
				<td><label id="moveOutDate" for="moveOutDate"></label></td>
				</c:otherwise>
				</c:choose>
			</tr>
			<tr>
				<td>
					<form:input type="text" id="field-moveInDate"
						path="moveInDate" value="${formattedMoveInDate }"/>
				</td>
				<c:choose>
				<c:when test="${ad.auction}">
				<td>
					<form:input type="text" id="field-moveOutDate" style="display:none;"
						path="moveOutDate" value="${formattedMoveOutDate }"/>
					<form:input type="text" id="field-auctionEndDate"  path="auctionEndDate" required="required" value="${formattedAuctionEndDate}"/>
				</td>
				</c:when>
				<c:when test="${ad.sale}">
				<td>
					<form:input type="text" id="field-moveOutDate" style="display:none;"
						path="moveOutDate" value="${formattedMoveOutDate }"/>
					<form:input type="text" id="field-auctionEndDate" style="display:none;" path="auctionEndDate" required="required" value="${formattedAuctionEndDate}"/>
				</td>
				</c:when>
				<c:otherwise>
				<td>
					<form:input type="text" id="field-moveOutDate" path="moveOutDate" value="${formattedMoveOutDate}"/>
					<form:input type="text" id="field-auctionEndDate" style="display:none;" path="auctionEndDate" required="required" value="${formattedAuctionEndDate}"/>
				</td>
				</c:otherwise>
				</c:choose>
			</tr>

			<tr>
				<td>
				<c:choose>
				<c:when test="${ad.auction}"><label id="month-Prize" for="field-Prize">Price not changeable</label></c:when>
				<c:when test="${ad.sale}"><label id="month-Prize" for="field-Prize">Buy price</label></c:when>
				<c:otherwise><label id="month-Prize" for="field-Prize">Price per month</label></c:otherwise>
				</c:choose>
				</td>
				<td><label for="field-SquareFootage">Square Meters</label></td>
			</tr>
			<tr>
				<td>
				<c:choose>
				<c:when test="${ad.auction}">
				<div style="display:none;">
				<form:input id="field-Prize" path="prize" value="${ad.prizePerMonth }" readonly="readonly" />
				</div>
				Actual: ${ad.prizePerMonth} CHF
				</c:when>
				<c:otherwise><form:input id="field-Prize" type="number" path="prize"
						placeholder="Prize per month" step="50" value="${ad.prizePerMonth }" /> <form:errors
						path="prize" cssClass="validationErrorText" />
				</c:otherwise>
				</c:choose>
				</td>
				<td>
					<form:input id="field-SquareFootage" type="number"
						path="squareFootage" placeholder="Prize per month" step="1" 
						value="${ad.squareFootage }"/> <form:errors
						path="squareFootage" cssClass="validationErrorText" />
				</td>
			</tr>
		</table>
	</fieldset>


	<br />
	<fieldset>
		<legend>Change Room Description</legend>

		<table class="placeAdTable">
			<tr>
				<td>
					<c:choose>
						<c:when test="${ad.smokers}">
							<form:checkbox id="field-smoker" path="smokers" checked="checked" /><label>Smoking
							inside allowed</label>
						</c:when>
						<c:otherwise>
							<form:checkbox id="field-smoker" path="smokers" /><label>Smoking
							inside allowed</label>
						</c:otherwise>
					</c:choose>
				</td>
				
				<td>
					<c:choose>
						<c:when test="${ad.animals}">
							<form:checkbox id="field-animals" path="animals"  checked="checked" /><label>Animals
						allowed</label>
						</c:when>
						<c:otherwise>
							<form:checkbox id="field-animals" path="animals" /><label>Animals
						allowed</label>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<td>
					<c:choose>
						<c:when test="${ad.garden}">
							<form:checkbox id="field-garden" path="garden" checked="checked" /><label>Garden
							(co-use)</label>
						</c:when>
						<c:otherwise>
							<form:checkbox id="field-garden" path="garden" /><label>Garden
							(co-use)</label>
						</c:otherwise>
					</c:choose>
				</td>
				
				<td>
					<c:choose>
						<c:when test="${ad.balcony}">
							<form:checkbox id="field-balcony" path="balcony"  checked="checked" /><label>Balcony
						or Patio</label>
						</c:when>
						<c:otherwise>
							<form:checkbox id="field-balcony" path="balcony" /><label>Balcony
						or Patio</label>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<td>
					<c:choose>
						<c:when test="${ad.cellar}">
							<form:checkbox id="field-cellar" path="cellar" checked="checked" /><label>Cellar
						or Attic</label>
						</c:when>
						<c:otherwise>
							<form:checkbox id="field-cellar" path="cellar" /><label>Cellar
						or Atticd</label>
						</c:otherwise>
					</c:choose>
				</td>
				
				<td>
					<c:choose>
						<c:when test="${ad.furnished}">
							<form:checkbox id="field-furnished" path="furnished"  checked="checked" /><label>Furnished
							</label>
						</c:when>
						<c:otherwise>
							<form:checkbox id="field-furnished" path="furnished" /><label>Furnished</label>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<td>
					<c:choose>
						<c:when test="${ad.cable}">
							<form:checkbox id="field-cable" path="cable" checked="checked" /><label>Cable TV</label>
						</c:when>
						<c:otherwise>
							<form:checkbox id="field-cable" path="cable" /><label>Cable TV</label>
						</c:otherwise>
					</c:choose>
				</td>
				
				<td>
					<c:choose>
						<c:when test="${ad.garage}">
							<form:checkbox id="field-garage" path="garage"  checked="checked" /><label>Garage
							</label>
						</c:when>
						<c:otherwise>
							<form:checkbox id="field-garage" path="garage" /><label>Garage</label>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<td>
					<c:choose>
						<c:when test="${ad.internet}">
							<form:checkbox id="field-internet" path="internet"  checked="checked" /><label>WiFi available
							</label>
						</c:when>
						<c:otherwise>
							<form:checkbox id="field-internet" path="internet" /><label>WiFi available</label>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>

		</table>
		<br />
		<form:textarea path="roomDescription" rows="10" cols="100" value="${ad.roomDescription}" />
		<form:errors path="roomDescription" cssClass="validationErrorText" />
	</fieldset>

	<br />
	<fieldset>
		<legend>Change preferences</legend>
		<form:textarea path="preferences" rows="5" cols="100"
			value="${ad.preferences}" ></form:textarea>
	</fieldset>

	
	<fieldset>
		<legend>Add visiting times</legend>
		
		<table>
			<tr>
				<td>
					<input type="text" id="field-visitDay" />
					
					<select id="startHour">
 					<% 
 						for(int i = 0; i < 24; i++){
 							String hour = String.format("%02d", i);
							out.print("<option value=\"" + hour + "\">" + hour + "</option>");
 						}
 					%>
					</select>
					
					<select id="startMinutes">
 					<% 
 						for(int i = 0; i < 60; i++){
 							String minute = String.format("%02d", i);
							out.print("<option value=\"" + minute + "\">" + minute + "</option>");
 						}
 					%>
					</select>
					
					<span>to&thinsp; </span>
					
					<select id="endHour">
 					<% 
 						for(int i = 0; i < 24; i++){
 							String hour = String.format("%02d", i);
							out.print("<option value=\"" + hour + "\">" + hour + "</option>");
 						}
 					%>
					</select>
					
					<select id="endMinutes">
 					<% 
 						for(int i = 0; i < 60; i++){
 							String minute = String.format("%02d", i);
							out.print("<option value=\"" + minute + "\">" + minute + "</option>");
 						}
 					%>
					</select>
			

					<div id="addVisitButton" class="smallPlusButton">+</div>
					
					<div id="addedVisits"></div>
				</td>
				
			</tr>
			
		</table>
		<br>
	</fieldset>

	<br />

	<fieldset>
		<legend>Change pictures</legend>
		<h3>Delete existing pictures</h3>
		<br />
		<div>
			<c:forEach items="${ad.pictures }" var="picture">
				<div class="pictureThumbnail">
					<div>
					<img src="${picture.filePath}" />
					</div>
					<button type="button" data-ad-id="${ad.id }" data-picture-id="${picture.id }">Delete</button>
				</div>
			</c:forEach>
		</div>
		<p class="clearBoth"></p>
		<br /><br />
		<hr />
		<h3>Add new pictures</h3>
		<br />
		<label for="field-pictures">Pictures</label> <input
			type="file" id="field-pictures" accept="image/*" multiple="multiple" />
		<table id="uploaded-pictures" class="styledTable">
			<tr>
				<th id="name-column">Uploaded picture</th>
				<th>Size</th>
				<th>Delete</th>
			</tr>
		</table>
		<br>
	</fieldset>

	<div>
		<button type="submit" onclick="return checkDates();">Submit</button>
		<c:choose>
		<c:when test="${ad.auction}">
		<a href="<c:url value='/auction?id=${ad.id}' />">
		</c:when>
		<c:otherwise><a href="<c:url value='/ad?id=${ad.id}' />"></c:otherwise>
		</c:choose> 
			<button type="button">Cancel</button>
		</a>
	</div>

</form:form>


<c:import url="template/footer.jsp" />
