<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<head>
    <title>Детали заказа</title>
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
            width: 100%; /* чтобы контейнер занимал 100% ширины */
            position: relative;
            z-index: 2; /* Один уровень выше фона */
            overflow-y: auto; /* Прокрутка по вертикали */
            max-height: 80vh; /* Максимальная высота для контейнера */
            text-align: center; /* Центрируем текст */
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

        .back-button {
            position: absolute;
            top: 20px;
            right: 20px; /* Перемещаем кнопку вправо */
            padding: 10px 15px;
            background-color: #ef5350;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            transition: background-color 0.3s;
            z-index: 2; /* Убедитесь, что кнопка верхнего уровня */
        }

        .back-button:hover {
            background-color: #c62828;
        }

        .status-button {
            padding: 10px 15px;
            background-color: #28a745;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s;
            margin-top: 20px; /* Отступ для кнопки */
        }

        .status-button:hover {
            background-color: #218838;
        }
    </style>
</head>
<body>
    <div class="shine"></div>
    <div class="gradient-background"></div>
    <div class="container">
        <a href="${pageContext.request.contextPath}/admin/getAllOrders" class="back-button">Назад к списку заказов</a>
        <h1>Пункты заказа</h1>
        <div class="table-container">
            <table>
                <thead>
                    <tr>
                        <th>Название лекарства</th>
                        <th>Количество</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${orderItems}">
                        <tr>
                            <td>${item.medicine.name}</td>
                            <td>${item.quantity}</td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty orderItems}">
                        <tr>
                            <td colspan="2">Нет доступных пунктов заказа.</td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>
        <div style="color: blue;">Сумма: ${amount} BYN</div>
        <div style="color: blue;">Адрес: ${address}</div>
       <form action="${pageContext.request.contextPath}/admin/order/${orderId}" method="post">
            <button type="submit" class="status-button">Изменить статус заказа</button>
        </form>
    </div>
</body>
</html>