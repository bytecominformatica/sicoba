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
  
  public static byte[] geraHash256(String valor){
    try{
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      md.update(valor.getBytes());
      return md.digest();
    }catch(NoSuchAlgorithmException alg){
      return null;
    }   
   
  }

}
