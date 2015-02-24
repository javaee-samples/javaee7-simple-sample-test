package org.javaee7.sample;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;

/**
 * @author Arun Gupta
 */
@RunWith(Arquillian.class)
public class PersonResourceSmokeTest extends BaseTest {

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
