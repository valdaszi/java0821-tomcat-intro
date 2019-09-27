package lt.bit.java.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/employee/delete")
public class EmployeeDeleteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            resp.getWriter().print("Naikiname irasa su id = " + id);


            //TODO redirectinti i /employees
            //resp.sendRedirect("/employees");

        } catch (NumberFormatException e) {
            //TODO
        }

    }
}
