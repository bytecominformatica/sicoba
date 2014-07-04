package net.servehttp.bytecom.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * 
 * @author felipe
 *
 */
public enum HashSHA256Util {
  
  INSTANCE;
  
  public static String geraHash256(String valor){
    try{
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      md.update(valor.getBytes());
      byte[] dataBytes = md.digest();
      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < dataBytes.length; i++) {
       sb.append(Integer.toString((dataBytes[i] & 0xff) + 0x100, 16).substring(1));
      }
      return sb.toString();
      
    }catch(NoSuchAlgorithmException alg){
      return null;
    }
   
  }

}
