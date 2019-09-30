package lt.bit.java.api;


import lt.bit.java.PersistenceUtil;
import lt.bit.java.entities.Employee;

import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/employee")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmployeeService {

    @GET
    @Path("/{empNo}")
    public Employee getEmployeeById(@PathParam("empNo") int empNo) {
        EntityManager em = PersistenceUtil.getEntityManager();
        Employee employee = em.find(Employee.class, empNo);
        return employee;
    }


}
