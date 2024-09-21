<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Регистрация</title>
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
            background-color: white;
            padding: 25px; /* Общий отступ внутри контейнера */
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            width: 300px;
            max-width: 90%; /* Ограничена ширина для мобильных устройств */
        }

        .container h1 {
            text-align: center;
            color: #333;
            margin-bottom: 15px; /* Отступ снизу для заголовка */
        }

        .container label {
            margin: 10px 0 5px; /* Отступ для меток */
            display: block;
            color: #555;
        }

        .container input {
            width: calc(100% - 20px); /* Уменьшена ширина для создания отступа */
            padding: 10px; /* Внутренний отступ для ввода */
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }

        .container input[type="submit"] {
            background-color: #28a745;
            color: white;
            border: none;
            cursor: pointer;
            font-size: 16px;
            padding: 10px; /* Внутренний отступ для кнопки */
        }

        .container input[type="submit"]:hover {
            background-color: #218838;
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
    <h1>Регистрация</h1>
    <form action="/registration" method="post" onsubmit="return validateForm()">
        <label for="name">Имя:</label>
        <input type="text" id="name" name="name" maxlength="20" required pattern="[A-Za-zА-Яа-яЁё\s]+" title="В поле 'Имя' могут быть только буквы">

        <label for="surname">Фамилия:</label>
        <input type="text" id="surname" name="surname" maxlength="20" required pattern="[A-Za-zА-Яа-яЁё\s]+" title="В поле 'Фамилия' могут быть только буквы">

        <label for="phone">Мобильный телефон:</label>
        <input type="phone" id="phone" name="phone" maxlength="15" required minlength="10" pattern="^\+\d{1,3}\s?\d{1,14}$" title="Формат: +<код страны (1-3 цифры)> <номер (1-14 цифр)>" >

        <label for="password">Пароль:</label>
                <div style="display: flex; align-items: center;">
                    <input type="password" id="password" name="password" maxlength="20" required minlength="8" pattern="^[A-Za-z0-9]*$" title="Введите пароль на ENG" oninput="checkPasswordMatch()">
                    <button type="button" onclick="togglePasswordVisibility('password')">👁️</button>
                </div>

                <label for="confirm-password">Подтверждение пароля:</label>
                <div style="display: flex; align-items: center;">
                    <input type="password" id="confirm-password" name="confirm-password" maxlength="20" required minlength="8" pattern="^[A-Za-z0-9]*$" title="Введите пароль на ENG" oninput="checkPasswordMatch()">
                    <button type="button" onclick="togglePasswordVisibility('confirm-password')">👁️</button>
                </div>
        <input type="submit" value="Зарегистрироваться">
        <div class="errorMessage">
            <c:if test="${not empty phoneNumberError}">
                <div style="color: red;">${phoneNumberError}</div>
            </c:if>
            <c:if test="${not empty BlockUserError}">
                <div style="color: red;">${BlockUserError}</div>
            </c:if>
        </div>
    </form>
    <div class="message">
        <p>Уже есть аккаунт? <a href="entrance">Войти</a></p>
    </div>

<script>
    function checkPasswordMatch() {
        const password = document.getElementById("password").value;
        const confirmPassword = document.getElementById("confirm-password").value;

        // Условие для проверки совпадения паролей
        if (password !== confirmPassword) {
            document.getElementById("confirm-password").setCustomValidity("Пароли не совпадают");
        } else {
            document.getElementById("confirm-password").setCustomValidity(""); // Сброс ошибки
        }
    }

    function validateForm() {
        const password = document.getElementById("password").value;
        const passwordCriteria = /^(?=.*[A-Z])(?=.*\d).{8,}$/; // Минимум 8 символов, одна заглавная буква и одна цифра

        if (!passwordCriteria.test(password)) {
            alert("Пароль должен содержать хотя бы одну заглавную букву, одну цифру и минимум 8 символов.");
            return false; // Запретить отправку формы
        }

        return true; // Разрешить отправку формы
    }

    function togglePasswordVisibility(elementId) {
            const input = document.getElementById(elementId);
            if (input.type === "password") {
                input.type = "text"; // Показываем пароль
            } else {
                input.type = "password"; // Скрываем пароль
            }
        }
</script>
</body>
</html>