package com.cristian.franchise.handler;

import com.cristian.franchise.dto.FranchiseDto;
import com.cristian.franchise.dto.UpdateFranchiseDto;
import com.cristian.franchise.service.FranchiseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@ExtendWith(MockitoExtension.class)
public class FranchiseHandlerTest {

    @InjectMocks
    private FranchiseHandler franchiseHandler;

    @Mock
    private FranchiseService franchiseService;

    @Mock
    private ServerRequest serverRequest;

    private FranchiseDto franchiseDto;
    private UpdateFranchiseDto updateFranchiseDto;
    private String franchiseId;

    @BeforeEach
    void setUp() {
        franchiseId = "663c1b6e1a1e8b378f7f53c2";

        franchiseDto = FranchiseDto.builder()
                .name("honda")
                .build();

        updateFranchiseDto = UpdateFranchiseDto.builder()
                .name("honda colombia")
                .build();
    }

    @Test
    @DisplayName("should create a franchise and return ServerResponse")
    void shouldCreateFranchise() {
        when(serverRequest.bodyToMono(FranchiseDto.class)).thenReturn(Mono.just(franchiseDto));
        when(franchiseService.createFranchise(any(Mono.class))).thenReturn(Mono.just(franchiseDto));

        Mono<ServerResponse> response = franchiseHandler.createFranchise(serverRequest);

        StepVerifier.create(response)
                .consumeNextWith(serverResponse -> {
                    assertEquals(ok().build().block().statusCode(), serverResponse.statusCode());
                })
                .verifyComplete();

        verify(franchiseService).createFranchise(any(Mono.class));
    }

    @Test
    @DisplayName("should update a franchise and return ServerResponse")
    void shouldUpdateFranchise() {
        when(serverRequest.pathVariable("franchiseId")).thenReturn(franchiseId);
        when(serverRequest.bodyToMono(UpdateFranchiseDto.class)).thenReturn(Mono.just(updateFranchiseDto));
        when(franchiseService.updateFranchise(eq(franchiseId), any(Mono.class))).thenReturn(Mono.just(franchiseDto));

        Mono<ServerResponse> response = franchiseHandler.updateFranchise(serverRequest);

        StepVerifier.create(response)
                .consumeNextWith(serverResponse -> {
                    assertEquals(ok().build().block().statusCode(), serverResponse.statusCode());
                })
                .verifyComplete();

        verify(franchiseService).updateFranchise(eq(franchiseId), any(Mono.class));
    }
}
