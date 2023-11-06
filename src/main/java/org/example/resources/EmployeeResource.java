package org.example.resources;

import io.dropwizard.auth.Auth;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;
import io.dropwizard.hibernate.UnitOfWork;
import org.example.core.AuthenticatedUser;
import org.example.core.Employee;
import org.example.core.User;
import org.example.db.EmployeeDAO;
import org.example.db.UserDAO;
import org.example.db.jdbi.EmployeeJDBIDAO;

@Path("/employee")
@Produces(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class EmployeeResource {

    private final EmployeeDAO employeeDAO;
    private final UserDAO userDAO;
    private final EmployeeJDBIDAO employeeJDBIDAO;


    @RolesAllowed("ADMIN")
    @POST
    @UnitOfWork
    public void addEmployee(@Auth AuthenticatedUser user, Employee employee) {
//        User user1 = userDAO.findByEmail(user.getEmail());
        System.out.println(String.format("Hey there, %s. It looks like you are an admin. ", user.getEmail()));
//       return employeeDAO.create(employee);
        employeeJDBIDAO.insertEmployee(employee.getName(), employee.getCity(), employee.getMobileNumber());
    }
}
