package com.magazynplus;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.magazynplus.config.WiremockConfig;
import com.magazynplus.dto.ProductRequest;
import com.magazynplus.entity.ProductEntity;
import com.magazynplus.repository.ProductJpaRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ActiveProfiles("test")
class ProductServiceApplicationTests extends WiremockConfig {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ProductJpaRepository productJpaRepository;
    @LocalServerPort
    private Integer port;


    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpassword");


    @DynamicPropertySource
    static void setPostgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
    }


    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 90545;
    }


    @Test
    void contextLoads() {
    }

    @Test
    void thatShouldAddProductCorrectly() throws JsonProcessingException {
        String jsonResponse = "{ \"id\": 2, \"email\": \"example@email.com\", \"firstname\": \"John\", \"lastname\": \"Doe\", \"products\": [ { \"id\": 1, \"name\": \"example_name\", \"category\": \"example_category\", \"producer\": \"example_producer\", \"price\": 29.99, \"quantity\": 10, \"description\": \"example_description\", \"availability\": true, \"productNumber\": \"ba550199-91a6-4d77-9ef6-8b17d050f517\", \"imageLink\": \"example_image_link\" } ] }";

        ProductRequest productRequestBuilder = ProductRequest.builder()
                .price(new BigDecimal("35.00"))
                .name("test")
                .description("tester")
                .producer("tester")
                .category("test")
                .quantity(5)
                .imageLink("testlink")
                .build();
        wireMockServer.stubFor(get(urlPathEqualTo("/api/user/info/2"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"userId\": 2, \"userName\": \"John Doe\"}")));

        Response response = given()
                .contentType(ContentType.JSON)
                .body(productRequestBuilder)
                .when()
                .post("http://localhost:" + port + "/api/product/add")
                .then()
                .assertThat()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        Boolean availabilityValue = jsonPath.getBoolean("availability");
        Integer id = jsonPath.getInt("id");
        List<ProductEntity> all = productJpaRepository.findAll();
        Assertions.assertEquals(true, availabilityValue);
        Assertions.assertEquals(1, id);
        Assertions.assertEquals(1, all.size());
    }

}
