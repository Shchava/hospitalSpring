<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="springForm" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions"%>
<%@ page isELIgnored="false" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

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
    <link rel="stylesheet" href="/css/navbar.css">

    <title><spring:message code="diagnosisPrediction.prediction.title"/></title>
    <spring:message var="dateFormat" code="dateFormat"/>
    <c:set var="foramter" value='${DateTimeFormatter.ofPattern(dateFormat)}'/>
</head>
<body>
<%@ include file="../reusable/navbar.jspf"%>
<div class="container-fluid main-content">
    <div class="row flex-xl-nowrap  main-content-inner">
        <div class="col-sm-2 sidenav"></div>

        <div class="col-sm-8 text-left container">
            <div class="table-wrapper">
                <spring:message var="tableName" code="diagnosisPrediction.predictHelpList.tableName"/>
                <c:set var="refreshLink" value="/diagnosis-prediction/help/list/{page}?recordsPerPage={records}"/>
                <%@ include file="../reusable/pageableTableTitle.jspf"%>

                <%@ include file="../reusable/pageableTableEntriesSelector.jspf"%>

                <table class="table table-striped table-hover">
                    <thead>
                    <tr>
                        <th><spring:message code="pagienation.id"/></th>
                        <th><spring:message code="diagnosisPrediction.prediction.list.creator"/></th>
                        <th><spring:message code="diagnosisPrediction.prediction.list.predictedDiagnosis"/></th>
                        <th><spring:message code="diagnosisPrediction.prediction.list.created"/></th>
                        <th><spring:message code="diagnosisPrediction.prediction.list.lastComment"/></th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${page.content}" var="helpRequest">
                        <tr>
                            <th><c:out value="${helpRequest.idPrediction}"/></th>
                            <th><c:out value="${helpRequest.patient.name}"/> <c:out value="${helpRequest.patient.patronymic}"/> <c:out
                                    value="${helpRequest.patient.surname}"/></th>
                            <th><span class="display-diagnosis"><c:out value="${helpRequest.predictedName}"/></span></th>
                            <th><c:out value="${helpRequest.created.format(foramter)}"/></th>
                            <th><c:if test="${!empty helpRequest.messages}">
                                <c:out value="${helpRequest.messages[fn:length(helpRequest.messages) - 1].date.format(foramter)}"/>
                                </c:if>
                            </th>
                            <th><a class="btn btn-primary" href="/diagnosis-prediction/help${helpRequest.idPrediction}" role="button">
                                <spring:message code="doctor.page.patientsList.open"/></a>
                            </th>
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

</body>
<script src="/webjars/jquery/3.4.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
        integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
        integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
        crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
        integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
        crossorigin="anonymous"></script>
<script src="/js/updateDiagnosisLabels.js"></script>
<script>
    updateDiagnosisLabels("${pageContext.response.locale}");
</script>
</html>