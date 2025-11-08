document.addEventListener("DOMContentLoaded", function () {

    const labelsInput = document.getElementById("soapLabels");
    const ratesInput = document.getElementById("soapRates");

    if (!labelsInput || !ratesInput) return;

    const labels = JSON.parse(labelsInput.value);
    const rates = JSON.parse(ratesInput.value);


    location.hash = "#soap";

    const ctx = document.getElementById("chartCanvas");
    if (!ctx) return;

    if (window.soapChart) {
        window.soapChart.destroy();
    }

    window.soapChart = new Chart(ctx, {
        type: "line",
        data: {
            labels: labels,
            datasets: [{
                label: "Árfolyam",
                data: rates,
                borderWidth: 2,
                fill: false
            }]
        }
    });
});