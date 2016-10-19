<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:import url="template/header.jsp" />

<pre><a href="/">Home</a>   &gt;   Search</pre>

<script>
	$(document).ready(function() {

		//changes between Sale prize/ Prize per month
		$("#sale").change(function(){
				if($("#sale").is(':checked')&& ! $("#rent").is(':checked')){
					document.getElementById('field-earliestMoveOutDate').style.display="none";
					document.getElementById('field-latestMoveOutDate').style.display="none";
					document.getElementById('earliestMoveOutDateLabel').innerHTML="";
					document.getElementById('latestMoveOutDateLabel').innerHTML="";
				}
				else{
					document.getElementById('field-earliestMoveOutDate').style.display="block";
					document.getElementById('field-latestMoveOutDate').style.display="block";
					document.getElementById('earliestMoveOutDateLabel').innerHTML="Earliest move-out date (optional)"
					document.getElementById('latestMoveOutDateLabel').innerHTML="Latest move-out date (optional)";
				}
		});
		$("#rent").change(function(){
			if(!$("#rent").is(':checked')&&  $("#sale").is(':checked')){
				document.getElementById('field-earliestMoveOutDate').style.display="none";
				document.getElementById('field-latestMoveOutDate').style.display="none";
				document.getElementById('earliestMoveOutDateLabel').innerHTML="";
				document.getElementById('latestMoveOutDateLabel').innerHTML="";
			}
			if($("#rent").is(':checked')){
					document.getElementById('field-earliestMoveOutDate').style.display="block";
					document.getElementById('field-latestMoveOutDate').style.display="block";
					document.getElementById('earliestMoveOutDateLabel').innerHTML="Earliest move-out date (optional)"
					document.getElementById('latestMoveOutDateLabel').innerHTML="Latest move-out date (optional)";
				}
		});

		$("#city").autocomplete({
			minLength : 2
		});
		$("#city").autocomplete({
			source : <c:import url="getzipcodes.jsp" />
		});
		$("#city").autocomplete("option", {
			enabled : true,
			autoFocus : true
		});

		var price = document.getElementById('prizeInput');
		var radius = document.getElementById('radiusInput');

		if(price.value == null || price.value == "" || price.value == "0")
			price.value = "500";
		if(radius.value == null || radius.value == "" || radius.value == "0")
			radius.value = "5";
	});
</script>


<script>
function validateType(form)
{
	var room = document.getElementById('room');
	var studio = document.getElementById('studio');
	var house = document.getElementById('house');
	var neither = document.getElementById('neither');
	var both = document.getElementById('both');
	var type = document.getElementById('type');
	var filtered = document.getElementById('filtered');
	var types = "";

	var sale = document.getElementById('sale');
	var rent = document.getElementById('rent');
	var bothType = document.getElementById('bothType');
	var saleType = document.getElementById('saleType');


	if(sale.checked && rent.checked){
		bothType.checked = true;

	}else if(sale.checked && !rent.checked){
		bothType.checked = false;
		saleType.checked = true;
	}else{
		bothType.checked = false;
		saleType.checked = false;
	}

	if(room.checked){
		types += ",room";
	}

	if(studio.checked){
		types += ",studio";
	}

	if(house.checked) {
		types += ",house";
	}

	document.getElementById('category').value = types;
}

function moreOptions(){
	document.getElementById('basic').style.float="left";
	document.getElementById('moreOptions').style.display="block";
	var filtered = document.getElementById('filtered');
	filtered.checked = true;
}
</script>

<h1>Search for an ad</h1>
<hr />

<form:form method="post" modelAttribute="searchForm" action="/results"
	id="searchForm" autocomplete="off">
	<div>
	<fieldset>
		<div id="basic">
		<table>
		<tr>
			<td><form:checkbox name="room" id="room" path="roomHelper" /><label>Room</label></td>
			<td><form:checkbox name="studio" id="studio" path="studioHelper" /><label>Studio</label></td>
			<td><form:checkbox name="house" id="house" path="houseHelper" /><label>House</label></td>
			<form:input style="display:none" type="text" name="category" id="category" path="category"/>
			<form:checkbox style="display:none" name="filtered" id="filtered" path="filtered" />
		</tr>
		<tr>
			<td><form:checkbox name="rent" id="rent" path="rentHelper" /><label>Rent</label></td>
			<td><form:checkbox name="sale" id="sale" path="saleHelper" /><label>Sale</label></td>
		<form:checkbox style="display:none" name="saleType" id="saleType" path="sale" />
		<form:checkbox style="display:none" name="bothType" id="bothType" path="bothRentAndSale" />
		</tr>
		</table>
		<br/>
		<label for="city">City / zip code:</label>
		<form:input type="text" name="city" id="city" path="city"
			placeholder="e.g. Bern" tabindex="3" />
		<form:errors path="city" cssClass="validationErrorText" />

		<br/>
		<label for="radius">Within radius of (max.):</label>
		<form:input id="radiusInput" type="number" path="radius"
			placeholder="e.g. 5" step="5" />
		km
		<form:errors path="radius" cssClass="validationErrorText" />
		<br /> <label for="prize">Price (max.):</label>
		<form:input id="prizeInput" type="number" path="prize"
			placeholder="e.g. 5" step="50" />
		CHF
		<form:errors path="prize" cssClass="validationErrorText" />
		<br /></div>
		<div id="moreOptions" style="display:none;">
		<table style="margin-left: 400px;">
		<tr>
				<td><label for="earliestMoveInDate">Earliest move-in date</label></td>
				<td><label id="earliestMoveOutDateLabel"for="earliestMoveOutDate">Earliest move-out date (optional)</label></td>
			</tr>
			<tr>
				<td><form:input type="text" id="field-earliestMoveInDate"
						path="earliestMoveInDate" /></td>
				<td><form:input type="text" id="field-earliestMoveOutDate"
						path="earliestMoveOutDate" /></td>
			</tr>
			<tr>
				<td><label for="latestMoveInDate">Latest move-in date</label></td>
				<td><label id="latestMoveOutDateLabel"for="latestMoveOutDate">Latest move-out date (optional)</label></td>
			</tr>
			<tr>
				<td><form:input type="text" id="field-latestMoveInDate"
						path="latestMoveInDate" /></td>
				<td><form:input type="text" id="field-latestMoveOutDate"
						path="latestMoveOutDate" /></td>
			</tr>
			<tr>
				<td><form:checkbox id="field-smoker" path="smokers" value="1" /><label>Smoking inside
						allowed</label></td>
				<td><form:checkbox id="field-animals" path="animals" value="1" /><label>Animals
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
				<td><form:checkbox id="field-internet" path="internet" value="1" /><label>WiFi</label></td>
			</tr>
			</table>
		<br />
		</div>
		<button type="submit" tabindex="7" onClick="validateType(this.form)">Search</button>
		<button type="button" tabindex="8" onClick="moreOptions()">More Options</button>
		<button type="reset" tabindex="9">Cancel</button>
		</div>
	</fieldset>

</form:form>

<c:import url="template/footer.jsp" />
