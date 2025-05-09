package com.cristian.franchise.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductHigherStockResponse {
    private String productName;
    private Integer stock;
    private BranchResponse branch;
}
