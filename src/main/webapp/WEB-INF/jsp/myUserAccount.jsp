<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Мой аккаунт - Онлайн Аптека</title>
    <style>
        html, body{
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            height: 100%;
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
            animation: colorChange 10s infinite alternate;
            color: white;
            font-size: 1.2em;
            padding: 15px 20px;
            text-align: center;
            text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.7);
        }

        nav {
            display: flex;
            justify-content: center;
            margin: 20px 0;
        }

        nav a {
            margin: 0 10px;
            padding: 10px 15px;
            text-decoration: none;
            color: #4CAF50;
            font-size: 1.5em;
            font-weight: bold;
            border-radius: 5px;
            transition: background 0.3s, color 0.3s;
            border: 1px solid transparent;
        }

        nav a:hover {
            background: rgba(255, 255, 255, 0.2);
            color: #FFD700;
            border: 1px solid #FFD700;
        }

        .content {
            flex: 1;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            padding: 20px;
            background: rgba(50, 50, 50, 0.8);
            border-radius: 12px;
            box-shadow: 0 0 20px rgba(255, 255, 255, 0.2);
            margin: 0 10px;
        }

        .welcome {
            font-size: 1.4em;
            margin-bottom: 20px;
            color: #fff;
        }

        .actions {
            display: flex;
            flex-direction: column;
            align-items: center;
            margin: 20px 0;
        }

        .action-button {
            padding: 15px 70px;
            margin: 10px;
            background: #4CAF50;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background 0.3s;
            font-size: 1.2em;
        }

        .action-button:hover {
            background: #45a049;
        }

        footer {
            text-align: center;
            padding: 15px;
            background: rgba(76, 175, 80, 0.9);
            color: white;
            width: 100%;
            text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.7);
        }

        @media (max-width: 600px) {
            header {
                font-size: 1.2em; /* Уменьшаем шрифт заголовка */
            }
            .welcome {
                font-size: 1.5em; /* Уменьшаем шрифт для приветствия */
            }
            nav a {
                font-size: 0.9em; /* Уменьшаем размер текста для кнопок навигации */
            }
            .action-button {
                width: 90%; /* Кнопки занимают 90% ширины на мобильных */
                margin: 10px 0; /* Увеличиваем отступы между кнопками */
            }
        }
    </style>
</head>
<body>
    <header>
    <sec:authorize access="hasRole('USER')">
        <h1>Добро пожаловать в ваш аккаунт!</h1>
    </sec:authorize>
    <sec:authorize access="hasRole('ADMIN')">
        <h1>Добро пожаловать, администратор!</h1>
    </sec:authorize>
    <sec:authorize access="hasRole('DOCTOR')">
        <h1>Добро пожаловать, доктор!</h1>
    </sec:authorize>
    </header>

    <nav>
        <sec:authorize access="hasRole('USER')">
            <a href="user/medicines">Лекарства</a>
        </sec:authorize>
        <sec:authorize access="hasAnyRole('ADMIN', 'DOCTOR')">
            <a href="/medicines">Лекарства</a>
        </sec:authorize>
        <a href="contactInformation">Контактная информация</a>
        <a href="/logout">Выход</a>
    </nav>

    <div class="content">
    <sec:authorize access="hasRole('USER')">
        <div class="welcome">Вы можете управлять своим аккаунтом, просматривать заказы и получить рецепт от врача.</div>
    </sec:authorize>
        <div class="actions">
        <sec:authorize access="hasRole('USER')">
            <button class="action-button" onclick="window.location='user/profile';">Мой профиль</button>
            <button class="action-button" onclick="window.location='user/order';">Оформление заказа</button>
            <button class="action-button" onclick="window.location='user/myRecipes';">Мои рецепты</button>
        </sec:authorize>
        <sec:authorize access="hasRole('ADMIN')">
             <button class="action-button" onclick="window.location='admin/users';">Пользователи</button>
             <button class="action-button" onclick="window.location='admin/getAllOrders';">Заказы</button>
             <button class="action-button" onclick="window.location='admin/addMedicine';">взаимодействие с лекарствами</button>
        </sec:authorize>
        <sec:authorize access="hasRole('DOCTOR')">
             <button class="action-button" onclick="window.location='doctor/users';">Пользователи</button>
             <button class="action-button" onclick="window.location='doctor/bid';">Заявки</button>
        </sec:authorize>
        </div>
    </div>

    <footer>
        <p>&copy; 2024 Онлайн Аптека. Все права защищены.</p>
    </footer>

</body>
</html>