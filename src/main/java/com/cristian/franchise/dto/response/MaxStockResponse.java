package com.cristian.franchise.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MaxStockResponse {
    private String franchiseName;
    private List<ProductHigherStockResponse> productsHigherStock;
}

