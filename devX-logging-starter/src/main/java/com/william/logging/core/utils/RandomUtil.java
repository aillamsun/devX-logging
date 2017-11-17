package com.william.logging.core.utils;

import java.util.Random;
import java.util.UUID;

/**
 * Created by sungang on 2017/11/17.
 */
public class RandomUtil {

    private static final Random random = new Random();
    static char[] chars = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    public RandomUtil() {
    }

    public static Random getRandom() {
        return random;
    }

    public static String randomChar(int len) {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");

        for(int i = 0; i < len; ++i) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 62]);
        }

        return shortBuffer.toString();
    }

    public static String randomChar() {
        return randomChar(8);
    }

}
