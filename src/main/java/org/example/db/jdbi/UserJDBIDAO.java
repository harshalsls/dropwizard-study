package org.example.db.jdbi;

import org.example.core.User;
import org.example.mapper.UserMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.UseRowMapper;

public interface UserJDBIDAO {


    @SqlQuery("SELECT id, name, city, `role`, email, password FROM test.`user` where email = :email")
    @UseRowMapper(UserMapper.class)
    User findByEmail(@Bind("email") String email);

}
