package com.wenjelly.wenjellyojbackendjudgeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.wenjelly.wenjellyojbackendserviceclient.service"})
public class WenjellyojBackendJudgeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(WenjellyojBackendJudgeServiceApplication.class, args);
    }

}
