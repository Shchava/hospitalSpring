<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="springForm" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page isELIgnored="false" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <META http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <sec:csrfMetaTags/>
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<%--    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet"--%>
<%--          integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">--%>
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/css/bootstrap-select.min.css">

    <link rel="stylesheet" href="/css/doctorPageMarkUp.css"/>
    <link rel="stylesheet" href="/css/listOfEntries.css"/>
    <link rel="stylesheet" href="/css/doctorPage.css">
    <link rel="stylesheet" href="/css/pagination.css"/>
    <link rel="stylesheet" href="/css/predictDiagnosisPageMarkup.css">
    <link rel="stylesheet" href="/css/forms.css">

    <title><spring:message code="diagnosisPrediction.prediction.title"/></title>
    <spring:message var="dateFormat" code="dateFormat"/>
    <c:set var="foramter" value='${DateTimeFormatter.ofPattern(dateFormat)}'/>

    <style>
        .main-information {
            display: flex;
        }

        .short-info {
            display: flex;
            flex-direction: column;
            flex-grow: 8;
            width: auto;

            text-align: left;
            padding: 1em;
        }

        .product-description {
            float: right;
        }

        .product-picture {
            max-width: 300px;
            flex-grow: 3;
            padding: 1em;

        }

        .product-picture {
            max-height: 400px;
        }

        .product-instruction {
            word-wrap: break-word;
            text-align: left;
        }

        .instruction-label {
            color: black;
            margin: auto;
            padding-top: 1em;
            padding-bottom: 1em;
        }

        .buy-button {
            flex-grow: 8;
        }

        .add-to-cart-button {
            margin-top: 0.5em;
            width: available;
        }

        .log-alert {
            margin-top: 0.5em;
            margin-bottom: 0.5em;
            height: auto;
        }

        .buy-controls {
            margin-top: auto;
            display: flex;
            flex-direction: column;
        }

        .count-and-add-buy-section {
            display: flex;
            flex-direction: row;
        }
        /*    counter*/

        .qty {
            display: flex;
            width: min-content;
        }

        .qty .count {
            color: #000;
            display: inline-block;
            vertical-align: top;
            font-size: 25px;
            font-weight: 700;
            line-height: 30px;
            padding: 0 2px;
            min-width: 35px;
            text-align: center;
        }

        .qty .plus {
            cursor: pointer;
            display: inline-block;
            vertical-align: top;
            color: white;
            width: 30px;
            height: 30px;
            font: 30px/1 Arial, sans-serif;
            text-align: center;
            border-radius: 50%;
            margin-top: auto;
            margin-bottom: auto;
        }

        .qty .minus {
            cursor: pointer;
            display: inline-block;
            vertical-align: top;
            color: white;
            width: 30px;
            height: 30px;
            font: 30px/1 Arial, sans-serif;
            text-align: center;
            border-radius: 50%;
            background-clip: padding-box;
            margin-top: auto;
            margin-bottom: auto;
            margin-left: 0.5em;
        }

        .minus:hover {
            background-color: #717fe0 !important;
        }

        .plus:hover {
            background-color: #717fe0 !important;
        }
        /*Prevent text selection*/
        span {
            -webkit-user-select: none;
            -moz-user-select: none;
            -ms-user-select: none;
        }

        input {
            border: 0;
            width: 2%;
        }

        nput::-webkit-outer-spin-button,
        input::-webkit-inner-spin-button {
            -webkit-appearance: none;
            margin: 0;
        }

        input:disabled {
            background-color: white;
        }

    </style>
</head>
<body>
<%@ include file="../reusable/navbar.jspf" %>
<div class="container-fluid text-center">
    <div class="row content">
        <div class="col-sm-2 sidenav"></div>

        <div class="col-sm-8 text-left container">
            <div class="table-wrapper">
                <div class="table-title">
                    <h2>${product.name}</h2>
                </div>
                <div class="main-information">
                    <img src="${product.pictureUrl}" alt="product picture" width="500" height="600"
                         class="product-picture">

                    <div class="short-info">
                        <h6><spring:message
                                code="diagnosisPrediction.shop.descriptionLabel"/></h6>
                        <p class="product-description">${product.description}</p>
                        <h6><spring:message
                                code="diagnosisPrediction.shop.manufacturerLabel"/></h6>
                        <p>${product.manufacturer}</p>
                        <h6><spring:message
                                code="diagnosisPrediction.shop.priceLabel"/></h6>
                        <p><fmt:formatNumber type="number" maxFractionDigits="0" value="${product.price/100}"/>.<c:out
                                value="${product.price%100}"/> <spring:message
                                code="diagnosisPrediction.shop.priceHrivna"/></p>


                        <div class="buy-controls">
                            <div class="count-and-add-buy-section">
                                <div class="qty">
                                    <span class="minus bg-dark">-</span>
                                    <input type="number" class="count" name="qty" value="1" id="counter">
                                    <span class="plus bg-dark">+</span>
                                </div>
                                <button type="button" class="btn btn-primary btn-lg btn-block buy-button" id="buyButton">
                                    <spring:message
                                            code="diagnosisPrediction.shop.buyButton"/></button>
                            </div>
                            <sec:authorize access="isAuthenticated()">
                            <button type="button" class="btn btn-primary btn-lg btn-block add-to-cart-button" id="addToCartButton">
                                <spring:message
                                        code="diagnosisPrediction.shop.addToCart"/></button>
                            </sec:authorize>
                            <sec:authorize access="!isAuthenticated()">
                                <div class="alert alert-info log-alert" role="alert">
                                    <spring:message code="diagnosisPrediction.shop.please" /> <a href="/login"><spring:message code="diagnosisPrediction.shop.login" /></a> <spring:message code="diagnosisPrediction.shop.addToCartAvailiability"/></div>
                            </sec:authorize>
                        </div>
                    </div>
                </div>

                <h3 class="instruction-label"><spring:message code="diagnosisPrediction.shop.instructionLabel"/></h3>

                <p class="product-instruction">
                    ${product.instruction}
                </p>

                <springForm:form method="POST" modelAttribute="order" id="buyForm" action="/shop/buySingle">
                    <springForm:input path="product" type="hidden"/>
                    <springForm:input path="count" type="hidden" id="buyFormCount"/>
                </springForm:form>

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

<script>
    $(document).ready(function () {
        $('.count').prop('disabled', true);
        $(document).on('click', '.plus', function () {
            $('.count').val(parseInt($('.count').val()) + 1);
        });
        $(document).on('click', '.minus', function () {
            $('.count').val(parseInt($('.count').val()) - 1);
            if ($('.count').val() == 0) {
                $('.count').val(1);
            }
        });

        $("#buyButton").click(()=>{
            console.log("test");
            let buyForm = $("#buyForm");
            $("#buyFormCount").val($("#counter").val());
            // console.log()
            buyForm.attr("action", "/shop/buySingle");
            buyForm.submit();
        })

        $("#addToCartButton").click(()=>{
            console.log("test");
            let buyForm = $("#buyForm");
            $("#buyFormCount").val($("#counter").val());
            buyForm.attr("action", "/shop/addToCart");
            buyForm.submit();
        })
    });
</script>
</html>