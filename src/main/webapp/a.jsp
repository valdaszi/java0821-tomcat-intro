<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Arrays" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>JSP</title>
</head>
<body>
    <h1>JSP</h1>

    <%
        List<String> names = Arrays.asList("Jonas", "Petras", "Ona", "Zose");
        for (String name : names) {
    %>
            <div><%=name %></div >
    <%
        }
    %>

    <br>
    <br>
    <br>

    <footer>Current time is <%= LocalDateTime.now() %></footer>
</body>
</html>