package com.battre.gwsvc.config;

import com.battre.gwsvc.service.LabSvcGrpcInvoker;
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
 * Implements the client-facing gateway routes for the Lab Service.
 *
 * <p>Note: Because incoming REST request are converted to gRPC requests in the filter, no forwarding
 * address is specified in the uri.</p>
 *
 */
@Configuration
public class LabSvcGatewayConfig {
    private static final Logger logger = Logger.getLogger(LabSvcGatewayConfig.class.getName());

    private final LabSvcGrpcInvoker grpcMethodInvoker;

    @Autowired
    public LabSvcGatewayConfig(LabSvcGrpcInvoker grpcMethodInvoker) {
        this.grpcMethodInvoker = grpcMethodInvoker;
    }

    @Bean
    public RouteLocator labSvcRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // getLabPlans
                .route("labsvc_getLabPlans", r -> r.path("/lab/getLabPlans")
                        .and()
                        .method(HttpMethod.GET)
                        .filters(f -> f.filter((exchange, chain) -> {
                            return processNoInputGrpcRequest(exchange, chain, grpcMethodInvoker::getLabPlans);
                        }))
                        .uri("no://op"))

                // getCurrentLabPlans
                .route("labsvc_getCurrentLabPlans", r -> r.path("/lab/getCurrentLabPlans")
                        .and()
                        .method(HttpMethod.GET)
                        .filters(f -> f.filter((exchange, chain) -> {
                            return processNoInputGrpcRequest(exchange, chain, grpcMethodInvoker::getCurrentLabPlans);
                        }))
                        .uri("no://op"))

                // getCurrentTesterBacklog
                .route("labsvc_getCurrentTesterBacklog", r -> r.path("/lab/getCurrentTesterBacklog")
                        .and()
                        .method(HttpMethod.GET)
                        .filters(f -> f.filter((exchange, chain) -> {
                            return processNoInputGrpcRequest(exchange, chain, grpcMethodInvoker::getCurrentTesterBacklog);
                        }))
                        .uri("no://op"))

                // getTesterBacklog
                .route("labsvc_getTesterBacklog", r -> r.path("/lab/getTesterBacklog")
                        .and()
                        .method(HttpMethod.GET)
                        .filters(f -> f.filter((exchange, chain) -> {
                            return processNoInputGrpcRequest(exchange, chain, grpcMethodInvoker::getTesterBacklog);
                        }))
                        .uri("no://op"))

                // getCurrentRefurbPlans
                .route("labsvc_getCurrentRefurbPlans", r -> r.path("/lab/getCurrentRefurbPlans")
                        .and()
                        .method(HttpMethod.GET)
                        .filters(f -> f.filter((exchange, chain) -> {
                            return processNoInputGrpcRequest(exchange, chain, grpcMethodInvoker::getCurrentRefurbPlans);
                        }))
                        .uri("no://op"))

                // getRefurbPlans
                .route("labsvc_getRefurbPlans", r -> r.path("/lab/getRefurbPlans")
                        .and()
                        .method(HttpMethod.GET)
                        .filters(f -> f.filter((exchange, chain) -> {
                            return processNoInputGrpcRequest(exchange, chain, grpcMethodInvoker::getRefurbPlans);
                        }))
                        .uri("no://op"))

                // changeBatteryTesterPriority
                .route("labsvc_changeBatteryTesterPriority", r -> r.path("/lab/changeBatteryTesterPriority")
                        .and()
                        .method(HttpMethod.POST)
                        .filters(f -> f.filter((exchange, chain) -> {
                            return processHeadersGrpcRequest(
                                    exchange,
                                    chain,
                                    (headers) -> {
                                        String batteryIdStr = headers.getFirst("batteryId");
                                        String priorityStr = headers.getFirst("priority");

                                        if (batteryIdStr == null || priorityStr == null) {
                                            return processMissingHeaderResponse(exchange);
                                        }

                                        int batteryId = Integer.parseInt(batteryIdStr);
                                        int priority = Integer.parseInt(priorityStr);

                                        return grpcMethodInvoker.changeBatteryTesterPriority(batteryId, priority);
                                    }
                            );
                        }))
                        .uri("no://op"))

                // changeBatteryRefurbPriority
                .route("labsvc_changeBatteryRefurbPriority", r -> r.path("/lab/changeBatteryRefurbPriority")
                        .and()
                        .method(HttpMethod.POST)
                        .filters(f -> f.filter((exchange, chain) -> {
                            return processHeadersGrpcRequest(
                                    exchange,
                                    chain,
                                    (headers) -> {
                                        String batteryIdStr = headers.getFirst("batteryId");
                                        String priorityStr = headers.getFirst("priority");

                                        if (batteryIdStr == null || priorityStr == null) {
                                            return processMissingHeaderResponse(exchange);
                                        }

                                        int batteryId = Integer.parseInt(batteryIdStr);
                                        int priority = Integer.parseInt(priorityStr);

                                        return grpcMethodInvoker.changeBatteryRefurbPriority(batteryId, priority);
                                    }
                            );
                        }))
                        .uri("no://op"))

                // getTesterMaintenanceLogs
                .route("labsvc_getTesterMaintenanceLogs", r -> r.path("/lab/getTesterMaintenanceLogs")
                        .and()
                        .method(HttpMethod.GET)
                        .filters(f -> f.filter((exchange, chain) -> {
                            return processNoInputGrpcRequest(exchange, chain, grpcMethodInvoker::getTesterMaintenanceLogs);
                        }))
                        .uri("no://op"))

                // getRefurbMaintenanceLogs
                .route("labsvc_getRefurbMaintenanceLogs", r -> r.path("/lab/getRefurbMaintenanceLogs")
                        .and()
                        .method(HttpMethod.GET)
                        .filters(f -> f.filter((exchange, chain) -> {
                            return processNoInputGrpcRequest(exchange, chain, grpcMethodInvoker::getRefurbMaintenanceLogs);
                        }))
                        .uri("no://op"))

                .build();
    }
}