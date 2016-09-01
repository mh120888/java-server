package com.github.mh120888.httpmessage;

public final class HTTPStatus {
    public static final int OK = 200;
    public static final int NO_CONTENT = 204;
    public static final int PARTIAL_CONTENT = 206;

    public static final int FOUND = 302;

    public static final int NOT_AUTHORIZED = 401;
    public static final int NOT_FOUND = 404;
    public static final int METHOD_NOT_ALLOWED = 405;
    public static final int IM_A_TEAPOT = 418;
    public static final int UNPROCESSABLE_ENTITY = 422;

    public static String getStatusText(int statusCode) {
        switch (statusCode) {
            case OK:
                return "OK";
            case NO_CONTENT:
                return "No Content";
            case PARTIAL_CONTENT:
                return "Partial Content";
            case FOUND:
                return "Found";
            case NOT_AUTHORIZED:
                return "Not Authorized";
            case NOT_FOUND:
                return "Not Found";
            case METHOD_NOT_ALLOWED:
                return "Method Not Allowed";
            case IM_A_TEAPOT:
                return "I'm a teapot";
            case UNPROCESSABLE_ENTITY:
                return "Unprocessable Entity";
            default:
                return "";
        }
    }
}
