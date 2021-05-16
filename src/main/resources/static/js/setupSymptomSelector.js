function setupSymptomSelector() {

    let selectSymptomBox = $('#selectSymptomBox');

    selectSymptomBox.on('changed.bs.select', function (e, clickedIndex, isSelected, previousValue) {

        let selectedOption = $("#selectSymptomBox > option")[clickedIndex];
        console.log(selectedOption);

        let selectedId = selectedOption.getAttribute("symptom-id");

        for (let symptomAlert of $("#selectedSymptoms > div")) {
            if (symptomAlert.getAttribute("symptom-id") === selectedId) {
                return;
            }
        }

        let symptom = document.createElement("div");
        let symptomClasses = symptom.classList;
        symptomClasses.add("alert");
        symptomClasses.add("alert-info");
        symptomClasses.add("alert-dismissible");
        symptomClasses.add("fade");
        symptomClasses.add("show");
        symptomClasses.add("show-symptom");

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
            selectSymptomBox.selectpicker({
                style: "symptom-select-button",
                title: "виберіть потрібні симптоми"
            });
        },
        error: function (data) {
            //todo: retry

            console.log(data);
        }
    });
}