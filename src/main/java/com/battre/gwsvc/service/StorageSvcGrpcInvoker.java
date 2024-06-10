package com.battre.gwsvc.service;

import com.battre.grpcifc.GrpcMethodInvoker;
import com.battre.stubs.services.GetStorageStatsRequest;
import com.battre.stubs.services.GetStorageStatsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

import static com.battre.gwsvc.utils.GatewayGrpcUtils.processComplexGrpcResponse;

/**
 * Contains gRPC functionality required for invoking Storage Service methods.
 *
 */
@Service
public class StorageSvcGrpcInvoker {
    private static final Logger logger = Logger.getLogger(StorageSvcGrpcInvoker.class.getName());
    private final GrpcMethodInvoker grpcMethodInvoker;
    private final String serviceName = "storagesvc";

    @Autowired
    public StorageSvcGrpcInvoker(GrpcMethodInvoker grpcMethodInvoker) {
        this.grpcMethodInvoker = grpcMethodInvoker;
    }

    public String getStorageStats() {
        GetStorageStatsRequest request = GetStorageStatsRequest.newBuilder().build();
        GetStorageStatsResponse response = grpcMethodInvoker.invokeNonblock(serviceName, "getStorageStats", request);

        return processComplexGrpcResponse(response, "Could not get Storage Stats.");
    }
}
