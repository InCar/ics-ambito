package com.incarcloud.ics.core.crypo;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/** 
* @description AbstractDigestHelper Tester.
* @author ThomasChan
* @since <pre>Jan 14, 2019</pre> 
* @version 1.0 
*/ 
public class AbstractDegiestHelperTest {

    @Before
    public void before() throws Exception {
    }
    @After
    public void after() throws Exception { 
    }
        
    @Test
    public void testGetBytes() throws Exception { 
    //TODO: Test goes here... 
    }


    @Test
    public void testSetBytes() throws Exception {
    //TODO: Test goes here...
    }

    
    @Test
    public void testGetIteration() throws Exception { 
    //TODO: Test goes here... 
    } 

    
    @Test
    public void testSetIteration() throws Exception { 
    //TODO: Test goes here... 
    } 

    
    @Test
    public void testGetAlgorithmName() throws Exception { 
    //TODO: Test goes here... 
    } 

    
    @Test
    public void testToBase64() throws Exception { 
    //TODO: Test goes here... 
    } 

    
    @Test
    public void testHashForSourceSaltIteration() throws Exception { 
    //TODO: Test goes here... 
    } 

    
    @Test
    public void testHashForSourceSalt() throws Exception { 
    //TODO: Test goes here... 
    } 

    
    @Test
    public void testHashSource() throws Exception { 
        //TODO: Test goes here...
    } 

    
    @Test
    public void testGetDefaultHelper() throws Exception {
        {
            DigestHelper defaultHelper = AbstractDigestHelper.getMd5DefaultHelper("123456".getBytes());
            Assert.assertEquals("4QrcOUm6Wau+VuBX8g+IPg==", defaultHelper.digestToBase64());
        }

        {
            DigestHelper defaultHelper = AbstractDigestHelper.getMd5SaltHelper("4QrcOUm6Wau+VuBX8g+IPg==".getBytes(), "1234".getBytes());
            Assert.assertEquals("MFsSw0Mexb7tmFWFdts9VA==", defaultHelper.digestToBase64());
        }

        {
            DigestHelper sha1Helper = AbstractDigestHelper.newInstance(DigestHelper.Algorithm.SHA1, "123456".getBytes(), null, 1);
            Assert.assertEquals("fEqNCco3Yq9h5ZUglD3CZJT4lBs=", sha1Helper.digestToBase64());
        }

        {
            DigestHelper sha1Helper = AbstractDigestHelper.newInstance(DigestHelper.Algorithm.SHA256, "123456".getBytes(), null, 1);
            Assert.assertEquals("jZae727K08KaOmKSgOaGzww/XVqGr/PKEgIMkjrcbJI=", sha1Helper.digestToBase64());
        }

        {
            DigestHelper md5helper = AbstractDigestHelper.newInstance(DigestHelper.Algorithm.MD5, "123456".getBytes(), "1234".getBytes(), 2);
            Assert.assertEquals("vILnxmabYfMuzlfEmMaFFQ==", md5helper.digestToBase64());
        }
    }

    
    @Test
    public void testGetName() throws Exception { 
    //TODO: Test goes here... 
    } 


} 
