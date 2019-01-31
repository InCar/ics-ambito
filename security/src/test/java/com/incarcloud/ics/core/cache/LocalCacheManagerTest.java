//package com.incarcloud.ics.core.cache;
//
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.Before;
//import org.junit.After;
//
///**
//* @description LocalCacheManager Tester.
//* @author ThomasChan
//* @since <pre>Jan 17, 2019</pre>
//* @version 1.0
//*/
//public class LocalCacheManagerTest {
//
//    @Before
//    public void before() throws Exception {
//    }
//    @After
//    public void after() throws Exception {
//    }
//
//    @Test
//    public void testGetCache() throws Exception {
//        LocalCacheManager<String,Integer> localCacheManager = new LocalCacheManager<>(1, 1, true);
//        ValidatingLRUCache testCache = (ValidatingLRUCache) localCacheManager.getCache("testCache");
//        Assert.assertNotNull(testCache);
//        Assert.assertEquals(testCache.getName(), "testCache");
//        Assert.assertEquals(testCache.getTimeToLiveSeconds(), 1);
//        Assert.assertEquals(testCache.isEternal(), Boolean.TRUE);
//        Assert.assertNotNull(localCacheManager.getCache("testCache"));
//        testCache.put("1", 1);
//        testCache.put("2", 2);
//        testCache.put("3", 3);
//        testCache.put("4", 4);
//        testCache.put("5", 5);
//        System.out.println(testCache);
//        Assert.assertEquals(testCache.get("1"), 1);
//        Thread.sleep(1000);
//        Assert.assertNull(testCache.get("1"));
//    }
//
//
//    @Test
//    public void testCreateCache() throws Exception {
//    //TODO: Test goes here...
//    }
//
//
//
//}
