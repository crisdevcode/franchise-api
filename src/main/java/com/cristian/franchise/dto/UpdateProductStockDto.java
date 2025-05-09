package com.cristian.franchise.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UpdateProductStockDto {
    @Schema(description = "Nuevo stock", example = "10")
    private Integer stock;
}
