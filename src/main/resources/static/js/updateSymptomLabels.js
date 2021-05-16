function updateSymptomLabels (lang) {
    $(".show-symptom span").each((i, symptom) => {
        console.log( lang);


        $.ajax({
            type: 'GET',
            url: "/diagnosis-prediction/symptom-translation",
            contentType: 'text',
            dataType: 'text',
            data: {
                lang: lang,
                symptomIdentifier: symptom.innerText
            },
            success: function (data) {
                symptom.innerText = data
            },
            error: function (data) {
                console.log("error")
                console.log(data);
            }
        });



    })
}