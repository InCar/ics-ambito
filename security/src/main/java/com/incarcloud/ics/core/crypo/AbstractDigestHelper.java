package com.incarcloud.ics.core.crypo;

import com.incarcloud.ics.core.utils.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2019/1/14
 */
public abstract class AbstractDigestHelper implements DigestHelper {


    public static final int DEFAULT_ITERATION = 1;
    public static final int SALT_ITERATION = 2;
    protected int iteration;
    protected final String algorithmName;
    private byte[] bytes;
    private String base64Encode = null;


    public AbstractDigestHelper(String algorithmName, byte[] source) throws NoSuchAlgorithmException {
        this(algorithmName, source, null);
    }

    public AbstractDigestHelper(String algorithmName, byte[] source, byte[] salt) throws NoSuchAlgorithmException {
        this(DEFAULT_ITERATION, algorithmName, source, salt);
    }

    public AbstractDigestHelper(int iteration, String algorithmName, byte[] source, byte[] salt) throws NoSuchAlgorithmException {
        this.iteration = Math.max(iteration, DEFAULT_ITERATION);
        this.algorithmName = algorithmName;
        this.bytes = hash(source, salt, iteration);
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public int getIteration() {
        return iteration;
    }

    public void setIteration(int iteration) {
        this.iteration = iteration;
    }

    public String getAlgorithmName() {
        return algorithmName;
    }

    @Override
    public String digestToBase64() {
        if(base64Encode == null){
            base64Encode = Base64.getEncoder().encodeToString(getBytes());
        }
        return base64Encode;
    }

    @Override
    public String digestToHex() {
        if(base64Encode == null){
            base64Encode = StringUtils.bytesToHexString(getBytes());
        }
        return base64Encode;
    }



    protected byte[] hash(byte[] source, byte[] salt, int iteration) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance(getAlgorithmName());
        if (salt != null) {
            digest.reset();
            digest.update(salt);
        }
        byte[] hashed = digest.digest(source);
        int iterations = iteration - 1; //already hashed once above
        //iterate remaining number:
        for (int i = 0; i < iterations; i++) {
            digest.reset();
            hashed = digest.digest(hashed);
        }
        return hashed;
    }

    protected byte[] hash(byte[] source, byte[] salt) throws NoSuchAlgorithmException {
        return hash(source, salt, DEFAULT_ITERATION);
    }

    protected byte[] hash(byte[] source) throws NoSuchAlgorithmException {
        return hash(source, null);
    }

    public static DigestHelper getMd5DefaultHelper(byte[] source) {
        try {
            return new AbstractDigestHelper(Algorithm.MD5.getName(), source){};
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static DigestHelper getMd5SaltHelper(byte[] source, byte[] salt) {
        try {
            return new AbstractDigestHelper(SALT_ITERATION, Algorithm.MD5.getName(), source, salt){};
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static DigestHelper newInstance(DigestHelper.Algorithm algorithm, byte[] source, byte[] salt, int iteration)  {
        try {
            return new AbstractDigestHelper(iteration, algorithm.getName(), source, salt){};
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static DigestHelper newInstance(DigestHelper.Algorithm algorithm, byte[] source, byte[] salt)  {
        return newInstance(algorithm, source, salt, DEFAULT_ITERATION);
    }

//    public static void main(String[] args) {
//        DigestHelper digestHelper = AbstractDigestHelper.newInstance(Algorithm.MD5, "123456".getBytes(), null, 1);
//        System.out.println(digestHelper.digestToHex());
//        DigestHelper digestHelper1 = AbstractDigestHelper.newInstance(Algorithm.MD5, "e10adc3949ba59abbe56e057f20f883e".getBytes(), "Sk3rMg==".getBytes(), 2);
//        System.out.println(digestHelper1.digestToBase64());
//    }
}
