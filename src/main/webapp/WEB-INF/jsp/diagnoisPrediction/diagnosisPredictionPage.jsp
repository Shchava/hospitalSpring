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



    <title><spring:message code="patientList.title"/></title>


    <style>

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
                    <a class="nav-link" href="/"><spring:message code="header.message"/><span
                            class="sr-only">(current)</span></a>
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
</body>

<script>
    $(document).ready(function () {
        let selectSymptomBox = $('#selectSymptomBox');

        selectSymptomBox.on('changed.bs.select', function (e, clickedIndex, isSelected, previousValue) {
            console.log(e);
            console.log(clickedIndex);
            console.log(isSelected);
            console.log(previousValue);

            let selectedOption = $("#selectSymptomBox > option")[clickedIndex];//.children()[clickedIndex];
            console.log(selectedOption);

            let symptom = document.createElement("div");
            let symptomClasses = symptom.classList;
            symptomClasses.add("alert");
            symptomClasses.add("alert-info");
            symptomClasses.add("alert-dismissible");
            symptomClasses.add("fade");
            symptomClasses.add("show");
            symptomClasses.add("show-symptom");

            let selectedId = selectedOption.getAttribute("symptom-id");

            for (let symptomAlert of $("#selectedSymptoms > div")) {
                if(symptomAlert.getAttribute("symptom-id") === selectedId) {
                    return;
                }
            }

            let symptomDismissButton = document.createElement("button");
            symptomDismissButton.classList.add("btn-close")
            symptomDismissButton.setAttribute("data-dismiss", "alert");
            symptomDismissButton.setAttribute("aria-label", "Close");

            symptom.setAttribute("symptom-id", selectedId);
            symptom.innerText = selectedOption.innerText;
            symptom.appendChild(symptomDismissButton);


            $('#selectedSymptoms')[0].appendChild(symptom);


        });

        selectSymptomBox.on('rendered.bs.select', () => {
            $('.filter-option-inner-inner')[0].innerText = "Виберіть потрібні симптоми";
        })


        $.ajax({
            type: 'GET',
            url: "/diagnosis-prediction/symptoms-list",
            dataType: 'json',
            contentType: 'application/json',
            success: function (data) {
                data.items.forEach(item => {

                    let option = document.createElement("option");
                    option.innerHTML = item.name;
                    option.setAttribute("data-tokens", item.synonyms);
                    option.setAttribute("symptom-id", item.symptomIdentifier);
                    $("#selectSymptomBox")[0].appendChild(option);
                })
                $('select').selectpicker({
                    style: "symptom-select-button",
                    title: "виберіть потрібні симптоми"
                });
            },
            error: function (data) {
                //todo: retry

                console.log(data);
            }
        });


        $("#submit-request").on('click', ()=> {
            let data = {};
            data.symptoms = [];

            for (let symptomAlert of $("#selectedSymptoms > div")) {
                data.symptoms.push(symptomAlert.getAttribute("symptom-id"))
            }

            let token = $("meta[name='_csrf']").attr("content");
            let header = $("meta[name='_csrf_header']").attr("content");



            let form = $("#submitForm");
            $("#symptomsField").val(JSON.stringify(data));
            // $("#symptomsField").value = JSON.stringify(data);

            console.log($("#symptomsField").value)
            form.submit();

            // $.ajax({
            //     type: 'POST',
            //     url: "/diagnosis-prediction/predict",
            //     data: JSON.stringify(data),
            //     dataType: 'json',
            //     contentType: 'application/json',
            //     success: function (data) {
            //         document.open();
            //         document.write(data.responseText);
            //         document.close();
            //
            //         console.log(data)
            //     },
            //     error: function (data) {
            //         //todo: retry
            //
            //         console.log(data);
            //     }
            // });
        })
    });
</script>
</html>
