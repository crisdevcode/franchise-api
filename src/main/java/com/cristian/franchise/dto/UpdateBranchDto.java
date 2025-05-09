package com.cristian.franchise.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UpdateBranchDto {
    @Schema(description = "Nuevo nombre para la sucursal", example = "frisby medellín")
    private String name;
}
