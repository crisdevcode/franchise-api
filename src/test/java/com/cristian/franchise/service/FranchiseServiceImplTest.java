package com.cristian.franchise.service;

import com.cristian.franchise.dto.FranchiseDto;
import com.cristian.franchise.dto.UpdateFranchiseDto;
import com.cristian.franchise.model.Franchise;
import com.cristian.franchise.repository.FranchiseRepository;
import com.cristian.franchise.service.impl.FranchiseServiceImpl;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FranchiseServiceImplTest {

    @InjectMocks
    private FranchiseServiceImpl franchiseService;

    @Mock
    private FranchiseRepository franchiseRepository;

    private Franchise franchise;
    private String franchiseId;
    private FranchiseDto franchiseDto;
    private UpdateFranchiseDto updateFranchiseDto;

    @BeforeEach
    void setUp() {
        franchiseId = "663c1b6e1a1e8b378f7f53c2";

        franchiseDto = FranchiseDto.builder()
                .name("honda")
                .build();

        franchise = new Franchise();
        franchise.setId(new ObjectId(franchiseId));
        franchise.setName("honda");

        updateFranchiseDto = UpdateFranchiseDto.builder()
                .name("honda colombia")
                .build();
    }

    @Test
    @DisplayName("should create a franchise")
    void shouldCreateFranchise() {
        when(franchiseRepository.save(any(Franchise.class))).thenReturn(Mono.just(franchise));

        Mono<FranchiseDto> result = franchiseService.createFranchise(Mono.just(franchiseDto));

        StepVerifier.create(result)
                .expectNextMatches(saved -> saved.getName().equals("honda") && saved.getId() != null)
                .verifyComplete();

        verify(franchiseRepository).save(any(Franchise.class));
    }

    @Test
    @DisplayName("should update the name of a franchise")
    void shouldUpdateFranchise() {
        ObjectId franchiseOid = new ObjectId(franchiseId);
        when(franchiseRepository.findById(franchiseOid)).thenReturn(Mono.just(franchise));
        when(franchiseRepository.save(any(Franchise.class))).thenReturn(Mono.just(franchise));

        Mono<FranchiseDto> result = franchiseService.updateFranchise(franchiseId, Mono.just(updateFranchiseDto));

        StepVerifier.create(result)
                .expectNextMatches(updated -> updated.getName().equals("honda colombia") && updated.getId().equals(franchiseId))
                .verifyComplete();

        verify(franchiseRepository).findById(franchiseOid);
        verify(franchiseRepository).save(any(Franchise.class));
    }

}

