package com.companyname.utils;

import java.util.Random;

public class Faker extends com.github.javafaker.Faker {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private final Random random;

    public Faker() {
        super();
        random = new Random();
    }

    public int getRandomInt(int from, int to) {
        return random.nextInt(to - from + 1) + from;
    }

    public String getRandomInt(int to){
        return Integer.toString(getRandomInt(0,to));
    }

    public String getRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(randomIndex));
        }

        return sb.toString();
    }
}
