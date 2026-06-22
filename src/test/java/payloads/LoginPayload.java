package payloads;

import java.util.HashMap;
import java.util.Map;

public class LoginPayload {

    public static Map<String, Object> validLoginPayload() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("email", "sam@gmail.com");
        payload.put("password", "123");
        return payload;
    }

    public static Map<String, Object> unknownEmailPayload() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("email", "user@gmail.com");
        payload.put("password", "password");
        return payload;
    }

    public static Map<String, Object> wrongPasswordPayload() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("email", "sam@gmail.com");
        payload.put("password", "wrongpassword");
        return payload;
    }

    public static Map<String, Object> missingFieldsPayload() {
        Map<String, Object> payload = new HashMap<>();

        return payload;
    }
}