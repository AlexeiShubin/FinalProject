<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Все заказы пользователей</title>
    <link rel="stylesheet" href="styles.css"> <!-- Подключение CSS файла -->
    <style>
            * {
                box-sizing: border-box; /* Позволяет учитывать padding и границы в общей ширине */
            }

            body {
                font-family: Arial, sans-serif;
                margin: 0;
                min-height: 100vh; /* Минимальная высота экрана */
                display: flex;
                align-items: center;
                justify-content: center;
                overflow: hidden; /* Запретить прокрутку тела */
                background-color: #0d47a1; /* Темно-синий фон */
            }

            .gradient-background {
                position: fixed; /* Фиксированное положение для неподвижного фона */
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background: linear-gradient(270deg, #0d47a1, #1976d2, #42a5f5, #81d4fa); /* Синий градиент */
                background-size: 400% 400%;
                animation: gradientAnimation 10s ease infinite;
                z-index: 0; /* Позади основного контента */
            }

            @keyframes gradientAnimation {
                0% { background-position: 0% 50%; }
                50% { background-position: 100% 50%; }
                100% { background-position: 0% 50%; }
            }

            .shine {
                position: fixed;
                top: -30%;
                left: -30%;
                width: 200%;
                height: 200%;
                background: radial-gradient(circle, rgba(255, 255, 255, 0.3) 30%, rgba(255, 255, 255, 0) 70%);
                animation: shineAnimation 3s infinite;
                pointer-events: none;
                z-index: 1; /* Фон эффекта сияния */
            }

            @keyframes shineAnimation {
                0% { transform: translate(-200%, -200%); }
                50% { transform: translate(200%, 200%); }
                100% { transform: translate(-200%, -200%); }
            }

            .container {
                background: rgba(255, 255, 255, 0.9);
                padding: 40px;
                border-radius: 10px;
                box-shadow: 0 4px 20px rgba(0, 0, 0, 0.2);
                max-width: 900px;
                width: 100%; /* Чтобы контейнер занимал 100% ширины */
                position: relative;
                z-index: 2; /* Один уровень выше фона */
                overflow-y: auto; /* Прокрутка по вертикали */
                max-height: 80vh; /* Максимальная высота для контейнера */
            }

            h1, h2 {
                color: #00796b;
                text-align: center;
                animation: bounceIn 1s ease forwards;
            }

            @keyframes bounceIn {
                0% { transform: scale(0.5); opacity: 0; }
                50% { transform: scale(1.05); opacity: 1; }
                100% { transform: scale(1); }
            }

            table {
                width: 100%; /* таблица займет 100% ширины контейнера */
                border-collapse: collapse;
                margin: 20px 0;
                animation: pulse 1s infinite alternate;
            }

            @keyframes pulse {
                from { transform: scale(1); }
                to { transform: scale(1.02); }
            }

            th, td {
                border: 1px solid #b2dfdb;
                padding: 15px;
                text-align: left;
                transition: background-color 0.3s;
            }

            th:hover, td:hover {
                background-color: rgba(130, 177, 160, 0.5);
            }

            th {
                background-color: #80cbc4;
                color: white;
            }

            input[type="number"], input[type="text"] {
                padding: 10px;
                width: 95%; /* Чтобы входы занимали 95% ширины контейнера */
                box-sizing: border-box; /* Включите padding в ширину */
                border: 1px solid #009688;
                border-radius: 5px;
                transition: border-color 0.3s;
            }

            input[type="number"]:focus, input[type="text"]:focus {
                border-color: #00796b;
                outline: none;
            }

            button {
                background-color: #ffca28;
                color: white;
                padding: 15px 20px;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                font-size: 16px;
                transition: background-color 0.3s, transform 0.3s;
                width: 100%; /* Кнопка занимает 100% ширины контейнера */
                margin-top: 20px;
            }

            button:hover {
                background-color: #ffc107;
                transform: scale(1.05);
            }

            .total-price {
                margin-top: 20px;
                text-align: right;
            }

            .back-button,
            .order-button {
                position: fixed;
                left: 20px;
                padding: 10px 15px;
                font-size: 16px;
                background-color: #ff5733;
                color: white;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                box-shadow: 0 2px 5px rgba(0, 0, 0, 0.3);
                transition: background-color 0.3s;
                z-index: 1000;
            }

            .back-button {
                top: 20px;
            }

            .order-button {
                top: 70px;
            }

            .back-button:hover,
            .order-button:hover {
                background-color: #c0392b;
            }

            .back-button,
            .order-button {
                min-width: 100px;
                max-width: 150px;
            }
        </style>
</head>
<body>
    <div class="container">
        <h1>Все заказы пользователей</h1>

        <a href="/myUserPage" class="back-button">Назад на панель администратора</a>
        <div style="max-height: 400px; overflow-y: auto;">
            <table>
                <thead>
                    <tr>
                        <th>ID Заказа</th>
                        <th>Пользователь</th>
                        <th>Статус</th>
                        <th>Дата заказа</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="order" items="${orders}">
                        <tr onclick="location.href='${pageContext.request.contextPath}/admin/order/${order.id}'">
                            <td>${order.id}</td>
                            <td>${order.user.name}</td>
                            <td>${order.status}</td>
                            <td>${order.orderDate}</td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty orders}">
                        <tr>
                            <td colspan="4">Нет доступных заказов.</td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>