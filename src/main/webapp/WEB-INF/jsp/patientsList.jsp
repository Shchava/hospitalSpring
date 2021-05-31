<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="springForm" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page isELIgnored="false" %>

<html>
<head>
    <META http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">

    <link rel="stylesheet" href="/css/doctorPageMarkUp.css"/>
    <link rel="stylesheet" href="/css/listOfEntries.css"/>
    <link rel="stylesheet" href="/css/doctorPage.css">
    <link rel="stylesheet" href="/css/pagination.css">
    <link rel="stylesheet" href="/css/navbar.css">


    <title><spring:message code="patientList.title"/></title>


</head>
<body>
<%@ include file="reusable/navbar.jspf"%>

<div class="container-fluid main-content">
    <div class="row flex-xl-nowrap  main-content-inner">
        <div class="col-sm-2 sidenav">


        </div>
        <div class="col-sm-8 text-left container">
            <div class="table-wrapper">
                <spring:message var="tableName" code="doctor.page.patientsList.title"/>
                <c:set var="refreshLink" value="/patientsList/{page}?recordsPerPage={records}"/>
                <%@ include file="reusable/pageableTableTitle.jspf"%>

                <%@ include file="reusable/pageableTableEntriesSelector.jspf"%>


                <table class="table table-striped table-hover">
                    <thead>
                    <tr>
                        <th><spring:message code="doctor.page.patientsList.id"/></th>
                        <th><spring:message code="doctor.page.patientsList.patient.name"/></th>
                        <th><spring:message code="doctor.page.patientsList.patient.email"/></th>
                        <th><spring:message code="doctor.page.patientsList.patient.lastDiagnosis"/></th>
                        <th><spring:message code="doctor.page.patientsList.patient.details"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${page.content}" var="patient">
                        <tr>
                            <th><c:out value="${patient.id}"/></th>
                            <th><c:out value="${patient.name}"/> <c:out value="${patient.patronymic}"/> <c:out
                                    value="${patient.surname}"/></th>
                            <th><c:out value="${patient.email}"/></th>
                            <th><span class="display-diagnosis"><c:out value="${patient.lastDiagnosisName}"/></span></th>
                            <th><a class="btn btn-primary" href="/patient${patient.id}/0?recordsPerPage=${page.size}" role="button"><spring:message
                                    code="doctor.page.patientsList.open"/></a>
                            </th>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

                <%@ include file="reusable/pageableTableFooter.jspf"%>
            </div>
        </div>
        <div class="col-sm-2 sidenav">


        </div>
    </div>
</div>

<footer class="container-fluid text-center">
</footer>

<script src="/webjars/jquery/3.4.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
        integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
        crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
        integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
        crossorigin="anonymous"></script>
</body>
</html>
<script src="/js/updateDiagnosisLabels.js"></script>
<script>
    updateDiagnosisLabels("${pageContext.response.locale}");
</script>