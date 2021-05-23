function updateDiagnosisLabels (lang) {
    $(".display-diagnosis").each((i, diagnosis) => {
        $.ajax({
            type: 'GET',
            url: "/diagnosis-prediction/diagnosis-translation",
            contentType: 'text',
            dataType: 'text',
            data: {
                lang: lang,
                diagnosisIdentifier: diagnosis.innerText
            },
            success: function (data) {
                diagnosis.innerText = data
            },
            error: function (data) {
                console.log("error")
                console.log(data);
            }
        });



    })
}