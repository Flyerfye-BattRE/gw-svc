package com.battre.gwsvc.config;

import com.battre.gwsvc.service.SpecSvcGrpcInvoker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import java.util.logging.Logger;

import static com.battre.gwsvc.utils.GatewayGrpcUtils.processNoInputGrpcRequest;

/**
 * Implements the client-facing gateway routes for the Spec Service.
 *
 * <p>Note: Because incoming REST request are converted to gRPC requests in the filter, no forwarding
 * address is specified in the uri.</p>
 *
 */
@Configuration
public class SpecSvcGatewayConfig {
    private static final Logger logger = Logger.getLogger(SpecSvcGatewayConfig.class.getName());

    private final SpecSvcGrpcInvoker grpcMethodInvoker;

    @Autowired
    public SpecSvcGatewayConfig(SpecSvcGrpcInvoker grpcMethodInvoker) {
        this.grpcMethodInvoker = grpcMethodInvoker;
    }

    @Bean
    public RouteLocator specSvcRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("specsvc_getAllBatterySpecs", r -> r.path("/spec/getAllBatterySpecs")
                        .and()
                        .method(HttpMethod.GET)
                        .filters(f -> f.filter((exchange, chain) -> {
                            return processNoInputGrpcRequest(exchange, chain, grpcMethodInvoker::getAllBatterySpecs);
                        }))
                        .uri("no://op"))
                .route("specsvc_getBatteryTiers", r -> r.path("/spec/getBatteryTiers")
                        .and()
                        .method(HttpMethod.GET)
                        .filters(f -> f.filter((exchange, chain) -> {
                            return processNoInputGrpcRequest(exchange, chain, grpcMethodInvoker::getBatteryTiers);
                        }))
                        .uri("no://op"))
                .build();
    }
}