package com.cristian.franchise.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UpdateProductNameDto {
    @Schema(description = "Nuevo nombre del producto", example = "1/4 pollo asado")
    private String name;
}
