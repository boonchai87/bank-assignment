package com.example.demo;

import java.security.SecureRandom;
import java.util.Optional;

public class Test {
    public static void main(String[] args) {
        SecureRandom secureRandom = new SecureRandom();
        // Generate a random integer between 0 (inclusive) and 1,000,000 (exclusive)
        // This ensures the number is within the range that can be represented by 6 digits (000000 - 999999)
        int randomInteger = secureRandom.nextInt(1000000);

        // Format the integer to a 6-digit string, padding with leading zeros if needed
        String generateNumber = String.format("%07d", randomInteger);
        System.out.println(generateNumber);
    }
}
