package constants;

public class Endpoints {

    public static final String HEALTH = "/";
    public static final String LOGIN = "/user/login";
    public static final String GET_ALL_INSTITUTION = "/getAll/institution";
    public static final String GET_ROLES_ALL = "/roles/getAll";
    public static final String GET_ALL_COURSE = "courses-structure/getAll";
    public static final String CREATE_NOTES = "/create/notes";
    public static final String GET_ALL_NOTES = "/getAll/notes";
    public static final String GET_NOTE_BY_ID = "/getById/notes/{id}";
    public static final String UPDATE_NOTE = "/update/notes/{id}";
    public static final String TOGGLE_PIN = "/toggle-pin/notes/{id}";
    public static final String DELETE_NOTE = "/delete/notes/ById/{id}";
}