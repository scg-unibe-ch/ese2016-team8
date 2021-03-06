<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:import url="template/header.jsp" />
  </body>
<pre><a href="/">Home</a>   &gt;   <a href="/searchAd/">Search</a>   &gt;   Results</pre>

<script src="http://maps.google.com/maps/api/js" type="text/javascript"></script>

<script>
	// console.log(${fn:length(results)});
	var addresses = new Array(${fn:length(results)});
	var adIds = new Array(${fn:length(results)});
	var i = 0;

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
		}else if(!sale.checked && rent.checked){
			bothType.checked = false;
			saleType.checked = false;
		}else{
			alert("Check a type of tenure!")
			return false;
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
		filtered.checked = true;

		if(types == ""){
			alert("Check at least one type of ads!");
			return false;
		}else{
			return true;
		}
	}
</script>

<script>
	/*
	* This script takes all the resultAd divs and sorts them by a parameter specified by the user.
	* No arguments need to be passed, since the function simply looks up the dropdown selection.
	*/
	function sort_div_attribute() {
		//determine sort modus (by which attribute, asc/desc)
		var sortmode = $('#modus').find(":selected").val();

		//only start the process if a modus has been selected
		if(sortmode.length > 0) {
			var attname;

			//determine which variable we pass to the sort function
			if(sortmode == "price_asc" || sortmode == "price_desc")
				attname = 'data-price';
			else if(sortmode == "moveIn_asc" || sortmode == "moveIn_desc")
				attname = 'data-moveIn';
			else
				attname = 'data-age';

			//copying divs into an array which we're going to sort
			var divsbucket = new Array();
			var divslist = $('div.resultAd');
			var divlength = divslist.length;
			for (a = 0; a < divlength; a++) {
				divsbucket[a] = new Array();
				divsbucket[a][0] = divslist[a].getAttribute(attname);
				divsbucket[a][1] = divslist[a];
				divslist[a].remove();
			}

			//sort the array
			if(attname == "data-price"){
				divsbucket.sort(function(a, b) {
				if (parseInt(a[0]) == parseInt(b[0]))
					return 0;
				else if (parseInt(a[0]) > parseInt(b[0]))
					return 1;
				else
					return -1;
			});
			}else{
				divsbucket.sort(function(a, b) {
				if (Date.parse(a[0]) == Date.parse(b[0]))
					return 0;
				else if (Date.parse(a[0]) > Date.parse(b[0]))
					return 1;
				else
					return -1;
			});
			}


			//invert sorted array for certain sort options
			if(sortmode == "price_desc" || sortmode == "moveIn_asc" || sortmode == "dateAge_asc")
				divsbucket.reverse();

			//insert sorted divs into document again
			for(a = 0; a < divlength; a++)
				$("#resultsDiv").append($(divsbucket[a][1]));
		}
	}
</script>

<script>
	$(document).ready(function() {
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

		$("#field-earliestMoveInDate").datepicker({
			dateFormat : 'dd-mm-yy'
		});
		$("#field-latestMoveInDate").datepicker({
			dateFormat : 'dd-mm-yy'
		});
		$("#field-earliestMoveOutDate").datepicker({
			dateFormat : 'dd-mm-yy'
		});
		$("#field-latestMoveOutDate").datepicker({
			dateFormat : 'dd-mm-yy'
		});

	    var map = new google.maps.Map(document.getElementById('map'), {
	      zoom: 7,
	      center: new google.maps.LatLng(46.8633639,8.213877),
	      mapTypeId: google.maps.MapTypeId.ROADMAP
	    });

	    var infowindow, address, id;
	    var geocoder = new google.maps.Geocoder();

	    for(var k = 0; k < addresses.length; k++){
	    	address = addresses[k];
	    	id = adIds[k];

	    geocoder.geocode({'address': address}, function(results, status, infowindow) {
	    if (status === google.maps.GeocoderStatus.OK) {
	      // map.setCenter(results[0].geometry.location);
	      var marker = new google.maps.Marker({
	        map: map,
	        position: results[0].geometry.location
	      });
	      infowindow = new google.maps.InfoWindow();
	      infowindow.setContent(results[0].formatted_address);
	      // console.log(id);
	      // Problem with anonymous function (see stackoverflow http://stackoverflow.com/questions/750486/javascript-closure-inside-loops-simple-practical-example)
	      marker.addListener('click', function() {
    		infowindow.open(map, marker);
  		  });

	    } else if(status === google.maps.GeocoderStatus.OVER_QUERY_LIMIT){
	    	k--;
	    } else {
	      //alert('Geocode was not successful for the following reason: ' + status);
	    }});
	}
	});
</script>

<h1>Search results:</h1>

<hr />

<div>
<select id="modus">
    <option value="">Sort by:</option>
    <option value="price_asc">Price (ascending)</option>
    <option value="price_desc">Price (descending)</option>
    <option value="moveIn_desc">Move-in date (earliest to latest)</option>
    <option value="moveIn_asc">Move-in date (latest to earliest)</option>
    <option value="dateAge_asc">Date created (youngest to oldest)</option>
    <option value="dateAge_desc">Date created (oldest to youngest)</option>
</select>

<button onClick="sort_div_attribute()">Sort</button>
</div>
<c:choose>
	<c:when test="${empty results}">
		<p>No results found!
	</c:when>
	<c:otherwise>
		<div id="resultsDiv" class="resultsDiv">
			<c:forEach var="ad" items="${results}">
				<div class="resultAd" data-price="${ad.prizePerMonth}"
								data-moveIn="${ad.moveInDate}" data-age="${ad.moveInDate}">
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
									<c:when test="${ad.sale}">Sell</c:when>
									<c:otherwise>Rent</c:otherwise>
								</c:choose>
							</i>
						</p>
					</div>
					<div class="resultRight">
						<h2>CHF ${ad.prizePerMonth }</h2>
						<!--displays premiumStar if the owner of the ad is a premiumUser -->
						<c:choose>
							<c:when test="${ad.user.premium}"><img class="adPremStar" src="/img/Star.png"></c:when>
						</c:choose>
						<br /> <br />

						<fmt:formatDate value="${ad.moveInDate}" var="formattedMoveInDate"
							type="date" pattern="dd.MM.yyyy" />

						<p>Move-in date: ${formattedMoveInDate }</p>
					</div>
				</div>
				<script>
					adIds[i] = "${ad.id}";
					addresses[i] = "${ad.street} ${ad.zipcode} ${ad.city}";
					// console.log(addresses);
					i++;
				</script>
			</c:forEach>
		</div>
	</c:otherwise>
</c:choose>

<form:form method="post" modelAttribute="searchForm" action="/results"
	id="filterForm" autocomplete="off">

	<div id="filterDiv">
		<h2>Filter results:</h2>
		<form:checkbox name="room" id="room" path="roomHelper" /><label>Room</label>
		<form:checkbox name="studio" id="studio" path="studioHelper" /><label>Studio</label>
		<form:checkbox name="house" id="house" path="houseHelper" /><label>House</label>
		<form:input type="text" style="display:none" name="category" id="category" path="category" />
		<form:checkbox style="display:none" name="filtered" id="filtered" path="filtered" />
		<form:errors path="noCategory" cssClass="validationErrorText" /> <br />

		<form:checkbox name="rent" id="rent" path="rentHelper" /><label>Rent</label>
		<form:checkbox name="sale" id="sale" path="saleHelper" /><label>Sale</label>
		<form:checkbox style="display:none" name="saleType" id="saleType" path="sale" />
		<form:checkbox style="display:none" name="bothType" id="bothType" path="bothRentAndSale" />
		<br />

		<label for="city">City / zip code:</label>
		<form:input type="text" name="city" id="city" path="city"
			placeholder="e.g. Bern" tabindex="3" />
		<form:errors path="city" cssClass="validationErrorText" /><br />

		<label for="radius">Within radius of (max.):</label>
		<form:input id="radiusInput" type="number" path="radius"
			placeholder="e.g. 5" step="1" />
		km
		<form:errors path="radius" cssClass="validationErrorText" />
		<br /> <label for="prize">Price (max.):</label>
		<form:input id="prizeInput" type="number" path="prize"
			placeholder="e.g. 5" step="50" />
		CHF
		<form:errors path="prize" cssClass="validationErrorText" /><br />
		<hr class="slim">

		<table style="width: 80%">
			<tr>
				<td><label for="earliestMoveInDate">Earliest move-in date</label></td>
				<td><label for="earliestMoveOutDate">Earliest move-out date (optional)</label></td>
			</tr>
			<tr>
				<td><form:input type="text" id="field-earliestMoveInDate"
						path="earliestMoveInDate" /></td>
				<td><form:input type="text" id="field-earliestMoveOutDate"
						path="earliestMoveOutDate" /></td>
			</tr>
			<tr>
				<td><label for="latestMoveInDate">Latest move-in date</label></td>
				<td><label for="latestMoveOutDate">Latest move-out date (optional)</label></td>
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

		<button type="submit" onClick="return validateType(this.form)">Filter</button>
		<button type="reset">Cancel</button>
		</br></br></br>
		<div id="map" style="width: 300px; height: 400px;"></div>
	</div>
</form:form>

<c:import url="template/footer.jsp" />
