package com.battre.gwsvc.service;

import static com.battre.gwsvc.utils.GatewayGrpcUtils.processComplexGrpcResponse;
import static com.battre.gwsvc.utils.GatewayGrpcUtils.processSimpleGrpcResponse;

import com.battre.grpcifc.GrpcMethodInvoker;
import com.battre.stubs.services.ChangeBatteryRefurbPriorityRequest;
import com.battre.stubs.services.ChangeBatteryRefurbPriorityResponse;
import com.battre.stubs.services.ChangeBatteryTesterPriorityRequest;
import com.battre.stubs.services.ChangeBatteryTesterPriorityResponse;
import com.battre.stubs.services.GetLabPlanStatusCountsRequest;
import com.battre.stubs.services.GetLabPlanStatusCountsResponse;
import com.battre.stubs.services.GetLabPlansRequest;
import com.battre.stubs.services.GetLabPlansResponse;
import com.battre.stubs.services.GetOpsSvcOverviewRequest;
import com.battre.stubs.services.GetOpsSvcOverviewResponse;
import com.battre.stubs.services.GetRefurbPlansRequest;
import com.battre.stubs.services.GetRefurbPlansResponse;
import com.battre.stubs.services.GetRefurbStnInfoRequest;
import com.battre.stubs.services.GetRefurbStnInfoResponse;
import com.battre.stubs.services.GetTesterBacklogRequest;
import com.battre.stubs.services.GetTesterBacklogResponse;
import com.battre.stubs.services.GetTesterStnInfoRequest;
import com.battre.stubs.services.GetTesterStnInfoResponse;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Contains gRPC functionality required for invoking Lab Service methods.
 *
 */
@Service
public class LabSvcGrpcInvoker {
    private static final Logger logger = Logger.getLogger(LabSvcGrpcInvoker.class.getName());
    private final GrpcMethodInvoker grpcMethodInvoker;
    private final String serviceName = "labsvc";

    @Autowired
    public LabSvcGrpcInvoker(GrpcMethodInvoker grpcMethodInvoker) {
        this.grpcMethodInvoker = grpcMethodInvoker;
    }

    public String getLabPlans() {
        GetLabPlansRequest request = GetLabPlansRequest.newBuilder().build();
        GetLabPlansResponse response = grpcMethodInvoker.invokeNonblock(serviceName, "getLabPlans", request);

        return processComplexGrpcResponse(response, "Could not get Lab Plans.");
    }

    public String getCurrentLabPlans() {
        GetLabPlansRequest request = GetLabPlansRequest.newBuilder().build();
        GetLabPlansResponse response = grpcMethodInvoker.invokeNonblock(serviceName, "getCurrentLabPlans", request);

        return processComplexGrpcResponse(response, "Could not get Current Lab Plans.");
    }

    public String getCurrentTesterBacklog() {
        GetTesterBacklogRequest request = GetTesterBacklogRequest.newBuilder().build();
        GetTesterBacklogResponse response = grpcMethodInvoker.invokeNonblock(serviceName, "getCurrentTesterBacklog", request);

        return processComplexGrpcResponse(response, "Could not get Current Tester Backlog.");
    }

    public String getTesterBacklog() {
        GetTesterBacklogRequest request = GetTesterBacklogRequest.newBuilder().build();
        GetTesterBacklogResponse response = grpcMethodInvoker.invokeNonblock(serviceName, "getTesterBacklog", request);

        return processComplexGrpcResponse(response, "Could not get Tester Backlog.");
    }

    public String getCurrentRefurbPlans() {
        GetRefurbPlansRequest request = GetRefurbPlansRequest.newBuilder().build();
        GetRefurbPlansResponse response = grpcMethodInvoker.invokeNonblock(serviceName, "getCurrentRefurbPlans", request);

        return processComplexGrpcResponse(response, "Could not get Current Refurb Plans.");
    }

    public String getRefurbPlans() {
        GetRefurbPlansRequest request = GetRefurbPlansRequest.newBuilder().build();
        GetRefurbPlansResponse response = grpcMethodInvoker.invokeNonblock(serviceName, "getRefurbPlans", request);

        return processComplexGrpcResponse(response, "Could not get Refurb Plans.");
    }

    public String changeBatteryTesterPriority(int batteryId, int priority) {
        ChangeBatteryTesterPriorityRequest request = ChangeBatteryTesterPriorityRequest.newBuilder()
                .setBatteryId(batteryId)
                .setPriority(priority)
                .build();
        ChangeBatteryTesterPriorityResponse response = grpcMethodInvoker.invokeNonblock(serviceName, "changeBatteryTesterPriority", request);

        return processSimpleGrpcResponse(response, "Could not change Battery Refurb Priority.");
    }

    public String changeBatteryRefurbPriority(int batteryId, int priority) {
        ChangeBatteryRefurbPriorityRequest request = ChangeBatteryRefurbPriorityRequest.newBuilder()
                .setBatteryId(batteryId)
                .setPriority(priority)
                .build();
        ChangeBatteryRefurbPriorityResponse response = grpcMethodInvoker.invokeNonblock(serviceName, "changeBatteryRefurbPriority", request);

        return processSimpleGrpcResponse(response, "Could not change Battery Refurb Priority.");
    }

    public String getTesterStnInfo() {
        GetTesterStnInfoRequest request = GetTesterStnInfoRequest.newBuilder().build();
        GetTesterStnInfoResponse response = grpcMethodInvoker.invokeNonblock(serviceName, "getTesterStnInfo", request);

        return processComplexGrpcResponse(response, "Could not get Tester Maintenance Logs.");
    }

    public String getRefurbStnInfo() {
        GetRefurbStnInfoRequest request = GetRefurbStnInfoRequest.newBuilder().build();
        GetRefurbStnInfoResponse response = grpcMethodInvoker.invokeNonblock(serviceName, "getRefurbStnInfo", request);

        return processComplexGrpcResponse(response, "Could not get Refurb Maintenance Logs.");
    }

    public String getLabPlanStatusCounts() {
        GetLabPlanStatusCountsRequest request = GetLabPlanStatusCountsRequest.newBuilder().build();
        GetLabPlanStatusCountsResponse response = grpcMethodInvoker.invokeNonblock(serviceName, "getLabPlanStatusCounts", request);

        return processComplexGrpcResponse(response, "Could not get lab plan status counts.");
    }
}
