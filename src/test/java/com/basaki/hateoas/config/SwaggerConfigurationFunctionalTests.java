package com.basaki.hateoas.config;

import com.basaki.hateoas.Application;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

/**
 * {@code SwaggerConfigurationIntegrationTests} represents functional tests for
 * {@code SwaggerConfiguration}.
 * <p/>
 *
 * @author Indra Basak
 * @since 08/04/18
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class SwaggerConfigurationFunctionalTests {

    @Value("${local.server.port}")
    private Integer port;

    @Test
    public void testApi() {
        Response response = given()
                .contentType(ContentType.JSON)
                .baseUri("http://localhost")
                .port(port)
                .contentType(ContentType.JSON)
                .get("/v2/api-docs?group=book");

        assertNotNull(response);
        assertEquals(200, response.getStatusCode());
        assertNotNull(response.getBody().prettyPrint());
    }
}
