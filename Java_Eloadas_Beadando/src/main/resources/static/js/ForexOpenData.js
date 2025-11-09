document.addEventListener("DOMContentLoaded", function () {
    const btn = document.getElementById("btnOpen");
    if (!btn) return;

    btn.addEventListener("click", function () {

        const instrument = document.getElementById("openInstrument").value;
        const units = parseInt(document.getElementById("openUnits").value);
        const div = document.getElementById("openResult");

        div.innerHTML = "Küldés folyamatban...";

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
                div.innerHTML =
                    `<p style="color:lime;">${data.message}</p>
                     <p>Trade ID: ${data.tradeId}</p>
                     <p>Nyitási ár: ${data.openPrice}</p>`;
            })
            .catch(err => {
                div.innerHTML = `<p style="color:red;">Hiba: ${err}</p>`;
            });
    });
});