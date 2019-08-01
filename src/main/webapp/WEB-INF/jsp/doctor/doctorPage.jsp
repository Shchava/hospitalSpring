<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="springForm" uri="http://www.springframework.org/tags/form" %>
<%@ page isELIgnored="false" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <META http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <link rel="stylesheet" href="css/register.css">
    <title><spring:message code="doctor.page.title"/></title>

    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<%--    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round">--%>
    <style>

        /* Set height of the grid so .sidenav can be 100% (adjust as needed) */
        .row.content {height: 100%}

        /* Set gray background color and 100% height */
        .sidenav {
            padding-top: 20px;
            background-color: #f1f1f1;
            height: 100%;
        }

        /* Set black background color, white text and some padding */
        footer {
            background-color: #555;
            color: white;
            padding: 15px;
        }

        /* On small screens, set height to 'auto' for sidenav and grid */
        @media screen and (max-width: 767px) {
            .sidenav {
                height: auto;
                padding: 15px;
            }
            .row.content {height:auto;}
        }

        /*!*grid css*!*/
        .container{
            font-family: 'Varela Round', sans-serif;
            color: #566787;
            margin: 0;
            padding: 0;
            font-size: 13px;
        }
        .table-wrapper {
            background: #fff;
            padding: 20px 25px;
            margin: 0;
            border-radius: 3px;
            box-shadow: 0 1px 1px rgba(0,0,0,.05);
        }
        .table-wrapper .btn {
            float: right;
            color: #333;
            background-color: #fff;
            border-radius: 3px;
            border: none;
            outline: none !important;
            margin-left: 10px;
        }
        .table-wrapper .btn:hover {
            color: #333;
            background: #f2f2f2;
        }
        .table-wrapper .btn.btn-primary {
            color: #fff;
            background: #03A9F4;
        }
        .table-wrapper .btn.btn-primary:hover {
            background: #03a3e7;
        }
        .table-title .btn {
            font-size: 13px;
            border: none;
        }
        .table-title .btn i {
            float: left;
            font-size: 21px;
            margin-right: 5px;
        }
        .table-title .btn span {
            float: left;
            margin-top: 2px;
        }
        .table-title {
            color: #fff;
            background: #4b5366;
            padding: 16px 25px;
            margin: -20px -25px 10px;
            border-radius: 3px 3px 0 0;
        }
        .table-title h2 {
            margin: 5px 0 0;
            font-size: 24px;
        }

        .table-filter .filter-group {
            float: right;
            margin-left: 15px;
        }
        .table-filter input, .table-filter select {
            height: 34px;
            border-radius: 3px;
            border-color: #ddd;
            box-shadow: none;
        }
        .table-filter {
            padding: 5px 0 5px;
            margin-bottom: 0;
        }
        .table-filter .btn {
            height: 34px;
            width: 34px;
        }
        .table-filter label {
            font-weight: normal;
            margin-left: 10px;
        }
        .table-filter select, .table-filter input {
            display: inline-block;
            margin-left: 5px;
        }
        .table-filter input {
            width: 200px;
            display: inline-block;
        }
        .filter-group select.form-control {
            width: 110px;
        }

        table.table tr th, table.table tr td {
            border-color: #e9e9e9;
            padding: 12px 15px;
            vertical-align: middle;
        }
        table.table tr th:first-child {
            width: 60px;
        }
        table.table tr th:last-child {
            width: 80px;
        }
        table.table-striped tbody tr:nth-of-type(odd) {
            background-color: #fcfcfc;
        }
        table.table-striped.table-hover tbody tr:hover {
            background: #f5f5f5;
        }
        table.table th i {
            font-size: 13px;
            margin: 0 5px;
            cursor: pointer;
        }

        table.table td a.view i {
            font-size: 22px;
            margin: 2px 0 0 1px;
        }

        .pagination {
            float: right;
            margin: 0 0 5px;
        }
        .pagination li a {
            border: none;
            font-size: 13px;
            min-width: 30px;
            min-height: 30px;
            color: #999;
            margin: 0 2px;
            line-height: 30px;
            border-radius: 2px !important;
            text-align: center;
            padding: 0 6px;
        }
        .pagination li a:hover {
            color: #666;
        }
        .pagination li.active a {
            background: #03324d;
        }
        .pagination li.active a:hover {
            background: #031327;
        }
        .pagination li.disabled i {
            color: #ccc;
        }
        .pagination li i {
            font-size: 16px;
            padding-top: 6px
        }
        .hint-text {
            float: left;
            margin-top: 10px;
            font-size: 13px;
        }

        .show-entries .dropdown{
            display: inline;
            margin: 0;
            padding: 0;
        }

        .show-entries .btn{
            margin-left: 0;
            width: 55px;
        }

        .show-entries .dropdown-menu {
            min-width: 20px;
            width: 55px;
        }
        .show-entries .dropdown-item{
            width: 55px;
            padding-left: 10px;
        }

        /*.show-entries select.form-control {*/
        /*    width: 60px;*/
        /*    margin: 0 5px;*/
        /*}*/

    </style>
</head>
<body>
<div>
    <nav class="navbar navbar-expand-sm navbar-dark bg-dark">
        <div class="collapse navbar-collapse">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item passive">
                    <label class="navbar-brand">${sessionScope.LoggedUser.name}</label>
                </li>
                <li class="nav-item active">
                    <a class="nav-link" href="/index.jsp"><spring:message code="header.message"/><span class="sr-only">(current)</span></a>
                </li>
            </ul>
            <ul class="navbar-nav ">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" id="dropdown" data-toggle="dropdown" aria-haspopup="true"
                       aria-expanded="false">${pageContext.response.locale.country}</a>
                    <div class="dropdown-menu" aria-labelledby="dropdown">
                        <a class="dropdown-item"
                           href="?lang=<spring:message code="header.language.UA.tag"/>"><spring:message
                                code="header.language.UA"/></a>
                        <a class="dropdown-item"
                           href="?lang=<spring:message code="header.language.EN.tag"/>"><spring:message
                                code="header.language.EN"/></a>
                    </div>
                </li>
                <li>
                    <a class="nav-link" href="/logout"><spring:message code="header.logout"/><span class="sr-only">(current)</span></a>
                </li>
            </ul>
        </div>
    </nav>
</div>

<div class="container-fluid text-center">
    <div class="row content">
        <div class="col-sm-2 sidenav">


        </div>
        <div class="col-sm-8 text-left container">
<%--            <div class="container">--%>
                <div class="table-wrapper">
                    <div class="table-title">
                        <div class="row">
                            <div class="col-sm-4">
                                <h2><spring:message code="doctor.page.patientsList.title"/></h2>
                            </div>
                            <div class="col-sm-8">
                                <a href="/doctor/page/${page.number}?recordsPerPage=${page.size}"
                                   class="btn btn-primary"><i class="material-icons">&#xE863;</i>
                                    <span><spring:message code="doctor.page.patientsList.refresh"/></span></a>
                            </div>
                        </div>
                    </div>
                    <div class="table-filter">
                        <div class="row">
                            <div class="col-sm-3">
                                <div class="show-entries">
                                    <span><spring:message code="pagination.show"/></span>
                                    <div class="dropdown">
                                        <button class="btn btn-primary  float-none dropdown-toggle paginationDropdown" type="button"  data-toggle="dropdown">${page.size}</button>
                                        <ul class="dropdown-menu ">
                                            <li><a class="dropdown-item" href="/doctor/page/${page.number}?recordsPerPage=5">5</a></li>
                                            <li><a class="dropdown-item" href="/doctor/page/${page.number}?recordsPerPage=10">10</a></li>
                                            <li><a class="dropdown-item" href="/doctor/page/${page.number}?recordsPerPage=15">15</a></li>
                                            <li><a class="dropdown-item" href="/doctor/page/${page.number}?recordsPerPage=20">20</a></li>
                                        </ul>
                                    </div>
                                    <span> <spring:message code="pagination.entries"/></span>
                                </div>
                            </div>
                            <div class="col-sm-9">
                                <button type="button" class="btn btn-primary"><i class="fa fa-search"></i></button>
                                <div class="filter-group">
                                    <label>Name</label>
                                    <input type="text" class="form-control">
                                </div>

                                <div class="filter-group">
                                    <label>Status</label>
                                    <select class="form-control">
                                        <option>Any</option>
                                        <option>Delivered</option>
                                        <option>Shipped</option>
                                        <option>Pending</option>
                                        <option>Cancelled</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
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
                                    <th><c:out value="${patient.name}"/> <c:out value="${patient.patronymic}"/> <c:out value="${patient.surname}"/></th>
                                    <th><c:out value="${patient.email}"/></th>
                                    <th><c:out value="${patient.lastDiagnosisName}"/></th>
                                    <th><a class="btn btn-primary" href="#" role="button"><spring:message code="doctor.page.patientsList.edit"/></a>
                                    </th>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>






                    <div class="clearfix">
                        <div class="hint-text"><spring:message code="pagination.label.showing"/> <b><c:out value="${page.numberOfElements}"/></b> <spring:message code="pagination.label.outOf"/> <b>${page.totalElements}</b> <spring:message code="pagination.label.entries"/></div>


                        <ul class="pagination">
                            <c:if test="${!page.first}">
                                <li class="page-item"><a class="page-link"
                                                         href="/doctor/page/${page.number - 1}?recordsPerPage=${page.size}"><spring:message code="pagination.previous"/></a>
                                </li>
                            </c:if>

                            <c:forEach begin="1" end="${page.totalPages}" var="i">
                                <c:choose>
                                    <c:when test="${page.number + 1 eq i}">
                                        <li class="page-item active"><a class="page-link">
                                                ${i} <span class="sr-only"><spring:message code="pagination.current"/></span></a>
                                        </li>
                                    </c:when>
                                    <c:otherwise>
                                        <li class="page-item"><a class="page-link"
                                                                 href="/doctor/page/${i}?recordsPerPage=${page.size}">${i}</a>
                                        </li>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>

                            <c:if test="${!page.last}">
                                <li class="page-item"><a class="page-link"
                                                         href="/doctor/page/${page.number+1}?recordsPerPage=${page.size}"><spring:message code="pagination.next"/></a>
                                </li>
                            </c:if>
                        </ul>
                    </div>
                </div>
<%--            </div>--%>

        </div>
        <div class="col-sm-2 sidenav">


        </div>
    </div>
</div>

<footer class="container-fluid text-center">
    <p>Footer Text</p>
</footer>

<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
</body>
</html>
