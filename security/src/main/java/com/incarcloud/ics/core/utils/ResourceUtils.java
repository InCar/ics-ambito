package com.incarcloud.ics.core.utils;


import com.incarcloud.ics.log.Logger;
import com.incarcloud.ics.log.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


public class ResourceUtils {
    public static final String CLASSPATH_PREFIX = "classpath:";
    public static final String URL_PREFIX = "url:";
    public static final String FILE_PREFIX = "file:";
    private static final Logger log = LoggerFactory.getLogger(ResourceUtils.class);

    private ResourceUtils() {
    }

    public static boolean hasResourcePrefix(String resourcePath) {
        return resourcePath != null && (resourcePath.startsWith("classpath:") || resourcePath.startsWith("url:") || resourcePath.startsWith("file:"));
    }

    public static boolean resourceExists(String resourcePath) {
        InputStream stream = null;
        boolean exists = false;

        try {
            stream = getInputStreamForPath(resourcePath);
            exists = true;
        } catch (IOException var12) {
            stream = null;
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException var11) {
                    ;
                }
            }

        }

        return exists;
    }

    public static InputStream getInputStreamForPath(String resourcePath) throws IOException {
        InputStream is;
        if (resourcePath.startsWith("classpath:")) {
            is = loadFromClassPath(stripPrefix(resourcePath));
        } else if (resourcePath.startsWith("url:")) {
            is = loadFromUrl(stripPrefix(resourcePath));
        } else if (resourcePath.startsWith("file:")) {
            is = loadFromFile(stripPrefix(resourcePath));
        } else {
            is = loadFromFile(resourcePath);
        }

        if (is == null) {
            throw new IOException("Resource [" + resourcePath + "] could not be found.");
        } else {
            return is;
        }
    }

    private static InputStream loadFromFile(String path) throws IOException {
        log.debug("Opening file [" + path + "]...");
        return new FileInputStream(path);
    }

    private static InputStream loadFromUrl(String urlPath) throws IOException {
        URL url = new URL(urlPath);
        return url.openStream();
    }

    private static InputStream loadFromClassPath(String path) {
        return ClassUtils.getResourceAsStream(path);
    }

    private static String stripPrefix(String resourcePath) {
        return resourcePath.substring(resourcePath.indexOf(":") + 1);
    }

    public static void close(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException var2) {
                log.warn("Error closing input stream.");
            }
        }

    }
}
