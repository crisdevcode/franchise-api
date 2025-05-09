package com.cristian.franchise.config.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerFilterFunction;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class LoggingFilter implements HandlerFilterFunction<ServerResponse, ServerResponse> {

    @Override
    public Mono<ServerResponse> filter(ServerRequest request, HandlerFunction<ServerResponse> next) {
        long start = System.currentTimeMillis();
        return next.handle(request)
                .doOnSuccess(response -> {
                    long executionTime = System.currentTimeMillis() - start;
                    log.info("Request: {} {} executed in {} ms",
                            request.method(),
                            request.path(),
                            executionTime);
                })
                .doOnError(error -> {
                    long executionTime = System.currentTimeMillis() - start;
                    log.error("Request: {} {} failed in {} ms - Error: {}",
                            request.method(),
                            request.path(),
                            executionTime,
                            error.getMessage());
                });
    }
}