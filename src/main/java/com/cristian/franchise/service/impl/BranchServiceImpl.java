package com.cristian.franchise.service.impl;

import com.cristian.franchise.dto.BranchDto;
import com.cristian.franchise.dto.UpdateBranchDto;
import com.cristian.franchise.mapper.EntityDtoMapper;
import com.cristian.franchise.model.Branch;
import com.cristian.franchise.repository.BranchRepository;
import com.cristian.franchise.repository.FranchiseRepository;
import com.cristian.franchise.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {

    private final FranchiseRepository franchiseRepository;
    private final BranchRepository branchRepository;

    @Override
    public Mono<BranchDto> createBranch(String franchiseId, Mono<BranchDto> branchDto) {
        ObjectId franchiseOid = new ObjectId(franchiseId);
        return franchiseRepository.findById(franchiseOid)
                .flatMap(franchise -> branchDto
                        .map(dto -> {
                            var branch = new Branch();
                            branch.setName(dto.getName());
                            branch.setFranchiseId(franchiseOid);
                            return branch;
                        })
                        .flatMap(branchRepository::save)
                        .map(EntityDtoMapper::toBranchDto)
                );
    }

    @Override
    public Mono<BranchDto> updateBranch(String branchId, Mono<UpdateBranchDto> branchDtoMono) {
        return branchRepository.findById(new ObjectId(branchId))
                .flatMap(branch ->
                        branchDtoMono.flatMap(branchDto -> {
                            branch.setName(branchDto.getName());
                            return branchRepository.save(branch);
                        })
                )
                .map(EntityDtoMapper::toBranchDto);
    }
}
