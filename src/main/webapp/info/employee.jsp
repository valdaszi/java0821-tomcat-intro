<%@ page import="java.sql.*" %>
<%--
  Created by IntelliJ IDEA.
  User: valdas
  Date: 2019-09-19
  Time: 11:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Employees</title>
</head>
<body>
    <%
        try {
            int id = Integer.parseInt(request.getParameter("id"));

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/employees?serverTimezone=Europe/Vilnius",
                    "root",
                    "mysql123");

            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM employees WHERE emp_no = ?");

            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
        %>
            <h3>Darbuotojo su tokiu id nera"</h3>

        <% } else { %>
                <h2> <%= resultSet.getString("first_name") %> </h2>
                <h2> <%= resultSet.getString("last_name") %> </h2>
        <%
            }
        } catch (SQLException | NumberFormatException e) {
        %>
                <h1>Klaida!!!</h1>
        <%
        }
        %>

</body>
</html>
