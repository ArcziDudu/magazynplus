package com.magazynplus;


import com.fasterxml.jackson.databind.ObjectMapper;

import com.magazynplus.dto.ProductRequest;
import com.magazynplus.entity.ProductEntity;
import com.magazynplus.entity.UserEntity;
import com.magazynplus.repository.ProductJpaRepository;
import com.magazynplus.service.ProductService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.List;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers

class ProductServiceApplicationTests {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductJpaRepository productJpaRepository;
    @LocalServerPort
    private int port;
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

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 90545;
    }

    @Test
    void contextLoads() {
    }

    @Test
    void thatShouldAddProductCorrectly(){
        ProductRequest productRequestBuilder = ProductRequest.builder()
                .price(new BigDecimal("35.00"))
                .name("test")
                .description("tester")
                .producer("tester")
                .category("test")
                .quantity(5)
                .imageLink("testlink")
                .user(UserEntity.builder()
                        .email("test@test.com")
                        .firstname("test")
                        .lastname("tester")
                        .products(List.of())
                        .build())
                .build();

        Response response = given()
                .contentType(ContentType.JSON)
                .body(productRequestBuilder)
                .when()
                .post("http://localhost:" + port + "/api/product/add")
                .then()
                .assertThat()
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
