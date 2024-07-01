package com.battre.gwsvc.config;

import com.battre.gwsvc.service.TriageSvcGrpcInvoker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import java.util.logging.Logger;

import static com.battre.gwsvc.utils.GatewayGrpcUtils.processNoInputGrpcRequest;

/**
 * Implements the client-facing gateway routes for the Triage Service.
 *
 * <p>Note: Because incoming REST request are converted to gRPC requests in the filter, no
 * forwarding address is specified in the uri.
 */
@Configuration
public class TriageSvcGatewayConfig {
  private static final Logger logger = Logger.getLogger(TriageSvcGatewayConfig.class.getName());

  private final TriageSvcGrpcInvoker grpcMethodInvoker;

  @Autowired
  public TriageSvcGatewayConfig(TriageSvcGrpcInvoker grpcMethodInvoker) {
    this.grpcMethodInvoker = grpcMethodInvoker;
  }

  @Bean
  public RouteLocator triageSvcRouteLocator(RouteLocatorBuilder builder) {
    return builder
        .routes()
        // generateIntakeBatteryOrder
        .route(
            "triagesvc_generateIntakeBatteryOrder",
            r ->
                r.path("/triage")
                    .and()
                    .method(HttpMethod.GET)
                    .filters(
                        f ->
                            f.filter(
                                (exchange, chain) -> {
                                  return processNoInputGrpcRequest(
                                      exchange,
                                      chain,
                                      grpcMethodInvoker::generateIntakeBatteryOrder);
                                }))
                    .uri("no://op"))
        .build();
  }
}
