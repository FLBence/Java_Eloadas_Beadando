document.addEventListener("DOMContentLoaded", function() {
    const loadBtn = document.getElementById("loadAccount");
    const container = document.getElementById("forex-account-container");

    async function loadAccountData() {
        container.innerHTML = "<p>Adatok betöltése...</p>";
        try {
            const response = await fetch("/api/forex/summary");
            if (!response.ok) throw new Error("Hiba a lekérés során");
            const data = await response.json();

            container.innerHTML = `
                <table style="width:100%; border-collapse:collapse;">
                    <tr><th style="text-align:left;">Számla ID</th><td>${data.id}</td></tr>
                    <tr><th style="text-align:left;">Név</th><td>${data.alias}</td></tr>
                    <tr><th style="text-align:left;">Devizanem</th><td>${data.currency}</td></tr>
                    <tr><th style="text-align:left;">Egyenleg</th><td>${data.balance}</td></tr>
                    <tr><th style="text-align:left;">Elérhető margin</th><td>${data.marginAvailable}</td></tr>
                    <tr><th style="text-align:left;">Nyitott pozíciók</th><td>${data.openTradeCount}</td></tr>
                    <tr><th style="text-align:left;">Nyitott megbízások</th><td>${data.pendingOrderCount}</td></tr>
                    <tr><th style="text-align:left;">Profit/loss</th><td>${data.unrealizedPL}</td></tr>
                </table>
            `;
        } catch (err) {
            container.innerHTML = `<p style="color:red;">Hiba történt: ${err.message}</p>`;
        }
    }

    loadBtn.addEventListener("click", loadAccountData);
});