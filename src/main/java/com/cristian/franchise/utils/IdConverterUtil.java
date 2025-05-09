package com.cristian.franchise.utils;

import org.bson.types.ObjectId;

public class IdConverterUtil {

    // Method to convert franchiseId and branchId to ObjectIds
    public static FranchiseBranchIds toFranchiseBranchIds(String franchiseId, String branchId) {
        return new FranchiseBranchIds(new ObjectId(franchiseId), new ObjectId(branchId));
    }

    // Method to convert franchiseId, branchId and productId to ObjectIds
    public static FranchiseBranchProductIds toFranchiseBranchProductIds(String franchiseId, String branchId, String productId) {
        return new FranchiseBranchProductIds(new ObjectId(franchiseId), new ObjectId(branchId), new ObjectId(productId));
    }

    // record for 2 IDs
    public static record FranchiseBranchIds(ObjectId franchiseId, ObjectId branchId) {}

    // record for 3 IDs
    public static record FranchiseBranchProductIds(ObjectId franchiseId, ObjectId branchId, ObjectId productId) {}
}
