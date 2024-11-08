<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Контактная информация</title>
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
    content{
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
    }
    </style>
</head>
<body>
    <header>
        <h1>Контактная информация</h1>
    </header>

    <div class="content">
        <h2>Наши контактные данные</h2>
        <p><strong>Адрес:</strong> ул. Примерная, д. 1, г. Минск, Беларусь</p>
        <p><strong>Телефон:</strong> +375 (29) 123-45-67</p>
        <p><strong>Электронная почта:</strong> support@onlinepharmacy.com</p>

        <h2>Часы доставки</h2>
        <ul>
            <li>Понедельник - Пятница: 8:00 - 22:00</li>
            <li>Суббота и воскресение: 10:00 - 19:00</li>
        </ul>
    </div>
</body>
</html>