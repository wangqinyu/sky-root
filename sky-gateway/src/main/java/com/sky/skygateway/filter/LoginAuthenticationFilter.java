package com.sky.skygateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * 可对客户端header 中的 Authorization 信息进行认证
 */
@Component
public class LoginAuthenticationFilter extends AbstractGatewayFilterFactory {

    private static final String Bearer_ = "Bearer ";

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpRequest.Builder mutate = request.mutate();
            ServerHttpResponse response = exchange.getResponse();
            try {
                //登录过滤（未实现）
                //Mono<WebSession> session = exchange.getSession();
                //改用spring security oauth2 + gateway 进行登录认证和健全
                //HttpSession session = request.getSession(false);
                //鉴权（未实现）
            }catch (Exception e){
                //没有token
                DataBuffer bodyDataBuffer = responseErrorInfo(response , HttpStatus.UNAUTHORIZED.toString() ,e.getMessage());
                return response.writeWith(Mono.just(bodyDataBuffer));
            }
            ServerHttpRequest build = mutate.build();
            return chain.filter(exchange.mutate().request(build).build());
        };
    }

    /**
     * 自定义返回错误信息
     * @param response
     * @param status
     * @param message
     * @return
     */
    public DataBuffer responseErrorInfo(ServerHttpResponse response , String status ,String message){
        HttpHeaders httpHeaders = response.getHeaders();
        httpHeaders.add("Content-Type", "application/json; charset=UTF-8");
        httpHeaders.add("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");

        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        Map<String,String> map = new HashMap<>();
        map.put("status",status);
        map.put("message",message);
        DataBuffer bodyDataBuffer = response.bufferFactory().wrap(map.toString().getBytes());
        return bodyDataBuffer;
    }
}
