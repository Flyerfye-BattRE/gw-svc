package com.battre.gwsvc.service;

import com.battre.grpcifc.GrpcMethodInvoker;
import com.battre.stubs.services.AddCustomerRequest;
import com.battre.stubs.services.AddCustomerResponse;
import com.battre.stubs.services.Customer;
import com.battre.stubs.services.DestroyBatteryRequest;
import com.battre.stubs.services.DestroyBatteryResponse;
import com.battre.stubs.services.GetBatteryInventoryRequest;
import com.battre.stubs.services.GetBatteryInventoryResponse;
import com.battre.stubs.services.GetCustomerListRequest;
import com.battre.stubs.services.GetCustomerListResponse;
import com.battre.stubs.services.RemoveCustomerRequest;
import com.battre.stubs.services.RemoveCustomerResponse;
import com.battre.stubs.services.UpdateCustomerRequest;
import com.battre.stubs.services.UpdateCustomerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

import static com.battre.gwsvc.utils.GatewayGrpcUtils.processComplexGrpcResponse;
import static com.battre.gwsvc.utils.GatewayGrpcUtils.processSimpleGrpcResponse;

/**
 * Contains gRPC functionality required for invoking Ops Service methods.
 *
 */
@Service
public class OpsSvcGrpcInvoker {
    private static final Logger logger = Logger.getLogger(OpsSvcGrpcInvoker.class.getName());

    private final GrpcMethodInvoker grpcMethodInvoker;
    private final String serviceName = "opssvc";

    @Autowired
    public OpsSvcGrpcInvoker(GrpcMethodInvoker grpcMethodInvoker) {
        this.grpcMethodInvoker = grpcMethodInvoker;
    }

    public String getCurrentBatteryInventory() {
        GetBatteryInventoryRequest request = GetBatteryInventoryRequest.newBuilder().build();
        GetBatteryInventoryResponse response = grpcMethodInvoker.invokeNonblock(serviceName, "getCurrentBatteryInventory", request);

        return processComplexGrpcResponse(response, "Could not get Tester Maintenance Logs.");
    }

    public String getBatteryInventory() {
        GetBatteryInventoryRequest request = GetBatteryInventoryRequest.newBuilder().build();
        GetBatteryInventoryResponse response = grpcMethodInvoker.invokeNonblock(serviceName, "getBatteryInventory", request);

        return processComplexGrpcResponse(response, "Could not get battery tiers.");
    }

    public String destroyBattery(int batteryId) {
        DestroyBatteryRequest request = DestroyBatteryRequest.newBuilder().setBatteryId(batteryId).build();
        DestroyBatteryResponse response = grpcMethodInvoker.invokeNonblock(serviceName, "destroyBattery", request);

        return processSimpleGrpcResponse(response, "Could not destroy battery.");
    }

    public String getCustomerList() {
        GetCustomerListRequest request = GetCustomerListRequest.newBuilder().build();
        GetCustomerListResponse response = grpcMethodInvoker.invokeNonblock(serviceName, "getCustomerList", request);

        return processComplexGrpcResponse(response, "Could not get Customer List.");
    }

    public String addCustomer(
            String lastName,
            String firstName,
            String email,
            String phone,
            String address
    ) {
        // not possible to specify customerId or loyaltyId when creating a new customer
        // these values are automatically randomly generated
        Customer customer = Customer.newBuilder()
                .setLastName(lastName)
                .setFirstName(firstName)
                .setEmail(email)
                .setPhone(phone)
                .setAddress(address)
                .build();
        AddCustomerRequest request = AddCustomerRequest.newBuilder().setCustomer(customer).build();
        AddCustomerResponse response = grpcMethodInvoker.invokeNonblock(serviceName, "addCustomer", request);

        return processSimpleGrpcResponse(response, "Could not Add Customer.");
    }

    public String removeCustomer(int customerId) {
        RemoveCustomerRequest request = RemoveCustomerRequest.newBuilder().setCustomerId(customerId).build();
        RemoveCustomerResponse response = grpcMethodInvoker.invokeNonblock(serviceName, "removeCustomer", request);

        return processSimpleGrpcResponse(response, "Could not remove Customer.");
    }

    public String updateCustomer(
            int customerId,
            String lastName,
            String firstName,
            String email,
            String phone,
            String address,
            String loyaltyId
    ) {
        // TODO: Ensure loyaltyId is being checked as a valid UUID
        Customer customer = Customer.newBuilder()
                .setCustomerId(customerId)
                .setLastName(lastName)
                .setFirstName(firstName)
                .setEmail(email)
                .setPhone(phone)
                .setAddress(address)
                .setLoyaltyId(loyaltyId)
                .build();
        UpdateCustomerRequest request = UpdateCustomerRequest.newBuilder().setCustomer(customer).build();
        UpdateCustomerResponse response = grpcMethodInvoker.invokeNonblock(serviceName, "updateCustomer", request);

        return processSimpleGrpcResponse(response, "Could not Update Customer.");
    }
}
