package org.javaee7.sample;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Arun Gupta
 */
@RunWith(Arquillian.class)
public class PersonResourceTest {
    
    private WebTarget target;
    
    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
            .addClasses(Person.class);
    }
    
    @ArquillianResource
    private URL base;

    @Before
    public void setUp() throws MalformedURLException {
        Client client = ClientBuilder.newClient();
        target = client.target(URI.create(new URL(base, "webresources/persons").toExternalForm()));
        target.register(Person.class);
    }

    /**
     * Test of getList method, of class MyResource.
     */
    @Test
    public void test1GetAll() {
        Person[] list = target.request().get(Person[].class);
        assertEquals(8, list.length);

        verifyInitialNames(list);
    }

    private void verifyInitialNames(Person[] list) {
        assertEquals("Penny", list[0].getName());
        assertEquals("Leonard", list[1].getName());
        assertEquals("Sheldon", list[2].getName());
        assertEquals("Amy", list[3].getName());
        assertEquals("Howard", list[4].getName());
        assertEquals("Bernadette", list[5].getName());
        assertEquals("Raj", list[6].getName());
        assertEquals("Priya", list[7].getName());
    }

    /**
     * Test of getPerson method, of class MyResource.
     */
    @Test
    public void test2GetSingle() {
        Person p = target
                .path("{id}")
                .resolveTemplate("id", "1")
                .request(MediaType.APPLICATION_XML)
                .get(Person.class);
        assertEquals("Leonard", p.getName());
    }

    /**
     * Test of getPerson method, of class MyResource.
     */
    @Test
    public void test3GetAntherSingle() {
        Person p = target
                .path("{id}")
                .resolveTemplate("id", "7")
                .request(MediaType.APPLICATION_XML)
                .get(Person.class);
        assertEquals("Priya", p.getName());
    }

    /**
     * Test of getList method, of class MyResource.
     */
    @Test
    public void test4Add2Names() {
        MultivaluedHashMap<String, String> map = new MultivaluedHashMap<>();
        map.add("name", "Leslie");
        target.request().post(Entity.form(map));

        map.clear();
        map.add("name", "Stuart");
        target.request().post(Entity.form(map));

        Person[] list = target.request().get(Person[].class);
        assertEquals(10, list.length);
        
        verifyInitialNames(list);

        assertEquals("Leslie", list[8].getName());
        assertEquals("Stuart", list[9].getName());
    }
    
    @Test
    public void test5DeleteTwoNames() {
        target
                .path("{name}")
                .resolveTemplate("name", "Leslie")
                .request()
                .delete();
        target
                .path("{name}")
                .resolveTemplate("name", "Stuart")
                .request()
                .delete();
        Person[] list = target.request().get(Person[].class);
        verifyInitialNames(list);
    }

}
