package utils;

public class NoteManager {

    private static String noteId;

    public static void setNoteId(String id) {
        noteId = id;
    }

    public static String getNoteId() {
        return noteId;
    }
}