package org.javaee7.sample;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Arun Gupta
 */
@RunWith(Arquillian.class)
public class PersonResourceSmokeTest {

    private WebTarget target;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {

        return ShrinkWrap.create(WebArchive.class).merge(
                Maven.configureResolver()
                .workOffline()
                .resolve("org.javaee7.sample:javaee7-simple-sample:war:" + 
                        System.getProperty("javaee7-sample-app-version"))
                .withoutTransitivity()
                .asSingle(WebArchive.class));

    }

    @ArquillianResource
    private URL base;

    @Before
    public void setUp() throws MalformedURLException {
        Client client = ClientBuilder.newClient();
        target = client.target(URI.create(new URL(base, "resources/persons").toExternalForm()));
        target.register(Person.class);
    }

    /**
     * Test of getList method, of class MyResource.
     */
    @Test
    public void test1GetAll() {
        Person[] list = target.request().get(Person[].class);
        assertEquals(8, list.length);

        assertEquals("Penny", list[0].getName());
        assertEquals("Leonard", list[1].getName());
        assertEquals("Sheldon", list[2].getName());
        assertEquals("Amy", list[3].getName());
        assertEquals("Howard", list[4].getName());
        assertEquals("Bernadette", list[5].getName());
        assertEquals("Raj", list[6].getName());
        assertEquals("Priya", list[7].getName());
    }
}
