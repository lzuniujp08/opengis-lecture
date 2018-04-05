package com.lzugis.helper;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.SecureRandom;

public class DesEncrypt {

    public static Key  getKey(String strKey) {
        try {
            KeyGenerator _generator = KeyGenerator.getInstance("DES");
            _generator.init(new SecureRandom(strKey.getBytes()));
            Key tmpkey = _generator.generateKey();
            _generator = null;
            return tmpkey;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getEncString(String strMing, Key key) {
        byte[] byteMi = null;
        byte[] byteMing = null;
        String strMi = "";
        try {
            return byte2hex(getEncCode(strMing.getBytes(),key));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            byteMing = null;
            byteMi = null;
        }
        return strMi;
    }

    public static String getEncString(String strMing, String strKey) {
        byte[] byteMi = null;
        byte[] byteMing = null;
        String strMi = "";
        try {
            return byte2hex(getEncCode(strMing.getBytes(),getKey(strKey)));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            byteMing = null;
            byteMi = null;
        }
        return strMi;
    }

    public static String getDesString(String strMi, Key key) {
        byte[] byteMing = null;
        byte[] byteMi = null;
        String strMing = "";
        try {
            return new String(getDesCode(hex2byte(strMi.getBytes()),key));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            byteMing = null;
            byteMi = null;
        }
        return strMing;
    }

    public static String getDesString(String strMi, String strKey) {
        byte[] byteMing = null;
        byte[] byteMi = null;
        String strMing = "";
        try {
            return new String(getDesCode(hex2byte(strMi.getBytes()),getKey(strKey)));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            byteMing = null;
            byteMi = null;
        }
        return strMing;
    }

    //cipher class for encode and decode
    private static byte[] getEncCode(byte[] byteS, Key key) {
        byte[] byteFina = null;
        Cipher cipher;
        try {
            cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byteFina = cipher.doFinal(byteS);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cipher = null;
        }
        return byteFina;
    }

    private static byte[] getDesCode(byte[] byteD, Key key) {
        Cipher cipher;
        byte[] byteFina = null;
        try {
            cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byteFina = cipher.doFinal(byteD);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cipher = null;
        }
        return byteFina;
    }


    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
        }
        return hs.toUpperCase();
    }



    public static byte[] hex2byte(byte[] b) {
        if ((b.length % 2) != 0)
            throw new IllegalArgumentException("not odds");
        byte[] b2 = new byte[b.length / 2];
        for (int n = 0; n < b.length; n += 2) {
            String item = new String(b, n, 2);
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }
        return b2;
    }

    public static void main(String[] args) {
        DesEncrypt des = new DesEncrypt();
        Key key1 = des.getKey("adfsdf");
        System.out.println(key1);
        String strEnc = des.getEncString("122", key1);
        System.out.println(strEnc);
        DesEncrypt des2 = new DesEncrypt();
        Key key2 = des2.getKey("adfsdf");
        System.out.println(key1);
        String strDes = des2.getDesString(strEnc, key1);
        System.out.println(strDes);
        //new DesEncrypt();
    }
}