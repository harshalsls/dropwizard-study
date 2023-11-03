package org.example.db;

import io.dropwizard.hibernate.AbstractDAO;
import io.dropwizard.hibernate.UnitOfWork;
import org.example.core.User;
import org.hibernate.SessionFactory;

public class UserDAO extends AbstractDAO<User> {

    public UserDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public User create(User user) {
        return persist(user);
    }

    @UnitOfWork
    public User findByEmail(String email) {
        return uniqueResult(query("SELECT id, name, city, `role`, email, password FROM User where email='" + email + "'"));
    }

//    @SqlUpdate("INSERT INTO test.`user`\n" +
//            "(id, name, city, `role`, email, password)\n" +
//            "VALUES(0, :name, :city, 'Admin', :email, :password)")
//    void addUser(@Bind("id") int id, @Bind("name") String name);
}
