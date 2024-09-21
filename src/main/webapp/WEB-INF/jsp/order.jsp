<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Оформление заказа</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            overflow: hidden;
            position: relative;
            background-color: #0d47a1; /* Темно-синий фон */
        }

        .gradient-background {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: linear-gradient(270deg, #0d47a1, #1976d2, #42a5f5, #81d4fa); /* Синий градиент */
            background-size: 400% 400%;
            animation: gradientAnimation 10s ease infinite;
        }

        @keyframes gradientAnimation {
            0% { background-position: 0% 50%; }
            50% { background-position: 100% 50%; }
            100% { background-position: 0% 50%; }
        }

        .shine {
            position: absolute;
            top: -30%;
            left: -30%;
            width: 200%;
            height: 200%;
            background: radial-gradient(circle, rgba(255, 255, 255, 0.3) 30%, rgba(255, 255, 255, 0) 70%);
            animation: shineAnimation 3s infinite;
            pointer-events: none;
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
            width: 100%;
            position: relative;
            z-index: 1;
            animation: fadeIn 1s ease forwards, slideIn 1s ease forwards;
        }

        @keyframes fadeIn {
            from { opacity: 0; }
            to { opacity: 1; }
        }

        @keyframes slideIn {
            from { transform: translateY(-20px); }
            to { transform: translateY(0); }
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
            width: 100%;
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
            width: 95%;
            box-sizing: border-box;
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
            width: 100%;
            margin-top: 20px;
        }

        button:hover {
            background-color: #ffc107;
            transform: scale(1.05);
        }

        .address-section {
            margin-top: 20px;
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
    <button class="back-button" onclick="location.href='/myUserPage'">На главную</button>
    <button class="order-button" onclick="location.href='/user/medicines'">Лекарства</button>
    <button class="back-button" onclick="location.href='/user/myRecipes'">К рецептам</button>
    <div class="gradient-background"></div>
    <div class="shine"></div>
    <div class="container">
        <h1>Оформление заказа</h1>

            <div style="max-height: 300px; overflow-y: auto; overflow-x: hidden;">
                <table style="width: 100%;">
                    <thead>
                        <tr>
                            <th>Название лекарства</th>
                            <th>Цена (руб)</th>
                            <th>Количество</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody id="medicinesTable">
                        <c:choose>
                            <c:when test="${empty orderItems}">
                                <tr>
                                    <td colspan="4">Нет доступных лекарств для заказа.</td>
                                </tr>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="orderItem" items="${orderItems}">
                                    <tr>
                                        <td>${orderItem.medicine.name}</td>
                                        <td class="price">${orderItem.medicine.price}</td>
                                        <td>
                                            <form action="quantity" method="post">
                                            <input type="number" name="quantity${orderItem.medicine.id}" value="${orderItem.quantity}" min="1" max="999" class="quantity" onchange="updateQuantity(${orderItem.medicine.id}, this.value)">
                                            </form>
                                        </td>
                                        <td>
                                            <form action="removeMedicine" method="post" style="display:inline;">
                                                <input type="hidden" name="medicineId" value="${orderItem.medicine.id}">
                                                <button type="submit" style="background-color: red; color: white; border: none; padding: 5px 10px; cursor: pointer;">Удалить</button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </div>
        <form action="/user/order" method="post" id="orderForm">
            <div class="address-section">
                <h2>Адрес доставки</h2>
                <input type="text" name="address" placeholder="Введите ваш адрес" required maxlength="255">
            </div>
            <div class="total-price">
                <h2>Полная цена: <span id="totalPrice">0</span> руб</h2>
                <input type="hidden" name="totalPrice" id="totalPriceInput" value="0">
                <c:if test="${not empty message}">
                     <div style="color: red;">${message}</div>
                </c:if>
            </div>
            <button type="submit">Оформить заказ</button>
        </form>
    </div>

    <script>
        const totalPriceElement = document.getElementById('totalPrice');

        function updateTotal() {
               let total = 0;
               const quantityInputs = document.querySelectorAll('input[type="number"].quantity');
               quantityInputs.forEach(input => {
                   const quantity = parseInt(input.value) || 0;
                   const price = parseFloat(input.closest('tr').querySelector('.price').textContent) || 0;
                   total += price * quantity;
               });
               totalPriceElement.textContent = total.toFixed(2);

               document.getElementById('totalPriceInput').value = total.toFixed(2);
           }

        updateTotal();

        function updateQuantity(medicineId, quantity) {
            fetch('/user/quantity', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: new URLSearchParams({
                    'medicineId': medicineId,
                    'quantity': quantity
                })
            })
            .then(response => response.text())
            .then(data => {
                console.log(data);
                updateTotal();
            })
            .catch((error) => {
                console.error('Error:', error);
            });
        }
    </script>
</body>
</html>