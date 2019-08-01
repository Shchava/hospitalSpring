<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="springForm" uri="http://www.springframework.org/tags/form" %>
<%@ page isELIgnored="false" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<html>
<head>
    <META http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css"/>
    <link rel="stylesheet" href="/css/doctorPageMarkUp.css"/>
    <link rel="stylesheet" href="/css/listOfEntries.css"/>
    <link rel="stylesheet" href="/css/pagination.css"/>
    <title>${patient.surname} ${patient.name} ${patient.patronymic}</title>

    <style>
        .diagnosesTitle{
            float: right;
        }
    </style>
    <c:set var="dateFormat">
        <spring:message code="dateFormat"/>
    </c:set>
    <c:set var="foramter" value='${DateTimeFormatter.ofPattern(dateFormat)}'/>
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
            <div class="table-wrapper">
                <div class="table-title">
                    <div class="row">
                        <div class="col-sm-9">
                            <h2>${patient.surname} ${patient.name} ${patient.patronymic}</h2>
                        </div>
                        <div class="col-sm-3">
                            <a href="/doctor/patient${patient.idUser}/${page.number}?recordsPerPage=${page.size}"
                               class="btn btn-primary"><i class="material-icons">&#xE863;</i>
                                <span><spring:message code="doctor.page.patientsList.refresh"/></span></a>
                        </div>
                    </div>
                </div>

                <div>
                    <h6>email:</h6>
                    <p>${patient.email}</p>
                    <h6>personal info:</h6>
                    <p>${patient.info}</p>
                </div>

                <div class="table-filter">
                    <div class="row">
                        <div class="col-sm-3">
                            <div class="show-entries">
                                <span><spring:message code="pagination.show"/></span>
                                <div class="dropdown">
                                    <button class="btn btn-primary  float-none dropdown-toggle paginationDropdown"
                                            type="button" data-toggle="dropdown">${page.size}</button>
                                    <ul class="dropdown-menu ">
                                        <li><a class="dropdown-item"
                                               href="/doctor/patient${patient.idUser}/${page.number}?recordsPerPage=5">5</a></li>
                                        <li><a class="dropdown-item"
                                               href="/doctor/patient${patient.idUser}/${page.number}?recordsPerPage=10">10</a></li>
                                        <li><a class="dropdown-item"
                                               href="/doctor/patient${patient.idUser}/${page.number}?recordsPerPage=15">15</a></li>
                                        <li><a class="dropdown-item"
                                               href="/doctor/patient${patient.idUser}/${page.number}?recordsPerPage=20">20</a></li>
                                    </ul>
                                </div>
                                <span> <spring:message code="pagination.entries"/></span>
                            </div>
                        </div>
                        <div class="col-sm-3"><h3 class="diagnosesTitle">Diagnoses</h3></div>

                        <div class="col-sm-6">
                            <button type="button" class="btn btn-primary"><i class="fa fa-search"></i></button>
                            <div class="filter-group">
                                <label>Name</label>
                                <input type="text" class="form-control">
                            </div>

                        </div>
                    </div>
                </div>

                <table class="table table-striped table-hover">
                    <thead>
                    <tr>
                        <th><spring:message code="doctor.showPatient.diagnoses.id"/></th>
                        <th><spring:message code="doctor.showPatient.diagnoses.name"/></th>
                        <th><spring:message code="doctor.showPatient.diagnoses.assigned"/></th>
                        <th><spring:message code="doctor.showPatient.diagnoses.assignedBy"/></th>
                        <th><spring:message code="doctor.showPatient.diagnoses.cured"/></th>
                        <th><spring:message code="doctor.showPatient.diagnoses.open"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${page.content}" var="diagnosis">
                        <tr>
                            <th><c:out value="${diagnosis.idDiagnosis}"/></th>
                            <th><c:out value="${diagnosis.name}"/>
                            <th><c:out value="${diagnosis.assigned.format(foramter)}" /> </th>
                            <th><c:out value="${diagnosis.doctor.surname}"/> <c:out value="${diagnosis.doctor.name}"/></th>
                            <th><c:out value="${diagnosis.cured}"/></th>
                            <th><a class="btn btn-primary" href="#" role="button"><spring:message
                                    code="doctor.showPatient.diagnosesList.open"/></a>
                            </th>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

                <a role="button" class="btn btn-primary btn-lg btn-block">+</a>
                <div class="clearfix">
                    <div class="hint-text"><spring:message code="pagination.label.showing"/> <b><c:out
                            value="${page.numberOfElements}"/></b> <spring:message code="pagination.label.outOf"/>
                        <b>${page.totalElements}</b> <spring:message code="pagination.label.entries"/></div>


                    <ul class="pagination">
                        <c:if test="${!page.first}">
                            <li class="page-item"><a class="page-link"
                                                     href="/doctor/patient${patient.idUser}/${page.number - 1}?recordsPerPage=${page.size}"><spring:message
                                    code="pagination.previous"/></a>
                            </li>
                        </c:if>

                        <c:forEach begin="1" end="${page.totalPages}" var="i">
                            <c:choose>
                                <c:when test="${page.number + 1 eq i}">
                                    <li class="page-item active"><a class="page-link">
                                            ${i} <span class="sr-only"><spring:message
                                            code="pagination.current"/></span></a>
                                    </li>
                                </c:when>
                                <c:otherwise>
                                    <li class="page-item"><a class="page-link"
                                                             href="/doctor/patient${patient.idUser}/${i}?recordsPerPage=${page.size}">${i}</a>
                                    </li>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>

                        <c:if test="${!page.last}">
                            <li class="page-item"><a class="page-link"
                                                     href="/doctor/patient${patient.idUser}/${page.number+1}?recordsPerPage=${page.size}"><spring:message
                                    code="pagination.next"/></a>
                            </li>
                        </c:if>
                    </ul>
                </div>
            </div>

        </div>
        <div class="col-sm-2 sidenav">


        </div>
    </div>
</div>

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