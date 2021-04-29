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
        .number-column {
            width: 20%;
        !important;
        }

        .text-column {
            width: 100%;
        }


        .fill-order-form form {
            color: #9ba5a8;
            /*border-radius: 3px;*/
            /*margin-bottom: 5px;*/
            /*background: #fff;*/
            /*box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);*/
        }

        .fill-order-form h2 {
            color: #4d4d4d;
            font-weight: bold;
            margin-top: 0;
        }

        .fill-order-form label {
            font-weight: normal;
            font-size: 13px;
            float: left;
        }

        .fill-order-form .btn {
            font-size: 16px;
            font-weight: bold;
            background: #49b293;
            border: none;
            min-width: 140px;
        }

        .fill-order-form .btn:hover, .signup-form .btn:focus {
            background: #3d9f81;
        }


        .fill-order-form a:hover {
            text-decoration: none;
        }

        .check-box-container {
            display: flex;
            padding-top: 0.3em;
            padding-bottom: 0.3em;
        }

        .check-box {
            margin-left: 0.5em;
            margin-top: auto;
            margin-bottom: auto;
        }

        .submit-button {
            width: 100%;
        }

        h2 {
            text-align: center;
        }
        h4 {
            text-align: center;
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
                    <h2><spring:message code="shop.buyPage.title"/></h2>
                </div>

                <h4><spring:message code="shop.buyPage.orderedProducts"/></h4>
                <table class="table table-striped table-hover">
                    <thead>
                    <tr>
                        <th class="col-md-8 text-column"><spring:message code="shop.buyPage.productNameLabel"/></th>
                        <th class="col-md-2 number-column"><spring:message code="shop.buyPage.productPriceLabel"/></th>
                        <th class="col-md-1 number-column" style="width: 20%"><spring:message
                                code="shop.buyPage.productCount"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${order.products}" var="product">
                        <tr>
                            <th>${product.product.name}</th>
                            <th><fmt:formatNumber type="number"
                                                  maxFractionDigits="0"
                                                  value="${product.product.price/100}"/>.<c:out
                                    value="${product.product.price%100}"/> <spring:message
                                    code="diagnosisPrediction.shop.priceHrivna"/></th>
                            <th>${product.count}</th>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

                <h4><spring:message code="shop.buyPage.totalPrice"/> <fmt:formatNumber type="number"
                                                                                       maxFractionDigits="0"
                                                                                       value="${totalPrice/100}"/>.<c:out
                        value="${totalPrice%100}"/> <spring:message
                        code="diagnosisPrediction.shop.priceHrivna"/></h4>


                <div class="fill-order-form">
                    <springForm:form method="POST" modelAttribute="order" action="/shop/buy">
                        <input name="${_csrf.parameterName}" value="${_csrf.token}" type="hidden">
                        <h2><spring:message code="shop.buyPage.orderFormLabel"/></h2>

                        <div class="form-group">
                            <label><spring:message code="shop.buyPage.name"/></label>
                            <springForm:errors path="name" cssClass="alert-danger error-message"/>

                            <springForm:input type="text" class="form-control" path="name" required="required"/>
                        </div>

                        <div class="form-group">
                            <label><spring:message code="shop.buyPage.phone"/></label>
                            <springForm:errors path="phoneNumber" cssClass="alert-danger error-message"/>
                            <springForm:input type="text" class="form-control" path="phoneNumber" required="required"/>
                        </div>


                        <div class="form-group">
                            <label><spring:message code="shop.buyPage.email"/></label>
                            <springForm:errors path="email" cssClass="alert-danger error-message"/>
                            <springForm:input type="text" class="form-control" path="email" required="required"/>
                        </div>

                        <div class="form-group">
                            <label><spring:message code="shop.buyPage.address"/></label>
                            <springForm:errors path="address" cssClass="alert-danger error-message"/>
                            <springForm:input type="text" class="form-control" path="address"/>
                        </div>


                        <div class="form-group">
                            <label><spring:message code="shop.buyPage.paymentMethod"/></label>
                            <springForm:errors path="paymentMethod" cssClass="alert-danger error-message"/>
                            <springForm:select path="paymentMethod" class="form-control">
                                <option value="ON_PLACE"><spring:message
                                        code="rshop.buyPage.paymentMethod.onPlace"/></option>
                                <option value="BY_CARD"><spring:message
                                        code="shop.buyPage.paymentMethod.byCard"/></option>
                            </springForm:select>
                        </div>

                        <div class="form-group">
                            <label><spring:message code="shop.buyPage.deleveryMethod"/></label>
                            <springForm:errors path="deliveryMethod" cssClass="alert-danger error-message"/>
                            <springForm:select path="deliveryMethod" class="form-control">
                                <option value="SELF_TAKEN"><spring:message
                                        code="shop.buyPage.deleveryMethod.selfTaken"/></option>
                                <option value="NOVA_POSHTA"><spring:message
                                        code="shop.buyPage.deleveryMethod.novaposhta"/></option>
                                <option value="UKRPOST"><spring:message
                                        code="shop.buyPage.deleveryMethod.ukrpost"/></option>
                            </springForm:select>
                        </div>

                        <div class="form-group">
                            <label><spring:message code="shop.buyPage.comments"/></label>
                            <springForm:errors path="comment" cssClass="alert-danger error-message"/>
                            <springForm:input type="text" class="form-control" path="comment"/>
                        </div>

                        <div class="form-group">
                            <div class="check-box-container">
                                <label><spring:message code="shop.buyPage.receiveCalls"/></label>
                                <springForm:checkbox path="notReceiveCall" cssClass="check-box"/>
                            </div>
                        </div>

                        <c:forEach var="person" items="${order.products}" varStatus="order">
                            <springForm:input path="products[${order.index}]" type="hidden"/>
                            <springForm:input path="products[${order.index}].productOrderId" type="hidden"/>
                            <springForm:input path="products[${order.index}].product" type="hidden"/>
                            <springForm:input path="products[${order.index}].count" type="hidden"/>
                        </c:forEach>

                        <div class="form-group">
                            <button type="submit" class="btn btn-primary btn-block btn-lg submit-button"><spring:message
                                    code="shop.buyPage.createOrder"/></button>
                        </div>
                    </springForm:form>
                    .
                </div>


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