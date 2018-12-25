package com.incarcloud.ambito.utils;


import java.util.HashMap;
import java.util.Map;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2018/12/25
 */
public class StringUtils {

    public static String camelToUnderline(String str){
        if(!hasLength(str)){
            return str;
        }
        char[] chars = str.toCharArray();
        StringBuffer buffer = new StringBuffer();
        for(int i = 0; i < chars.length; i++){
            char c = chars[i];
            if(CHAR_CASE.get(c) == null || !CHAR_CASE.get(c)){
                buffer.append(c);
            }else{
                if(str.length() > 1 && c != str.charAt(0)){
                    buffer.append("_");
                }
                buffer.append((char)(c + 32));
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

    private static Map<Character,Boolean> CHAR_CASE = new HashMap<>();
    static {
        char[] upper = {'A','B','C','D','E','F','G','H','I','G','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
        char[] lower = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
        for(char u : upper){
            CHAR_CASE.put(u, true);
        }
        for(char l : lower){
            CHAR_CASE.put(l, false);
        }
    }

    public static boolean isEmpty(String whereSql) {
        return whereSql == null || whereSql.isEmpty();
    }


}
