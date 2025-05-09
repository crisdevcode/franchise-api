package com.cristian.franchise.handler;

import com.cristian.franchise.dto.FranchiseDto;
import com.cristian.franchise.dto.UpdateFranchiseDto;
import com.cristian.franchise.service.FranchiseService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class FranchiseHandler {

    private final FranchiseService franchiseService;

    public Mono<ServerResponse> createFranchise(ServerRequest request) {
        return request
                .bodyToMono(FranchiseDto.class)
                .as(franchiseService::createFranchise)
                .flatMap(ServerResponse.ok()::bodyValue);
    }

    public Mono<ServerResponse> updateFranchise(ServerRequest request) {
        String franchiseId = request.pathVariable("franchiseId");
        return request
                .bodyToMono(UpdateFranchiseDto.class)
                .flatMap(dto -> franchiseService.updateFranchise(franchiseId, Mono.just(dto)))
                .flatMap(franchise -> ServerResponse.ok().bodyValue(franchise));
    }

}


