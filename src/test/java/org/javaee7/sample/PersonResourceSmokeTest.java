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

        verifyInitialNames(list);
    }
}
