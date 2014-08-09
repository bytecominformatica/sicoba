package net.servehttp.bytecom.util;

import org.apache.shiro.crypto.hash.Sha256Hash;

/**
 * 
 * @author Clairton Luz
 *
 */
public abstract class HashUtil {

  public static String sha256ToHex(String valor) {
    return new Sha256Hash(valor).toHex();
  }

}
