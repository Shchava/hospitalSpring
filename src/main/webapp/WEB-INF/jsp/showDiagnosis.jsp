<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="springForm" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page isELIgnored="false" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <META http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <sec:csrfMetaTags/>
    <link rel="stylesheet" href="/webjars/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="/css/doctorPageMarkUp.css"/>
    <link rel="stylesheet" href="/css/listOfEntries.css"/>
    <link rel="stylesheet" href="/css/pagination.css"/>
    <link rel="stylesheet" href="/css/showDiagnosis.css"/>
    <link rel="stylesheet" href="/css/navbar.css">
    <title>${diagnosis.name}</title>

    <c:set var="dateFormat">
        <spring:message code="dateFormat"/>
    </c:set>
    <c:set var="foramtter" value='${DateTimeFormatter.ofPattern(dateFormat)}'/>

    <c:set var="showMedicineAdditionalInfo">
        <spring:message code="doctor.showDiagnosis.showTherapy.openButton"/>
    </c:set>

    <c:set var="therapyDescription">
        <spring:message code="doctor.showDiagnosis.showTherapy.description"/>
    </c:set>

    <c:set var="therapyDoctorEmail">
        <spring:message code="doctor.showDiagnosis.showTherapy.doctorEmail"/>
    </c:set>

    <c:set var="medicineRefill">
        <spring:message code="doctor.showDiagnosis.showMedicine.refill"/>
    </c:set>

    <c:set var="procedureAppointmentDates">
        <spring:message code="doctor.showDiagnosis.showProcedure.appointMentDates"/>
    </c:set>

    <c:set var="JSdateFormat">
        <spring:message code="JS.dateFormat"/>
    </c:set>

    <c:set var="dateFormat">
        <spring:message code="dateFormat"/>
    </c:set>


    <c:set var="removeDate">
        <spring:message code="doctor.showDiagnosis.removeDateField"/>
    </c:set>


    <c:set var="foramter" value='${DateTimeFormatter.ofPattern(dateFormat)}'/>

    <%--    <jsp:useBean id="now" class="java.time.LocalDateTime" />--%>

    <style>
        .medicine-container {
            display: none;
        }

        .procedure-container {
            display: none;
        }

        .show-button {
            margin-bottom: 10px;
        }

        .medicineAdditionalInfo {
            display: none;
        }

        .procedureAdditionalInfo {
            display: none;
        }

        .addNewMedicine {
            display: none;
        }

        .addNewProcedure {
            display: none;
        }

        .addNewSurgery {
            display: none;
        }

        .fieldError {
            display: none;
        }

        .notification {
            display: none;
        }

        .pageContainer {
            width: max-content;
            max-width: 600px;
        }

        .helpRequestLink{
            text-align: center;
            color: #555555;
            font-size: 2em;
            margin-top: 0.5em;
            display: block;
        }
    </style>
</head>
<body>
<%@ include file="reusable/navbar.jspf"%>


<div class="container-fluid main-content">
    <div class="row flex-xl-nowrap  main-content-inner">
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
                            <sec:authorize access="hasRole('DOCTOR')">
                                <c:if test="${empty diagnosis.cured}">
                                    <button id="closeDiagnosisButton" class="btn btn-primary">
                                    <span><spring:message code="doctor.closeDiagnosis"/></span></button>
                                </c:if>
                            </sec:authorize>
                        </div>
                    </div>
                </div>

                <div id="closed" class="alert alert-info notification" role="alert"><spring:message code="diagnosis.closeDiagnosis.closed" /></div>

                <div id="cantClose" class="alert alert-danger notification" role="alert"><spring:message code="diagnosis.closeDiagnosis.cantClose" /></div>

                <div>
                    <h6><spring:message code="doctor.showDiagnosis.description"/></h6>
                    <p>${diagnosis.description}</p>
                    <h6><spring:message code="doctor.showDiagnosis.diagnosed"/></h6>
                    <p>${diagnosis.assigned.format(foramtter)}</p>
                    <h6><spring:message code="doctor.showDiagnosis.diagnosedBy"/></h6>
                    <p>${diagnosis.doctor.surname} ${diagnosis.doctor.name} ${diagnosis.doctor.patronymic}</p>
                    <c:if test="${!empty diagnosis.cured}">
                        <h6><spring:message code="doctor.showDiagnosis.cured"/></h6>
                        <p>${diagnosis.cured.format(foramtter)}</p>
                    </c:if>
                </div>

                <button id="showMedicine" role="button" class="btn btn-primary btn-lg btn-block show-button">
                    <spring:message code="doctor.showDiagnosis.showMedicine"/></button>
                <div class="clearfix"></div>
                <div id="medicineContainer" class="medicine-container">
                    <div class="table-wrapper">

                        <div id="medicineCreated" class="alert alert-info notification" role="alert">
                            <spring:message code="doctor.showDiagnosis.medicineCreated"/></div>
                        <div id="medicineCreationError" class="alert alert-danger fieldError" role="alert"></div>

                        <div class="table-filter">
                            <div class="row">
                                <div class="col-sm-3">
                                    <div class="show-entries">
                                        <span><spring:message code="pagination.show"/></span>
                                        <div id="medicineEntriesDropdown" class="dropdown">
                                            <button id="medicineEntriesDropdownButton"
                                                    class="btn btn-primary  float-none dropdown-toggle paginationDropdown"
                                                    type="button" data-toggle="dropdown">${page.size}</button>
                                            <ul class="dropdown-menu ">
                                                <li><a class="dropdown-item">5</a>
                                                </li>
                                                <li><a class="dropdown-item">10</a>
                                                </li>
                                                <li><a class="dropdown-item">15</a>
                                                </li>
                                                <li><a class="dropdown-item">20</a>
                                                </li>
                                            </ul>
                                        </div>
                                        <span> <spring:message code="pagination.entries"/></span>
                                    </div>
                                </div>


                                <div class="col-sm-9">
                                    <div class="filter-group">
                                    </div>
                                </div>
                            </div>
                        </div>

                        <table id="medicineTable" class="table table-striped table-hover">
                            <thead>
                            <tr>
                                <th><spring:message code="doctor.showDiagnosis.showTherapy.id"/></th>
                                <th><spring:message code="doctor.showDiagnosis.showMedicine.name"/></th>
                                <th><spring:message code="doctor.showDiagnosis.showTherapy.assigned"/></th>
                                <th><spring:message code="doctor.showDiagnosis.showTherapy.assignedBy"/></th>
                                <th><spring:message code="doctor.showDiagnosis.showMedicine.count"/></th>
                                <th><spring:message code="doctor.showDiagnosis.showTherapy.open"/></th>
                            </tr>
                            </thead>
                            <tbody id="medicineTbody">
                            </tbody>
                        </table>
                        <div class="clearfix">
                            <div class="hint-text"><spring:message code="pagination.label.showing"/> <b
                                    id="medicinePageEntries"></b> <spring:message code="pagination.label.outOf"/>
                                <b id="medicineTotalEntries"></b> <spring:message code="pagination.label.entries"/>
                            </div>


                            <ul class="pagination">

                                <li id="medicinePreviousPage" class="page-item">
                                    <a class="page-link"><spring:message code="pagination.previous"/></a>
                                </li>
                                <ul id="medicinePages" class="pagination"></ul>
                                <li id="medicineNextPage" class="page-item">
                                    <a class="page-link"><spring:message code="pagination.next"/></a>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <sec:authorize access="hasAnyRole('DOCTOR','NURSE')">
                        <div id="addMedicine" class="addNewMedicine">
                            <form id="addMedicineForm" action="/doctor/diagnosis${diagnosis.idDiagnosis}/addMedicine"
                                  method="POST" enctype="utf8">
                                <div class="form-group">
                                    <label><spring:message code="doctor.showDiagnosis.addTherapy.name"/></label>
                                    <div id="medicineNameFieldError" class="alert alert-danger fieldError" role="alert"
                                         required="required"></div>
                                    <input type="text" name="name" class="form-control" value=""/>
                                </div>
                                <div class="form-group">
                                    <label><spring:message code="doctor.showDiagnosis.addTherapy.description"/></label>
                                    <textarea type="text" name="description" class="form-control  input-description"
                                              value=""></textarea>
                                </div>
                                <div class="form-group">
                                    <label><spring:message code="doctor.showDiagnosis.addMedicine.count"/></label>
                                    <div id="medicineCountFieldError" class="alert alert-danger fieldError"
                                         role="alert"></div>
                                    <input type="number" class="form-control" name="count" value="" required="required"/>
                                </div>
                                <div class="form-group">
                                    <label><spring:message code="doctor.showDiagnosis.addMedicine.refill"/></label>
                                    <input type='date' name="refill" class="form-control"/>
                                </div>
                            </form>
                        </div>
                        <button id="addMedicineBtn" role="button" class="btn btn-primary btn-lg btn-block show-button">
                            <spring:message code="doctor.showDiagnosis.addMedicine.button"/></button>
                    </sec:authorize>
                </div>

                <button id="showProcedures" role="button" class="btn btn-primary btn-lg btn-block show-button">
                    <spring:message code="doctor.showDiagnosis.showProcedures"/></button>

                <div class="clearfix"></div>

                <div id="procedureContainer" class="procedure-container">
                    <div class="table-wrapper">
                        <div class="table-filter">

                            <div id="procedureCreated" class="alert alert-info notification" role="alert">
                                <spring:message code="doctor.showDiagnosis.procedureCreated"/></div>
                            <div id="procedureCreationError" class="alert alert-danger fieldError" role="alert"></div>

                            <div class="row">
                                <div class="col-sm-3">
                                    <div class="show-entries">
                                        <span><spring:message code="pagination.show"/></span>
                                        <div id="procedureEntriesDropdown" class="dropdown">
                                            <button id="procedureEntriesDropdownButton"
                                                    class="btn btn-primary  float-none dropdown-toggle paginationDropdown"
                                                    type="button" data-toggle="dropdown">${page.size}</button>
                                            <ul class="dropdown-menu ">
                                                <li><a class="dropdown-item">5</a>
                                                </li>
                                                <li><a class="dropdown-item">10</a>
                                                </li>
                                                <li><a class="dropdown-item">15</a>
                                                </li>
                                                <li><a class="dropdown-item">20</a>
                                                </li>
                                            </ul>
                                        </div>
                                        <span> <spring:message code="pagination.entries"/></span>
                                    </div>
                                </div>


                                <div class="col-sm-9">
                                    <div class="filter-group">

                                    </div>
                                </div>
                            </div>
                        </div>

                        <table id="procedureTable" class="table table-striped table-hover">
                            <thead>
                            <tr>
                                <th><spring:message code="doctor.showDiagnosis.showTherapy.id"/></th>
                                <th><spring:message code="doctor.showDiagnosis.showTherapy.name"/></th>
                                <th><spring:message code="doctor.showDiagnosis.showTherapy.assigned"/></th>
                                <th><spring:message code="doctor.showDiagnosis.showTherapy.assignedBy"/></th>
                                <th><spring:message code="doctor.showDiagnosis.showProcedure.room"/></th>
                                <th><spring:message code="doctor.showDiagnosis.showTherapy.open"/></th>
                            </tr>
                            </thead>
                            <tbody id="procedureTbody">
                            </tbody>
                        </table>
                        <div class="clearfix">
                            <div class="hint-text"><spring:message code="pagination.label.showing"/> <b
                                    id="procedurePageEntries"></b> <spring:message code="pagination.label.outOf"/>
                                <b id="procedureTotalEntries"></b> <spring:message code="pagination.label.entries"/>
                            </div>


                            <ul class="pagination">

                                <li id="procedurePreviousPage" class="page-item">
                                    <a class="page-link"><spring:message code="pagination.previous"/></a>
                                </li>

                                <ul id="procedurePages" class="pagination"></ul>

                                <li id="procedureNextPage" class="page-item">
                                    <a class="page-link"><spring:message code="pagination.next"/></a>
                                </li>
                            </ul>
                        </div>
                    </div>

                    <sec:authorize access="hasAnyRole('DOCTOR','NURSE')">
                        <div id="addProcedure" class="addNewProcedure">
                            <form id="addProcedureForm" method="POST" enctype="utf8">
                                <div class="form-group">
                                    <label><spring:message code="doctor.showDiagnosis.addTherapy.name"/></label>
                                    <div id="procedureNameFieldError" class="alert alert-danger fieldError" role="alert"
                                         required="required"></div>
                                    <input type="text" name="name" class="form-control" value=""/>
                                </div>
                                <div class="form-group">
                                    <label><spring:message code="doctor.showDiagnosis.addTherapy.description"/></label>
                                    <textarea type="text" name="description" class="form-control  input-description"
                                              value=""></textarea>
                                </div>

                                <div class="form-group">
                                    <label><spring:message code="doctor.showDiagnosis.addProcedure.room"/></label>
                                    <div id="procedureRoomFieldError" class="alert alert-danger fieldError"
                                         role="alert"></div>
                                    <input type="number" class="form-control" name="room" value="" required="required"/>
                                </div>

                                <div id="appointmentDatesDiv" class="form-group assignedDatesWrap">
                                    <label><spring:message
                                            code="doctor.showDiagnosis.addProcedure.apponitmentdates"/></label>
                                    <button id="addAssignedDateBtn" class="btn btn-primary  add_field_button"><spring:message
                                            code="doctor.showDiagnosis.addProcedure.addNewDate"/></button>
                                </div>

                            </form>
                        </div>

                        <button id="addProcedureBtn" role="button" class="btn btn-primary btn-lg btn-block show-button">
                            <spring:message code="doctor.showDiagnosis.addProcedure.button"/></button>

                    </sec:authorize>
                </div>
                <button id="showSurgeries" role="button" class="btn btn-primary btn-lg btn-block"><spring:message
                        code="doctor.showDiagnosis.showSurgeries"/></button>
                <div class="clearfix"></div>
                <div id="surgeryContainer" class="procedure-container">
                    <div class="table-wrapper">


                        <div id="surgeryCreated" class="alert alert-info notification" role="alert">
                            <spring:message code="doctor.showDiagnosis.surgeryCreated"/></div>
                        <div id="surgeryCreationError" class="alert alert-danger fieldError" role="alert"></div>


                        <div class="table-filter">
                            <div class="row">
                                <div class="col-sm-3">
                                    <div class="show-entries">
                                        <span><spring:message code="pagination.show"/></span>
                                        <div id="surgeryEntriesDropdown" class="dropdown">
                                            <button id="surgeryEntriesDropdownButton"
                                                    class="btn btn-primary  float-none dropdown-toggle paginationDropdown"
                                                    type="button" data-toggle="dropdown">${page.size}</button>
                                            <ul class="dropdown-menu ">
                                                <li><a class="dropdown-item">5</a>
                                                </li>
                                                <li><a class="dropdown-item">10</a>
                                                </li>
                                                <li><a class="dropdown-item">15</a>
                                                </li>
                                                <li><a class="dropdown-item">20</a>
                                                </li>
                                            </ul>
                                        </div>
                                        <span> <spring:message code="pagination.entries"/></span>
                                    </div>
                                </div>


                                <div class="col-sm-9">
                                    <div class="filter-group">
                                    </div>
                                </div>
                            </div>
                        </div>

                        <table id="surgeryTable" class="table table-striped table-hover">
                            <thead>
                            <tr>
                                <th><spring:message code="doctor.showDiagnosis.showTherapy.id"/></th>
                                <th><spring:message code="doctor.showDiagnosis.showSurgery.name"/></th>
                                <th><spring:message code="doctor.showDiagnosis.showTherapy.assigned"/></th>
                                <th><spring:message code="doctor.showDiagnosis.showTherapy.assignedBy"/></th>
                                <th><spring:message code="doctor.showDiagnosis.showSurgery.date"/></th>
                                <th><spring:message code="doctor.showDiagnosis.showTherapy.open"/></th>
                            </tr>
                            </thead>
                            <tbody id="surgeryTbody">
                            </tbody>
                        </table>
                        <div class="clearfix">
                            <div class="hint-text"><spring:message code="pagination.label.showing"/> <b
                                    id="surgeryPageEntries"></b> <spring:message code="pagination.label.outOf"/>
                                <b id="surgeryTotalEntries"></b> <spring:message code="pagination.label.entries"/></div>


                            <ul class="pagination">

                                <li id="surgeryPreviousPage" class="page-item">
                                    <a class="page-link"><spring:message code="pagination.previous"/></a>
                                </li>

                                <ul id="surgeryPages" class="pagination"></ul>

                                <li id="surgeryNextPage" class="page-item">
                                    <a class="page-link"><spring:message code="pagination.next"/></a>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <sec:authorize access="hasRole('DOCTOR')">
                        <div id="addSurgery" class="addNewProcedure">
                            <form id="addSurgeryForm" method="POST" enctype="utf8">
                                <div class="form-group">
                                    <label><spring:message code="doctor.showDiagnosis.addTherapy.name"/></label>
                                    <div id="surgeryNameFieldError" class="alert alert-danger fieldError" role="alert"
                                         required="required"></div>
                                    <input type="text" name="name" class="form-control" value=""/>
                                </div>
                                <div class="form-group">
                                    <label><spring:message code="doctor.showDiagnosis.addTherapy.description"/></label>
                                    <textarea type="text" name="description" class="form-control  input-description"
                                              value=""></textarea>
                                </div>
                                <div class="form-group">
                                    <label><spring:message code="doctor.showDiagnosis.addSurgery.date"/></label>
                                    <div id="surgeryDateFieldError" class="alert alert-danger fieldError"
                                         role="alert"></div>
                                    <input id="surgeryDateField" type="datetime-local" name="surgeryDate"
                                           max="2100-06-14T00:00"/>
                                </div>
                            </form>
                        </div>
                        <button id="addSurgeryBtn" role="button" class="btn btn-primary btn-lg btn-block show-button">
                            <spring:message code="doctor.showSurgery.addSurgery.button"/></button>
                    </sec:authorize>
                </div>
                <c:if test="${!empty diagnosis.causingHelpRequest}">
                    <a class="helpRequestLink" href="/diagnosis-prediction/help${diagnosis.causingHelpRequest.idPrediction}">
                        <spring:message code="doctor.showDiagnosis.helpRequestLink"/></a>
                </c:if>
            </div>

            <div><h1 id="ankor">.</h1></div>
        </div>
        <div class="col-sm-2 sidenav">


        </div>
    </div>
</div>


<script src="/webjars/jquery/3.4.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js"
        integrity="sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4"
        crossorigin="anonymous"></script>
<script src="/webjars/bootstrap/4.3.1/js/bootstrap.min.js"></script>
<script>
    $(document).ready(function () {
        $("#showMedicine").click(function () {
            if ($("#medicineContainer").is(":visible")) {
                $("#medicineContainer").hide();
            } else {
                $("#medicineContainer").show();
                loadMedicine(0, 10);
            }
        });
        <sec:authorize access="hasAnyRole('DOCTOR','NURSE')">
            $("#addMedicineBtn").click(function () {
                if ($("#addMedicine").is(":visible")) {
                    sendAddMedicine();
                } else {
                    $("#addMedicine").show();
                }
            });
        </sec:authorize>

        $("#showProcedures").click(function () {
            if ($("#procedureContainer").is(":visible")) {
                $("#procedureContainer").hide();
            } else {
                $("#procedureContainer").show();
                loadProcedures(0, 10);
            }
        });
        <sec:authorize access="hasAnyRole('DOCTOR','NURSE')">
            $("#addProcedureBtn").click(function () {
                if ($("#addProcedure").is(":visible")) {
                    sendAddProcedure();
                } else {
                    $("#addProcedure").show();
                }
            });
        </sec:authorize>

        $("#showSurgeries").click(function () {
            if ($("#surgeryContainer").is(":visible")) {
                $("#surgeryContainer").hide();
            } else {
                $("#surgeryContainer").show();
                loadSurgeries(0, 10);
            }
        });

        <sec:authorize access="hasRole('DOCTOR')">

        $("#addSurgeryBtn").click(function () {
            if ($("#addSurgery").is(":visible")) {
                sendAddSurgery();

            } else {
                $("#addSurgery").show();
            }
        });

        $("#surgeryDateField").val(new Date().toJSON().slice(0, 19));
        $("#surgeryDateField").attr("min", (new Date().toJSON().slice(0, 19)));

        $("#closeDiagnosisButton").click(function () {
            closeDiagnosis();
        });

        </sec:authorize>

        var x;
        $("#addAssignedDateBtn").click(function (e) {
            e.preventDefault();

            x++;
            $("#appointmentDatesDiv").append('<div>' +
                '<input type="datetime-local" name="appointmentDates" ' +
                'value="' + new Date().toJSON().slice(0, 19) +
                '" min="' + new Date().toJSON().slice(0, 19) +
                '" max="2100-06-14T00:00"/>' +
                '<a href="#" class="remove_field">${removeDate}</a></div>');

        });

        $("#appointmentDatesDiv").on("click", ".remove_field", function (e) {
            e.preventDefault();
            $(this).parent('div').remove();
            x--;
        });

        $("#ankor").hide();
    });

    //Loading medicine

    function loadMedicine(page, recordsPerPage) {

        console.log(page);
        $.ajax({
            type: 'GET',
            url: "/getMedicine${diagnosis.idDiagnosis}/?pageNumber=" + page + "&recordsPerPage=" + recordsPerPage,
            contentType: "text/plain",
            dataType: 'json',
            success: function (data) {
                populateMedicineDataTable(data);
                setMedicinePaginationData(data)
            },
            error: function (e) {
                console.log("There was an error with your request...");
                console.log("error: " + JSON.stringify(e));
            }
        });
    }

    function setMedicinePaginationData(data) {
        console.log(data);
        var page = data.pageable.pageNumber;
        var recordsPerPage = data.pageable.pageSize;

        $('#medicineEntriesDropdownButton').html(recordsPerPage);
        $('#medicineEntriesDropdown a').click(function (e) {
            e.preventDefault(); // cancel the link behaviour
            var x = $(this).text();
            console.log(x + "fsf");
            loadMedicine(page, x);
        });
        $('#medicinePageEntries').html(data.numberOfElements);
        $('#medicineTotalEntries').html(data.totalElements);

        if (data.first) {
            $('#medicinePreviousPage').hide();
        } else {
            var prevButton = $('#medicinePreviousPage').show();
            prevButton.off("click");
            prevButton.click(function (e) {
                loadMedicine(page - 1, recordsPerPage);
            })
        }

        if (data.last) {
            $('#medicineNextPage').hide();
        } else {
            var nextButton = $('#medicineNextPage');
            nextButton.show();
            nextButton.off("click");
            nextButton.click(function (e) {
                loadMedicine(page + 1, recordsPerPage);
            })
        }

        $("#medicinePages").html("");
        for (var i = 1; i <= data.totalPages; i++) {
            if (i - 1 == data.number) {
                $("#medicinePages").append(
                    "<li class='page-item active'>" +
                    "<a class='page-link'>" + i + "</a>" +
                    "</li>");
            } else {
                $("#medicinePages").append(
                    "<li class='page-item'>" +
                    "<a id='medicinePage" + i + "' class='page-link'>" + i + "</a>" +
                    "</li>");

                $('#medicinePage' + i).click(function (e) {
                    var pag = $(this).text() - 1;
                    loadMedicine(pag, recordsPerPage);
                })
            }
        }


    }

    function populateMedicineDataTable(data) {
        $("#medicineTbody").html("");
        for (var i = 0; i < data.numberOfElements; i++) {
            addMedicineRow(data.content[i]);
        }
    }

    function addMedicineRow(dataEntry) {

        var refillDate;
        if(!dataEntry.refill || dataEntry.refill === null || dataEntry.refill === ""){
            refillDate = " "
        }else{
            refillDate = "<h6>${medicineRefill}</h6>" +
                "<p>" + new Date(dataEntry.refill).toLocaleString().slice(0, 10) + "</p>";
        }

        var row =
            "<tr>" +
            "<th>" + dataEntry.idTherapy + "</th>" +
            "<th>" + dataEntry.name + "</th>" +
            "<th>" + new Date(dataEntry.assigned).toLocaleString() + "</th>" +
            "<th>" + dataEntry.assignedBy.surname + " " + dataEntry.assignedBy.name + " " + dataEntry.assignedBy.patronymic + "</th>" +
            "<th>" + dataEntry.count + "</th>" +
            "<th>" + "<button id='showMedicineData" + dataEntry.idTherapy + "' class='btn btn-primary'>${showMedicineAdditionalInfo}</button> </th>" +
            "</tr>" +
            "<tr id='mAd" + dataEntry.idTherapy + "' class='medicineAdditionalInfo'>" +
            "<th colspan='6'>" +
            "<h6>${therapyDescription}</h6>" +
            "<p>" + dataEntry.description + "</p>" +
            "<h6>${therapyDoctorEmail}</h6>" +
            "<p>" + dataEntry.assignedBy.email + "</p>" +
            refillDate +
            "</th>" +
            "</tr>";


        $("#medicineTbody").append(row);

        var dataContainer = $("#mAd" + dataEntry.idTherapy);
        $("#showMedicineData" + dataEntry.idTherapy).click(function () {
            if (dataContainer.is(":visible")) {
                dataContainer.hide();
            } else {
                dataContainer.toggle();
            }
        });
    }

    //Loading procedures


    function loadProcedures(page, recordsPerPage) {
        $.ajax({
            type: 'GET',
            url: "/getProcedures${diagnosis.idDiagnosis}/?pageNumber=" + page + "&recordsPerPage=" + recordsPerPage,
            contentType: "text/plain",
            dataType: 'json',
            success: function (data) {
                populateProceduresDataTable(data);
                setProceduresPaginationData(data)
            },
            error: function (e) {
                console.log("There was an error with your request...");
                console.log("error: " + JSON.stringify(e));
            }
        });
    }

    function setProceduresPaginationData(data) {
        console.log(data);
        var page = data.pageable.pageNumber;
        var recordsPerPage = data.pageable.pageSize;

        $('#procedureEntriesDropdownButton').html(recordsPerPage);
        $('#procedureEntriesDropdown a').click(function (e) {
            e.preventDefault(); // cancel the link behaviour
            var x = $(this).text();
            console.log(x + "fsf");
            loadProcedures(page, x);
        });
        $('#procedurePageEntries').html(data.numberOfElements);
        $('#procedureTotalEntries').html(data.totalElements);

        if (data.first) {
            $('#procedurePreviousPage').hide();
        } else {
            var prevButton = $('#procedurePreviousPage').show();
            prevButton.off("click");
            prevButton.click(function (e) {
                loadProcedures(page - 1, recordsPerPage);
            })
        }

        if (data.last) {
            $('#procedureNextPage').hide();
        } else {
            var nextButton = $('#procedureNextPage');
            nextButton.show();
            nextButton.off("click");
            nextButton.click(function (e) {
                loadProcedures(page + 1, recordsPerPage);
            })
        }

        $("#procedurePages").html("");
        for (var i = 1; i <= data.totalPages; i++) {
            if (i - 1 == data.number) {
                $("#procedurePages").append(
                    "<li class='page-item active'>" +
                    "<a class='page-link'>" + i + "</a>" +
                    "</li>");
            } else {
                $("#procedurePages").append(
                    "<li class='page-item'>" +
                    "<a id='procedurePage" + i + "' class='page-link'>" + i + "</a>" +
                    "</li>");

                $('#procedurePage' + i).click(function (e) {
                    var pag = $(this).text() - 1;
                    loadProcedures(pag, recordsPerPage);
                })
            }
        }


    }

    function populateProceduresDataTable(data) {
        $("#procedureTbody").html("");
        for (var i = 0; i < data.numberOfElements; i++) {
            addProcedureRow(data.content[i]);
        }
    }

    function addProcedureRow(dataEntry) {
        var dates = "";
        for (var i = 0; i < dataEntry.appointmentDates.length; i++) {
            dates += "<p>" + new Date(dataEntry.appointmentDates[i]).toLocaleString() + "</p>";
        }

        var row =
            "<tr>" +
            "<th>" + dataEntry.idTherapy + "</th>" +
            "<th>" + dataEntry.name + "</th>" +
            "<th>" + new Date(dataEntry.assigned).toLocaleString() + "</th>" +
            "<th>" + dataEntry.assignedBy.surname + " " + dataEntry.assignedBy.name + " " + dataEntry.assignedBy.patronymic + "</th>" +
            "<th>" + dataEntry.room + "</th>" +
            "<th>" + "<button id='showProcedureData" + dataEntry.idTherapy + "' class='btn btn-primary'>${showMedicineAdditionalInfo}</button> </th>" +
            "</tr>" +
            "<tr id='prAd" + dataEntry.idTherapy + "' class='procedureAdditionalInfo'>" +
            "<th colspan='6'>" +
            "<h6>${therapyDescription}</h6>" +
            "<p>" + dataEntry.description + "</p>" +
            "<h6>${therapyDoctorEmail}</h6>" +
            "<p>" + dataEntry.assignedBy.email + "</p>" +
            "<h6>${procedureAppointmentDates}</h6>" +
            dates +
            "</th>" +
            "</tr>";


        $("#procedureTbody").append(row);

        var dataContainer = $("#prAd" + dataEntry.idTherapy);
        $("#showProcedureData" + dataEntry.idTherapy).click(function () {
            if (dataContainer.is(":visible")) {
                dataContainer.hide();
            } else {
                dataContainer.toggle();
            }
        });
    }


    //Loading surgeries


    function loadSurgeries(page, recordsPerPage) {
        $.ajax({
            type: 'GET',
            url: "/getSurgeries${diagnosis.idDiagnosis}/?pageNumber=" + page + "&recordsPerPage=" + recordsPerPage,
            contentType: "text/plain",
            dataType: 'json',
            success: function (data) {
                populateSurgeryDataTable(data);
                setSurgeryPaginationData(data)
            },
            error: function (e) {
                console.log("There was an error with your request...");
                console.log("error: " + JSON.stringify(e));
            }
        });
    }

    function setSurgeryPaginationData(data) {
        console.log(data);
        var page = data.pageable.pageNumber;
        var recordsPerPage = data.pageable.pageSize;

        $('#surgeryEntriesDropdownButton').html(recordsPerPage);
        $('#surgeryEntriesDropdown a').click(function (e) {
            e.preventDefault(); // cancel the link behaviour
            var x = $(this).text();
            console.log(x + "fsf");
            loadSurgeries(page, x);
        });
        $('#surgeryPageEntries').html(data.numberOfElements);
        $('#surgeryTotalEntries').html(data.totalElements);

        if (data.first) {
            $('#surgeryPreviousPage').hide();
        } else {
            var prevButton = $('#surgeryPreviousPage').show();
            prevButton.off("click");
            prevButton.click(function (e) {
                loadSurgeries(page - 1, recordsPerPage);
            })
        }

        if (data.last) {
            $('#surgeryNextPage').hide();
        } else {
            var nextButton = $('#surgeryNextPage');
            nextButton.show();
            nextButton.off("click");
            nextButton.click.click(function (e) {
                loadSurgeries(page + 1, recordsPerPage);
            })
        }

        $("#surgeryPages").html("");
        for (var i = 1; i <= data.totalPages; i++) {
            if (i - 1 == data.number) {
                $("#surgeryPages").append(
                    "<li class='page-item active'>" +
                    "<a class='page-link'>" + i + "</a>" +
                    "</li>");
            } else {
                $("#surgeryPages").append(
                    "<li class='page-item'>" +
                    "<a id='surgeryPage" + i + "' class='page-link'>" + i + "</a>" +
                    "</li>");

                $('#surgeryPage' + i).click(function (e) {
                    var pag = $(this).text() - 1;
                    loadSurgeries(pag, recordsPerPage);
                })
            }
        }


    }

    function populateSurgeryDataTable(data) {
        $("#surgeryTbody").html("");
        for (var i = 0; i < data.numberOfElements; i++) {
            addSurgeryRow(data.content[i]);
        }
    }

    function addSurgeryRow(dataEntry) {
        var row =
            "<tr>" +
            "<th>" + dataEntry.idTherapy + "</th>" +
            "<th>" + dataEntry.name + "</th>" +
            "<th>" + new Date(dataEntry.assigned).toLocaleString() + "</th>" +
            "<th>" + dataEntry.assignedBy.surname + " " + dataEntry.assignedBy.name + " " + dataEntry.assignedBy.patronymic + "</th>" +
            "<th>" + new Date(dataEntry.date).toLocaleString() + "</th>" +
            "<th>" + "<button id='showSurgeryData" + dataEntry.idTherapy + "' class='btn btn-primary'>${showMedicineAdditionalInfo}</button> </th>" +
            "</tr>" +
            "<tr id='suAd" + dataEntry.idTherapy + "' class='medicineAdditionalInfo'>" +
            "<th colspan='6'>" +
            "<h6>${therapyDescription}</h6>" +
            "<p>" + dataEntry.description + "</p>" +
            "<h6>${therapyDoctorEmail}</h6>" +
            "<p>" + dataEntry.assignedBy.email + "</p>" +
            "</th>" +
            "</tr>";
        console.log(row);


        $("#surgeryTbody").append(row);

        var dataContainer = $("#suAd" + dataEntry.idTherapy);
        $("#showSurgeryData" + dataEntry.idTherapy).click(function () {
            if (dataContainer.is(":visible")) {
                dataContainer.hide();
            } else {
                dataContainer.toggle();
            }
        });
    }

    //add new therapy functions
    <sec:authorize access="hasAnyRole('DOCTOR','NURSE')">
    function sendAddMedicine() {
        $("#medicineCreated").hide();

        var formData = getFormData($('#addMedicineForm'));
        console.log(formData);

        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");

        $.ajaxSetup({
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            }
        });

        $.ajax({
            type: 'POST',
            url: "/doctor/diagnosis${diagnosis.idDiagnosis}/addMedicine",
            dataType: 'json',
            contentType: 'application/json',
            data: JSON.stringify(formData),
            success: function (data) {
                $("#medicineNameFieldError").hide();
                $("#medicineCountFieldError").hide();
                $("#addMedicine").hide();
                console.log(data);
                if (data.message === "created") {
                    $("#medicineCreated").show();
                } else {
                    $("#medicineCreationError").html(data.message);
                    $("#medicineCreationError").show();
                }

            },
            error: function (data) {
                console.log(data);
                data.responseJSON.errors.forEach(function (error) {
                    console.log(error);
                    if (error.field === "name") {
                        var errMessage = $("#medicineNameFieldError");
                        errMessage.html(error.defaultMessage);
                        errMessage.show();

                    } else if (error.field === "count") {
                        var errMessage = $("#medicineCountFieldError");
                        errMessage.html(error.defaultMessage);
                        errMessage.show();
                    } else {
                        var errMessage = $("#medicineCreationError");
                        errMessage.html(error.defaultMessage);
                        errMessage.show();
                    }
                });
            }
        });


    }

    function sendAddProcedure() {

        $("#procedureCreated").hide();

        var formData = getFormData($("#addProcedureForm"));
        console.log(formData);
        console.log(JSON.stringify(formData));

        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");

        $.ajaxSetup({
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            }
        });

        $.ajax({
            type: 'POST',
            url: "/doctor/diagnosis${diagnosis.idDiagnosis}/addProcedure",
            dataType: 'json',
            contentType: 'application/json',
            data: JSON.stringify(formData),
            success: function (data) {
                console.log(data);

                $("#procedureNameFieldError").hide();
                $("#procedureRoomFieldError").hide();
                $("#addProcedure").hide();
                console.log(data);
                if (data.message === "created") {
                    $("#procedureCreated").show();
                } else {
                    $("#procedureCreationError").html(data.message);
                    $("#procedureCreationError").show();
                }

            },
            error: function (data) {
                console.log(data);
                if (data.responseJSON.errors !== 'undefined' && data.responseJSON.errors.length > 0) {
                    data.responseJSON.errors.forEach(function (error) {
                        if (error.field === "name") {
                            var errMessage = $("#procedureNameFieldError");
                            errMessage.html(error.defaultMessage);
                            errMessage.show();

                        } else if (error.field === "room") {
                            var errMessage = $("#procedureRoomFieldError");
                            errMessage.html(error.defaultMessage);
                            errMessage.show();
                        } else {
                            var errMessage = $("#procedureCreationError");
                            errMessage.html(data.responseJSON.message);
                            errMessage.show();
                        }
                    });
                } else {
                    var errMessage = $("#procedureCreationError");
                    errMessage.html(data.responseJSON.message);
                    errMessage.show();
                }
            }
        });

    }
    </sec:authorize>
    <sec:authorize access="hasRole('DOCTOR')">

    function sendAddSurgery() {
        $("#surgeryCreated").hide();

        var formData = getFormData($("#addSurgeryForm"));
        console.log(formData);
        console.log(JSON.stringify(formData));

        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");

        $.ajaxSetup({
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            }
        });

        $.ajax({
            type: 'POST',
            url: "/doctor/diagnosis${diagnosis.idDiagnosis}/addSurgery",
            dataType: 'json',
            contentType: 'application/json',
            data: JSON.stringify(formData),
            success: function (data) {
                console.log(data);

                $("#surgeryNameFieldError").hide();
                $("#surgeryDateFieldError").hide();
                $("#addSurgery").hide();
                console.log(data);
                if (data.message === "created") {
                    $("#surgeryCreated").show();
                } else {
                    $("#surgeryCreationError").html(data.message);
                    $("#surgeryCreationError").show();
                }

            },
            error: function (data) {
                console.log(data);
                if (data.responseJSON.errors !== 'undefined' && data.responseJSON.errors.length > 0) {
                    data.responseJSON.errors.forEach(function (error) {
                        if (error.field === "name") {
                            var errMessage = $("#surgeryNameFieldError");
                            errMessage.html(error.defaultMessage);
                            errMessage.show();

                        } else if (error.field === "surgeryDate") {
                            var errMessage = $("#surgeryDateFieldError");
                            errMessage.html(error.defaultMessage);
                            errMessage.show();
                        } else {
                            var errMessage = $("#surgeryCreationError");
                            errMessage.html(data.responseJSON.message);
                            errMessage.show();
                        }
                    });
                } else {
                    var errMessage = $("#surgeryCreationError");
                    errMessage.html(data.responseJSON.message);
                    errMessage.show();
                }
            }
        });
    }

    </sec:authorize>

    function getFormData($form) {
        var unindexed_array = $form.serializeArray();

        console.log(unindexed_array);

        var indexed_array = {};

        var dateIndex = 0;
        $.map(unindexed_array, function (n, i) {
            if (n['name'] === "appointmentDates") {
                console.log(n);
                if (dateIndex === 0) {
                    indexed_array['appointmentDates'] = [];
                }
                indexed_array.appointmentDates[(dateIndex++)] = n['value'];
            } else {
                indexed_array[n['name']] = n['value'];
            }
        });
        return indexed_array;
    }

    function closeDiagnosis() {
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");

        $.ajaxSetup({
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            }
        });

        $.ajax({
            type: 'PATCH',
            url: "/doctor/diagnosis${diagnosis.idDiagnosis}/closeDiagnosis",
            dataType: 'json',
            contentType: 'application/json',
            success: function (data) {
                console.log(data);
                if (data.response === "closed") {
                    $("#closed").show();
                    $("#closeDiagnosisButton").hide();
                    $("#cantClose").hide();
                } else {
                    $("#closed").hide();
                    $("#cantClose").show();
                }
            },
            error: function (data) {
                console.log(data);
                $("#closed").hide();
                $("#cantClose").show();
            }
        });
    }
</script>
</body>
</html>
