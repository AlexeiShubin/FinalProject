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

        .container .message {
            text-align: center;
            margin-top: 15px;
            color: #777;
        }

        .error-message {
            color: red;
            margin-top: 20px;
            text-align: center;
        }

    </style>
</head>
<body>
    <div class="container">
        <h1>Добро пожаловать в онлайн аптеку</h1>
        <form id="loginForm" action="/login" method="POST">
            <div class="input-group">
                <label for="phone">Телефон:</label>
                <input type="text" id="phone" name="phone" required maxlength="15" minlength="10" pattern="^\+\d{1,3}\s?\d{1,14}$" title="Формат: +<код страны (1-3 цифры)> <номер (1-14 цифр)>">
            </div>
            <div class="input-group">
                <label for="password">Пароль:</label>
                <input type="password" id="password" name="password" required minlength="8" pattern="^[A-Za-z0-9]*$" title="Введите пароль на ENG">
            </div>
            <button type="submit">Войти</button>
        </form>

        <div class="message">
            <p>Нет аккаунта? <a href="registration">Регистрация</a></p>
        </div>

        <c:if test="${not empty errorMessage}">
            <div class="error-message">${errorMessage}</div>
        </c:if>
    </div>
</body>
</html>