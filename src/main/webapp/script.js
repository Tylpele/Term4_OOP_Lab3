//объект для отправлки http запросов
let request  = new XMLHttpRequest();

//запрос для получения данных из json
request.open("GET", "laptops.json");
request.responseType="json";
request.send();

// обработчик событий для загрузки ответа
request.onload = function (){
    let laptops = request.response; //массив объектов ноутбуков

    fillTable(laptops); //вызов функции по заполнению таблицы
}

function fillTable(laptops){
    let tbody = document.querySelector("tbody"); // поиск элемента tbody
    laptops.forEach(laptop => { // цикл для заполенеия таблицы
        //создание новой строки в таблице
        let newRow = document.createElement("tr");
        newRow.innerHTML = `
                <td>${laptop["model"]}</td>
                <td>${laptop["processor"]}</td>
                <td>${laptop["RAM"]}</td>
                <td>${laptop["storage_drive"]}</td>
                <td>${laptop["display"]}</td>
                <td>${laptop["price"]}</td>`;

        tbody.appendChild(newRow); // добавление новой строки
    })

}
