package com.battre.gwsvc.service;

import com.battre.grpcifc.GrpcMethodInvoker;
import com.battre.stubs.services.ChangeBatteryRefurbPriorityRequest;
import com.battre.stubs.services.ChangeBatteryRefurbPriorityResponse;
import com.battre.stubs.services.ChangeBatteryTesterPriorityRequest;
import com.battre.stubs.services.ChangeBatteryTesterPriorityResponse;
import com.battre.stubs.services.GetLabPlansRequest;
import com.battre.stubs.services.GetLabPlansResponse;
import com.battre.stubs.services.GetRefurbMaintenanceLogsRequest;
import com.battre.stubs.services.GetRefurbMaintenanceLogsResponse;
import com.battre.stubs.services.GetRefurbPlansRequest;
import com.battre.stubs.services.GetRefurbPlansResponse;
import com.battre.stubs.services.GetTesterBacklogRequest;
import com.battre.stubs.services.GetTesterBacklogResponse;
import com.battre.stubs.services.GetTesterMaintenanceLogsRequest;
import com.battre.stubs.services.GetTesterMaintenanceLogsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

import static com.battre.gwsvc.utils.GatewayGrpcUtils.processComplexGrpcResponse;
import static com.battre.gwsvc.utils.GatewayGrpcUtils.processSimpleGrpcResponse;

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

    public String getTesterMaintenanceLogs() {
        GetTesterMaintenanceLogsRequest request = GetTesterMaintenanceLogsRequest.newBuilder().build();
        GetTesterMaintenanceLogsResponse response = grpcMethodInvoker.invokeNonblock(serviceName, "getTesterMaintenanceLogs", request);

        return processComplexGrpcResponse(response, "Could not get Tester Maintenance Logs.");
    }

    public String getRefurbMaintenanceLogs() {
        GetRefurbMaintenanceLogsRequest request = GetRefurbMaintenanceLogsRequest.newBuilder().build();
        GetRefurbMaintenanceLogsResponse response = grpcMethodInvoker.invokeNonblock(serviceName, "getRefurbMaintenanceLogs", request);

        return processComplexGrpcResponse(response, "Could not get Refurb Maintenance Logs.");
    }
}
