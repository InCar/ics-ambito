package com.incarcloud.ics.core.utils;


import com.incarcloud.ics.log.Logger;
import com.incarcloud.ics.log.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;


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

    public static DirectoryStream<Path> getResourcesAsStreamOfJar(Class clazz, String folder){
        try{
            URL jar = clazz.getProtectionDomain().getCodeSource().getLocation();
            Path jarFile = Paths.get(jar.toURI());
            FileSystem fs = FileSystems.newFileSystem(jarFile, null);
            return Files.newDirectoryStream(fs.getPath(folder));
        }catch(IOException | URISyntaxException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 读取resources目录下某文件夹里的所有文件
     * @param clazz  类型
     * @param folder 文件夹路径
     * @return 文件夹中所有文件的输入流集合
     */
    public static List<InputStream> getResourcesAsStreams(Class clazz, String folder){
        ClassLoader classLoader = clazz.getClassLoader();
        URI uri = null;
        try {
            URL resource = classLoader.getResource(folder);
            if(resource == null){
                throw new RuntimeException("未找到文件夹["+folder+"]");
            }else {
                uri = resource.toURI();
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException(e.getMessage());
        }

        List<InputStream> inputStreamList = new ArrayList<>();
        if(uri.getScheme().contains("jar")){
            /** jar case */
            try(DirectoryStream<Path> directoryStream = getResourcesAsStreamOfJar(clazz, folder)){
                for(Path p: directoryStream){
                    InputStream is = clazz.getResourceAsStream(p.toString());
                    inputStreamList.add(is);
                }
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }

        }else {
            /** IDE case */
            Path path = Paths.get(uri);
            try(DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path);) {
                for(Path p : directoryStream){
                    InputStream is = new FileInputStream(p.toFile());
                    inputStreamList.add(is);
                }
            } catch (IOException _e) {
                throw new RuntimeException(_e.getMessage());
            }
        }
        return inputStreamList;
    }
}
