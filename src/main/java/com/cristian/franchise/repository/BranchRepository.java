package com.cristian.franchise.repository;

import com.cristian.franchise.model.Branch;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchRepository extends ReactiveMongoRepository<Branch, ObjectId> {
}
