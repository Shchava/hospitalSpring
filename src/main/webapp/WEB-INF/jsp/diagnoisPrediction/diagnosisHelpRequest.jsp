<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="springForm" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page isELIgnored="false" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

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

    <c:set var="dateFormat">
        <spring:message code="dateFormat"/>
    </c:set>
    <c:set var="foramter" value='${DateTimeFormatter.ofPattern(dateFormat)}'/>
    <sec:authentication var="username" property="principal.username"/>


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

        .action-button {
            display: inline-block;
            width: 100%;
        }

        .comments {
            background-color: #eaeaea;
            padding: 0.2em 0.5em;
            border-radius: 0.5em;
        }

        .comment-own {
            margin-left: auto;
        }
        .comment-others {
            margin-right: auto;
        }

        .comment {
            width: 70%;
            background-color: #cde9e6;
            border: 1px solid #031327;
            border-radius: 0.5em;
            margin-bottom: 1em;
        }

        .comment-header {
            display: flex;
            flex-direction: row;
            justify-content: space-between;
            margin: 0.5em;
            height: fit-content;
        }
        .comment-author {
            color: black;
            height: fit-content;
            margin: 0;
        }

        .comment-time {
            color: black;
            height: fit-content;
            margin: 0;
        }

        .comment-content {
            color: black;
            text-align: start;
            margin: 0.5em;

        }

        .comment-field-label {
            float: left;
            color: black;
            text-align: start;
            margin-left: 0.5em;
            margin-top: 1em;
            margin-bottom: 0.2em;
        }

        .hidden {
            display: none;
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
                            <span>
                            <c:out value="${symptom}"/>
                            </span>
                            <button class="btn-close" data-dismiss="alert" aria-label="Close"></button>
                        </div>
                    </c:forEach>

                </div>
                <c:set value="${helpRequest.messages.isEmpty()}" var="hasEmptyComments"/>
                <div class="comments <c:if test="${hasEmptyComments}">hidden</c:if>" id="commentSection">
                    <h5>comments</h5>
                    <c:forEach items="${helpRequest.messages}" var="comment">
                        <c:set value="${comment.author.email eq username}" var="isOwner"/>
                        <div class="comment <c:out value="${isOwner ? 'comment-own': 'comment-others'}"/>">
                            <div class="comment-header">
                                <h6 class="comment-author"><b><c:out value="${comment.author.name}"/> <c:out value="${comment.author.surname}"/></b></h6>
                                <p class="comment-time"><c:out value="${comment.date.format(foramter)}"/></p>
                            </div>

                            <p class="comment-content"><c:out value="${comment.content}"/></p>
                        </div>
                        <c:set var="lastCommentTime" value='${comment.date}'/>
                    </c:forEach>
                </div>

                <form id="addCommentForm" method="POST" enctype="utf8">
                    <div class="form-group">
                        <label class="comment-field-label"><spring:message code="diagnosisPrediction.predictResultPage.commentLabel"/></label>
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
<script src="https://stevenlevithan.com/assets/misc/date.format.js"></script>
</body>

<script>
    const userEmail = "${username}";
    <%--const dateFormatting = "${dateFormat}";--%>
    const dateFormatting = "yyyy.mm.dd hh:MM";
    const refreshInterval = 1500;
    const jspLastCommentDate = "${lastCommentTime}";
    let emptyComments = "${hasEmptyComments}"

    let refreshIntervalFunc;
    let lastCommentDate = new Date(jspLastCommentDate);
    let lasCommentId;

    if(!jspLastCommentDate) {
        lastCommentDate = new Date(-8640000000000000);
    }

    $(document).ready(function () {
        refreshIntervalFunc = window.setInterval(()=>updateComments(), refreshInterval);

        // setupAjax(); todo: uncoment when csrf turned on

        $("#addCommentButton").click(function () {
            let commentField = $("#commentField")

            let comment =commentField.val();

            if(!comment.trim()) {
                return;
            }

            $.ajax({
                type: 'POST',
                url: "/diagnosis-prediction/help${helpRequest.idPrediction}/addComment",
                contentType: 'application/json',
                dataType: 'json',
                data: comment,
                success: function (data) {
                    console.log("comment added")
                    clearInterval(refreshIntervalFunc);

                    lastCommentDate = new Date(data.date);
                    lasCommentId = data.commentId;
                    addComment(data);
                    commentField.val("");
                    refreshIntervalFunc = window.setInterval(()=>updateComments(), refreshInterval);
                },
                error: function (data) {
                    console.log("error")
                    console.log(data);
                //    todo: implement
                }
            });
        });

        updateSymptomLabels();
    })

    function updateComments() {
        $.ajax({
            type: 'GET',
            url: "/diagnosis-prediction/help${helpRequest.idPrediction}/getComments",
            contentType: 'text',
            dataType: 'text',
            success: function (data) {
                let comments = JSON.parse(data);

                comments.forEach(comment => {
                    const commentDate = new Date(comment.date);
                    if (comment.commentId !== lasCommentId && commentDate > lastCommentDate) {
                        addComment(comment);
                        lastCommentDate = commentDate;
                    }
                })
            },
            error: function (data) {
                console.log("error")
                console.log(data);
                //    todo: implement
            }
        });
    }

    function addComment(comment) {
        console.log("lastComment id : " + lasCommentId);
        console.log("lastComment : " + lastCommentDate);
        console.log("new Comment : " + new Date(comment.date));

        const commentSection = $('#commentSection')[0];

        if(emptyComments) {
            commentSection.className = "comments";
            emptyComments = false;
        }

        let commentDiv = document.createElement("div");
        let commentDivClasses = commentDiv.classList;
        commentDivClasses.add("comment");
        if(comment.author.email === userEmail) {
            commentDivClasses.add("comment-own");
        } else {
            commentDivClasses.add("comment-others");
        }

        let commentHeader = document.createElement("div");
        commentHeader.className = "comment-header";

        let authorName = document.createElement("h6");
        authorName.className = "comment-author";
        let authorNameBold = document.createElement("b");

        let commentTime = document.createElement("p");
        commentTime.className = "comment-time";

        let commentContent = document.createElement("p");
        commentContent.className = "comment-content";

        authorNameBold.innerText = comment.author.name + " " + comment.author.surname;
        commentTime.innerHTML =  new Date(comment.date).format(dateFormatting);
        commentContent.innerHTML = comment.content;

        authorName.appendChild(authorNameBold);
        commentHeader.appendChild(authorName);
        commentHeader.appendChild(commentTime)
        commentDiv.appendChild(commentHeader);
        commentDiv.appendChild(commentContent);

        commentSection.appendChild(commentDiv);
    }

    function setupAjax() {
        let token = $("meta[name='_csrf']").attr("content");
        let header = $("meta[name='_csrf_header']").attr("content");

        $.ajaxSetup({
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            }
        });
    }

    function updateSymptomLabels () {
        $(".symptom-box span").each((i, symptom) => {
            console.log( "${pageContext.response.locale}");


            $.ajax({
                type: 'GET',
                url: "/diagnosis-prediction/symptom-translation",
                contentType: 'text',
                dataType: 'text',
                data: {
                    lang: "${pageContext.response.locale}",
                    symptomIdentifier: symptom.innerText
                },
                success: function (data) {
                    symptom.innerText = data
                },
                error: function (data) {
                    console.log("error")
                    console.log(data);
                }
            });



        })
    }
</script>