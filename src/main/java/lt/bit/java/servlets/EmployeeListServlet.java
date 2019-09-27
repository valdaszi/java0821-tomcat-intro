package lt.bit.java.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/employees")
public class EmployeeListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter writer = resp.getWriter();

        writer.print("<html><head><title>Employees</title></head><body>");

        // turinys

        writer.print("<h1>Employees</h1>");
        writer.print("<table>");
        writer.print("<tr><th>#</th><th>First-name</th><th>Last-name</th><th></th></tr>");

        // Jungiames is skaitome duomenis is DB
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/employees?serverTimezone=Europe/Vilnius",
                    "root",
                    "mysql123");

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM employees LIMIT 10");
            while (resultSet.next()) {
                writer.print("<tr>");
                writer.print("<td>" + resultSet.getInt("emp_no") + "</td>");
                writer.print("<td>" + resultSet.getString("first_name") + "</td>");
                writer.print("<td>" + resultSet.getString("last_name") + "</td>");

//                writer.print("<td><a href=\"/employee/delete?id=" + resultSet.getInt("emp_no") + "\">Delete</a></td>");

                writer.print("<td><form action=\"/employee/delete\">" +
                        "<input type=\"hidden\" name=\"id\" value=\"" +
                            resultSet.getInt("emp_no") + "\">" +
                        "<button>Delete</button>" +
                        "</form></td>");

                writer.print("</tr>");
            }

        } catch (ClassNotFoundException | SQLException e) {

        }
        writer.print("</table>");


        writer.print("<a href=\"/employee/create\">Create new employee</a>");

        writer.print("</body></html>");
    }
}
