<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Информация пользователя</title>
    <style>
        * {
             box-sizing: border-box; /* Учитываем контент, отступы и границы в размерах блоков */
        }
        html, body {
             width: 100%;
             height: 100%;
             margin: 0;
             font-family: Arial, sans-serif;
             background-color: #f4f4f4;
             overflow: hidden;
        }
        body {
             display: flex;
             flex-direction: column;

        }
        @keyframes colorChange {
             0% { background: rgba(0, 102, 204, 0.9); }
             25% { background: rgba(153, 0, 153, 0.9); }
             50% { background: rgba(190, 58, 255, 0.9); }
             75% { background: rgba(126, 38, 255, 0.9); }
             100% { background: rgba(43, 194, 255, 0.9); }
        }
        header {
             animation: colorChange 10s infinite alternate; /* Анимация изменения цвета */
             color: white;
             font-size: 1.2em;
             padding: 15px 20px;
             text-align: center;
             text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.7); /* Тень текста для лучшей читаемости */
        }
        content {
            flex: 1;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            padding: 20px;
            background: rgba(50, 50, 50, 0.8);
            border-radius: 12px;
            box-shadow: 0 0 20px rgba(255, 255, 255, 0.2); /* Увеличенная тень для глубины */
            margin: 0 10px; /* Убираем боковые внешние отступы, оставляя отступы сверху и снизу */
            text-align: center;
            font-size: 1000%;
        }
        </style>
    </head>
    <body>
        <header>
            <h1>Ваш профиль онлайн аптеки</h1>
        </header>

        <div class="content">
            <h2>Ваши данные:</h2>
            <p><strong>Фамилия: <c:out value="${surname}"/></strong></p>
            <p><strong>Имя: <c:out value="${name}"/></strong></p>
            <p><strong>Номер телефона: <c:out value="${phone}"/></strong></p>
        </div>
</body>
</html>