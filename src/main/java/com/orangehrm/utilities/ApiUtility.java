package com.orangehrm.utilities;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ApiUtility {

    // GET request
    public static Response sendGetRequest(String endpoint) {
        return RestAssured
                .given()
                .when()
                .get(endpoint);
    }

    // POST request
    public static Response sendPostRequest(String endpoint, String payload) {
        return RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body(payload)
                .when()
                .post(endpoint);
    }

    // Status code validation
    public static boolean validateStatusCode(Response response, int statusCode) {
        return response.getStatusCode() == statusCode;
    }

    // Extract JSON value (String)
    public static String getJsonValue(Response response, String path) {
        return response.jsonPath().getString(path);
    }

    // Extract JSON value (Type-safe)
    public static <T> T getJsonValue(Response response, String path, Class<T> type) {
        return response.jsonPath().getObject(path, type);
    }
}
