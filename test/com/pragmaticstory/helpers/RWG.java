package com.pragmaticstory.helpers;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * RWG = Random Word Generator
 *
 * User: 1001923
 * Date: 15. 4. 3.
 * Time: 오전 11:39
 * To change this template use File | Settings | File Templates.
 */
public class RWG {
    private static char[] alphabet;
    static{
        alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
    }

    public static String randomWord(int length){
        StringBuffer sb = new StringBuffer(length);
        SecureRandom random = random();
        for(int idx=0;idx<length;idx++){
            sb.append(alphabet[random.nextInt(alphabet.length)]);
        }
        return sb.toString();
    }

    private static SecureRandom random(){
        SecureRandom secureRandom = null;
        try {
            secureRandom = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        secureRandom.setSeed(System.currentTimeMillis());
        return secureRandom;
    }
}
