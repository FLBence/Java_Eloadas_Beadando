function loadHistory() {
    const inst = document.getElementById("histInstrument").value;
    const gran = document.getElementById("histGranularity").value;

    fetch("/historyPrice?instrument=" + inst + "&granularity=" + gran)
        .then(r => r.json())
        .then(data => {
            let table = document.getElementById("historyTable");
            table.innerHTML = "<tr><th>Időpont</th><th>Záróár</th></tr>";

            data.forEach(row => {
                table.innerHTML += `
                        <tr>
                            <td>${row.time}</td>
                            <td>${row.price}</td>
                        </tr>`;
            });
        })
        .catch(err => alert("Hiba történt: " + err));
}
