package com.cristian.franchise.service.impl;

import com.cristian.franchise.dto.ProductDto;
import com.cristian.franchise.dto.UpdateProductNameDto;
import com.cristian.franchise.exception.BranchDoesNotBelongToFranchiseException;
import com.cristian.franchise.exception.ProductNotFoundException;
import com.cristian.franchise.mapper.EntityDtoMapper;
import com.cristian.franchise.model.Branch;
import com.cristian.franchise.model.Franchise;
import com.cristian.franchise.model.Product;
import com.cristian.franchise.repository.BranchRepository;
import com.cristian.franchise.repository.FranchiseRepository;
import com.cristian.franchise.repository.ProductRepository;
import com.cristian.franchise.service.ProductService;

import com.cristian.franchise.utils.IdConverterUtil;
import com.cristian.franchise.validators.FranchiseBranchValidator;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final FranchiseRepository franchiseRepository;
    private final BranchRepository branchRepository;
    private final ProductRepository productRepository;

    private final FranchiseBranchValidator franchiseBranchValidator;

    @Override
    public Mono<ProductDto> createProduct(String franchiseId, String branchId, Mono<ProductDto> productDtoMono) {

        IdConverterUtil.FranchiseBranchIds ids = IdConverterUtil.toFranchiseBranchIds(franchiseId, branchId);

        return franchiseBranchValidator.checkIfFranchiseAndBranchExist(ids.franchiseId(), ids.branchId())
                .then(Mono.zip(
                        franchiseRepository.findById(ids.franchiseId()).switchIfEmpty(Mono.empty()),
                        branchRepository.findById(ids.branchId()).switchIfEmpty(Mono.empty()),
                        productDtoMono
                ).flatMap(tuple -> {
                    Franchise franchise = tuple.getT1();
                    Branch branch = tuple.getT2();
                    ProductDto productDto = tuple.getT3();

                    if (!branch.getFranchiseId().equals(franchise.getId())) {
                        return Mono.error(new BranchDoesNotBelongToFranchiseException("Branch does not belong to the specified franchise"));
                    }

                    Product newProduct = Product.builder()
                            .name(productDto.getName())
                            .stock(productDto.getStock())
                            .branchId(branch.getId())
                            .build();

                    return productRepository.save(newProduct)
                            .map(EntityDtoMapper::toProductDto);
                }));
    }

    @Override
    public Mono<Void> deleteProduct(String franchiseId, String branchId, String productId) {

        IdConverterUtil.FranchiseBranchProductIds ids = IdConverterUtil.toFranchiseBranchProductIds(franchiseId, branchId, productId);

        return franchiseBranchValidator.checkIfFranchiseAndBranchExist(ids.franchiseId(), ids.branchId())
                .then(productRepository.findById(ids.productId())
                        .flatMap(productRepository::delete)
                        .then());
    }

    @Override
    public Mono<ProductDto> updateProductName(String franchiseId, String branchId, String productId, UpdateProductNameDto dto) {

        IdConverterUtil.FranchiseBranchProductIds ids = IdConverterUtil.toFranchiseBranchProductIds(franchiseId, branchId, productId);

        return franchiseBranchValidator.checkIfFranchiseAndBranchExist(ids.franchiseId(), ids.branchId())
                .then(productRepository.findById(ids.productId())
                        .switchIfEmpty(Mono.error(new ProductNotFoundException("Product not found")))
                        .flatMap(product -> {
                            product.setName(dto.getName());
                            return productRepository.save(product);
                        })
                        .map(EntityDtoMapper::toProductDto));
    }
}
