package com.magazynplus.config;

import com.magazynplus.service.ProductService;
import lombok.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")

@SpringBootTest(
        classes = ProductService.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public abstract class AbstractIT {

    @LocalServerPort
    protected int port;


    protected String basePath = "http://api-gateway";

}