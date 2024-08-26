<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Вход в онлайн аптеку</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        .container {
            background: white;
            padding: 30px; /* Общий отступ внутри контейнера */
            border-radius: 12px; /* Радиус скругления */
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
            width: 350px; /* Ширина контейнера */
            max-width: 90%; /* Ограничена ширина для мобильных устройств */
        }

        h1 {
            text-align: center;
            margin-bottom: 20px; /* Отступ снизу для заголовка */
            font-size: 1.5em; /* Размер шрифта */
        }

        .input-group {
            margin-bottom: 15px; /* Отступ для группы ввода */
        }

        label {
            display: block;
            margin-bottom: 5px; /* Отступ снизу для меток */
        }

        input {
            width: calc(100% - 20px); /* Уменьшена ширина для создания отступа */
            padding: 12px; /* Внутренний отступ для ввода */
            margin-bottom: 15px; /* Отступ снизу для полей ввода */
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        button {
            width: calc(100% - 20px); /* Уменьшена ширина для создания отступа */
            padding: 12px; /* Внутренний отступ для кнопки */
            background-color: #28a745;
            border: none;
            color: white;
            border-radius: 4px;
            cursor: pointer;
            font-size: 1em; /* Размер шрифта */
        }

        button:hover {
            background-color: #218838;
        }

        .error-message {
            color: red;
            text-align: center;
            margin-top: 10px; /* Отступ сверху для сообщений об ошибках */
        }

        .container .message {
            text-align: center;
            margin-top: 15px;
            color: #777;
        }

        .container .errorMessage {
            text-align: center;
            margin-top: 20px;
        }

    </style>
</head>
<body>
    <div class="container">
        <h1>Добро пожаловать в онлайн аптеку</h1>
        <form id="loginForm" action="/startPageAfterEntrance" method="POST">
            <div class="input-group">
                <label for="username">Телефон:</label>
                <input type="text" id="username" name="username" required>
            </div>
            <div class="input-group">
                <label for="password">Пароль:</label>
                <input type="password" id="password" name="password" required>
            </div>
            <button type="submit">Войти</button>
        </form>

        <div class="message">
            <p>Нет аккаунта? <a href="registration">Регистрация</a></p>
        </div>

        <div class="errorMessage">
            <c:if test="${not empty errorMessage}">
                <div style="color: red;">${errorMessage}</div>
            </c:if>
        </div>
    </div>
</body>
</html>