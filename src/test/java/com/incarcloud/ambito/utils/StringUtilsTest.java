package com.incarcloud.ambito.utils;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After; 

/** 
* @description StringUtils Tester. 
* @author ThomasChan
* @since <pre>Dec 25, 2018</pre> 
* @version 1.0 
*/ 
public class StringUtilsTest { 

    @Before
    public void before() throws Exception {
    }
    @After
    public void after() throws Exception { 
    }
        
    @Test
    public void testHasLength() throws Exception { 

    } 

    
    @Test
    public void testCapitalize() throws Exception {
        Assert.assertEquals(StringUtils.capitalize("abcd"), "Abcd");
    } 

    
    @Test
    public void testUncapitalize() throws Exception { 
    //TODO: Test goes here... 
    } 


    
    @Test
    public void testChangeFirstCharacterCase() throws Exception { 
    //TODO: Test goes here... 
        /* 
        try { 
           Method method = StringUtils.getClass().getMethod("changeFirstCharacterCase", String.class, boolean.class); 
           method.setAccessible(true); 
           method.invoke(<Object>, <Parameters>); 
        } catch(NoSuchMethodException e) { 
        } catch(IllegalAccessException e) { 
        } catch(InvocationTargetException e) { 
        } 
        */ 
    }
    @Test
    public void testCamelToUnderline(){
        Assert.assertEquals("abc_ac", StringUtils.camelToUnderline("abcAc"));
        Assert.assertEquals("", StringUtils.camelToUnderline(""));
        Assert.assertEquals("1", StringUtils.camelToUnderline("1"));
        Assert.assertEquals("1_a", StringUtils.camelToUnderline("1A"));
        Assert.assertEquals("1_ac", StringUtils.camelToUnderline("1Ac"));
        Assert.assertEquals("a", StringUtils.camelToUnderline("a"));
        Assert.assertEquals("a", StringUtils.camelToUnderline("A"));
        Assert.assertEquals("ab", StringUtils.camelToUnderline("Ab"));
        Assert.assertEquals("a)b", StringUtils.camelToUnderline("A)b"));
        Assert.assertEquals("a_b", StringUtils.camelToUnderline("aB"));
        Assert.assertEquals("a_bc", StringUtils.camelToUnderline("aBc"));
    }

} 
