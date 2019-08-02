<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="springForm" uri="http://www.springframework.org/tags/form" %>
<%@ page isELIgnored="false" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <META http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="/webjars/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="/css/doctorPageMarkUp.css"/>
    <link rel="stylesheet" href="/css/listOfEntries.css"/>
    <link rel="stylesheet" href="/css/pagination.css"/>
    <link rel="stylesheet" href="/css/showDiagnosis.css"/>
    <title>${diagnosis.name}</title>

    <c:set var="showMedicineAdditionalInfo">
        <spring:message code="doctor.showDiagnosis.showMedicine.openButton"/>
    </c:set>

    <c:set var="medicineDescription">
        <spring:message code="doctor.showDiagnosis.showMedicine.description"/>
    </c:set>

    <c:set var="medicineDoctorEmail">
        <spring:message code="doctor.showDiagnosis.showMedicine.doctorEmail"/>
    </c:set>

    <c:set var="medicineRefill">
        <spring:message code="doctor.showDiagnosis.showMedicine.refill"/>
    </c:set>

    <c:set var="JSdateFormat">
        <spring:message code="JS.dateFormat"/>
    </c:set>

    <c:set var="dateFormat">
        <spring:message code="dateFormat"/>
    </c:set>
    <c:set var="foramter" value='${DateTimeFormatter.ofPattern(dateFormat)}'/>

    <style>
        .medicine-container{
            display: none;
        }
        .show-button{
            margin-bottom: 10px;
        }
        .medicineAdditionalInfo{
            display: none;
        }
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
            <div class="table-wrapper">
                <div class="table-title">
                    <div class="row">
                        <div class="col-sm-9">
                            <h2>${diagnosis.name}</h2>
                        </div>
                        <div class="col-sm-3">
                            <a href="/doctor/patient${patient.idUser}/${page.number}?recordsPerPage=${page.size}"
                               class="btn btn-primary"><i class="material-icons">&#xE863;</i>
                                <span><spring:message code="doctor.page.patientsList.refresh"/></span></a>
                        </div>
                    </div>
                </div>

                <div>
                    <h6>description:</h6>
                    <p>${diagnosis.description}</p>
                    <h6>diagnosed:</h6>
                    <p>${diagnosis.assigned}</p>
                    <h6>diagnosed by</h6>
                    <p>${diagnosis.doctor.surname} ${diagnosis.doctor.name} ${diagnosis.doctor.patronymic}</p>
                </div>

                <div id="medicineContainer" class="medicine-container">
                    <div class="table-wrapper">
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
                                                       href="/doctor/patient${patient.idUser}/${page.number}?recordsPerPage=5">5</a>
                                                </li>
                                                <li><a class="dropdown-item"
                                                       href="/doctor/patient${patient.idUser}/${page.number}?recordsPerPage=10">10</a>
                                                </li>
                                                <li><a class="dropdown-item"
                                                       href="/doctor/patient${patient.idUser}/${page.number}?recordsPerPage=15">15</a>
                                                </li>
                                                <li><a class="dropdown-item"
                                                       href="/doctor/patient${patient.idUser}/${page.number}?recordsPerPage=20">20</a>
                                                </li>
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

                                </div>
                            </div>
                        </div>

                        <table id="medicineTable" class="table table-striped table-hover">
                            <thead>
                            <tr>
                                <th><spring:message code="doctor.showDiagnosis.showMedicine.id"/></th>
                                <th><spring:message code="doctor.showDiagnosis.showMedicine.name"/></th>
                                <th><spring:message code="doctor.showDiagnosis.showMedicine.assigned"/></th>
                                <th><spring:message code="doctor.showDiagnosis.showMedicine.assignedBy"/></th>
                                <th><spring:message code="doctor.showDiagnosis.showMedicine.count"/></th>
                                <th><spring:message code="doctor.showDiagnosis.showMedicine.open"/></th>
                            </tr>
                            </thead>
                            <tbody id = "medicineTbody">
                            <c:forEach items="${page.content}" var="diagnosis">
                                <tr>
                                    <th><c:out value="${diagnosis.idDiagnosis}"/></th>
                                    <th><c:out value="${diagnosis.name}"/>
                                    <th><c:out value="${diagnosis.assigned.format(foramter)}"/></th>
                                    <th><c:out value="${diagnosis.doctor.surname}"/> <c:out
                                            value="${diagnosis.doctor.name}"/></th>
                                    <th><c:out value="${diagnosis.cured}"/></th>
                                    <th><a class="btn btn-primary" href="#" role="button"><spring:message
                                            code="doctor.showPatient.diagnosesList.open"/></a>
                                    </th>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
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
                <button id="showMedicine" role="button" class="btn btn-primary btn-lg btn-block show-button"><spring:message code="doctor.showDiagnosis.showMedicine"/></button>

                <button id="showProcedures" role="button" class="btn btn-primary btn-lg btn-block"><spring:message code="doctor.showDiagnosis.showProcedures"/></button>
                <button id="showSurgeries" role="button" class="btn btn-primary btn-lg btn-block"><spring:message code="doctor.showDiagnosis.showSurgeries"/></button>
            </div>

        </div>
        <div class="col-sm-2 sidenav">


        </div>
    </div>
</div>


<script src="/webjars/jquery/3.4.1/jquery.min.js"></script>
<script src="/webjars/popper.js"></script>
<script src="/webjars/bootstrap/4.3.1/js/bootstrap.min.js"></script>
<script>
    $(document).ready(function () {
        $("#showMedicine").click(function () {
            if($("#medicineContainer").is(":visible")){
                $("#medicineContainer").hide();
            } else {
                $("#medicineContainer").show();
                loadMedicine();
            }
        });


    });
    
    function loadMedicine() {
        $.ajax({
            type: 'GET',
            url: "/getMedicine${diagnosis.idDiagnosis}",
            contentType: "text/plain",
            dataType: 'json',
            success: function (data) {
                var myJsonData = data;
                console.log(data);
                populateDataTable(myJsonData);
            },
            error: function (e) {
                console.log("There was an error with your request...");
                console.log("error: " + JSON.stringify(e));
            }
        });
    }

    function populateDataTable(data) {
        console.log('-----------');
        for (var i = 0; i < data.numberOfElements; i++) {
            console.log(data.content[i]);
            addMedicineRow(data.content[i]);
        }
    }

    function addMedicineRow(dataEntry) {
        console.log("fs");
<%--        ${dateFormat}--%>
        var row =
            "<tr>" +
                "<th>" + dataEntry.idTherapy + "</th>" +
                "<th>" + dataEntry.name + "</th>" +
                "<th>" +   new Date(dataEntry.assigned).toLocaleString() + "</th>" +
                "<th>" + dataEntry.assignedBy.surname + " " + dataEntry.assignedBy.name + " " + dataEntry.assignedBy.patronymic + "</th>" +
                "<th>" + dataEntry.count + "</th>" +
                "<th>" + "<button id='showMedicineData"+ dataEntry.idTherapy + "' class='btn btn-primary'>${showMedicineAdditionalInfo}</button> </th>" +
            "</tr>" +
            "<tr id='mAd"+dataEntry.idTherapy+"' class='medicineAdditionalInfo'>" +
                "<th colspan='6'>" +
                    "<h6>${medicineDescription}</h6>" +
                    "<p>" + dataEntry.description + "</p>" +
                    "<h6>${medicineDoctorEmail}</h6>" +
                    "<p>" + dataEntry.assignedBy.email + "</p>" +
                    "<h6>${medicineRefill}</h6>" +
                    "<p>" + new Date(dataEntry.refill).toLocaleString() + "</p>" +
                "</th>" +
            "</tr>";
        console.log(row);


        $("#medicineTbody").html(row);

        var dataContainer = $("#mAd" + dataEntry.idTherapy);
        $("#showMedicineData" + dataEntry.idTherapy).click(function () {
            if(dataContainer.is(":visible")){
                dataContainer.hide();
            } else {
                dataContainer.toggle();
            }
        });


    }

    <%--<th><spring:message code="doctor.showDiagnosis.showMedicine.id"/></th>--%>
    <%--<th><spring:message code="doctor.showDiagnosis.showMedicine.name"/></th>--%>
    <%--<th><spring:message code="doctor.showDiagnosis.showMedicine.assigned"/></th>--%>
    <%--<th><spring:message code="doctor.showDiagnosis.showMedicine.assignedBy"/></th>--%>
    <%--<th><spring:message code="doctor.showDiagnosis.showMedicine.count"/></th>--%>
    <%--<th><spring:message code="doctor.showDiagnosis.showMedicine.open"/></th>--%>


        // $("#medicineTable").empty();
        //
        // var Length = Object.keys(data.numberOfElements);
        // Console.log(Length);
        //
        // for(var i = 1; i < length+1; i++) {
        //     var customer = data.content['customer'+i];
        //
        //
        //     $('#example').dataTable().fnAddData( [
        //         customer.first_name,
        //         customer.last_name,
        //         customer.occupation,
        //         customer.email_address
        //     ]);
        // }



</script>
</body>
</html>
