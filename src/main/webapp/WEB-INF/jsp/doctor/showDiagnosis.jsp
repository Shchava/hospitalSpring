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

        .procedure-container{
            display: none;
        }

        .show-button{
            margin-bottom: 10px;
        }
        .medicineAdditionalInfo{
            display: none;
        }

        .procedureAdditionalInfo{
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

                <button id="showMedicine" role="button" class="btn btn-primary btn-lg btn-block show-button"><spring:message code="doctor.showDiagnosis.showMedicine"/></button>
                <div class="clearfix"></div>
                <div id="medicineContainer" class="medicine-container">
                    <div class="table-wrapper">
                        <div class="table-filter">
                            <div class="row">
                                <div class="col-sm-3">
                                    <div class="show-entries">
                                        <span><spring:message code="pagination.show"/></span>
                                        <div id="medicineEntriesDropdown" class="dropdown">
                                            <button id="medicineEntriesDropdownButton" class="btn btn-primary  float-none dropdown-toggle paginationDropdown"
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
                                <th><spring:message code="doctor.showDiagnosis.showTherapy.id"/></th>
                                <th><spring:message code="doctor.showDiagnosis.showTherapy.name"/></th>
                                <th><spring:message code="doctor.showDiagnosis.showTherapy.assigned"/></th>
                                <th><spring:message code="doctor.showDiagnosis.showTherapy.assignedBy"/></th>
                                <th><spring:message code="doctor.showDiagnosis.showMedicine.count"/></th>
                                <th><spring:message code="doctor.showDiagnosis.showTherapy.open"/></th>
                            </tr>
                            </thead>
                            <tbody id = "medicineTbody">
                            </tbody>
                        </table>
                        <div class="clearfix">
                            <div class="hint-text"><spring:message code="pagination.label.showing"/> <b id = "medicinePageEntries"></b> <spring:message code="pagination.label.outOf"/>
                                <b id="medicineTotalEntries"></b> <spring:message code="pagination.label.entries"/></div>


                            <ul class="pagination">

                                <li id="medicinePreviousPage" class="page-item">
                                    <a class="page-link"><spring:message code="pagination.previous"/></a>
                                </li>

                                <div id="medicinePages"></div>

                                <li id="medicineNextPage" class="page-item">
                                    <a class="page-link"><spring:message code="pagination.next"/></a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                <button id="showProcedures" role="button" class="btn btn-primary btn-lg btn-block show-button"><spring:message code="doctor.showDiagnosis.showProcedures"/></button>

                <div class="clearfix"></div>

                <div id="procedureContainer" class="procedure-container">
                    <div class="table-wrapper">
                        <div class="table-filter">
                            <div class="row">
                                <div class="col-sm-3">
                                    <div class="show-entries">
                                        <span><spring:message code="pagination.show"/></span>
                                        <div id="procedureEntriesDropdown" class="dropdown">
                                            <button id="procedureEntriesDropdownButton" class="btn btn-primary  float-none dropdown-toggle paginationDropdown"
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
                                    <button type="button" class="btn btn-primary"><i class="fa fa-search"></i></button>
                                    <div class="filter-group">
                                        <label>Name</label>
                                        <input type="text" class="form-control">
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
                            <tbody id = "procedureTbody">
                            </tbody>
                        </table>
                        <div class="clearfix">
                            <div class="hint-text"><spring:message code="pagination.label.showing"/> <b id = "procedurePageEntries"></b> <spring:message code="pagination.label.outOf"/>
                                <b id="procedureTotalEntries"></b> <spring:message code="pagination.label.entries"/></div>


                            <ul class="pagination">

                                <li id="procedurePreviousPage" class="page-item">
                                    <a class="page-link"><spring:message code="pagination.previous"/></a>
                                </li>

                                <div id="procedurePages"></div>

                                <li id="procedureNextPage" class="page-item">
                                    <a class="page-link"><spring:message code="pagination.next"/></a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>

                <button id="showSurgeries" role="button" class="btn btn-primary btn-lg btn-block"><spring:message code="doctor.showDiagnosis.showSurgeries"/></button>
                <div class="clearfix"></div>
                <div id="surgeryContainer" class="procedure-container">
                    <div class="table-wrapper">
                        <div class="table-filter">
                            <div class="row">
                                <div class="col-sm-3">
                                    <div class="show-entries">
                                        <span><spring:message code="pagination.show"/></span>
                                        <div id="surgeryEntriesDropdown" class="dropdown">
                                            <button id="surgeryEntriesDropdownButton" class="btn btn-primary  float-none dropdown-toggle paginationDropdown"
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
                                    <button type="button" class="btn btn-primary"><i class="fa fa-search"></i></button>
                                    <div class="filter-group">
                                        <label>Name</label>
                                        <input type="text" class="form-control">
                                    </div>

                                </div>
                            </div>
                        </div>

                        <table id="surgeryTable" class="table table-striped table-hover">
                            <thead>
                            <tr>
                                <th><spring:message code="doctor.showDiagnosis.showTherapy.id"/></th>
                                <th><spring:message code="doctor.showDiagnosis.showTherapy.name"/></th>
                                <th><spring:message code="doctor.showDiagnosis.showTherapy.assigned"/></th>
                                <th><spring:message code="doctor.showDiagnosis.showTherapy.assignedBy"/></th>
                                <th><spring:message code="doctor.showDiagnosis.showSurgery.date"/></th>
                                <th><spring:message code="doctor.showDiagnosis.showTherapy.open"/></th>
                            </tr>
                            </thead>
                            <tbody id = "surgeryTbody">
                            </tbody>
                        </table>
                        <div class="clearfix">
                            <div class="hint-text"><spring:message code="pagination.label.showing"/> <b id = "surgeryPageEntries"></b> <spring:message code="pagination.label.outOf"/>
                                <b id="surgeryTotalEntries"></b> <spring:message code="pagination.label.entries"/></div>


                            <ul class="pagination">

                                <li id="surgeryPreviousPage" class="page-item">
                                    <a class="page-link"><spring:message code="pagination.previous"/></a>
                                </li>

                                <div id="surgeryPages"></div>

                                <li id="surgeryNextPage" class="page-item">
                                    <a class="page-link"><spring:message code="pagination.next"/></a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>

            </div>

        </div>
        <div class="col-sm-2 sidenav">


        </div>
    </div>
</div>


<script src="/webjars/jquery/3.4.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js" integrity="sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4" crossorigin="anonymous"></script>
<script src="/webjars/bootstrap/4.3.1/js/bootstrap.min.js"></script>
<script>
    $(document).ready(function () {
        $("#showMedicine").click(function () {
            if($("#medicineContainer").is(":visible")){
                $("#medicineContainer").hide();
            } else {
                $("#medicineContainer").show();

                loadMedicine(0,10);
            }
        });

        $("#showProcedures").click(function () {
            if($("#procedureContainer").is(":visible")){
                $("#procedureContainer").hide();
            } else {
                $("#procedureContainer").show();

                loadProcedures(0,10);
            }
        });

        $("#showSurgeries").click(function () {
            if($("#surgeryContainer").is(":visible")){
                $("#surgeryContainer").hide();
            } else {
                $("#surgeryContainer").show();

                loadSurgeries(0,10);
            }
        });

    });

    //Loading medicine
    
    function loadMedicine(page,recordsPerPage) {
        $.ajax({
            type: 'GET',
            url: "/getMedicine${diagnosis.idDiagnosis}/?pageNumber="+page+"&recordsPerPage="+recordsPerPage,
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
        $('#medicineEntriesDropdown a').click(function(e) {
            e.preventDefault(); // cancel the link behaviour
            var x = $(this).text();
            console.log(x+"fsf");
            loadMedicine(page,x);
        });
        $('#medicinePageEntries').html(data.numberOfElements);
        $('#medicineTotalEntries').html(data.totalElements);

        if(data.first) {
            $('#medicinePreviousPage').hide();
        }else{
            $('#medicinePreviousPage').click(function(e) {
                loadMedicine(page - 1,recordsPerPage);
            })
        }

        if(data.last) {
            $('#medicineNextPage').hide();
        }else{
            $('#medicineNextPage').click(function(e) {
                loadMedicine(page + 1,recordsPerPage);
            })
        }

        $("#medicinePages").html("");
        for (var i = 1; i <= data.totalPages; i++ ){
            if(i-1==data.number){
                $("#medicinePages").append(
                    "<li class='page-item active'>" +
                    "<a class='page-link'>" + i + "</a>" +
                    "</li>");
            }else{
                $("#medicinePages").append(
                    "<li class='page-item'>" +
                    "<a id='medicinePage"+ i +"' class='page-link'>" + i + "</a>" +
                    "</li>");

                $('#medicinePage'+ i).click(function(e) {
                    var pag = i;
                    loadMedicine(pag,recordsPerPage);
                })
            }
        }


    }

    function populateMedicineDataTable(data) {
        for (var i = 0; i < data.numberOfElements; i++) {
            console.log(data.content[i]);
            addMedicineRow(data.content[i]);
        }
    }

    function addMedicineRow(dataEntry) {
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
                    "<h6>${therapyDescription}</h6>" +
                    "<p>" + dataEntry.description + "</p>" +
                    "<h6>${therapyDoctorEmail}</h6>" +
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

    //Loading procedures


    function loadProcedures(page,recordsPerPage) {
        $.ajax({
            type: 'GET',
            url: "/getProcedures${diagnosis.idDiagnosis}/?pageNumber="+page+"&recordsPerPage="+recordsPerPage,
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
        $('#procedureEntriesDropdown a').click(function(e) {
            e.preventDefault(); // cancel the link behaviour
            var x = $(this).text();
            console.log(x+"fsf");
            loadProcedures(page,x);
        });
        $('#procedurePageEntries').html(data.numberOfElements);
        $('#procedureTotalEntries').html(data.totalElements);

        if(data.first) {
            $('#procedurePreviousPage').hide();
        }else{
            $('#procedurePreviousPage').click(function(e) {
                loadProcedures(page - 1,recordsPerPage);
            })
        }

        if(data.last) {
            $('#procedureNextPage').hide();
        }else{
            $('#procedureNextPage').click(function(e) {
                loadProcedures(page + 1,recordsPerPage);
            })
        }

        $("#procedurePages").html("");
        for (var i = 1; i <= data.totalPages; i++ ){
            if(i-1==data.number){
                $("#procedurePages").append(
                    "<li class='page-item active'>" +
                    "<a class='page-link'>" + i + "</a>" +
                    "</li>");
            }else{
                $("#procedurePages").append(
                    "<li class='page-item'>" +
                    "<a id='procedurePage"+ i +"' class='page-link'>" + i + "</a>" +
                    "</li>");

                $('#procedurePage'+ i).click(function(e) {
                    var pag = i;
                    loadProcedures(pag,recordsPerPage);
                })
            }
        }


    }

    function populateProceduresDataTable(data) {
        for (var i = 0; i < data.numberOfElements; i++) {
            console.log(data.content[i]);
            addProcedureRow(data.content[i]);
        }
    }

    function addProcedureRow(dataEntry) {
        var row =
            "<tr>" +
            "<th>" + dataEntry.idTherapy + "</th>" +
            "<th>" + dataEntry.name + "</th>" +
            "<th>" +   new Date(dataEntry.assigned).toLocaleString() + "</th>" +
            "<th>" + dataEntry.assignedBy.surname + " " + dataEntry.assignedBy.name + " " + dataEntry.assignedBy.patronymic + "</th>" +
            "<th>" + dataEntry.room + "</th>" +
            "<th>" + "<button id='showProcedureData"+ dataEntry.idTherapy + "' class='btn btn-primary'>${showMedicineAdditionalInfo}</button> </th>" +
            "</tr>" +
            "<tr id='prAd"+dataEntry.idTherapy+"' class='procedureAdditionalInfo'>" +
            "<th colspan='6'>" +
            "<h6>${therapyDescription}</h6>" +
            "<p>" + dataEntry.description + "</p>" +
            "<h6>${therapyDoctorEmail}</h6>" +
            "<p>" + dataEntry.assignedBy.email + "</p>" +
            "<h6>${medicineRefill}</h6>" +
            // "<p>" + new Date(dataEntry.refill).toLocaleString() + "</p>" +
            "</th>" +
            "</tr>";
        console.log(row);


        $("#procedureTbody").html(row);

        var dataContainer = $("#prAd" + dataEntry.idTherapy);
        $("#showProcedureData" + dataEntry.idTherapy).click(function () {
            if(dataContainer.is(":visible")){
                dataContainer.hide();
            } else {
                dataContainer.toggle();
            }
        });
    }


    //Loading surgeries


    function loadSurgeries(page,recordsPerPage) {
        $.ajax({
            type: 'GET',
            url: "/getSurgeries${diagnosis.idDiagnosis}/?pageNumber="+page+"&recordsPerPage="+recordsPerPage,
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
        $('#surgeryEntriesDropdown a').click(function(e) {
            e.preventDefault(); // cancel the link behaviour
            var x = $(this).text();
            console.log(x+"fsf");
            loadSurgeries(page,x);
        });
        $('#surgeryPageEntries').html(data.numberOfElements);
        $('#surgeryTotalEntries').html(data.totalElements);

        if(data.first) {
            $('#surgeryPreviousPage').hide();
        }else{
            $('#surgeryPreviousPage').click(function(e) {
                loadSurgeries(page - 1,recordsPerPage);
            })
        }

        if(data.last) {
            $('#surgeryNextPage').hide();
        }else{
            $('#surgeryNextPage').click(function(e) {
                loadSurgeries(page + 1,recordsPerPage);
            })
        }

        $("#surgeryPages").html("");
        for (var i = 1; i <= data.totalPages; i++ ){
            if(i-1==data.number){
                $("#surgeryPages").append(
                    "<li class='page-item active'>" +
                    "<a class='page-link'>" + i + "</a>" +
                    "</li>");
            }else{
                $("#surgeryPages").append(
                    "<li class='page-item'>" +
                    "<a id='surgeryPage"+ i +"' class='page-link'>" + i + "</a>" +
                    "</li>");

                $('#surgeryPage'+ i).click(function(e) {
                    var pag = i;
                    loadSurgeries(pag,recordsPerPage);
                })
            }
        }


    }

    function populateSurgeryDataTable(data) {
        for (var i = 0; i < data.numberOfElements; i++) {
            console.log(data.content[i]);
            addSurgeryRow(data.content[i]);
        }
    }

    function addSurgeryRow(dataEntry) {
        var row =
            "<tr>" +
            "<th>" + dataEntry.idTherapy + "</th>" +
            "<th>" + dataEntry.name + "</th>" +
            "<th>" +   new Date(dataEntry.assigned).toLocaleString() + "</th>" +
            "<th>" + dataEntry.assignedBy.surname + " " + dataEntry.assignedBy.name + " " + dataEntry.assignedBy.patronymic + "</th>" +
            "<th>" +   new Date(dataEntry.date).toLocaleString()  + "</th>" +
            "<th>" + "<button id='showSurgeryData"+ dataEntry.idTherapy + "' class='btn btn-primary'>${showMedicineAdditionalInfo}</button> </th>" +
            "</tr>" +
            "<tr id='suAd"+dataEntry.idTherapy+"' class='medicineAdditionalInfo'>" +
            "<th colspan='6'>" +
            "<h6>${therapyDescription}</h6>" +
            "<p>" + dataEntry.description + "</p>" +
            "<h6>${therapyDoctorEmail}</h6>" +
            "<p>" + dataEntry.assignedBy.email + "</p>" +
            "</th>" +
            "</tr>";
        console.log(row);


        $("#surgeryTbody").html(row);

        var dataContainer = $("#suAd" + dataEntry.idTherapy);
        $("#showSurgeryData" + dataEntry.idTherapy).click(function () {
            if(dataContainer.is(":visible")){
                dataContainer.hide();
            } else {
                dataContainer.toggle();
            }
        });
    }

</script>
</body>
</html>
