document.addEventListener("DOMContentLoaded", function () {

    const btnClose = document.getElementById("btnClose");
    const closeTradeId = document.getElementById("closeTradeId");
    const closeResult = document.getElementById("closeResult");

    if (btnClose) {
        btnClose.addEventListener("click", function () {
            const tradeId = closeTradeId.value.trim();

            if (!tradeId) {
                closeResult.innerHTML = `<p style="color:red;">Adj meg egy Trade ID-t!</p>`;
                return;
            }

            fetch("/forex-close", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ tradeId: tradeId })
            })
                .then(r => r.json())
                .then(data => {
                    closeResult.innerHTML = `
                        <p style="color:${data.status === 'OK' ? 'lime' : 'red'};">
                            ${data.message}
                        </p>
                        <p>Trade ID: ${data.tradeId}</p>
                        <p>Instrument: ${data.instrument ?? '-'}</p>
                        <p>Záróár (piaci ár): ${data.closedPrice || '—'}</p>
                    `;
                })
                .catch(err => {
                    closeResult.innerHTML = `<p style="color:red;">Hiba: ${err}</p>`;
                });
        });
    }

    window.addEventListener("beforeunload", function () {
        if (closeTradeId) closeTradeId.value = "";
        if (closeResult) closeResult.innerHTML = "";
    });

    window.addEventListener("pageshow", function (event) {
        if (closeTradeId) closeTradeId.value = "";
        if (closeResult) closeResult.innerHTML = "";
    });

    window.addEventListener("hashchange", function() {
        if (window.location.hash === '#') {
            if (closeTradeId) closeTradeId.value = "";
            if (closeResult) closeResult.innerHTML = "";
            console.log("Forex-Zár visszaállítva hashChange után.");
        }
    });
});