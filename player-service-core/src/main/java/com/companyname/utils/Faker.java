package com.companyname.utils;

import java.util.Random;

/**
 * Utility class Faker to extend Faker library capabilities for data generation.
 */
public class Faker extends com.github.javafaker.Faker {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private final Random random;

    public Faker() {
        super();
        random = new Random();
    }

    /**
     * Gets random int.
     *
     * @param from the from
     * @param to   the to
     * @return the random int
     */
    public int getRandomInt(int from, int to) {
        return random.nextInt(to - from + 1) + from;
    }

    /**
     * Gets random int.
     *
     * @param to the to
     * @return the random int
     */
    public String getRandomInt(int to) {
        return Integer.toString(getRandomInt(0, to));
    }

    /**
     * Gets random string.
     *
     * @param length the length
     * @return the random string
     */
    public String getRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(randomIndex));
        }
        return sb.toString();
    }
}
