package lt.bit.java.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter("/employees")
public class SampleFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        response.getWriter().println("<h1>Filter in action :)</h1>");
        //chain.doFilter(request, response);
        response.getWriter().println("<h5>After all :)</h5>");
    }
}
