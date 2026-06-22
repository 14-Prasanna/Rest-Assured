package payloads;

import java.util.HashMap;
import java.util.Map;

public class NotesPayload {

    public static Map<String,Object> createNotePayload(){

        Map<String,Object> payload = new HashMap<>();

        payload.put("title","API Test in Postman");
        payload.put("content","Created by Prasanna");
        payload.put("tags",new String[]{"postman","Test"});
        payload.put("isPinned",false);
        payload.put("color","#bbbbbb");

        return payload;
    }

    public static Map<String,Object> optionalFieldsPayload(){

        Map<String,Object> payload = new HashMap<>();

        payload.put("title","API Test in Postman");
        payload.put("content","Created by Prasanna");
        payload.put("isPinned",false);

        return payload;
    }

    public static Map<String, Object> nobody(){

        return new HashMap<>();
    }
}