<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Заявки на продление рецепта</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #e0f7fa; /* Цвет фона для всей страницы */
            margin: 0;
            padding: 20px;
        }
        header {
            text-align: center; /* Центрируем заголовок */
            margin-bottom: 20px;
        }
        h1 {
            color: #00796b; /* Цвет текста заголовка */
            background-color: #b2dfdb; /* Фоновый цвет для заголовка */
            padding: 15px;
            border-radius: 5px; /* Скругление углов заголовка */
            display: inline-block; /* Чтобы установить размер, соответствующий содержимому */
        }
        .back-button {
            background-color: #ff5733;
            color: white;
            border: none;
            border-radius: 5px;
            padding: 10px 20px;
            cursor: pointer;
            transition: background-color 0.3s;
            position: absolute; /* Перемещение кнопки; */
            top: 20px; /* Отступ сверху */
            left: 20px; /* Отступ слева */
            z-index: 1000; /* Кнопка будет сверху других элементов */
        }
        .back-button:hover {
            background-color: #c0392b;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            background-color: #fff; /* Белый цвет фона таблицы */
        }
        th, td {
            padding: 15px;
            text-align: left;
            border-bottom: 1px solid #dddddd;
        }
        th {
            background-color: #4CAF50; /* Цвет заголовков таблицы */
            color: white;
        }
        tr:hover {
            background-color: #f1f1f1;
        }
        .pagination {
            display: flex;
            justify-content: center;
            margin: 20px 0;
        }
        .pagination a {
            margin: 0 5px;
            text-decoration: none;
            padding: 8px 12px;
            border: 1px solid #4CAF50;
            color: #4CAF50;
            border-radius: 5px;
            transition: background-color 0.3s;
        }
        .pagination a.active {
            background-color: #4CAF50;
            color: white;
        }
        .pagination a:hover {
            background-color: #f1f1f1;
        }
        .extend-button {
            background-color: #00796b; /* Цвет кнопки "Продлить" */
            color: white;
            border: none;
            border-radius: 5px;
            padding: 5px 10px;
            cursor: pointer;
            transition: background-color 0.3s;
        }
        .extend-button:hover {
            background-color: #004d40; /* Цвет кнопки при наведении */
        }
        .no-requests {
            text-align: center;
            font-size: 24px;
            color: blue;
        }
    </style>
</head>
<body>

<header>
    <h1>Заявки на продление рецепта</h1>
</header>

<a href="/myUserPage">
    <button class="back-button">Назад</button>
</a>

<c:if test="${not empty requests}">
    <table>
        <thead>
            <tr>
                <th>Пользователь</th>
                <th>Лекарство</th>
                <th>Дата окончания рецепта</th>
                <th>Дата создания заявки</th>
                <th>Действие</th> <!-- Новый заголовок для кнопки "Продлить" -->
            </tr>
        </thead>
        <tbody>
            <c:forEach var="request" items="${requests}">
                <tr>
                    <td>${request.user.name} ${request.user.surname} ${request.user.phone}</td>
                    <td>${request.medicine.name}</td>
                    <td><fmt:formatDate value="${recipe.issueDate}" pattern="yyyy-MM-dd" /></td>
                    <td><fmt:formatDate value="${recipe.expirationDate}" pattern="yyyy-MM-dd" /></td>
                    <td>
                        <form action="/doctor/bid" method="POST">
                            <input type="hidden" name="requestId" value="${request.id}">
                            <button type="submit" class="extend-button">Продлить</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</c:if>

<c:if test="${empty requests}">
    <p class="no-requests">Запросов нет.</p>
</c:if>

<div class="pagination">
    <c:if test="${currentPage > 0}">
        <a href="?page=${currentPage - 1}&size=30">&laquo; Назад</a>
    </c:if>

    <c:if test="${totalPages > 0}">
        <c:forEach var="page" begin="0" end="${totalPages - 1}">
            <a class="${page == currentPage ? 'active' : ''}" href="?page=${page}&size=30">${page + 1}</a>
        </c:forEach>
    </c:if>

    <c:if test="${currentPage < totalPages - 1}">
        <a href="?page=${currentPage + 1}&size=30">Вперед &raquo;</a>
    </c:if>
</div>
</body>
</html>