<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Список пользователей</title>
    <style>
        body {
            margin: 0;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(to bottom right, #2193b0, #6dd5ed);
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            animation: colorChange 10s infinite alternate; /* Анимация изменения цвета */
        }

        .content {
            background-color: white;
            border-radius: 10px;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.2);
            width: 90%;
            max-width: 900px;
            padding: 30px;
            text-align: center;
        }
        @keyframes colorChange {
                     0% { background: rgba(0, 102, 204, 0.9); }
                     25% { background: rgba(153, 0, 153, 0.9); }
                     50% { background: rgba(190, 58, 255, 0.9); }
                     75% { background: rgba(126, 38, 255, 0.9); }
                     100% { background: rgba(43, 194, 255, 0.9); }
                }

        header {
            background-color: #2193b0;
            color: white;
            padding: 15px;
            border-radius: 10px 10px 0 0;
            font-size: 1.5em;
            text-shadow: 1px 1px 3px rgba(0, 0, 0, 0.6);
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th, td {
            padding: 15px;
            border: 1px solid #ddd;
            text-align: left;
        }

        th {
            background-color: #f4f4f4;
            color: #333;
        }

        tr:nth-child(even) {
            background-color: #f9f9f9;
        }

        tr:hover {
            background-color: #e0f7fa; /* Цвет при наведении на строку */
        }
        .back-button {
                    position: fixed;
                    top: 20px;
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
                    z-index: 1000; /* Чтобы кнопка была поверх других элементов */
                }

                .back-button:hover {
                    background-color: #c0392b;
                }

        @media (max-width: 600px) {
            th, td {
                padding: 8px;
                font-size: 14px;
            }
        }
    </style>
</head>
<body>
    <a href="/myUserPage" class="back-button">Назад</a>
    <div class="content">
        <header>
            <h2>Список пользователей</h2>
        </header>
        <div class="search-container">
            <input type="number" id="userIdInput" placeholder="Введите ID пользователя">
            <button onclick="searchUserById()">Поиск</button>
        </div>
        <table id="userTable">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Имя</th>
                    <th>Фамилия</th>
                    <th>Номер телефона</th>
                    <th>Действия</th> <!-- Добавляем заголовок для действий -->
                </tr>
            </thead>
            <tbody>
                <c:forEach var="user" items="${userList}">
                    <tr>
                        <td><c:out value="${user.id}"/></td>
                        <td><c:out value="${user.name}"/></td>
                        <td><c:out value="${user.surname}"/></td>
                        <td><c:out value="${user.phone}"/></td>
                        <td>
                            <form action="${pageContext.request.contextPath}/admin/blockUser/${user.id}" method="post">
                                <button type="submit" class="block-button">Заблокировать</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
<script>
function searchUserById() {
        const userId = document.getElementById("userIdInput").value;
        const rows = document.querySelectorAll("#userTable tbody tr");
        let found = false;

        rows.forEach(function(row) {
            const idCell = row.querySelector("td:nth-child(1)");
            if (idCell) {
                const idValue = idCell.textContent || idCell.innerText;
                if (idValue === userId) {
                    row.style.display = "";
                    found = true;
                } else {
                    row.style.display = "none";
                }
            }
        });
        window.history.pushState({ searchId: userId }, "Поиск пользователя", "?searchId=" + userId);
    }

    window.onpopstate = function(event) {
        if (event.state) {
            const userId = event.state.searchId;
            const rows = document.querySelectorAll("#userTable tbody tr");
            rows.forEach(function(row) {
                const idCell = row.querySelector("td:nth-child(1)");
                if (idCell) {
                    const idValue = idCell.textContent || idCell.innerText;
                    row.style.display = (idValue === userId) ? "" : "none";
                }
            });
            document.getElementById("userIdInput").value = userId;
        } else {
            const rows = document.querySelectorAll("#userTable tbody tr");
            rows.forEach(function(row) {
                row.style.display = "";
            });
            document.getElementById("userIdInput").value = "";
        }
    }

    </script>
</html>