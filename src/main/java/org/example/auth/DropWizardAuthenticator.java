package org.example.auth;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.hibernate.UnitOfWork;
import lombok.RequiredArgsConstructor;
import org.example.core.AuthenticatedUser;
import org.example.core.User;
import io.dropwizard.auth.basic.BasicCredentials;
import org.example.db.UserDAO;
import org.example.db.jdbi.UserJDBIDAO;

import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
public class DropWizardAuthenticator implements Authenticator<BasicCredentials, AuthenticatedUser> {

    private final UserDAO userDAO;
    private final UserJDBIDAO userJDBIDAO;
    @Override
    @UnitOfWork
    public Optional<AuthenticatedUser> authenticate(BasicCredentials basicCredentials) throws AuthenticationException {

        User user = userJDBIDAO.findByEmail(basicCredentials.getUsername());
        if(Objects.nonNull(user)) {
            if (user.getPassword().equals(basicCredentials.getPassword())) {
                AuthenticatedUser authenticatedUser = new AuthenticatedUser(user.getEmail(), user.getRole());

                return Optional.of(authenticatedUser);
            }
        }
//        if ("Test@123".equals(basicCredentials.getPassword())) {
//                return Optional.of(new AuthenticatedUser(basicCredentials.getUsername()));
//            }

        return Optional.empty();
    }
}
