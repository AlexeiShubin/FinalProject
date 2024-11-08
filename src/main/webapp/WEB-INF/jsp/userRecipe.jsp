<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Список рецептов</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: linear-gradient(to right, #ff6f61, #de4d92); /* Градиентный фон */
            color: #ffffff;
        }

        .container {
            width: 80%;
            margin: 50px auto;
            background-color: rgba(0, 0, 0, 0.7); /* Прозрачный черный фон */
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.5);
        }

        h1 {
            text-align: center;
            margin-bottom: 20px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 10px;
            text-align: center;
        }

        th {
            background-color: #4CAF50;
            color: white;
        }

        tr:nth-child(even) {
            background-color: blue;
        }

        tr:hover {
            background-color: #ddd;
        }

        .no-recipes {
            text-align: center;
            padding: 20px;
            font-size: 1.2em;
            color: #ffcc00; /* Цвет для сообщения "Рецепты не найдены" */
        }

        .button {
            background-color: #4CAF50; /* Зеленый фон кнопки */
            border: none;
            color: white;
            padding: 10px 20px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            margin: 5px 2px;
            cursor: pointer;
            border-radius: 5px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Список ваших рецептов</h1>
        <div style="text-align:center;">
            <a href="/myUserPage" class="button">На главную</a><br/>
            <a href="/user/order" class="button">К заказу</a><br/>
        </div>
        <table>
            <thead>
                <tr>
                    <th>Лекарство</th>
                    <th>Дата выдачи</th>
                    <th>Дата окончания</th>
                    <th>Статус</th>
                    <th>Действия</th>
                </tr>
            </thead>
            <tbody>
                <c:if test="${not empty recipes}">
                    <c:forEach var="recipe" items="${recipes}">
                        <tr>
                            <td>${recipe.medicine.name}</td>
                            <td><fmt:formatDate value="${recipe.issueDate}" pattern="yyyy-MM-dd" /></td>
                            <td><fmt:formatDate value="${recipe.expirationDate}" pattern="yyyy-MM-dd" /></td>
                            <td>${recipe.status}</td>
                            <td>
                                <form action="/user/myRecipes" method="post" style="display:inline;">
                                   <input type="hidden" name="recipeId" value="${recipe.id}">
                                   <button type="submit" style="background-color: red; color: white; border: none; padding: 5px 10px; cursor: pointer;">Продлить</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </c:if>
                <c:if test="${empty recipes}">
                    <tr>
                        <td colspan="5" class="no-recipes">Рецепты не найдены.</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </div>
</body>
</html>