<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<!-- check if user is logged in -->
<security:authorize var="loggedIn" url="/profile" />

<script src="https://apis.google.com/js/platform.js" async defer></script>

<meta name="google-signin-client_id" content="609599959744-qjl118p0tlktjnljrim47l4lgmba9ebb.apps.googleusercontent.com">

<c:import url="template/header.jsp" />

<pre>
	<a href="/">Home</a>   &gt;   Login</pre>

<h1>Login</h1>

<c:choose>
	<c:when test="${loggedIn}">
		<p>You are already logged in!</p>
	</c:when>
	<c:otherwise>
		<c:if test="${!empty param.error}">
			<p>Incorrect email or password. Please retry using correct email
				and password.</p>
			<br />
		</c:if>
		<form id="login-form" method="post" action="/j_spring_security_check">
			<label for="field-email">Email:</label> <input name="j_username"
				id="field-email" /> <label for="field-password">Password:</label> <input
				name="j_password" id="field-password" type="password" />
			<button type="submit">Login</button>
		</form>
		<br />
		<div class="g-signin2" data-onsuccess="onSignIn"></div>
		<br><br>

			Or <a class="link" href="<c:url value="/signup" />">sign up</a> as a new user.

	</c:otherwise>
</c:choose>

<div>
	<form:form id="googleForm" type="hidden" class="form-horizontal" method="post"
		modelAttribute="googleForm" action="google">

			<spring:bind path="firstName">
						<form:input type="hidden" path="firstName" cssClass="form-control"
							id="field-firstName" />
			</spring:bind>

			<spring:bind path="lastName">
						<form:input type="hidden" path="lastName" id="field-lastName"
							cssClass="form-control" />
			</spring:bind>

			<spring:bind path="email">
						<form:input type="hidden" path="email" id="field-mail"
							cssClass="form-control" />
			</spring:bind>

			<spring:bind path="picture">
						<form:input type="hidden" path="picture" id="field-picture"
							cssClass="form-control" />
			</spring:bind>

			<button type="submit" style="visibility:hidden;" class="btn btn-primary" value="signup" id="googleButton"
					>Sign up</button>
	</form:form>
</div>

<script>
	function onSignIn(googleUser) {
	 	var profile = googleUser.getBasicProfile();
		$("#field-firstName").val(profile.getGivenName());
		$("#field-lastName").val(profile.getFamilyName());
		$("#field-mail").val(profile.getEmail());
		$("#field-picture").val(profile.getImageUrl());
		var auth2 = gapi.auth2.getAuthInstance();
    	auth2.signOut();
		$("#googleButton").click();
	}
</script>

<c:import url="template/footer.jsp" />
