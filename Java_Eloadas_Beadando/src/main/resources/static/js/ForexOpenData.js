document.addEventListener("DOMContentLoaded", function () {
    const btn = document.getElementById("btnOpen");
    if (!btn) {
        console.log("btnOpen NEM található!");
        return;
    }

    btn.addEventListener("click", function () {
        const instrument = document.getElementById("openInstrument").value;
        const units = document.getElementById("openUnits").value;
        const resultDiv = document.getElementById("openResult");

        console.log("Küldés indul...");

        fetch("/forex-open", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({
                instrument: instrument,
                units: units
            })
        })
            .then(r => r.json())
            .then(data => {
                console.log("Válasz érkezett:", data);
                resultDiv.innerHTML = `
                <p style="color:lime;">${data.status}</p>
                <p>Order ID: ${data.orderId || ''}</p>
                <p>Ár: ${data.filledPrice || ''}</p>
            `;
            })
            .catch(err => {
                console.log("Fetch hiba:", err);
                resultDiv.innerHTML = `<p style="color:red;">Hiba: ${err}</p>`;
            });
    });
});