<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page isELIgnored="false" %>

<html>
<head>
    <META http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <title><spring:message code="login.title"/></title>
</head>

<body>
<div>
    <nav class="navbar navbar-expand-sm navbar-dark bg-dark">
        <div class="collapse navbar-collapse">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item passive">
                    <label class="navbar-brand">${sessionScope.LoggedUser.name}</label>
                </li>
                <li class="nav-item active">
                    <a class="nav-link" href="/"><spring:message code="header.message"/><span class="sr-only">(current)</span></a>
                </li>
            </ul>
            <ul class="navbar-nav ">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" id="dropdown" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">${pageContext.response.locale.country}</a>
                    <div class="dropdown-menu" aria-labelledby="dropdown">
                        <a class="dropdown-item" href="?lang=<spring:message code="header.language.UA.tag"/>"><spring:message code="header.language.UA"/></a>
                        <a class="dropdown-item" href="?lang=<spring:message code="header.language.EN.tag"/>"><spring:message code="header.language.EN"/></a>
                    </div>
                </li>
            </ul>
        </div>
    </nav>
</div>

<div class="container login-container">
    <div class="row">
        <div class="col login-form-1">


            <h3><spring:message code="login.label"/></h3>
            <c:if test="${registered}">
                <div class="alert alert-info" role="alert"><spring:message code="login.registered" /></div>
            </c:if>
            <c:if test="${logout}">
                <div class="alert alert-info" role="alert"><spring:message code="login.logoutMessage" /></div>
            </c:if>
            <c:if test="${error}">
                <div class="alert alert-danger" role="alert"><spring:message code="login.loginError" /></div>
            </c:if>
            <form method="POST">
                <input name="${_csrf.parameterName}" value="${_csrf.token}" type="hidden">
                <div class="form-group">
                    <input type="text" name="email" class="form-control" placeholder="<spring:message code="login.email"/>" value="" />
                </div>
                <div class="form-group">
                    <input type="password" name="password" class="form-control" placeholder="<spring:message code="login.password"/>" value="" />
                </div>
                <div class="form-group">
                    <input type="submit" class="form-control"  value="<spring:message code="login.login.button" />" />
                </div>
                <p class="text-center"><spring:message code="login.registrationInvitation"/>
                    <a href="/registration"><spring:message code="login.registrationLink"/></a></p>
            </form>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
</body>
</html>
