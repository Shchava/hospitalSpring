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
<div class="container login-container">
    <div class="row">
        <div class="col login-form-1">
            <h3><spring:message code="login.label"/></h3>
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
            </form>
        </div>
    </div>
</div>
</body>
</html>
