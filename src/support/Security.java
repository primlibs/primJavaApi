/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package support;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Random;

/**
 * класс отвечает за безопасность
 *
 * @author User
 */
public class Security {
  private Security(){
  }

  /**
   * возвращает случайную строку
   * @return
   * @throws Exception 
   */
  public static String getKey() throws Exception {
    Random rand = new Random();
    Integer i = rand.nextInt();
    return md5(i.toString());
  }

  /**
   * возвращает md5 от переданной строки
   * @param str
   * @return
   * @throws Exception 
   */
  public static String md5(String str) throws Exception {
    MessageDigest m = MessageDigest.getInstance("MD5");
    m.update(str.getBytes(), 0, str.length());
    String hash = new BigInteger(1, m.digest()).toString(16);
    return hash;
  }
}
