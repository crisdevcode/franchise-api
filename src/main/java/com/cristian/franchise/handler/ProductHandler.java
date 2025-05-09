package com.cristian.franchise.handler;

import com.cristian.franchise.dto.*;
import com.cristian.franchise.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProductHandler {

    private final ProductService productService;

    public Mono<ServerResponse> createProduct(ServerRequest request) {
        String franchiseId = request.pathVariable("franchiseId");
        String branchId = request.pathVariable("branchId");

        return request
                .bodyToMono(ProductDto.class)
                .as(dto -> productService.createProduct(franchiseId, branchId, dto))
                .flatMap(ServerResponse.ok()::bodyValue);

    }

    public Mono<ServerResponse> deleteProduct(ServerRequest request) {
        String franchiseId = request.pathVariable("franchiseId");
        String branchId = request.pathVariable("branchId");
        String productId = request.pathVariable("productId");

        return productService.deleteProduct(franchiseId, branchId, productId)
                .then(ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> updateProductName(ServerRequest request) {
        String franchiseId = request.pathVariable("franchiseId");
        String branchId = request.pathVariable("branchId");
        String productId = request.pathVariable("productId");

        return request
                .bodyToMono(UpdateProductNameDto.class)
                .flatMap(dto -> productService.updateProductName(franchiseId, branchId, productId, dto))
                .flatMap(updatedProduct -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(updatedProduct));
    }
}

