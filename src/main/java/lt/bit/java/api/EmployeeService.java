package lt.bit.java.api;

import lt.bit.java.PersistenceUtil;
import lt.bit.java.entities.Employee;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;

@Path("/employee")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmployeeService {

    /**
     * Grazina darbuotoja pagal jo id
     * @param empNo darbuotojo id
     * @return darbuotojas
     */
    @GET
    @Path("/{empNo}")
    @Secure({"ADMIN","USER"})
    public Response getEmployeeById(@PathParam("empNo") int empNo) {
        EntityManager em = null;
        try {
            em = PersistenceUtil.getEntityManager();
            Employee employee = em.find(Employee.class, empNo);
            if (employee == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            } else {
                return Response.ok(employee).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Collections.singletonMap("error", e.getMessage()))
                    .build();
        } finally {
            if (em != null) em.close();
        }
    }

    @DELETE
    @Path("/{empNo}")
    @Secure("ADMIN")
    public Response deleteEmployeeById(@PathParam("empNo") int empNo) {
        EntityManager em = PersistenceUtil.getEntityManager();
        em.getTransaction().begin();
        Employee employee = em.find(Employee.class, empNo);
        if (employee != null) {
            em.remove(employee);
        }
        em.getTransaction().commit();
        return Response.ok().build();
    }

    @POST
    @Secure("ADMIN")
    public Response createEmployee(Employee employee) {
        EntityManager em = PersistenceUtil.getEntityManager();
        em.getTransaction().begin();
        em.persist(employee);
        em.getTransaction().commit();
        return Response.ok(employee).build();
    }

    @PUT
    @Secure("ADMIN")
    public Response updateEmployee(Employee employee) {
        EntityManager em = PersistenceUtil.getEntityManager();
        em.getTransaction().begin();

        Employee origEmpl = em.find(Employee.class, employee.getEmpNo());
        if (origEmpl == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        origEmpl.setFirstName(employee.getFirstName());
        origEmpl.setLastName(employee.getLastName());
        origEmpl.setGender(employee.getGender());
        origEmpl.setBirthDate(employee.getBirthDate());
        origEmpl.setHireDate(employee.getHireDate());

        em.merge(origEmpl);

        em.getTransaction().commit();
        return Response.ok(employee).build();
    }


}
