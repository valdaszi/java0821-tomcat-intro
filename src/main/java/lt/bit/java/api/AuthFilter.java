package lt.bit.java.api;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@Provider
public class AuthFilter implements ContainerRequestFilter {

    @Context
    HttpServletRequest request;

    @Context
    ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        Method method = resourceInfo.getResourceMethod();
        if (method.isAnnotationPresent(Secure.class)) {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("usr") == null) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
                return;
            }
            User user = (User) session.getAttribute("usr");
            Secure secure = method.getAnnotation(Secure.class);

            // Jei useris neturi roles (arba turi "tuscia" role)
            // ir Secure anotacija turi kazkokias roles
            // tai UNAUTHORIZED
            if ((user.getRole() == null || user.getRole().isEmpty()) &&
                    secure.value().length > 0) {
                requestContext.abortWith(Response
                        .status(Response.Status.UNAUTHORIZED)
                        .build());
                return;
            }

            // Jei useris turi role
            // ir Secure turi roles
            // ir tose nurodytose rolese nera userio roles
            // tai UNAUTHORIZED
            if (user.getRole() != null && !user.getRole().isEmpty() &&
                    secure.value().length > 0 &&
                    !Arrays.asList(secure.value()).contains(user.getRole())) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            }
        }
    }
}
