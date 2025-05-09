package com.cristian.franchise.mapper;

import com.cristian.franchise.dto.*;
import com.cristian.franchise.model.Branch;
import com.cristian.franchise.model.Franchise;
import com.cristian.franchise.model.Product;
import org.bson.types.ObjectId;

public class EntityDtoMapper {

    // == Franchise Mappers ==
    public static Franchise toEntity(FranchiseDto dto) {
        var franchise = new Franchise();
        franchise.setName(dto.getName());
        return franchise;
    }

    public static FranchiseDto toDto(Franchise franchise) {
        return FranchiseDto.builder()
                .id(franchise.getId().toHexString())
                .name(franchise.getName())
                .build();
    }

    // == Branch Mappers ==
    public static BranchDto toBranchDto(Branch branch) {
        return BranchDto.builder()
                .id(branch.getId().toHexString())
                .name(branch.getName())
                .build();
    }

    public static Branch toBranchEntity(BranchDto dto) {
        var branch = new Branch();
        branch.setId(new ObjectId(dto.getId()));
        branch.setName(dto.getName());
        return branch;
    }

    // == Product Mappers ==
    public static ProductDto toProductDto(Product product) {
        return ProductDto.builder()
                .id(product.getId().toHexString())
                .name(product.getName())
                .stock(product.getStock())
                .build();
    }

    public static Product toProductEntity(ProductDto dto) {
        var product = new Product();
        product.setId(new ObjectId(dto.getId()));
        product.setName(dto.getName());
        product.setStock(dto.getStock());
        return product;
    }
}
