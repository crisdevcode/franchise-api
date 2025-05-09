package com.cristian.franchise.service;

import com.cristian.franchise.dto.BranchDto;
import com.cristian.franchise.dto.UpdateBranchDto;
import reactor.core.publisher.Mono;

public interface BranchService {
    Mono<BranchDto> createBranch(String franchiseId, Mono<BranchDto> branchDto);
    Mono<BranchDto> updateBranch(String branchId, Mono<UpdateBranchDto> branchDto);
}
