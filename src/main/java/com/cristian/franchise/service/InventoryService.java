package com.cristian.franchise.service;

import com.cristian.franchise.dto.ProductDto;
import com.cristian.franchise.dto.UpdateProductStockDto;
import com.cristian.franchise.dto.response.MaxStockResponse;
import reactor.core.publisher.Mono;

public interface InventoryService {
    Mono<ProductDto> updateProductStock(String franchiseId, String branchId, String productId, UpdateProductStockDto productStockDtoMono);
    Mono<MaxStockResponse> findMaxStockProductsByFranchiseName(String franchiseName);
}
