package com.wenjelly.wenjellyojbackendgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class WenjellyojBackendGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(WenjellyojBackendGatewayApplication.class, args);
    }

}
