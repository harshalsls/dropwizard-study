package org.example.auth;

import io.dropwizard.auth.AuthorizationContext;
import io.dropwizard.auth.Authorizer;
import jakarta.ws.rs.container.ContainerRequestContext;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.example.core.AuthenticatedUser;
import org.example.core.User;
public class DropWizardAuthorizer implements Authorizer<AuthenticatedUser> {

    @Override
    public boolean authorize(AuthenticatedUser user, String s, @Nullable ContainerRequestContext containerRequestContext) {
        return s.equalsIgnoreCase("ADMIN");
    }

    @Override
    public AuthorizationContext<AuthenticatedUser> getAuthorizationContext(AuthenticatedUser principal, String role, @Nullable ContainerRequestContext requestContext) {
        return Authorizer.super.getAuthorizationContext(principal, role, requestContext);
    }
}
