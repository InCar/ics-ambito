package com.incarcloud.ics.core.cache;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/** 
* @description ValidatingLRUCache Tester. 
* @author ThomasChan
* @since <pre>Jan 16, 2019</pre> 
* @version 1.0 
*/ 
public class ValidatingLRUCacheTest { 

    @Before
    public void before() throws Exception { 
    }
    @After
    public void after() throws Exception { 
    }
        
    @Test
    public void testValidate() throws Exception {
        {
            ValidatingLRUCache lruCache = new ValidatingLRUCache("testCache", 3, 1);
            lruCache.put("1", 1);
            lruCache.put("2", 2);
            lruCache.put("3", 3);
            lruCache.put("4", 4);
            lruCache.put("5", 5);
            Assert.assertEquals(lruCache.get("1"), null);
            Assert.assertEquals(lruCache.get("2"), null);
            Assert.assertEquals(lruCache.get("3"), 3);
            Assert.assertEquals(lruCache.get("4"), 4);
            Assert.assertEquals(lruCache.get("5"), 5);
        }
        {
            ValidatingLRUCache lruCache = new ValidatingLRUCache(false, "testCache", 3, 1);
            lruCache.put("1", 1);
            lruCache.put("2", 2);
            lruCache.put("3", 3);
            lruCache.put("4", 4);
            lruCache.put("5", 5);
            Assert.assertEquals(lruCache.get("1"), null);
            Assert.assertEquals(lruCache.get("2"), null);
            Assert.assertEquals(lruCache.get("3"), 3);
            TimeUnit.SECONDS.sleep(1);
            Assert.assertEquals(lruCache.get("3"), null);
            Assert.assertEquals(lruCache.get("4"), null);
            Assert.assertEquals(lruCache.get("5"), null);

        }
    }

    
    @Test
    public void testGetTimeout() throws Exception { 
    //TODO: Test goes here... 
    } 

    
    @Test
    public void testGetStartTimestamp() throws Exception { 
    //TODO: Test goes here... 
    } 

    
    @Test
    public void testDoGet() throws Exception { 
    //TODO: Test goes here... 
    } 


} 
