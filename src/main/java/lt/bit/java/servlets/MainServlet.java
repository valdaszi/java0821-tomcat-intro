package lt.bit.java.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

@WebServlet("/")
public class MainServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");
        writer.println("<html>");
        writer.println("<body>");
        writer.println("<h1>Yes</h1>");
        writer.println("</body>");
        writer.println("</html>");


    }
}
