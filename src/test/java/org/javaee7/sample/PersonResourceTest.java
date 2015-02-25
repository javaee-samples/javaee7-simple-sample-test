package org.javaee7.sample;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;

/**
 * @author Arun Gupta
 */
@RunWith(Arquillian.class)
public class PersonResourceTest extends BaseTest {

    /**
     * GET all resources
     */
    @Test
    @InSequence(1)
    public void testGetAll() {
        Person[] list = target.request().get(Person[].class);
        assertEquals(8, list.length);

        verifyInitialNames(list);
    }

    /**
     * GET a single resource
     */
    @Test
    @InSequence(2)
    public void testGetSingle() {
        Person p = target
                .path("{id}")
                .resolveTemplate("id", "1")
                .request(MediaType.APPLICATION_XML)
                .get(Person.class);
        assertEquals("Leonard", p.getName());
    }

    /**
     * GET another single resource
     */
    @Test
    @InSequence(3)
    public void testGetAnotherSingle() {
        Person p = target
                .path("{id}")
                .resolveTemplate("id", "7")
                .request(MediaType.APPLICATION_XML)
                .get(Person.class);
        assertEquals("Priya", p.getName());
    }

}
