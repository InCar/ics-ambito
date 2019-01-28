
package com.incarcloud.ics.ambito.utils;

import java.io.*;

public abstract class SerializationUtils {
    public SerializationUtils() {
    }

    public static byte[] serialize(Object object) {
        if (object == null) {
            return null;
        } else {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);

            try {
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(object);
                oos.flush();
            } catch (IOException ex) {
                throw new IllegalArgumentException("Failed to serialize object of type: " + object.getClass(), ex);
            }

            return baos.toByteArray();
        }
    }

    public static Object deserialize(byte[] bytes) {
        if (bytes == null) {
            return null;
        } else {
            try {
                ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
                return ois.readObject();
            } catch (IOException ex) {
                throw new IllegalArgumentException("Failed to deserialize object", ex);
            } catch (ClassNotFoundException ex1) {
                throw new IllegalStateException("Failed to deserialize object type", ex1);
            }
        }
    }
}
