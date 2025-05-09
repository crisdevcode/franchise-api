package com.cristian.franchise.service.impl;

import com.cristian.franchise.dto.FranchiseDto;
import com.cristian.franchise.dto.UpdateFranchiseDto;
import com.cristian.franchise.exception.FranchiseNotFoundException;
import com.cristian.franchise.mapper.EntityDtoMapper;
import com.cristian.franchise.repository.FranchiseRepository;
import com.cristian.franchise.service.FranchiseService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class FranchiseServiceImpl implements FranchiseService {

    private final FranchiseRepository repository;

    @Override
    public Mono<FranchiseDto> createFranchise(Mono<FranchiseDto> franchiseDtoMono) {
        return franchiseDtoMono
                .map(EntityDtoMapper::toEntity)
                .flatMap(repository::save)
                .map(EntityDtoMapper::toDto);
    }

    @Override
    public Mono<FranchiseDto> updateFranchise(String franchiseId, Mono<UpdateFranchiseDto> franchiseDtoMono) {
        return repository.findById(new ObjectId(franchiseId))
                .switchIfEmpty(Mono.error(new FranchiseNotFoundException("Franchise with id: " + franchiseId + " not found")))
                .flatMap(franchise ->
                        franchiseDtoMono.flatMap(dto -> {
                            franchise.setName(dto.getName());
                            return repository.save(franchise);
                        })
                )
                .map(EntityDtoMapper::toDto);
    }

}
