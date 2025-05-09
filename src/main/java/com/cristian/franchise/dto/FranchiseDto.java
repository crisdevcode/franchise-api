package com.cristian.franchise.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FranchiseDto {

    @Schema(hidden = true)
    private String id;

    @Schema(description = "Nombre de la franquicia", example = "frisby")
    private String name;

}
