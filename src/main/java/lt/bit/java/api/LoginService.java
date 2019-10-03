package lt.bit.java.api;

import lt.bit.java.PersistenceUtil;
import lt.bit.java.entities.Account;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.annotation.security.PermitAll;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/login")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LoginService {

    @Context
    HttpServletRequest request;

    @PermitAll  // dabar nieko nedaro, tik del pvz
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response login(@FormParam("username") String username, @FormParam("password") String password) {
        EntityManager em = PersistenceUtil.getEntityManager();
        Account account = em.find(Account.class, username.toLowerCase());
        if (account == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        if (BCrypt.checkpw(password, account.getPassword())) {
            HttpSession session = request.getSession();
            User user = new User();
            user.setFirstName(account.getFirstName());
            user.setLastName(account.getLastName());
            user.setRole(account.getRole());
            session.setAttribute("usr", user);
            return Response.ok(user).build();

        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

//        if ("admin".equals(username) && "123".equals(password)) {
//            HttpSession session = request.getSession();
//            User user = new User();
//            user.setFirstName("Arminas");
//            user.setLastName("Tramplinas");
//            session.setAttribute("usr", user);
//            return Response.ok().build();
//        } else {
//            return Response.status(Response.Status.UNAUTHORIZED).build();
//        }
    }

    @GET
    public Response logout() {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
            return Response.ok("Logout success").build();
        } else {
            return Response.ok("No session").build();
        }
    }
}
