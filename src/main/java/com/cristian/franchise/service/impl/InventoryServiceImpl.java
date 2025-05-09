package com.cristian.franchise.service.impl;

import com.cristian.franchise.dto.ProductDto;
import com.cristian.franchise.dto.UpdateProductStockDto;
import com.cristian.franchise.dto.response.MaxStockResponse;
import com.cristian.franchise.exception.ProductNotFoundException;
import com.cristian.franchise.mapper.EntityDtoMapper;
import com.cristian.franchise.repository.ProductRepository;
import com.cristian.franchise.service.InventoryService;

import com.cristian.franchise.utils.IdConverterUtil;
import com.cristian.franchise.validators.FranchiseBranchValidator;
import com.mongodb.BasicDBObject;
import lombok.RequiredArgsConstructor;
import org.bson.Document;

import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final FranchiseBranchValidator franchiseBranchValidator;
    private final ProductRepository productRepository;

    private final ReactiveMongoTemplate reactiveMongoTemplate;

    @Override
    public Mono<ProductDto> updateProductStock(String franchiseId, String branchId, String productId, UpdateProductStockDto dto) {

        IdConverterUtil.FranchiseBranchProductIds ids = IdConverterUtil.toFranchiseBranchProductIds(franchiseId, branchId, productId);

        return franchiseBranchValidator.checkIfFranchiseAndBranchExist(ids.franchiseId(), ids.branchId())
                .then(productRepository.findById(ids.productId()))
                .switchIfEmpty(Mono.error(new ProductNotFoundException("Product not found")))
                .flatMap(product -> {
                    product.setStock(dto.getStock());
                    return productRepository.save(product);
                })
                .map(EntityDtoMapper::toProductDto);

    }

    @Override
    public Mono<MaxStockResponse> findMaxStockProductsByFranchiseName(String franchiseName) {

        // => Build Aggregation

        MatchOperation matchFranchise = Aggregation.match(Criteria.where("name").is(franchiseName));

        LookupOperation lookupBranches = LookupOperation.newLookup()
                .from("branches")
                .localField("_id")
                .foreignField("franchiseId")
                .as("branches");

        UnwindOperation unwindBranches = Aggregation.unwind("branches");

        LookupOperation lookupProducts = LookupOperation.newLookup()
                .from("products")
                .localField("branches._id")
                .foreignField("branchId")
                .as("branch_products");

        // Add top product with the highest stock per branch
        AggregationOperation addTopProduct = context -> new Document("$addFields",
                new Document("top_product",
                        new Document("$cond", Arrays.asList(
                                new Document("$gt", Arrays.asList(new Document("$size", "$branch_products"), 0)),
                                new Document("$first",
                                        new Document("$sortArray",
                                                new Document("input", "$branch_products")
                                                        .append("sortBy", new Document("stock", -1))
                                        )
                                ),
                                null
                        ))
                )
        );

        MatchOperation matchTopProductNotNull = Aggregation.match(Criteria.where("top_product").ne(null));

        ProjectionOperation projectBranchesOnlyName = Aggregation.project()
                .and("name").as("name")
                .and("branches.name").as("branchName")
                .and("top_product").as("top_product");

        GroupOperation groupByFranchise = Aggregation.group("_id")
                .first("name").as("franchiseName")
                .push(new BasicDBObject()
                        .append("productName", "$top_product.name")
                        .append("stock", "$top_product.stock")
                        .append("branch", new BasicDBObject()
                                .append("name", "$branchName")
                        )
                ).as("productsHigherStock");

        ProjectionOperation projectFinal = Aggregation.project()
                .andExclude("_id")
                .andInclude("franchiseName", "productsHigherStock");

        Aggregation aggregation = Aggregation.newAggregation(
                matchFranchise,
                lookupBranches,
                unwindBranches,
                lookupProducts,
                addTopProduct,
                matchTopProductNotNull,
                projectBranchesOnlyName,
                groupByFranchise,
                projectFinal
        );

        return reactiveMongoTemplate.aggregate(aggregation, "franchises", MaxStockResponse.class)
                .singleOrEmpty();
    }
}

