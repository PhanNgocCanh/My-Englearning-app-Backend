package com.exerciseapp.myapp.common.constants;

public class ErrorConstant {

    // Start system error
    public static String INTERNAL_SERVER_ERROR = "error.system.internal_server_error";
    public static String FILE_FORMAT_INVALID = "error.system.file_format_invalid";
    public static String FILE_NOT_FOUND = "error.system.file_not_found";
    // End system error

    //-----------------------------------------------------------------------------------------------------------------

    // Start error user
    public static String EMAIL_BLANK = "error.user.email_blank";
    public static String USER_ALREADY_EXISTS = "error.user.user_already_exists";
    public static String USER_NOT_FOUND = "error.user.not_found";
    public static String IS_NOT_MANAGER_USER = "error.user.is_not_manage_user";
    // End error user

    //-----------------------------------------------------------------------------------------------------------------

    // Start collection error
    public static String COLLECTION_NAME_EMPTY = "error.collection.name_empty";
    // End collection error

    //-----------------------------------------------------------------------------------------------------------------

    // Start lesson error
    public static String LESSON_NAME_EMPTY = "error.lesson.name_empty";
    public static String LESSON_NOT_EXISTS = "error.lesson.not_exists";
    // End lesson error

    //------------------------------------------------------------------------------------------------------------------

    // Start document error
    public static String DOCUMENT_NOT_FOUND = "error.document.not_found";
    // End document error
}
