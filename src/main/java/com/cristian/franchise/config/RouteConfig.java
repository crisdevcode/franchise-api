package com.cristian.franchise.config;

import com.cristian.franchise.config.filter.LoggingFilter;
import com.cristian.franchise.handler.BranchHandler;
import com.cristian.franchise.handler.FranchiseHandler;
import com.cristian.franchise.handler.InventoryHandler;
import com.cristian.franchise.handler.ProductHandler;
import com.cristian.franchise.dto.*;
import com.cristian.franchise.dto.response.MaxStockResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.*;

@Configuration
public class RouteConfig {

    @Bean
    @RouterOperations({
            // -------------------- FRANCHISES --------------------
            @RouterOperation(
                    path = "/api/franchises",
                    method = RequestMethod.POST,
                    beanClass = FranchiseHandler.class,
                    beanMethod = "createFranchise",
                    operation = @Operation(
                            operationId = "createFranchise",
                            summary = "Crear nueva franquicia",
                            description = "Registra una nueva franquicia.",
                            requestBody = @RequestBody(
                                    content = @Content(schema = @Schema(implementation = FranchiseDto.class)),
                                    required = true
                            ),
                            responses = @ApiResponse(responseCode = "200", description = "Franquicia creada",
                                    content = @Content(schema = @Schema(implementation = FranchiseDto.class)))
                    )
            ),
            @RouterOperation(
                    path = "/api/franchises/{franchiseId}",
                    method = RequestMethod.PUT,
                    beanClass = FranchiseHandler.class,
                    beanMethod = "updateFranchise",
                    operation = @Operation(
                            operationId = "updateFranchise",
                            summary = "Actualizar franquicia",
                            description = "Actualiza los datos de una franquicia existente.",
                            parameters = {
                                    @Parameter(in = ParameterIn.PATH, name = "franchiseId", description = "Identificador de la franquicia", required = true),
                            },
                            requestBody = @RequestBody(
                                    content = @Content(schema = @Schema(implementation = FranchiseDto.class)),
                                    required = true
                            ),
                            responses = @ApiResponse(responseCode = "200", description = "Franquicia actualizada",
                                    content = @Content(schema = @Schema(implementation = FranchiseDto.class)))
                    )
            ),

            // -------------------- BRANCHES --------------------
            @RouterOperation(
                    path = "/api/franchises/{franchiseId}/branches",
                    method = RequestMethod.POST,
                    beanClass = BranchHandler.class,
                    beanMethod = "createBranch",
                    operation = @Operation(
                            operationId = "createBranch",
                            summary = "Crear nueva sucursal",
                            description = "Registra una nueva sucursal para una franquicia.",
                            parameters = {
                                    @Parameter(in = ParameterIn.PATH, name = "franchiseId", description = "Identificador de la franquicia", required = true),
                            },
                            requestBody = @RequestBody(
                                    content = @Content(schema = @Schema(implementation = BranchDto.class)),
                                    required = true
                            ),
                            responses = @ApiResponse(responseCode = "200", description = "Sucursal creada",
                                    content = @Content(schema = @Schema(implementation = BranchDto.class)))
                    )
            ),
            @RouterOperation(
                    path = "/api/franchises/{franchiseId}/branches/{branchId}",
                    method = RequestMethod.PUT,
                    beanClass = BranchHandler.class,
                    beanMethod = "updateBranch",
                    operation = @Operation(
                            operationId = "updateBranch",
                            summary = "Actualizar sucursal",
                            description = "Actualiza los datos de una sucursal existente.",
                            parameters = {
                                    @Parameter(in = ParameterIn.PATH, name = "franchiseId", description = "Identificador de la franquicia", required = true),
                                    @Parameter(in = ParameterIn.PATH, name = "branchId", description = "Identificador de la sucursal", required = true),
                            },
                            requestBody = @RequestBody(
                                    content = @Content(schema = @Schema(implementation = BranchDto.class)),
                                    required = true
                            ),
                            responses = @ApiResponse(responseCode = "200", description = "Sucursal actualizada",
                                    content = @Content(schema = @Schema(implementation = BranchDto.class)))
                    )
            ),

            // -------------------- PRODUCTS --------------------
            @RouterOperation(
                    path = "/api/franchises/{franchiseId}/branches/{branchId}/products",
                    method = RequestMethod.POST,
                    beanClass = ProductHandler.class,
                    beanMethod = "createProduct",
                    operation = @Operation(
                            operationId = "createProduct",
                            summary = "Crear nuevo producto",
                            description = "Registra un nuevo producto en una sucursal.",
                            parameters = {
                                    @Parameter(in = ParameterIn.PATH, name = "franchiseId", description = "Identificador de la franquicia", required = true),
                                    @Parameter(in = ParameterIn.PATH, name = "branchId", description = "Identificador de la sucursal", required = true)
                            },
                            requestBody = @RequestBody(
                                    content = @Content(schema = @Schema(implementation = ProductDto.class)),
                                    required = true
                            ),
                            responses = @ApiResponse(responseCode = "200", description = "Producto creado",
                                    content = @Content(schema = @Schema(implementation = ProductDto.class)))
                    )
            ),
            @RouterOperation(
                    path = "/api/franchises/{franchiseId}/branches/{branchId}/products/{productId}/name",
                    method = RequestMethod.PUT,
                    beanClass = ProductHandler.class,
                    beanMethod = "updateProductName",
                    operation = @Operation(
                            operationId = "updateProductName",
                            summary = "Actualizar nombre del producto",
                            description = "Modifica el nombre de un producto existente.",
                            parameters = {
                                    @Parameter(in = ParameterIn.PATH, name = "franchiseId", description = "Identificador de la franquicia", required = true),
                                    @Parameter(in = ParameterIn.PATH, name = "branchId", description = "Identificador de la sucursal", required = true),
                                    @Parameter(in = ParameterIn.PATH, name = "productId", description = "Identificador del producto", required = true)
                            },
                            requestBody = @RequestBody(
                                    content = @Content(schema = @Schema(implementation = UpdateProductNameDto.class)),
                                    required = true
                            ),
                            responses = @ApiResponse(responseCode = "200", description = "Nombre del producto actualizado",
                                    content = @Content(schema = @Schema(implementation = ProductDto.class)))
                    )
            ),
            @RouterOperation(
                    path = "/api/franchises/{franchiseId}/branches/{branchId}/products/{productId}",
                    method = RequestMethod.DELETE,
                    beanClass = ProductHandler.class,
                    beanMethod = "deleteProduct",
                    operation = @Operation(
                            operationId = "deleteProduct",
                            summary = "Eliminar producto",
                            description = "Elimina un producto de una sucursal.",
                            parameters = {
                                    @Parameter(in = ParameterIn.PATH, name = "franchiseId", description = "Identificador de la franquicia", required = true),
                                    @Parameter(in = ParameterIn.PATH, name = "branchId", description = "Identificador de la sucursal", required = true),
                                    @Parameter(in = ParameterIn.PATH, name = "productId", description = "Identificador del producto", required = true)
                            },
                            responses = @ApiResponse(responseCode = "204", description = "Producto eliminado exitosamente")
                    )
            ),

            // -------------------- INVENTORY --------------------
            @RouterOperation(
                    path = "/api/inventory/franchises/{franchiseId}/max-stock",
                    method = RequestMethod.GET,
                    beanClass = InventoryHandler.class,
                    beanMethod = "findMaxStockProductsByFranchiseName",
                    operation = @Operation(
                            operationId = "findMaxStockProductsByFranchiseName",
                            summary = "Obtener productos con stock máximo",
                            description = "Devuelve los productos con el stock más alto de una franquicia.",
                            parameters = @Parameter(name = "franchiseName", description = "Nombre de la franquicia", required = true, example = "frisby"),
                            responses = @ApiResponse(responseCode = "200", description = "Lista de productos obtenida",
                                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = MaxStockResponse.class))))
                    )
            ),
            @RouterOperation(
                    path = "/api/inventory/franchises/{franchiseId}/branches/{branchId}/products/{productId}/stock",
                    method = RequestMethod.PUT,
                    beanClass = InventoryHandler.class,
                    beanMethod = "updateProductStock",
                    operation = @Operation(
                            operationId = "updateProductStock",
                            summary = "Actualizar stock del producto",
                            description = "Modifica el stock de un producto en una sucursal específica.",
                            parameters = {
                                    @Parameter(in = ParameterIn.PATH, name = "franchiseId", description = "Identificador de la franquicia", required = true),
                                    @Parameter(in = ParameterIn.PATH, name = "branchId", description = "Identificador de la sucursal", required = true),
                                    @Parameter(in = ParameterIn.PATH, name = "productId", description = "Identificador del producto", required = true)
                            },
                            requestBody = @RequestBody(
                                    content = @Content(schema = @Schema(implementation = UpdateProductStockDto.class)),
                                    required = true
                            ),
                            responses = @ApiResponse(responseCode = "200", description = "Stock actualizado correctamente",
                                    content = @Content(schema = @Schema(implementation = ProductDto.class)))
                    )
            )
    })
    public RouterFunction<ServerResponse> appRoutes(
            FranchiseHandler franchiseHandler,
            BranchHandler branchHandler,
            ProductHandler productHandler,
            InventoryHandler inventoryHandler,
            LoggingFilter loggingFilter
    ) {
        return RouterFunctions.route()
                .path("/api", () -> baseRoutes(franchiseHandler, branchHandler, productHandler, inventoryHandler))
                .filter(loggingFilter)
                .build();
    }

    private RouterFunction<ServerResponse> baseRoutes(
            FranchiseHandler franchiseHandler,
            BranchHandler branchHandler,
            ProductHandler productHandler,
            InventoryHandler inventoryHandler
    ) {
        return RouterFunctions.route()
                .path("/franchises", () -> franchiseRoutes(franchiseHandler, branchHandler, productHandler))
                .path("/inventory", () -> inventoryRoutes(inventoryHandler))
                .build();
    }

    private RouterFunction<ServerResponse> franchiseRoutes(
            FranchiseHandler franchiseHandler,
            BranchHandler branchHandler,
            ProductHandler productHandler
    ) {
        return RouterFunctions.route()
                // Franchise routes
                .POST("", franchiseHandler::createFranchise)
                .PUT("/{franchiseId}", franchiseHandler::updateFranchise)

                // Branch routes
                .POST("/{franchiseId}/branches", branchHandler::createBranch)
                .PUT("/{franchiseId}/branches/{branchId}", branchHandler::updateBranch)

                // Product routes
                .POST("/{franchiseId}/branches/{branchId}/products", productHandler::createProduct)
                .PUT("/{franchiseId}/branches/{branchId}/products/{productId}/name", productHandler::updateProductName)
                .DELETE("/{franchiseId}/branches/{branchId}/products/{productId}", productHandler::deleteProduct)

                .build();
    }

    private RouterFunction<ServerResponse> inventoryRoutes(InventoryHandler inventoryHandler) {
        return RouterFunctions.route()
                .GET("/franchises/{franchiseId}/max-stock", inventoryHandler::findMaxStockProductsByFranchiseName)
                .PUT("/franchises/{franchiseId}/branches/{branchId}/products/{productId}/stock", inventoryHandler::updateProductStock)
                .build();
    }
}
