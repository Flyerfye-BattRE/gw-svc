package com.battre.gwsvc.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.protobuf.Message;
import java.util.function.Function;
import java.util.function.Supplier;
import org.junit.jupiter.api.Test;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class GatewayGrpcUtilsTest {
    @Test
    void testProcessNoInputGrpcRequest_Success() {
        ServerWebExchange exchange = mock(ServerWebExchange.class);
        GatewayFilterChain chain = mock(GatewayFilterChain.class);
        ServerHttpResponse response = mock(ServerHttpResponse.class);
        HttpHeaders httpHeaders = new HttpHeaders();
        Supplier<String> grpcMethod = () -> "Success: OK";

        when(exchange.getResponse()).thenReturn(response);
        when(response.getHeaders()).thenReturn(httpHeaders);
        when(response.getHeaders()).thenReturn(mock(HttpHeaders.class));
        when(response.bufferFactory()).thenReturn(new DefaultDataBufferFactory());
        when(chain.filter(exchange)).thenReturn(Mono.empty());
        when(response.writeWith(any())).thenReturn(Mono.empty());

        Mono<Void> result = GatewayGrpcUtils.processNoInputGrpcRequest(exchange, chain, grpcMethod);

        StepVerifier.create(result)
                .expectComplete()
                .verify();

        verify(response).setStatusCode(HttpStatus.OK);
        verify(response.getHeaders()).setContentType(MediaType.APPLICATION_JSON);
        verify(response).writeWith(any());
    }

    @Test
    void testProcessNoInputGrpcRequest_Failure() {
        ServerWebExchange exchange = mock(ServerWebExchange.class);
        GatewayFilterChain chain = mock(GatewayFilterChain.class);
        ServerHttpResponse response = mock(ServerHttpResponse.class);
        HttpHeaders httpHeaders = new HttpHeaders();
        Supplier<String> grpcMethod = () -> "Failure: Error";

        when(exchange.getResponse()).thenReturn(response);
        when(response.getHeaders()).thenReturn(httpHeaders);
        when(response.bufferFactory()).thenReturn(new DefaultDataBufferFactory());
        when(chain.filter(exchange)).thenReturn(Mono.empty());
        when(response.writeWith(any())).thenReturn(Mono.empty());

        Mono<Void> result = GatewayGrpcUtils.processNoInputGrpcRequest(exchange, chain, grpcMethod);

        StepVerifier.create(result)
                .expectComplete()
                .verify();

        verify(response).setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        verify(response).getHeaders();

        assertEquals(MediaType.APPLICATION_JSON, httpHeaders.getContentType());
        verify(response).writeWith(any());
    }

    @Test
    void testProcessHeadersGrpcRequest_Success() {
        ServerWebExchange exchange = mock(ServerWebExchange.class);
        GatewayFilterChain chain = mock(GatewayFilterChain.class);
        ServerHttpResponse response = mock(ServerHttpResponse.class);
        HttpHeaders headers = new HttpHeaders();
        Function<HttpHeaders, String> grpcMethod = (h) -> "Success: OK";

        when(exchange.getRequest()).thenReturn(mock(ServerHttpRequest.class));
        when(exchange.getRequest().getHeaders()).thenReturn(headers);
        when(exchange.getRequest().getBody()).thenReturn(Flux.empty());
        when(exchange.getResponse()).thenReturn(response);
        when(response.getHeaders()).thenReturn(mock(HttpHeaders.class));
        when(response.bufferFactory()).thenReturn(new DefaultDataBufferFactory());
        when(chain.filter(exchange)).thenReturn(Mono.empty());
        when(response.writeWith(any())).thenReturn(Mono.empty());

        Mono<Void> result = GatewayGrpcUtils.processHeadersGrpcRequest(exchange, chain, grpcMethod);

        StepVerifier.create(result)
                .expectComplete()
                .verify();

        verify(response).setStatusCode(HttpStatus.OK);
        verify(response.getHeaders()).setContentType(MediaType.APPLICATION_JSON);
        verify(response).writeWith(any());
    }

    @Test
    void testProcessHeadersGrpcRequest_Failure() {
        ServerWebExchange exchange = mock(ServerWebExchange.class);
        GatewayFilterChain chain = mock(GatewayFilterChain.class);
        ServerHttpResponse response = mock(ServerHttpResponse.class);
        HttpHeaders headers = new HttpHeaders();
        Function<HttpHeaders, String> grpcMethod = (h) -> "Failure: Error";

        when(exchange.getRequest()).thenReturn(mock(ServerHttpRequest.class));
        when(exchange.getRequest().getHeaders()).thenReturn(headers);
        when(exchange.getRequest().getBody()).thenReturn(Flux.empty());
        when(exchange.getResponse()).thenReturn(response);
        when(response.getHeaders()).thenReturn(mock(HttpHeaders.class));
        when(response.bufferFactory()).thenReturn(new DefaultDataBufferFactory());
        when(chain.filter(exchange)).thenReturn(Mono.empty());
        when(response.writeWith(any())).thenReturn(Mono.empty());

        Mono<Void> result = GatewayGrpcUtils.processHeadersGrpcRequest(exchange, chain, grpcMethod);

        StepVerifier.create(result)
                .expectComplete()
                .verify();

        verify(response).setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        verify(response.getHeaders()).setContentType(MediaType.APPLICATION_JSON);
        verify(response).writeWith(any());
    }
    @Test
    void testProcessMissingHeaderResponse() {
        ServerWebExchange exchange = mock(ServerWebExchange.class);
        ServerHttpResponse response = mock(ServerHttpResponse.class);

        when(exchange.getResponse()).thenReturn(response);
        when(response.bufferFactory()).thenReturn(new DefaultDataBufferFactory());

        String result = GatewayGrpcUtils.processMissingHeaderResponse(exchange);

        assertEquals("Failure: Missing required input parameter(s) in header", result);
        verify(response).setStatusCode(HttpStatus.BAD_REQUEST);
        verify(response).writeWith(any());
    }

    @Test
    void testProcessSimpleGrpcResponse_Success() throws Exception {
        MockGrpcResponse grpcResponse = mock(MockGrpcResponse.class);
        when(grpcResponse.getSuccess()).thenReturn(true);

        String result = GatewayGrpcUtils.processSimpleGrpcResponse(grpcResponse, "Error");

        assertEquals("Success: Ok", result);
    }

    @Test
    void testProcessSimpleGrpcResponse_Failure() throws Exception {
        MockGrpcResponse grpcResponse = mock(MockGrpcResponse.class);
        when(grpcResponse.getSuccess()).thenReturn(false);

        String result = GatewayGrpcUtils.processSimpleGrpcResponse(grpcResponse, "Error");

        assertEquals("Failure: Error", result);
    }

    @Test
    void testProcessSimpleGrpcResponse_Exception() {
        Message response = mock(Message.class);

        String result = GatewayGrpcUtils.processSimpleGrpcResponse(response, "Error occurred");

        assertTrue(result.contains("Exception"));
    }

    //TODO: Implement testProcessComplexGrpcResponse_Success()

    @Test
    void testProcessComplexGrpcResponse_Failure() {
        Message response = null;

        String result = GatewayGrpcUtils.processComplexGrpcResponse(response, "Error occurred");

        assertEquals("Failure: Error occurred", result);
    }
}
