<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Управление лекарствами</title>
    <style>
        * {
            box-sizing: border-box; /* Устанавливаем box-sizing для всех элементов */
        }

        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background: rgba(240, 240, 240, 0.8);
        }

        .navbar {
            display: flex;                /* Используем Flexbox для горизонтального размещения */
        }

        .button {
            flex: 1;
            padding: 10px;
            text-align: center;
            background-color: #007bff;
            color: white;
            border: none;
            cursor: pointer;
            text-decoration: none;
            transition: background-color 0.3s;
            border-radius: 5px;
        }

        .button:hover {
            background-color: #0056b3;   /* Цвет кнопки при наведении */
        }

        .container {
            width: 50%;
            margin: 20px auto;
            background: white;
            padding: 30px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            border-radius: 10px;
        }

        h1 {
            text-align: center;
            color: #333;
        }

        label {
            display: block;
            margin-bottom: 5px; /* Уменьшил отступ между меткой и полем */
            font-weight: bold;
        }

        input[type="text"],
        input[type="number"],
        textarea,
        select {
            width: 100%; /* Обеспечивает одинаковую ширину */
            padding: 12px;
            margin: 10px 0;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        button {
            width: 100%; /* Убедитесь, что кнопка занимает 100% ширины контейнера */
            background-color: #007bff; /* Синяя кнопка */
            color: white;
            padding: 12px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        button:hover {
            background-color: #0056b3; /* Темнее на наведение */
        }

        a {
            display: block;
            margin-top: 20px;
            text-align: center;
            color: #007bff;
            text-decoration: none;
        }

        a:hover {
            text-decoration: underline;
        }

        .hidden {
            display: none;
        }

        .container .errorMessage {
            text-align: center;
            margin-top: 20px;
        }
    </style>
</head>
<body>
<div class="container">
        <h1 id="form-title">Добавить лекарство</h1>
        <div class="navbar">
            <a href="/admin/getMedicine" class="button">Изменить лекарство</a>
            <a href="/admin/deleteMedicine" class="button">Удалить лекарство</a>
        </div>

        <!-- Форма добавления лекарства -->
        <form id="medicine-form" action="/admin/addMedicine" method="post">
            <label for="name">Название:</label>
            <input type="text" id="name" name="name" required maxlength="40">

            <label for="description">Описание:</label>
            <textarea id="description" name="description" required maxlength="1000"></textarea>

            <label for="prescription">Нужен ли рецепт?</label>
            <select id="prescription" name="prescription" required>
                <option value="true">Да</option>
                <option value="false">Нет</option>
            </select>

            <label for="availability">Наличие:</label>
            <select id="availability" name="availability" required>
                <option value="true">В наличии</option>
                <option value="false">Нет в наличии</option>
            </select>

            <label for="price">Цена:</label>
            <input type="number" id="price" name="price" step="0.01" required>

            <button type="submit">Добавить лекарство</button>

            <div class="message">
                <div style="color: red;">${message}</div>
            </div>
        </form>
        <a href="/myUserPage" class="button">Назад на главную</a>
</div>
</body>
</html>