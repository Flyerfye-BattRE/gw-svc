package com.battre.gwsvc.config;

import com.battre.gwsvc.service.OpsSvcGrpcInvoker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import java.util.logging.Logger;

import static com.battre.gwsvc.utils.GatewayGrpcUtils.processHeadersGrpcRequest;
import static com.battre.gwsvc.utils.GatewayGrpcUtils.processMissingHeaderResponse;
import static com.battre.gwsvc.utils.GatewayGrpcUtils.processNoInputGrpcRequest;

/**
 * Implements the client-facing gateway routes for the Ops Service.
 *
 * <p>Note: Because incoming REST request are converted to gRPC requests in the filter, no forwarding
 * address is specified in the uri.</p>
 *
 */
@Configuration
public class OpsSvcGatewayConfig {
    private static final Logger logger = Logger.getLogger(OpsSvcGatewayConfig.class.getName());
    private final OpsSvcGrpcInvoker grpcMethodInvoker;

    @Autowired
    public OpsSvcGatewayConfig(OpsSvcGrpcInvoker grpcMethodInvoker) {
        this.grpcMethodInvoker = grpcMethodInvoker;
    }

    @Bean
    public RouteLocator opsSvcRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // getCurrentBatteryInventory
                .route("opssvc_getCurrentBatteryInventory", r -> r.path("/ops/getCurrentBatteryInventory")
                        .and()
                        .method(HttpMethod.GET)
                        .filters(f -> f.filter((exchange, chain) -> {
                            return processNoInputGrpcRequest(exchange, chain, grpcMethodInvoker::getCurrentBatteryInventory);
                        }))
                        .uri("no://op"))

                // getBatteryInventory
                .route("opssvc_getBatteryInventory", r -> r.path("/ops/getBatteryInventory")
                        .and()
                        .method(HttpMethod.GET)
                        .filters(f -> f.filter((exchange, chain) -> {
                            return processNoInputGrpcRequest(exchange, chain, grpcMethodInvoker::getBatteryInventory);
                        }))
                        .uri("no://op"))

                // destroyBattery
                .route("opssvc_destroyBattery", r -> r.path("/ops/destroyBattery")
                        .and()
                        .method(HttpMethod.DELETE)
                        .filters(f -> f.filter((exchange, chain) -> {
                            return processHeadersGrpcRequest(
                                    exchange,
                                    chain,
                                    (headers) -> {
                                        String batteryIdStr = headers.getFirst("batteryId");

                                        if (batteryIdStr == null) {
                                            return processMissingHeaderResponse(exchange);
                                        }

                                        int batteryId = Integer.parseInt(batteryIdStr);

                                        return grpcMethodInvoker.destroyBattery(batteryId);
                                    }
                            );
                        }))
                        .uri("no://op"))

                // getCustomerList
                .route("opssvc_getCustomerList", r -> r.path("/ops/getCustomerList")
                        .and()
                        .method(HttpMethod.GET)
                        .filters(f -> f.filter((exchange, chain) -> {
                            return processNoInputGrpcRequest(exchange, chain, grpcMethodInvoker::getCustomerList);
                        }))
                        .uri("no://op"))

                // addCustomer
                .route("opssvc_addCustomer", r -> r.path("/ops/addCustomer")
                        .and()
                        .method(HttpMethod.POST)
                        .filters(f -> f.filter((exchange, chain) -> {
                            return processHeadersGrpcRequest(
                                    exchange,
                                    chain,
                                    (headers) -> {
                                        String lastName = headers.getFirst("lastName");
                                        String firstName = headers.getFirst("firstName");
                                        String email = headers.getFirst("email");
                                        String phone = headers.getFirst("phone");
                                        String address = headers.getFirst("address");

                                        if (
                                                lastName == null ||
                                                        firstName == null ||
                                                        email == null ||
                                                        phone == null ||
                                                        address == null
                                        ) {
                                            return processMissingHeaderResponse(exchange);
                                        }

                                        return grpcMethodInvoker.addCustomer(
                                                lastName,
                                                firstName,
                                                email,
                                                phone,
                                                address
                                        );
                                    }
                            );
                        }))
                        .uri("no://op"))

                // removeCustomer
                .route("opssvc_removeCustomer", r -> r.path("/ops/removeCustomer")
                        .and()
                        .method(HttpMethod.DELETE)
                        .filters(f -> f.filter((exchange, chain) -> {
                            return processHeadersGrpcRequest(
                                    exchange,
                                    chain,
                                    (headers) -> {
                                        String customerIdStr = headers.getFirst("customerId");

                                        if (customerIdStr == null) {
                                            return processMissingHeaderResponse(exchange);
                                        }

                                        int customerId = Integer.parseInt(customerIdStr);

                                        return grpcMethodInvoker.removeCustomer(customerId);
                                    }
                            );
                        }))
                        .uri("no://op"))

                // updateCustomer
                .route("opssvc_updateCustomer", r -> r.path("/ops/updateCustomer")
                        .and()
                        .method(HttpMethod.PUT)
                        .filters(f -> f.filter((exchange, chain) -> {
                            return processHeadersGrpcRequest(
                                    exchange,
                                    chain,
                                    (headers) -> {
                                        String customerIdStr = headers.getFirst("customerId");
                                        String lastName = headers.getFirst("lastName");
                                        String firstName = headers.getFirst("firstName");
                                        String email = headers.getFirst("email");
                                        String phone = headers.getFirst("phone");
                                        String address = headers.getFirst("address");
                                        String loyaltyId = headers.getFirst("loyaltyId");

                                        if (
                                                customerIdStr == null ||
                                                        lastName == null ||
                                                        firstName == null ||
                                                        email == null ||
                                                        phone == null ||
                                                        address == null ||
                                                        loyaltyId == null
                                        ) {
                                            return processMissingHeaderResponse(exchange);
                                        }

                                        int customerId = Integer.parseInt(customerIdStr);

                                        return grpcMethodInvoker.updateCustomer(
                                                customerId,
                                                lastName,
                                                firstName,
                                                email,
                                                phone,
                                                address,
                                                loyaltyId
                                        );
                                    }
                            );
                        }))
                        .uri("no://op"))

                .build();
    }

}