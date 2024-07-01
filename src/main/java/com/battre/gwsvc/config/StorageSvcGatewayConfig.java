package com.battre.gwsvc.config;

import com.battre.gwsvc.service.StorageSvcGrpcInvoker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import java.util.logging.Logger;

import static com.battre.gwsvc.utils.GatewayGrpcUtils.processNoInputGrpcRequest;

/**
 * Implements the client-facing gateway routes for the Storage Service.
 *
 * <p>Note: Because incoming REST request are converted to gRPC requests in the filter, no
 * forwarding address is specified in the uri.
 */
@Configuration
public class StorageSvcGatewayConfig {
  private static final Logger logger = Logger.getLogger(StorageSvcGatewayConfig.class.getName());

  private final StorageSvcGrpcInvoker grpcMethodInvoker;

  @Autowired
  public StorageSvcGatewayConfig(StorageSvcGrpcInvoker grpcMethodInvoker) {
    this.grpcMethodInvoker = grpcMethodInvoker;
  }

  @Bean
  public RouteLocator storageSvcRouteLocator(RouteLocatorBuilder builder) {
    return builder
        .routes()
        // getStorageStats
        .route(
            "storagesvc_getStorageStats",
            r ->
                r.path("/storage/getStorageStats")
                    .and()
                    .method(HttpMethod.GET)
                    .filters(
                        f ->
                            f.filter(
                                (exchange, chain) -> {
                                  return processNoInputGrpcRequest(
                                      exchange, chain, grpcMethodInvoker::getStorageStats);
                                }))
                    .uri("no://op"))
        .build();
  }
}
