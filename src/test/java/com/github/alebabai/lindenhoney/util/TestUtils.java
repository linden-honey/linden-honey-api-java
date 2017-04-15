package com.github.alebabai.lindenhoney.util;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

public abstract class TestUtils {

    public static final int MIN_ENTITIES_COUNT = 1;
    public static final int MAX_STRING_LENGTH = 25;
    public static final int MAX_ENTITIES_COUNT = 20;

    public static String getRandomString(int length) {
        return RandomStringUtils.random(length, true, true);
    }

    public static Integer getRandomInteger(int bound) {
        return RandomUtils.nextInt(1, bound);
    }

    public static Boolean getRandomBoolean() {
        return BooleanUtils.toBoolean(getRandomInteger(1));
    }

    public static Long getRandomLong(long bound) {
        return RandomUtils.nextLong(1, bound);
    }
}
