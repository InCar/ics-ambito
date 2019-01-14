package com.incarcloud.ics.core.crypo;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/14
 */
public interface DigestHelper {
    String toBase64();

    byte[] getBytes();

    enum Algorithm{
        MD5("MD5"),

        SHA1("SHA-1"),

        SHA256("SHA-256");

        private String name;

        Algorithm(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
