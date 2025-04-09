package com.bahl.dynamicsales.utils;

import jakarta.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

public class ErrorResponseBuilder {

    public static Response buildErrorResponse(boolean success, String message, int statusCode) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("success", success);
        errorResponse.put("message", message);
        errorResponse.put("statusCode", statusCode);
        return Response.status(statusCode).entity(errorResponse).build();
    }

    // Overload for convenience if you don't always have a specific status code
    public static Response buildErrorResponse(boolean success, String message, Response.Status status) {
        return buildErrorResponse(success, message, status.getStatusCode());
    }

    // Example of a specific error response builder
    public static Response buildBadRequestResponse(String message) {
        return buildErrorResponse(false, message, Response.Status.BAD_REQUEST.getStatusCode());
    }

    // Example of another specific error response builder
    public static Response buildUnauthorizedResponse(String message) {
        return buildErrorResponse(false, message, Response.Status.UNAUTHORIZED.getStatusCode());
    }

    // You can add more specific builders as needed
}
