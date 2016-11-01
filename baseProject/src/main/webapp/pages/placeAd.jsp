<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:import url="template/header.jsp"/>

<script src="/js/jquery.ui.widget.js"></script>
<script src="/js/jquery.iframe-transport.js"></script>
<script src="/js/jquery.fileupload.js"></script>

<script src="/js/pictureUpload.js"></script>


<script>
	$(document).ready(function() {

		// Go to controller take what you need from user
		// save it to a hidden field
		// iterate through it
		// if there is id == x then make "Bookmark Me" to "bookmarked"

		//changes between Sale prize/ Prize per month
    $("#type-sale").on("click", function(){
		document.getElementById('type-rent').checked="";
		document.getElementById('type-auction').checked="";
        document.getElementById('month-Prize').innerHTML="Sale prize";
        document.getElementById('field-moveOutDate').style.display="none";
        document.getElementById('moveOutDate').innerHTML="";
		document.getElementById('field-auctionEndDate').style.display="none";
    });
    $("#type-rent").on("click", function(){
		document.getElementById('type-sale').checked="";
		document.getElementById('type-auction').checked="";
        document.getElementById('month-Prize').innerHTML="Prize per Month";
		document.getElementById('month-Prize').innerHTML="Prize per Month";
        document.getElementById('field-moveOutDate').style.display="block";
        document.getElementById('moveOutDate').innerHTML="Move-out date (optional)";
		document.getElementById('field-auctionEndDate').style.display="none";
    });

    $("#type-auction").on("click", function(){
    	document.getElementById('type-rent').checked="";
		document.getElementById('type-sale').checked="checked";
		document.getElementById('type-auction').checked="checked";
        document.getElementById('month-Prize').innerHTML="Start Prize";
        document.getElementById('field-Prize').path="startPrize";
        document.getElementById('field-Prize').placeholder="Start Prize";
		document.getElementById('field-moveOutDate').style.display="none";
		document.getElementById('field-auctionEndDate').style.display="block";
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

		$("#field-auctionEndDate").datepicker({
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
	});

	function checkDates(){
			var moveInDate = new Date($("#field-moveInDate").val());
			var moveOutDate = new Date($("#field-moveOutDate").val());

			if(!moveOutDate || moveOutDate.length == 0){
				return true;
			}else if(moveOutDate.getTime() < moveInDate.getTime()){
				alert("Invalid dates. The move out date can't be before the move in.");
				return false;
			}else{
				return true;
			}
		};

</script>

<pre>
	<a href="/">Home</a>   &gt;   Place ad</pre>

<h1>Place an ad</h1>
<hr />

<form:form method="post" modelAttribute="placeAdForm"
	action="/profile/placeAd" id="placeAdForm" autocomplete="off"
	enctype="multipart/form-data">

	<fieldset>
		<legend>General info</legend>
		<table class="placeAdTable">
			<tr>
				<td><label for="field-title">Ad Title</label></td>
				<td><label for="type-room">Type:</label></td>
				<td><label for="type-rent">Tenure:</label></td>
			</tr>

			<tr>
				<td><form:input id="field-title" path="title"
						placeholder="Ad Title" /></td>
				<td><form:radiobutton id="type-room" path="category" value="room"
						checked="checked" />Room <form:radiobutton id="type-studio"
						path="category" value="studio" />Studio <form:radiobutton id="type-house"
						path="category" value="house" />House</td>

				<td><form:radiobutton id="type-rent" path="sale" value="0"
						checked="checked" />Rent <form:radiobutton id="type-sale"
						path="sale" value="1" />Sale</td>
				<td><form:radiobutton id="type-auction" path="auction" value="1" />Auction</td>
			</tr>

			<tr>
				<td><label for="field-street">Street</label></td>
				<td><label for="field-city">City / Zip code</label></td>
			</tr>

			<tr>
				<td><form:input id="field-street" path="street"
						placeholder="Street" /></td>
				<td><form:input id="field-city" path="city" placeholder="City" />
					<form:errors path="city" cssClass="validationErrorText" /></td>
			</tr>

			<tr>
				<td><label for="moveInDate">Move-in date (required)</label></td>
				<td><label id="moveOutDate" for="moveOutDate">Move-out date (optional)</label></td>
			</tr>
			<tr>
				<td><form:input type="text" id="field-moveInDate"
						path="moveInDate" /></td>
				<td><form:input type="text" id="field-moveOutDate"
						path="moveOutDate" />
					<form:input type="text" id="field-auctionEndDate" style="display:none;"
						path="auctionEndDate" /></td>
			</tr>

			<tr>
				<td><label id="month-Prize" for="field-Prize">Prize per month</label></td>
				<td><label for="field-SquareFootage">Square Meters</label></td>
			</tr>
			<tr>
				<td><form:input id="field-Prize" type="number" path="prize"
						placeholder="Prize per month" step="50" /> <form:errors
						path="prize" cssClass="validationErrorText" /></td>
				<td><form:input id="field-SquareFootage" type="number"
						path="squareFootage" placeholder="Prize per month" step="5" /> <form:errors
						path="squareFootage" cssClass="validationErrorText" /></td>
			</tr>
		</table>
	</fieldset>


	<br />
	<fieldset>
		<legend>Room Description</legend>

		<table class="placeAdTable">
			<tr>
				<td><form:checkbox id="field-smoker" path="smokers" value="1" /><label>Animals
						allowed</label></td>
				<td><form:checkbox id="field-animals" path="animals" value="1" /><label>Smoking
						inside allowed</label></td>
			</tr>
			<tr>
				<td><form:checkbox id="field-garden" path="garden" value="1" /><label>Garden
						(co-use)</label></td>
				<td><form:checkbox id="field-balcony" path="balcony" value="1" /><label>Balcony
						or Patio</label></td>
			</tr>
			<tr>
				<td><form:checkbox id="field-cellar" path="cellar" value="1" /><label>Cellar
						or Attic</label></td>
				<td><form:checkbox id="field-furnished" path="furnished"
						value="1" /><label>Furnished</label></td>
			</tr>
			<tr>
				<td><form:checkbox id="field-cable" path="cable" value="1" /><label>Cable
						TV</label></td>
				<td><form:checkbox id="field-garage" path="garage" value="1" /><label>Garage</label>
				</td>
			</tr>
			<tr>
				<td><form:checkbox id="field-internet" path="internet"
						value="1" /><label>WiFi available</label></td>
			</tr>

		</table>
		<br />
		<form:textarea path="roomDescription" rows="10" cols="100"
			placeholder="Room Description" />
		<form:errors path="roomDescription" cssClass="validationErrorText" />
	</fieldset>

	<br />

	<fieldset>
		<legend>Preferences (optional)</legend>
		<form:textarea path="preferences" rows="5" cols="100"
			placeholder="Preferences"></form:textarea>
	</fieldset>

	<fieldset>
		<legend>Pictures (optional)</legend>
		<br /> <label for="field-pictures">Pictures</label> <input
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

	<fieldset>
		<legend>Visiting times (optional)</legend>

		<table>
			<tr>
				<td><input type="text" id="field-visitDay" /> <select
					id="startHour">
						<%
							for (int i = 0; i < 24; i++) {
									String hour = String.format("%02d", i);
									out.print("<option value=\"" + hour + "\">" + hour
											+ "</option>");
								}
						%>
				</select> <select id="startMinutes">
						<%
							for (int i = 0; i < 60; i++) {
									String minute = String.format("%02d", i);
									out.print("<option value=\"" + minute + "\">" + minute
											+ "</option>");
								}
						%>
				</select> <span>to&thinsp; </span> <select id="endHour">
						<%
							for (int i = 0; i < 24; i++) {
									String hour = String.format("%02d", i);
									out.print("<option value=\"" + hour + "\">" + hour
											+ "</option>");
								}
						%>
				</select> <select id="endMinutes">
						<%
							for (int i = 0; i < 60; i++) {
									String minute = String.format("%02d", i);
									out.print("<option value=\"" + minute + "\">" + minute
											+ "</option>");
								}
						%>
				</select>



					<div id="addVisitButton" class="smallPlusButton">+</div>

					<div id="addedVisits"></div></td>

			</tr>

		</table>
		<br>
	</fieldset>



	<br />
	<div>
		<button id="submit" type="submit" onclick="return checkDates();">Submit</button>
		<a href="/"><button type="button">Cancel</button></a>
	</div>

</form:form>

<c:import url="template/footer.jsp" />
