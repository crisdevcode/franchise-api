package com.cristian.franchise.validators;

import com.cristian.franchise.exception.FranchiseOrBranchNotFoundException;

import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class FranchiseBranchValidator {

    private final ReactiveMongoTemplate reactiveMongoTemplate;

    public Mono<Void> checkIfFranchiseAndBranchExist(ObjectId franchiseId, ObjectId branchId) {
        return Mono.zip(
                existsFranchise(franchiseId),
                existsBranch(branchId)
        ).flatMap(tuple -> {
            boolean franchiseExists = tuple.getT1();
            boolean branchExists = tuple.getT2();

            if (!franchiseExists || !branchExists) {
                return Mono.error(new FranchiseOrBranchNotFoundException("Franchise or branch not found."));
            }
            return Mono.empty();
        });
    }

    private Mono<Boolean> existsFranchise(ObjectId franchiseId) {
        MatchOperation match = Aggregation.match(Criteria.where("_id").is(franchiseId));
        Aggregation aggregation = Aggregation.newAggregation(match);

        return reactiveMongoTemplate.aggregate(aggregation, "franchises", Object.class)
                .count()
                .map(count -> count > 0)
                .defaultIfEmpty(false);
    }

    private Mono<Boolean> existsBranch(ObjectId branchId) {
        MatchOperation match = Aggregation.match(Criteria.where("_id").is(branchId));
        Aggregation aggregation = Aggregation.newAggregation(match);

        return reactiveMongoTemplate.aggregate(aggregation, "branches", Object.class)
                .count()
                .map(count -> count > 0)
                .defaultIfEmpty(false);
    }
}
