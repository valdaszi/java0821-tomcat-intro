package lt.bit.java.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet("/employee")
public class EmployeeServlet extends HttpServlet {

    // http://.../employee?id=10001
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));

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
                resp.getWriter().println("Darbuotojo su tokiu id nera");
            } else {
                resp.getWriter().println(
                        resultSet.getString("first_name") + " " +
                                resultSet.getString("last_name"));
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (NumberFormatException e) {
            resp.getWriter().println("Nenurodytas arba neteisingai nurodytas id");
        } catch (SQLException e) {
            resp.getWriter().println("Klaida dirbant su DB: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            resp.getWriter().println("Nera MySQL draiverio");
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println("DELETE!");
    }
}
