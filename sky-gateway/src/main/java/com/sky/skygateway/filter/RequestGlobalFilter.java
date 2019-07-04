package com.sky.skygateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;

/**
 * 全局过滤器
 * 所有请求都会执行
 */
@Component
public class RequestGlobalFilter implements GlobalFilter, Ordered {
    private static final Logger logger = LoggerFactory.getLogger(RequestGlobalFilter.class);

    //执行逻辑
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String uri = request.getURI().toString();
        InetSocketAddress remoteAddress = request.getRemoteAddress();
        String hostString = remoteAddress.getHostString();
        MultiValueMap<String, String> queryParams = request.getQueryParams();
        logger.info(">> Host:" + hostString + "->End:sky-gateway//uri : " + uri);//打印每次请求的url
        for (String str : queryParams.keySet()) {
            for (String value : queryParams.get(str)) {
                logger.info(">>->> RequestParam:" + str + "=" + value);
            }
        }
        return chain.filter(exchange);
    }

    //执行顺序
    @Override
    public int getOrder() {
        return 1;
    }
}
