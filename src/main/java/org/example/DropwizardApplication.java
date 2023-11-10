package org.example;

import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.jobs.Job;
import io.dropwizard.jobs.JobsBundle;
import io.dropwizard.migrations.MigrationsBundle;
import org.example.auth.DropWizardAuthenticator;
import org.example.auth.DropWizardAuthorizer;
import org.example.core.AuthenticatedUser;
import org.example.core.Employee;
import org.example.core.User;
import org.example.db.EmployeeDAO;
import org.example.db.UserDAO;
import org.example.db.jdbi.EmployeeJDBIDAO;
import org.example.db.jdbi.UserJDBIDAO;
import org.example.jobs.DailyJob;
import org.example.resources.EmployeeResource;
import org.example.resources.HelloWorldResource;
import org.example.resources.UserResource;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.jdbi.v3.core.Jdbi;

public class DropwizardApplication extends Application<DropwizardConfiguration> {

    public static void main(final String[] args) throws Exception {
        new DropwizardApplication().run(args);
    }

    @Override
    public String getName() {
        return "Dropwizard";
    }

//    private final HibernateBundle<DropwizardConfiguration> hibernateEmployee =
//            new HibernateBundle<DropwizardConfiguration>(Employee.class) {
//        @Override
//        public DataSourceFactory getDataSourceFactory(DropwizardConfiguration configuration) {
//            return configuration.getDataSourceFactory();
//        }
//    };

    private final HibernateBundle<DropwizardConfiguration> hibernate = new HibernateBundle<DropwizardConfiguration>(User.class, Employee.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(DropwizardConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };

    @Override
    public void initialize(final Bootstrap<DropwizardConfiguration> bootstrap) {
        bootstrap.addBundle(new MigrationsBundle<DropwizardConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(DropwizardConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        });

       // bootstrap.addBundle(hibernateEmployee);
        Job dailyJob = new DailyJob();
        bootstrap.addBundle(new JobsBundle(dailyJob));
        bootstrap.addBundle(hibernate);
    }

    @Override
    public void run(final DropwizardConfiguration configuration,
                    final Environment environment) {
        // TODO: implement application

        final JdbiFactory factory = new JdbiFactory();
        final Jdbi jdbi = factory.build(environment, configuration.getDataSourceFactory(), "mysql");
        final EmployeeJDBIDAO employeeJDBIDAO = jdbi.onDemand(EmployeeJDBIDAO.class);
        final UserJDBIDAO userJDBIDAO = jdbi.onDemand(UserJDBIDAO.class);


        final EmployeeDAO employeeDAO = new EmployeeDAO(hibernate.getSessionFactory());
        final UserDAO userDAO = new UserDAO(hibernate.getSessionFactory());

        environment.jersey().register(new HelloWorldResource());
        environment.jersey().register(new UserResource(userDAO));
        environment.jersey().register(new EmployeeResource(employeeDAO, userDAO, employeeJDBIDAO ));

        environment.jersey().register(new AuthDynamicFeature(
                new BasicCredentialAuthFilter.Builder<AuthenticatedUser>()
                        .setAuthenticator(new DropWizardAuthenticator(userDAO, userJDBIDAO))
                        .setAuthorizer(new DropWizardAuthorizer())
                        .setRealm("SUPER SECRET STUFF")
                        .buildAuthFilter()));
        environment.jersey().register(RolesAllowedDynamicFeature.class);
        //If you want to use @Auth to inject a custom Principal type into your resource
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(AuthenticatedUser.class));






    }

}
