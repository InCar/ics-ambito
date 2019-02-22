package com.incarcloud.ics.utils;

public final class OsUtils {

   private static String OS = null;
   public static String getOsName() {
      if(OS == null) { OS = System.getProperty("os.name"); }
      return OS;
   }

   public static boolean isWindows() {
      return getOsName().toLowerCase().contains("win");
   }

   public static boolean isLinux() {
      return getOsName().toLowerCase().contains("nux");
   }

   public static boolean isMacOS() {
      return getOsName().toLowerCase().contains("mac");
   }
}