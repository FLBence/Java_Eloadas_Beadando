document.addEventListener("DOMContentLoaded", function () {

    const btn = document.getElementById("loadPositions");
    if (!btn) return;

    btn.addEventListener("click", function () {

        const table = document.getElementById("posTable");
        table.innerHTML = "<tr><td colspan='4'>Betöltés...</td></tr>";

        fetch("/forex-positions")
            .then(r => r.json())
            .then(data => {

                table.innerHTML =
                    `<tr>
                        <th>Instrumentum</th>
                        <th>Mennyiség</th>
                        <th>Átlagár</th>
                        <th>Trade ID</th>
                    </tr>`;

                if (data.length === 0) {
                    table.innerHTML +=
                        "<tr><td colspan='4'>Nincs nyitott pozíció.</td></tr>";
                    return;
                }

                data.forEach(p => {
                    table.innerHTML +=
                        `<tr>
                            <td>${p.instrument}</td>
                            <td>${p.units}</td>
                            <td>${p.price}</td>
                            <td>${p.tradeId}</td>
                        </tr>`;
                });
            })
            .catch(err => {
                table.innerHTML =
                    `<tr><td colspan='4' style='color:red;'>Hiba: ${err}</td></tr>`;
            });
    });
});