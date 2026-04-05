package core;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import utils.ConfigReader;

/**
 * Central place to build Rest Assured specs so every client shares
 * the same base URL, content type, and logging/Allure filters.
 */
public final class RequestSpecFactory {

    private static final RequestSpecification BASE_SPEC = new RequestSpecBuilder()
        .setBaseUri(ConfigReader.get("base.url"))
        .setContentType(ContentType.JSON)
        .addFilter(new AllureRestAssured())
        .addFilter(new RequestLoggingFilter())
        .addFilter(new ResponseLoggingFilter())
        .build();

    private RequestSpecFactory() {
        // utility
    }

    public static RequestSpecification baseSpec() {
        return BASE_SPEC;
    }
}
