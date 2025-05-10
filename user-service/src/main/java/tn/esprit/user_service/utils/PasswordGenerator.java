package tn.esprit.user_service.utils;

import java.security.SecureRandom;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PasswordGenerator {
    private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
    private static final String NUMBER = "0123456789";
    private static final String SPECIAL_CHARS = "!@#$%^&*()_+-=[]{}|;:,.<>?";
    private static final String PASSWORD_ALLOW = CHAR_LOWER + CHAR_UPPER + NUMBER + SPECIAL_CHARS;
    private static final SecureRandom random = new SecureRandom();

    public static String generateRandomPassword(int length) {
        if (length < 8) throw new IllegalArgumentException("Password must be at least 8 characters");

        StringBuilder password = new StringBuilder();

        // Ensure required character types
        password.append(CHAR_UPPER.charAt(random.nextInt(CHAR_UPPER.length())));
        password.append(CHAR_LOWER.charAt(random.nextInt(CHAR_LOWER.length())));
        password.append(NUMBER.charAt(random.nextInt(NUMBER.length())));
        password.append(SPECIAL_CHARS.charAt(random.nextInt(SPECIAL_CHARS.length())));

        // Fill the rest with random characters
        IntStream.range(4, length)
                .map(i -> random.nextInt(PASSWORD_ALLOW.length()))
                .mapToObj(PASSWORD_ALLOW::charAt)
                .forEach(password::append);

        // Shuffle to randomize positions
        return password.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        chars -> {
                            Collections.shuffle(chars);
                            return chars.stream()
                                    .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                                    .toString();
                        }));
    }
}