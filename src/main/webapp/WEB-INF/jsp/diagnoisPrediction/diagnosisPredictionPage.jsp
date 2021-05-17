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
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/css/bootstrap-select.min.css">

    <link rel="stylesheet" href="/css/doctorPageMarkUp.css"/>
    <link rel="stylesheet" href="/css/listOfEntries.css"/>
    <link rel="stylesheet" href="/css/doctorPage.css">
    <link rel="stylesheet" href="/css/pagination.css">
    <link rel="stylesheet" href="/css/predictDiagnosisPageMarkup.css">
    <link rel="stylesheet" href="/css/navbar.css">

    <title><spring:message code="diagnosisPrediction.prediction.title"/></title>
</head>
<spring:message var="selectSymptomMessage" code="diagnosisPrediction.predictResultPage.selectSymptom"/>
<body>
<%@ include file="../reusable/navbar.jspf"%>
<div class="container-fluid main-content">
    <div class="row flex-xl-nowrap  main-content-inner">
        <div class="col-sm-2 sidenav">


        </div>
        <div class="col-sm-8 text-left container">
            <div class="table-wrapper">
                <div class="table-title">
                    <div class="row">
                        <div class="col-sm-4">
                            <h2><spring:message code="diagnosisPrediction.predictPage.name"/></h2>
                        </div>
                    </div>
                </div>
                <div class="select-symptom-box">
                    <select data-live-search="true" id="selectSymptomBox" class="symptom-select ">
                    </select>
                </div>
                <div id="selectedSymptoms">

                </div>
                <div class="select-symptom-box" id="submit-request">
                    <button type="button" class="btn btn-primary btn-lg btn-block symptom-select">submit</button>
                </div>

                <form id="submitForm" method="post" action="/diagnosis-prediction/predict" target="_self">
                    <input name="${_csrf.parameterName}" value="${_csrf.token}" type="hidden">
                    <input type="hidden" id="symptomsField" name="symptoms"/>
                </form>

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
<script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/js/bootstrap-select.min.js"></script>
<script src="/js/setupSymptomSelector.js"></script>
</body>

<script>
    $(document).ready(function () {
        setupSymptomSelector("${pageContext.response.locale}", "${selectSymptomMessage}");

        $("#submit-request").on('click', () => {
            let data = {};
            data.symptoms = [];

            for (let symptomAlert of $("#selectedSymptoms > div")) {
                data.symptoms.push(symptomAlert.getAttribute("symptom-id"))
            }

            let form = $("#submitForm");
            $("#symptomsField").val(JSON.stringify(data));

            console.log($("#symptomsField").value)
            form.submit();
        })
    });
</script>
</html>
