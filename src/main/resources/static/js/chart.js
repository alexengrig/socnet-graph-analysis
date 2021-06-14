function createChart(ys, elementId) {
    var ctx = document.getElementById(elementId).getContext("2d");
    new Chart(ctx, {
        type: 'line',
        data: {
            labels: [2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16],
            datasets: [{
                data: ys,
                label: "Сумма квадратов ошибок",
                borderColor: "green",
                fill: false
            }]
        },
        options: {
            responsive: true,
            title: {
                display: true,
                text: 'Метод локтя'
            }
        }
    });
}
