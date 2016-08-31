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

    public static String getStatusText(int statusCode) {
        switch (statusCode) {
            case 200: return "OK";
            case 204: return "No Content";
            case 206: return "Partial Content";
            case 302: return "Found";
            case 401: return "Not Authorized";
            case 404: return "Not Found";
            case 405: return "Method Not Allowed";
            case 418: return "I'm a teapot";
            default:  return "";
        }
    }
}
