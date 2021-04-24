<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="springForm" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions"%>
<%@ page isELIgnored="false" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <META http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <sec:csrfMetaTags/>
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/css/bootstrap-select.min.css">

    <link rel="stylesheet" href="/css/doctorPageMarkUp.css"/>
    <link rel="stylesheet" href="/css/listOfEntries.css"/>
    <link rel="stylesheet" href="/css/doctorPage.css">
    <link rel="stylesheet" href="/css/pagination.css"/>
    <link rel="stylesheet" href="/css/predictDiagnosisPageMarkup.css">

    <title><spring:message code="diagnosisPrediction.prediction.title"/></title>
    <spring:message var="dateFormat" code="dateFormat"/>
    <c:set var="foramter" value='${DateTimeFormatter.ofPattern(dateFormat)}'/>
</head>
<body>
<%@ include file="../reusable/navbar.jspf"%>
<div class="container-fluid text-center">
    <div class="row content">
        <div class="col-sm-2 sidenav"></div>

        <div class="col-sm-8 text-left container">
            <div class="table-wrapper">
                <spring:message var="tableName" code="diagnosisPrediction.predictHelpList.tableName"/>
                <c:set var="refreshLink" value="/shop/{page}?recordsPerPage={records}"/>
                <%@ include file="../reusable/pageableTableTitle.jspf"%>

                <%@ include file="../reusable/pageableTableEntriesSelector.jspf"%>

                <table class="table table-striped table-hover">
                    <thead>
                    <tr>
                        <th><spring:message code="pagienation.id"/></th>
                        <th><spring:message code="diagnosisPrediction.shop.name"/></th>
                        <th><spring:message code="diagnosisPrediction.shop.description"/></th>
                        <th><spring:message code="diagnosisPrediction.shop.price"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${page.content}" var="product">
                        <tr>
                            <th><c:out value="${product.productId}"/></th>
                            <th><c:out value="${product.name}"/></th>
                            <th><c:out value="${product.description}"/></th>
                            <th><fmt:formatNumber type = "number" maxFractionDigits = "0" value = "${product.price/100}"/>.<c:out value="${product.price%100}"/></th>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

                <%@ include file="../reusable/pageableTableFooter.jspf"%>
            </div>
        </div>

        <div class="col-sm-2 sidenav"></div>
    </div>
</div>

<footer class="container-fluid text-center">
</footer>

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