package com.cristian.franchise.service;

import com.cristian.franchise.dto.ProductDto;

import com.cristian.franchise.dto.UpdateProductNameDto;
import reactor.core.publisher.Mono;

public interface ProductService {
    Mono<ProductDto> createProduct(String franchiseId, String branchId, Mono<ProductDto> productDto);
    Mono<Void> deleteProduct(String franchiseId, String branchId, String productId);
    Mono<ProductDto> updateProductName(String franchiseId, String branchId, String productId, UpdateProductNameDto productNameDtoMono);
}
