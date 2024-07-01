package com.battre.gwsvc.service;

import com.battre.grpcifc.GrpcMethodInvoker;
import com.battre.stubs.services.GetAllBatterySpecsRequest;
import com.battre.stubs.services.GetAllBatterySpecsResponse;
import com.battre.stubs.services.GetBatteryTiersRequest;
import com.battre.stubs.services.GetBatteryTiersResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

import static com.battre.gwsvc.utils.GatewayGrpcUtils.processComplexGrpcResponse;

/** Contains gRPC functionality required for invoking Spec Service methods. */
@Service
public class SpecSvcGrpcInvoker {
  private static final Logger logger = Logger.getLogger(SpecSvcGrpcInvoker.class.getName());
  private final GrpcMethodInvoker grpcMethodInvoker;
  private final String serviceName = "specsvc";

  @Autowired
  public SpecSvcGrpcInvoker(GrpcMethodInvoker grpcMethodInvoker) {
    this.grpcMethodInvoker = grpcMethodInvoker;
  }

  public String getAllBatterySpecs() {
    GetAllBatterySpecsRequest request = GetAllBatterySpecsRequest.newBuilder().build();
    GetAllBatterySpecsResponse response =
        grpcMethodInvoker.invokeNonblock(serviceName, "getAllBatterySpecs", request);

    return processComplexGrpcResponse(response, "Could not get All Battery Specs.");
  }

  public String getBatteryTiers() {
    GetBatteryTiersRequest request = GetBatteryTiersRequest.newBuilder().build();
    GetBatteryTiersResponse response =
        grpcMethodInvoker.invokeNonblock(serviceName, "getBatteryTiers", request);

    return processComplexGrpcResponse(response, "Could not get Battery Tiers.");
  }
}
