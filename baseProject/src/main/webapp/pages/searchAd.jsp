<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:import url="template/header.jsp" />

<pre><a href="/">Home</a>   &gt;   Search</pre>

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

	filtered.checked = false;
}
</script>

<h1>Search for an ad</h1>
<hr />

<form:form method="post" modelAttribute="searchForm" action="/results"
	id="searchForm" autocomplete="off">

	<fieldset>
		<table>
		<tr>
			<td><form:checkbox name="room" id="room" path="roomHelper" /><label>Room</label></td>
			<td><form:checkbox name="studio" id="studio" path="studioHelper" /><label>Studio</label></td>
			<td><form:checkbox name="house" id="house" path="houseHelper" /><label>House</label></td>
			<form:input style="display:none" type="text" name="category" id="category" path="category"/>
			<form:checkbox style="display:none" name="filtered" id="filtered" path="filtered" />
		</tr>
		<tr>
			<td><form:checkbox name="rent" id="rent" path="rentHelper" checked="checked"/><label>Rent</label></td>
			<td><form:checkbox name="sale" id="sale" path="saleHelper" checked="checked"/><label>Sale</label></td>
		<form:checkbox style="display:none" name="saleType" id="saleType" path="sale" />
		<form:checkbox style="display:none" name="bothType" id="bothType" path="bothRentAndSale" />
		</tr>
		</table>
		<br/>
		<label for="city">City / zip code:</label>
		<form:input type="text" name="city" id="city" path="city"
			placeholder="e.g. Bern" tabindex="3" />
		<form:errors path="city" cssClass="validationErrorText" />


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
		<br />

		<br />
		<button type="submit" tabindex="7" onClick="validateType(this.form)">Search</button>
		<button type="reset" tabindex="8">Cancel</button>
	</fieldset>

</form:form>

<c:import url="template/footer.jsp" />
