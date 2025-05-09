package com.cristian.franchise.repository;

import com.cristian.franchise.model.Franchise;
import org.bson.types.ObjectId;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FranchiseRepository extends ReactiveCrudRepository<Franchise, ObjectId> {
}
