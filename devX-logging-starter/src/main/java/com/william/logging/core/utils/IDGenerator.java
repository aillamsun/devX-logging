package com.william.logging.core.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by sungang on 2017/11/17.
 */
public interface IDGenerator<T> {

    T generate();

    IDGenerator<String> UUID = java.util.UUID.randomUUID()::toString;

    IDGenerator<String> RANDOM = RandomUtil::randomChar;

    IDGenerator<String> MD5 = () -> {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(UUID.generate().concat(RandomUtil.randomChar()).getBytes());
            return new BigInteger(1, md.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    };
}
