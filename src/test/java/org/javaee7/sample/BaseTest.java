package org.javaee7.sample;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Before;

/**
 * @author Arun Gupta
 */
public class BaseTest {

    protected WebTarget target;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return Maven.configureResolver()
                .workOffline()
//                .withRemoteRepo("my-repo", System.getProperty("nexus-repo"), "default")
                .resolve("org.javaee7.sample:javaee7-simple-sample:war:"
                        + System.getProperty("javaee7-sample-app-version"))
                .withoutTransitivity()
                .asSingle(WebArchive.class);
    }

    @ArquillianResource
    protected URL base;

    @Before
    public void setUp() throws MalformedURLException {
        Client client = ClientBuilder.newClient();
        target = client.target(URI.create(new URL(base, "resources/persons").toExternalForm()));
        target.register(Person.class);
    }
}
