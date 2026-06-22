package utils;

public class TokenManager {

    private static String token;

    public static void setToken(String tokenValue) {
        token = tokenValue;
    }

    public static String getToken() {
        return token;
    }
}