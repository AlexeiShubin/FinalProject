<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Список Лекарств</title>
    <style>
        html, body {
            height: auto;
            overflow: auto;
            margin: 0;
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
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
        table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        th, td {
            padding: 15px;
            text-align: left;
            border-bottom: 1px solid #dddddd;
        }
        th {
            background-color: #4CAF50;
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
        }
        .pagination a.active {
            background-color: #4CAF50;
            color: white;
        }
        .pagination a:hover {
            background-color: #f1f1f1;
        }
        .back-button, .order-button, .add-button, .request-button {
            padding: 10px 20px; /* Убедитесь, что отступы одинаковы */
            font-size: 16px; /* Убедитесь, что размер шрифта одинаков */
            background-color: #ff5733;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.3);
            transition: background-color 0.3s;
            z-index: 1000; /* Чтобы кнопка была поверх других элементов */
            margin: 5px; /* Отступ между кнопками */
            display: inline-block; /* Убедитесь, что кнопки ведут себя как блочные элементы */
            min-width: 150px; /* Минимальная ширина для единообразия */
        }
        .back-button:hover,
        .order-button:hover,
        .add-button:hover,
        .request-button:hover {
            background-color: #c0392b;
        }
        .back-button {
            position: fixed;
            top: 20px;
            left: 20px;
        }
        .order-button {
            position: fixed;
            top: 70px; /* Отступ под кнопкой назад */
            left: 20px;
        }
    </style>
</head>
<body>
    <sec:authorize access="isAuthenticated() == true">
         <button class="back-button" onclick="location.href='/myUserPage'">Назад</button>
    </sec:authorize>
    <sec:authorize access="isAuthenticated() == false">
         <button class="back-button" onclick="location.href='/'">Назад</button>
    </sec:authorize>
    <sec:authorize access="hasRole('ROLE_USER')">
         <button class="order-button" onclick="location.href='/user/order'">К заказу</button>
    </sec:authorize>

    <header>
        <h1>Перечень лекарств</h1>
        <div class="error">
            <c:if test="${not empty error}">
                <div style="color: red;">${error}</div>
            </c:if>
            <c:if test="${not empty success}">
                <div style="color: green;">${success}</div>
            </c:if>
        </div>
    </header>
    <main>
        <table>
            <thead>
                <tr>
                    <th>Название</th>
                    <th>Описание</th>
                    <th>Необходимость рецепта</th>
                    <th>Наличие</th>
                    <th>Цена</th>
                    <sec:authorize access="hasRole('USER')">
                    <th>Добавить лекарство</th>
                    </sec:authorize>
                </tr>
            </thead>
            <tbody>
                <c:if test="${not empty medicines}">
                    <c:forEach var="medicine" items="${medicines}">
                        <tr>
                            <td>${medicine.name}</td>
                            <td>${medicine.description}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${medicine.doctorPrescription}">
                                        С рецептом
                                    </c:when>
                                    <c:otherwise>
                                        Без рецепта
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${medicine.availability}">
                                        В наличии
                                    </c:when>
                                    <c:otherwise>
                                        Нет в наличии
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>${medicine.price} BYN</td>
                            <sec:authorize access="hasRole('USER')">
                                <td>
                                    <c:choose>
                                        <c:when test="${medicine.availability}">
                                            <form action="/user/medicines" method="post" style="display:inline;">
                                                <input type="hidden" name="medicineId" value="${medicine.id}"/>
                                                <button type="submit" class="add-button">Добавить</button>
                                            </form>
                                            <c:if test="${medicine.doctorPrescription}">
                                                <form action="/user/requestRecipe" method="post" style="display:inline;">
                                                    <input type="hidden" name="medicineId" value="${medicine.id}"/>
                                                    <button type="submit" class="request-button">Запросить рецепт</button>
                                                </form>
                                            </c:if>
                                        </c:when>
                                        <c:otherwise>
                                            &nbsp;
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </sec:authorize>
                        </tr>
                    </c:forEach>
                </c:if>
                <c:if test="${empty medicines}">
                    <tr>
                        <td colspan="6">Нет доступных лекарств.</td>
                    </tr>
                </c:if>
            </tbody>
        </table>

        <div class="pagination">
            <c:if test="${currentPage > 0}">
                <a href="?page=${currentPage - 1}&size=${pageSize}">Назад</a>
            </c:if>
            <c:forEach var="pg" begin="0" end="${totalPages - 1}">
                <c:choose>
                    <c:when test="${pg == currentPage}">
                        <a href="?page=${pg}&size=${pageSize}" class="active">${pg + 1}</a>
                    </c:when>
                    <c:otherwise>
                        <a href="?page=${pg}&size=${pageSize}">${pg + 1}</a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            <c:if test="${currentPage < totalPages - 1}">
                <a href="?page=${currentPage + 1}&size=${pageSize}">Вперед</a>
            </c:if>
        </div>
    </main>
</body>
</html>