package com.outdoor.connect.utils;

import java.util.Random;

public abstract class StringUtils {

    public static String Random6DigitCode() {
        return String.valueOf(new Random().nextInt(999999));
    }
}
