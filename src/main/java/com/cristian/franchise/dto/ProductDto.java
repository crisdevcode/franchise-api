package com.cristian.franchise.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDto {

    @Schema(hidden = true)
    private String id;

    @Schema(description = "Nombre del producto", example = "1/4 pollo broaster")
    private String name;

    @Schema(description = "Cantidad disponible del producto en inventario", example = "150")
    private Integer stock;

}
