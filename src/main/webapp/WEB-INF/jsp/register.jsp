<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="springForm" uri="http://www.springframework.org/tags/form" %>
<%@ page isELIgnored="false" %>

<html>
<head>
    <META http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <link rel="stylesheet" href="/css/forms.css">
    <link rel="stylesheet" href="/css/register.css">
    <title><spring:message code="registration.title"/></title>

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
                    <a class="nav-link dropdown-toggle" id="dropdown" data-toggle="dropdown" aria-haspopup="true"
                       aria-expanded="false">${pageContext.response.locale.country}</a>
                    <div class="dropdown-menu" aria-labelledby="dropdown">
                        <a class="dropdown-item"
                           href="?lang=<spring:message code="header.language.UA.tag"/>"><spring:message
                                code="header.language.UA"/></a>
                        <a class="dropdown-item"
                           href="?lang=<spring:message code="header.language.EN.tag"/>"><spring:message
                                code="header.language.EN"/></a>
                    </div>
                </li>
                <li>
                    <a class="nav-link" href="/logout"><spring:message code="header.logout"/><span class="sr-only">(current)</span></a>
                </li>
            </ul>
        </div>
    </nav>
</div>

<div class="container">
    <div class="signup-form">
        <springForm:form method="POST" modelAttribute="user">
            <input name="${_csrf.parameterName}" value="${_csrf.token}" type="hidden">
            <h2><spring:message code="registration.label"/></h2>
            <div class="form-group">
                <label><spring:message code="registration.name"/></label>
                <springForm:errors path="name" cssClass="alert-danger error-message" />

                <springForm:input type="text" class="form-control" path="name" required="required"/>
            </div>
            <div class="form-group">
                <label><spring:message code="registration.surname"/></label>
                <springForm:errors path="surname" cssClass="alert-danger error-message" />
                <springForm:input type="text" class="form-control" path="surname" required="required"/>
            </div>
            <div class="form-group">
                <label><spring:message code="registration.patronymic"/></label>
                <springForm:errors path="patronymic" cssClass="alert-danger error-message" />
                <springForm:input type="text" class="form-control" path="patronymic" required="required"/>

            </div>
            <div class="form-group">
                <label><spring:message code="registration.email"/></label>
                <springForm:errors path="email" cssClass="alert-danger error-message" />
                <springForm:input type="email" class="form-control" path="email" required="required"/>

            </div>
            <div class="form-group">
                <label><spring:message code="registration.password"/></label>
                <springForm:errors path="password" cssClass="alert-danger error-message" />
                <springForm:input type="password" class="form-control" path="password" required="required"/>

            </div>
            <div class="form-group">
                <label><spring:message code="registration.passwordСonfirmation"/></label>
                <springForm:errors element="PasswordMatches"  cssClass="alert-danger error-message" />
                <springForm:input type="password" class="form-control" path="confirmPassword" required="required"/>

            </div>

            <div class="form-group">
                <label><spring:message code="registration.role"/></label>
                <springForm:errors path="role" cssClass="alert-danger error-message" />
                <springForm:select path="role" class="form-control">
                    <option value="PATIENT"><spring:message code="registration.patient"/></option>
                    <option value="NURSE"><spring:message code="registration.nurse"/></option>
                    <option value="DOCTOR"><spring:message code="registration.doctor"/></option>
                    <option value="SHOP_WORKER"><spring:message code="registration.shopWorker"/></option>
                </springForm:select>
            </div>

            <div class="form-group">
                <button type="submit" class="btn btn-primary btn-block btn-lg"><spring:message
                        code="registration.signUp"/></button>
            </div>
            <p class="text-center"><spring:message code="registration.loginInvitation"/>
                <a href="/login"><spring:message code="registration.loginLink"/></a></p>
            <p class="small text-center"><spring:message code="registration.agreement"/></p>
        </springForm:form>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
        integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
        integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
        crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
        integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
        crossorigin="anonymous"></script>
</body>
</html>