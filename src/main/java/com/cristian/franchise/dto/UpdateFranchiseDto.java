package com.cristian.franchise.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UpdateFranchiseDto {
    @Schema(description = "Nuevo nombre para la franquicia", example = "frisby colombia")
    private String name;
}
