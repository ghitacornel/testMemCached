package tests;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClient;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestCRUD {

    private MemcachedClient client;

    @Before
    public void setUp() throws Exception {
        String host = "localhost";
        int port = 11211;
        client = new XMemcachedClient(host, port);
    }

    @Test
    public void test() throws Exception {

        String key = "key";
        Integer value = 3;

        //store a value for one hour(synchronously).
        client.set(key, 3600, value);

        //Retrieve a value.(synchronously).
        {
            Object object1 = client.get(key);
            Assert.assertEquals(value, object1);
        }

        //Retrieve a value.(synchronously),operation timeout two seconds
        {
            Object object2 = client.get(key, 2000);
            Assert.assertEquals(value, object2);
        }

        //Touch cache item ,update it's expire time to 10 seconds.
        {
            boolean success = client.touch(key, 10);
            Assert.assertTrue(success);
        }

        //delete value
        {
            client.delete(key);
            boolean success = client.touch(key, 10);
            Assert.assertFalse(success);
        }

    }
}
