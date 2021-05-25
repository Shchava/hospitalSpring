function setupDiagnosisSelector(lang, selectDiagnosisMessage) {



    $.ajax({
        type: 'GET',
        url: "/diagnosis-prediction/diagnoses-list?lang="+lang,
        dataType: 'json',
        contentType: 'application/json',
        success: function (data) {
            data.items.forEach(item => {

                let option = document.createElement("option");
                option.innerHTML = item.name;
                option.setAttribute("data-tokens", item.synonyms);
                option.setAttribute("value", item.diagnosisIdentifier);
                selectDiagnosisBox[0].appendChild(option);
            })
            selectDiagnosisBox.selectpicker({
                style: "symptom-select-button",
                title: selectDiagnosisMessage
            });
        },
        error: function (data) {
            //todo: retry

            console.log(data);
        }
    });
}