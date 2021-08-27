package com.hollay.commonutils;

import com.sun.org.apache.bcel.internal.classfile.Code;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.Random;

/**
 * 随机值工具类
 */
public class RandomUtils {

    private static final char[] CODE_SEQ = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ123456789".toCharArray();

    private static final char[] NUMBER_ARRAY = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

    private static Random random = new Random();

    private static final DecimalFormat FOUR_DF = new DecimalFormat("0000");

    private static final DecimalFormat SIX_DF = new DecimalFormat("000000");

    public static String getFourBitRandom() {
        return FOUR_DF.format(random.nextInt(10000));
    }

    public static String getSixBitRandom() {
        return SIX_DF.format(random.nextInt(1000000));
    }

    public static String randomString(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(String.valueOf(CODE_SEQ[random.nextInt(CODE_SEQ.length)]));
        }
        return sb.toString();
    }

    public static String randomNumberString(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(String.valueOf(NUMBER_ARRAY[random.nextInt(NUMBER_ARRAY.length)]));
        }
        return sb.toString();
    }

    public static Color randomColor(int fc, int bc) {
        int f = fc;
        int b = bc;
        Random random = new Random();
        if (f > 255) {
            f = 255;
        }
        if (b > 255) {
            b = 255;
        }
        return new Color(f + random.nextInt(b - f), f + random.nextInt(b - f), f + random.nextInt(b - f));
    }

    public static int nextInt(int bound) {
        return random.nextInt(bound);
    }
}

