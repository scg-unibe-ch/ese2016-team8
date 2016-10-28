<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<c:import url="template/header.jsp" />

<script>
	$(document).ready(function() {
		$("#about-me").val("${currentUser.aboutMe}")
		});
</script>
<script>
function revokePremium(){
	var m = confirm("Are you sure you dont want premium status no more?");
	if(m==true){
		document.getElementById("premium").value= false;
		alert("We are sorry to lose you as a premium member.\n Click on Update to confirm");
		document.forms['editProfile'].submit();
	}else{
		alert("happy to keep you ");
	}

}

function confirmPremium(){

    var r = confirm("Do you want to pay us 5$ for premium access?");
    if (r == true) {
			document.getElementById("premium").value= true;
			document.forms['editProfile'].submit();
    } else {
        alert("maybe next time");
    }

};
</script>

<pre><a href="/">Home</a>   &gt;   <a href="/user?id=${currentUser.id}">Public Profile</a>   &gt;   Edit profile</pre>

<h1>Edit your Profile</h1>
<hr />

<!-- check if user is logged in -->
<security:authorize var="loggedIn" url="/profile" />


<c:choose>
	<c:when test="${loggedIn}">
		<a id="profile_picture_editPage"> <c:import
					url="/pages/getUserPicture.jsp" />
		</a>
	</c:when>
	<c:otherwise>
		<a href="/login">Login</a>
	</c:otherwise>
</c:choose>

<c:choose>
	<c:when test="${currentUser.premium}">
		<h2>You are a premium User </h2><br>
		<button class="button" id="nomorePremium" onclick="revokePremium()">revoke premium</button>
	</c:when>
<c:otherwise>
	<h4> click here to get your premium access: </h4><br>
	<button class="button" id="premiumButton"  onclick="confirmPremium()">get your Premium</button>
	</c:otherwise>
</c:choose>


<form:form method="post" modelAttribute="editProfileForm"
	action="/profile/editProfile" name="editProfile" id="editProfileForm" autocomplete="off"
	enctype="multipart/form-data">

<table class="editProfileTable">
	<tr>
		<td class="spacingTable"><label for="user-name">Username:</label><a>&emsp;</a>
		<form:input id="user-name" path="username" value="${currentUser.username}" /></td>

	</tr>
	<tr>
		<td class="spacingTable"><label for="first-name">First name:</label><a>&emsp;</a>
		<form:input id="first-name" path="firstName" value="${currentUser.firstName}" /></td>
	</tr>
	<tr>
		<td class="spacingTable"><label for="last-name">Last name:</label><a>&emsp;</a>
		<form:input id="last-name" path="lastName" value="${currentUser.lastName}" /></td>
	</tr>
	<tr>
		<td class="spacingTable"><label for="password">Password:</label><a>&emsp;&thinsp;</a>
		<form:input type="password" id="password" path="password" value="${currentUser.password}" /></td>
	</tr>

	<tr style="display: none">
		<td class="spacingTable"><label  for="premium">Premium:</label><a>&emsp;&thinsp;</a>
		<form:input type="premium" id="premium" path="premium" value="${currentUser.premium}" /></td>
	</tr>

	<tr>
		<td class="spacingTable"><label for="about-me">About me:</label><a>&emsp;&thinsp;</a><br>
		<form:textarea id="about-me" path="aboutMe" rows="10" cols="100" /></td>
	</tr>
</table>

<div>
		<button type="submit">Update</button>
</div>

</form:form>


<c:import url="template/footer.jsp" />
