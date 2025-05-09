package com.cristian.franchise.handler;

import com.cristian.franchise.dto.BranchDto;
import com.cristian.franchise.dto.UpdateBranchDto;
import com.cristian.franchise.exception.BranchNotFoundException;
import com.cristian.franchise.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class BranchHandler {

    private final BranchService branchService;

    public Mono<ServerResponse> createBranch(ServerRequest request) {
        String franchiseId = request.pathVariable("franchiseId");
        return request.bodyToMono(BranchDto.class)
                .as(mono -> branchService.createBranch(franchiseId, mono))
                .flatMap(branchDto -> ServerResponse.ok().bodyValue(branchDto))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> updateBranch(ServerRequest request) {
        String branchId = request.pathVariable("branchId");

        return request.bodyToMono(UpdateBranchDto.class)
                .flatMap(dto -> branchService.updateBranch(branchId, Mono.just(dto)))
                .flatMap(branchDto -> ServerResponse.ok().bodyValue(branchDto))
                .switchIfEmpty(Mono.error(new BranchNotFoundException("Branch with id: " + branchId + " not found")));
    }

}
