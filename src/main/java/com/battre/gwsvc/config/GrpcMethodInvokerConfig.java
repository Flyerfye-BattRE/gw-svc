package com.battre.gwsvc.config;

import com.battre.grpcifc.DiscoveryClientAdapter;
import com.battre.grpcifc.GrpcMethodInvoker;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configures the grpc method invoker to be able to use Eureka service discovery.
 *
 */
@Configuration
public class GrpcMethodInvokerConfig {
    @Bean
    public DiscoveryClientAdapter discoveryClientAdapter(DiscoveryClient discoveryClient) {
        return serviceName -> discoveryClient.getInstances(serviceName)
                .stream()
                .findFirst()
                .map(instance -> instance.getUri().toString())
                .orElseThrow(() -> new RuntimeException("Service instance not found in Eureka: " + serviceName));
    }

    @Bean
    public GrpcMethodInvoker grpcMethodInvoker(DiscoveryClientAdapter discoveryClientAdapter) {
        return new GrpcMethodInvoker(discoveryClientAdapter);
    }
}
