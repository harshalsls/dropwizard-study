package org.example.db;

import io.dropwizard.hibernate.AbstractDAO;
import org.example.core.Employee;
import org.example.core.User;
import org.hibernate.SessionFactory;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public class EmployeeDAO extends AbstractDAO<Employee> {

    public EmployeeDAO(SessionFactory factory) {
        super(factory);
    }


    public Employee create(Employee employee) {
        return persist(employee);
    }

}
