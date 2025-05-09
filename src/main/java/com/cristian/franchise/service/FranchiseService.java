package com.cristian.franchise.service;

import com.cristian.franchise.dto.FranchiseDto;
import com.cristian.franchise.dto.UpdateFranchiseDto;
import reactor.core.publisher.Mono;

public interface FranchiseService {
    Mono<FranchiseDto> createFranchise(Mono<FranchiseDto> franchiseDtoMono);
    Mono<FranchiseDto> updateFranchise(String franchiseId, Mono<UpdateFranchiseDto> franchiseDtoMono);
}
