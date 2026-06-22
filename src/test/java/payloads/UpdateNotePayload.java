package payloads;

import java.util.HashMap;
import java.util.Map;

public class UpdateNotePayload {

    public static Map<String,Object> updatePayload(){

        Map<String,Object> payload =
                new HashMap<>();

        payload.put(
                "title",
                "API Test Note (edited)");

        payload.put(
                "content",
                "Updated content");

        return payload;
    }
}