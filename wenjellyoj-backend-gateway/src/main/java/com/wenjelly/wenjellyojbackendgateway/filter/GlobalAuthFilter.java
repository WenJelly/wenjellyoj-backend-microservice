package com.wenjelly.wenjellyojbackendgateway.filter;

/*
 * @time 2024/6/20 10:45
 * @package com.wenjelly.wenjellyojbackendgateway.filter
 * @project wenjellyoj-backend-microservice
 * @author WenJelly
 */

import cn.hutool.core.text.AntPathMatcher;
import com.alibaba.excel.event.Order;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

public class GlobalAuthFilter implements GlobalFilter, Ordered {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获得一些请求信息，可以根据路径判断哪些是可以访问，哪些是不能访问的
        ServerHttpRequest request = exchange.getRequest();
        // 拿到请求实际地址
        String path = request.getURI().getPath();
        if (antPathMatcher.match("/**/inner/**", path)) {
            // 拒绝请求
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.FORBIDDEN);
            // 返回给用户信息
            DataBufferFactory dataBufferFactory = response.bufferFactory();
            DataBuffer dataBuffer = dataBufferFactory.wrap("无权限".getBytes(StandardCharsets.UTF_8));
            return response.writeWith(Mono.just(dataBuffer));
        }
        // 其他权限校验
        return chain.filter(exchange);
    }

    /**
     * 提高这个拦截器的优先级
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
