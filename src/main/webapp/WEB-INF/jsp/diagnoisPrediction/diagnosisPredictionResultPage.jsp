<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="springForm" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page isELIgnored="false" %>

<html>
<head>
    <META http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <sec:csrfMetaTags/>
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">

    <link rel="stylesheet" href="/css/doctorPageMarkUp.css"/>
    <link rel="stylesheet" href="/css/listOfEntries.css"/>
    <link rel="stylesheet" href="/css/doctorPage.css">
    <link rel="stylesheet" href="/css/pagination.css">
    <link rel="stylesheet" href="/css/navbar.css">

    <title><spring:message code="diagnosisPrediction.predictionResultPage.title"/></title>
    <style>
        .action-button-container {
            display: flex;
            width: 100%;
        }
        .action-button {
            display: inline-block;
            width: 100%;
        }

        .hidden-form {
            /*display: none;*/
            display: block;
            padding: 1em;
            width: available;
            margin: auto;
        }

        .row.content {
            height: available;
        }

        .text-field-label {
            float: left;
            margin: 1em;
        }

        .text-field {
            /*margin: 1em;*/
            margin: auto;
        }

        .table-wrapper {
            box-shadow: none;
        }
    </style>
</head>
<body>
<%@ include file="../reusable/navbar.jspf"%>
<div class="container-fluid text-center">
    <div class="row content">
        <div class="col-sm-2 sidenav">


        </div>
        <div class="col-sm-8 text-left container">
            <div class="table-wrapper">
                <div class="table-title">
                    <div class="row">
                        <div class="col-sm-4">
                            <h2><spring:message code="diagnosisPrediction.predictResultPage.name"/></h2>
                        </div>
                    </div>
                </div>
                <h2><spring:message code="diagnosisPrediction.predictResultPage.explanation"/> <b><c:out value="${prediction.name}"/></b>
                </h2>
                <h3><spring:message code="diagnosisPrediction.predictResultPage.accuracy"/> <c:out value="${prediction.accuracy}"/>%</h3>

                <div id="selectedSymptoms">

                </div>
                <div class="action-button-container">
                    <button type="button" class="btn btn-primary btn-lg action-button"><spring:message code="diagnosisPrediction.predictResultPage.askHelp"/></button>
                    <button type="button" class="btn btn-primary btn-lg action-button"><spring:message code="diagnosisPrediction.predictResultPage.predictDifferentDiagnosis"/></button>
                </div>

                <div id="addDiagnosis" class="hidden-form">
                    <springForm:form method="POST" modelAttribute="prediction" action="/diagnosis-prediction/askHelp">
<%--                        <input name="${_csrf.parameterName}" value="${_csrf.token}" type="hidden">--%>
                        <springForm:input type="hidden" path="name"/>
                        <springForm:input type="hidden" path="accuracy"/>
                        <springForm:input type="hidden" path="symptoms"/>
                        <div class="form-group">
                            <label class="text-field-label"><spring:message code="diagnosisPrediction.predictResultPage.addMoreInfo"/></label>
                            <springForm:textarea path="comments" type="text" class="form-control input-description text-field"/>
                        </div>
                        <button role="button" class="btn btn-primary btn-lg action-button"><spring:message code="diagnosisPrediction.predictResultPage.createRequestForHelp"/></button>
                    </springForm:form>
                </div>

            </div>
        </div>
        <div class="col-sm-2 sidenav">


        </div>
    </div>
</div>
</body>