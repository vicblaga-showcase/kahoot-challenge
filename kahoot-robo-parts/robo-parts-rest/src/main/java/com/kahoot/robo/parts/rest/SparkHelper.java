package com.kahoot.robo.parts.rest;

import static spark.Spark.options;

public class SparkHelper {
    public static void enableCors() {
        options("/*",
                (request, response) -> {
                    String accessControlRequestHeaders = request
                            .headers("Access-Control-Request-Headers");
                    if (accessControlRequestHeaders != null) {
                        response.header("Access-Control-Allow-Headers",
                                accessControlRequestHeaders);
                    }

                    return "OK";
                });
    }
}
