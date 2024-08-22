<%@ page contentType="text/html;charset=utf-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Онлайн Аптека</title>
    <style>
        * {
            box-sizing: border-box; /* Учитываем контент, отступы и границы в размерах блоков */
        }
        html, body {
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
        nav {
            display: flex;
            justify-content: center;
            margin: 20px 0; /* Добавленный верхний отступ для перемещения вниз */
        }

        nav a {
            margin: 0 10px; /* Уменьшен внешний отступ между ссылками */
            padding: 10px 15px; /* Добавим немного внутреннего отступа для кнопок */
            text-decoration: none;
            color: #4CAF50; /* Цвет текста */
            font-size: 1.5em; /* Увеличиваем размер текста для лучшего восприятия */
            font-weight: bold; /* Делаем текст жирным */
            transition: background 0.3s, color 0.3s; /* Плавный переход фона и цвета при наведении */
            border-radius: 5px; /* Закругление углов */
            border: 1px solid transparent;
        }

        nav a:hover {
            background: rgba(255, 255, 255, 0.2); /* Эффект наведения */
            color: #FFD700; /* Изменение цвета текста при наведении */
            border: 1px solid #FFD700; /* Цвет рамки при наведении */
        }

        .links {
            flex: 1;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            padding: 10px;
            background: rgba(50, 50, 50, 0.8);
            border-radius: 12px;
            box-shadow: 0 0 20px rgba(255, 255, 255, 0.2); /* Увеличенная тень для глубины */
            margin: 0 10px; /* Убираем боковые внешние отступы, оставляя отступы сверху и снизу */
        }
        .text {
            text-align: center;
            padding: 20px; /* Увеличен внутренний отступ для высоты блока */
            color: #fff; /* Более контрастный цвет текста */
            min-height: 200px; /* Минимальная высота для блока текста */
        }
        .photos {
            display: flex;
            justify-content: center;
            flex-wrap: wrap;
            margin: 10px 0; /* Отступ сверху и снизу для блока фотографий */
            gap: 0px; /* Промежуток между фотографиями */
        }
        .photo {
            width: 350px; /* Фиксированная ширина для фотографий */
            height: 200px; /* Фиксированная высота для фотографий */
            background-color: #f0f0f0; /* Цвет фона для фотографии (можно заменить на изображение) */
            border-radius: 5px; /* Закругление углов для фотографий */
            display: flex;
            justify-content: center;
            align-items: center;
            font-size: 1em; /* Размер текста внутри блока фото */
            color: #333;
        }
        footer {
            text-align: center;
            padding: 15px;
            background: rgba(76, 175, 80, 0.9); /* Полупрозрачный фон для подвала */
            color: white;
            width: 100%;
            text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.7);
        }

        @media (max-width: 600px) {
            nav a {
                margin: 0 5px; /* Уменьшаем отступы для мобильных */
                font-size: 1em; /* Уменьшаем размер текста для удобства */
            }
            header h1 {
                font-size: 1.5em; /* Уменьшаем размер заголовка на мобильных */
            }
            .text h2 {
                font-size: 1.5em; /* Уменьшаем размер заголовка текста */
            }
            .photo {
                width: 100px; /* Уменьшаем размер фото на мобильных */
                height: 100px;
            }
        }
    </style>
</head>
<body>
<header>
    <h1>Добро пожаловать в Онлайн Аптеку</h1>
</header>

<nav>
    <a href="medicines">Лекарства</a>
    <a href="contactInformation">Контактная информация</a>
    <a href="entrance">Вход</a>
    <a href="registration">Регистрация</a>
</nav>

<div class="links">
    <div class="text">
        <h2>Мы рады видеть вас!</h2>
        <p>На нашей платформе вы сможете найти широкий выбор медицинских препаратов и товаров для здоровья.</p>
        <p>Пожалуйста, зарегистрируйтесь или войдите в свой аккаунт, чтобы заказать у нас товары и получить доступ к полному функционалу сайта.</p>
    </div>
    <div class="photos">
        <div class="photo">
             <img src="/images/startPage.jpg" style="width: 100%; height: auto; border-radius: 10px;" />
        </div>
        <div class="photo">Фото 2</div>
        <div class="photo">Фото 3</div>
        <div class="photo">Фото 4</div>
    </div>
</div>

<footer>
    <p>&copy; 2024 Онлайн Аптека. Все права защищены.</p>
</footer>
</body>
</html>