package com.battre.gwsvc.service;

import com.battre.grpcifc.GrpcMethodInvoker;
import com.battre.stubs.services.GenerateIntakeBatteryOrderRequest;
import com.battre.stubs.services.GenerateIntakeBatteryOrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

import static com.battre.gwsvc.utils.GatewayGrpcUtils.processComplexGrpcResponse;
import static com.battre.gwsvc.utils.GatewayGrpcUtils.processSimpleGrpcResponse;

/** Contains gRPC functionality required for invoking Triage Service methods. */
@Service
public class TriageSvcGrpcInvoker {
  private static final Logger logger = Logger.getLogger(TriageSvcGrpcInvoker.class.getName());
  private final GrpcMethodInvoker grpcMethodInvoker;
  private final String serviceName = "triagesvc";

  @Autowired
  public TriageSvcGrpcInvoker(GrpcMethodInvoker grpcMethodInvoker) {
    this.grpcMethodInvoker = grpcMethodInvoker;
  }

  public String generateIntakeBatteryOrder() {
    GenerateIntakeBatteryOrderRequest request =
        GenerateIntakeBatteryOrderRequest.newBuilder().build();
    GenerateIntakeBatteryOrderResponse response =
        grpcMethodInvoker.invokeNonblock(serviceName, "generateIntakeBatteryOrder", request);

    return processComplexGrpcResponse(response, "Could not generate Intake Battery Order.");
  }
}
