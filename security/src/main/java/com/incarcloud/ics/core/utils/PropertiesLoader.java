package com.incarcloud.ics.core.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;


public class PropertiesLoader {
    private String properiesName = "";

    public PropertiesLoader() {
    }

    public PropertiesLoader(String fileName) {
        this.properiesName = fileName;
    }

    public String readProperty(String key) {
        String value = "";
        InputStream is = null;
        try {
            is = PropertiesLoader.class.getClassLoader().getResourceAsStream(
                    properiesName);
            Properties p = new Properties();
            p.load(is);
            value = p.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                Objects.requireNonNull(is).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return value;
    }

    public Properties getProperties() {
        Properties p = new Properties();
        InputStream is = null;
        try {
            is = PropertiesLoader.class.getClassLoader().getResourceAsStream(
                    properiesName);
            p.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                Objects.requireNonNull(is).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return p;
    }

//    public void writeProperty(String key, String value) {
//        InputStream is = null;
//        OutputStream os = null;
//        Properties p = new Properties();
//        try {
//            is = new FileInputStream(properiesName);
//            p.load(is);
//            os = new FileOutputStream(PropertiesLoader.class.getClassLoader().getResource(properiesName).getFile());
//
//            p.setProperty(key, value);
//            p.store(os, key);
//            os.flush();
//            os.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                Objects.requireNonNull(is).close();
//                Objects.requireNonNull(os).close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//    }



}