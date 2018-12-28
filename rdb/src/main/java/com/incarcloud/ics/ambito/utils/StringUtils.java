package com.incarcloud.ics.ambito.utils;


import org.springframework.util.Assert;
import sun.jvm.hotspot.runtime.Bytes;

import javax.xml.stream.events.Characters;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2018/12/25
 */
public class StringUtils {

    private StringUtils(){}

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private static final int DEFAULT_SECURE_SIZE = 4;

    public static String camelToUnderline(String str){
        if(!hasLength(str)){
            return str;
        }
        char[] chars = str.toCharArray();
        StringBuffer buffer = new StringBuffer();
        for(int i = 0; i < chars.length; i++){
            char c = chars[i];
            if( isNotLetter(c) || isLowerLetter(c)){
                buffer.append(c);
            }else{
                if(str.length() > 1 && c != str.charAt(0)){
                    buffer.append("_");
                }
                buffer.append(Character.toLowerCase(c));
            }
        }
        return buffer.toString();
    }


    public static boolean hasLength(CharSequence str) {
        return str != null && str.length() > 0;
    }

    public static String capitalize(String str) {
        return changeFirstCharacterCase(str, true);
    }

    public static String uncapitalize(String str) {
        return changeFirstCharacterCase(str, false);
    }

    private static String changeFirstCharacterCase(String str, boolean capitalize) {
        if (!hasLength(str)) {
            return str;
        } else {
            char baseChar = str.charAt(0);
            char updatedChar;
            if (capitalize) {
                updatedChar = Character.toUpperCase(baseChar);
            } else {
                updatedChar = Character.toLowerCase(baseChar);
            }
            if (baseChar == updatedChar) {
                return str;
            } else {
                char[] chars = str.toCharArray();
                chars[0] = updatedChar;
                return new String(chars, 0, chars.length);
            }
        }
    }

    private static boolean isUpperLetter(char c){
        return  c >= 'A' && c <= 'Z';
    }

    private static boolean isLowerLetter(char c){
        return  c >= 'a' && c <= 'z';
    }

    private static boolean isNotLetter(char c){
        return !isUpperLetter(c) && !isLowerLetter(c);
    }

    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean isNotEmpty(String str) {
        return str != null && !str.isEmpty();
    }

    public static String getRandomSecureSalt(){
        byte[] bytes = new byte[DEFAULT_SECURE_SIZE];
        SECURE_RANDOM.nextBytes(bytes);
        return Base64.getEncoder().encodeToString(bytes);
    }
}
