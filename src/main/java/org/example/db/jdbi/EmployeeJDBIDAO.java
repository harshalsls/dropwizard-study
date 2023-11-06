package org.example.db.jdbi;

import org.example.core.Employee;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface EmployeeJDBIDAO {

//    @SqlUpdate("insert into something (id, name) values (:id, :name)")
    @SqlUpdate("INSERT INTO test.employee (id, name, city, mobileNumber) VALUES(0, :name, :city, :mobileNumber) ")
    void insertEmployee(@Bind("name") String name, @Bind("city") String city, @Bind("mobileNumber") String mobileNumber);




}
