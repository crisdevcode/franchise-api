package com.cristian.franchise.handler;

import com.cristian.franchise.dto.UpdateProductStockDto;
import com.cristian.franchise.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class InventoryHandler {

    private final InventoryService inventoryService;

    public Mono<ServerResponse> updateProductStock(ServerRequest request) {
        String franchiseId = request.pathVariable("franchiseId");
        String branchId = request.pathVariable("branchId");
        String productId = request.pathVariable("productId");

        return request
                .bodyToMono(UpdateProductStockDto.class)
                .flatMap(dto -> inventoryService.updateProductStock(franchiseId, branchId, productId, dto))
                .flatMap(updatedProduct -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(updatedProduct));
    }

    public Mono<ServerResponse> findMaxStockProductsByFranchiseName(ServerRequest request) {
        return request
                .queryParam("franchiseName")
                .map(franchiseName -> inventoryService.findMaxStockProductsByFranchiseName(franchiseName)
                        .flatMap(maxStockResponse -> ServerResponse.ok().bodyValue(maxStockResponse))
                        .switchIfEmpty(ServerResponse.notFound().build()))
                .orElseGet(() -> ServerResponse.badRequest()
                        .bodyValue(Collections.singletonMap("error", "Missing required query parameter: franchiseName")));
    }

}
