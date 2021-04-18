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

    <title><spring:message code="diagnosisPrediction.predictionResultPage.title"/></title>
    <style>
        .symptom-list {
            display: flex;
        }

        .symptom-list-label {
            display: inline-block;
            height: available;
            margin-right: 0.5em;
            text-align: center;
            align-self: center
        }

        .symptom-box {
            margin: 0.5em;
        }

        .action-button-container {
            display: flex;
            width: 100%;
        }
    </style>
</head>
<body>
<%@ include file="../reusable/navbar.jspf" %>
<div class="container-fluid text-center">
    <div class="row content">
        <div class="col-sm-2 sidenav">


        </div>
        <div class="col-sm-8 text-left container">
            <div class="table-wrapper">
                <div class="table-title">
                    <div class="row">
                        <div class="col-sm-4">
                            <h2><spring:message code="diagnosisPrediction.predictHelpPage.name"/></h2>
                        </div>
                    </div>
                </div>
                <h2 class=""><spring:message code="diagnosisPrediction.predictResultPage.explanation"/> <b><c:out
                        value="${helpRequest.predictedName}"/></b>
                </h2>
                <h3><spring:message code="diagnosisPrediction.predictResultPage.accuracy"/> <c:out
                        value="${helpRequest.predictedAccuracy}"/>%</h3>

                <div id="selectedSymptoms" class="symptom-list">
                    <h3 class="symptom-list-label"><spring:message
                            code="diagnosisPrediction.predictResultPage.symptomList"/></h3>

                    <c:forEach items="${helpRequest.symptoms}" var="symptom">
                        <div class="alert alert-info alert-dismissible fade show symptom-box">
                            <c:out value="${symptom}"/>
                            <button class="btn-close" data-dismiss="alert" aria-label="Close"></button>
                        </div>
                    </c:forEach>

                </div>


                <form id="addCommentForm" method="POST" enctype="utf8">
                    <div class="form-group">
                        <label><spring:message code="diagnosisPrediction.predictResultPage.commentLabel"/></label>
                        <textarea id="commentField" type="text" name="comment" class="form-control  input-description"
                                  value=""></textarea>
                    </div>
                </form>

                <div class="action-button-container">
                    <button type="button" class="btn btn-primary btn-lg action-button"><spring:message code="diagnosisPrediction.predictResultPage.addAdditionalSymptom"/></button>
                    <button type="button" id="addCommentButton" class="btn btn-primary btn-lg action-button"><spring:message code="diagnosisPrediction.predictResultPage.addComment"/></button>
                </div>

            </div>
        </div>
        <div class="col-sm-2 sidenav">


        </div>
    </div>
</div>
<script src="/webjars/jquery/3.4.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
        integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
        crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
        integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/js/bootstrap-select.min.js"></script>
</body>

<script>
    $(document).ready(function () {
        $("#addCommentButton").click(function () {

            let comment = $("#commentField").val();

            if(comment === "") {
                return;
            }

            console.log(comment)
            // setupAjax(); todo: uncoment when csrf turned on

            $.ajax({
                type: 'POST',
                url: "/diagnosis-prediction/help${helpRequest.idPrediction}/addComment",
                dataType: 'json',
                contentType: 'application/json',
                data: comment,
                success: function (data) {
                    console.log(data);


                },
                error: function (data) {
                    console.log(data);
                //    todo: implement
                }
            });
        });
    })

    function setupAjax() {
        let token = $("meta[name='_csrf']").attr("content");
        let header = $("meta[name='_csrf_header']").attr("content");

        $.ajaxSetup({
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            }
        });
    }
</script>