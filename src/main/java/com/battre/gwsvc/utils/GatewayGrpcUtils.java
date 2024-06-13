package com.battre.gwsvc.utils;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.util.JsonFormat;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.logging.Logger;

/**
 * Contains utility functions to help the process of performing gRPC requests and processing responses.
 *
 */
public class GatewayGrpcUtils {
    private static final Logger logger = Logger.getLogger(GatewayGrpcUtils.class.getName());

    public static Mono<Void> processNoInputGrpcRequest(ServerWebExchange exchange,
                                                       GatewayFilterChain chain,
                                                       Supplier<String> grpcMethod) {
        String grpcResponse = grpcMethod.get();
        logger.info("gRPC response: " + grpcResponse);

        if (grpcResponse.startsWith("Success:")) {
            exchange.getResponse().setStatusCode(HttpStatus.OK);
        } else if (grpcResponse.startsWith("Failure:")) {
            exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            exchange.getResponse().setStatusCode(HttpStatus.SERVICE_UNAVAILABLE);
        }

        // Strip the grpc status prefix from the response
        grpcResponse = grpcResponse.replaceFirst("^(Failure: |Success: )", "");

        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(grpcResponse.getBytes(StandardCharsets.UTF_8));

        // Write the response and proceed with the filter chain
        return exchange.getResponse().writeWith(Mono.just(buffer))
                .then(chain.filter(exchange));
    }

    public static Mono<Void> processHeadersGrpcRequest(ServerWebExchange exchange,
                                                       GatewayFilterChain chain,
                                                       Function<HttpHeaders, String> grpcMethod) {
        return exchange.getRequest().getBody().next()
                .defaultIfEmpty(exchange.getResponse().bufferFactory().wrap(new byte[0])) // Handle empty body
                .flatMap(dataBuffer -> {
                    String grpcResponse = grpcMethod.apply(exchange.getRequest().getHeaders());
                    logger.info("gRPC response: " + grpcResponse);

                    if (grpcResponse.startsWith("Failure")) {
                        exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                    } else {
                        exchange.getResponse().setStatusCode(HttpStatus.OK);
                    }

                    exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
                    DataBuffer buffer = exchange
                            .getResponse()
                            .bufferFactory()
                            .wrap(grpcResponse.getBytes(StandardCharsets.UTF_8));
                    return exchange.getResponse().writeWith(Mono.just(buffer));
                }).then(chain.filter(exchange));
    }

    // If a header is missing, set response status to 400 Bad Request
    public static String processMissingHeaderResponse(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
        DataBuffer buffer = exchange
                .getResponse()
                .bufferFactory()
                .wrap("Missing required input parameter(s) in header.".getBytes(StandardCharsets.UTF_8));
        exchange.getResponse().writeWith(Mono.just(buffer));

        return "Failure: Missing required input parameter(s) in header";
    }

    public static <T extends Message> String processSimpleGrpcResponse(T response, String failureMessage) {
        try {
            // if the response class contains a success boolean, use it to evaluate the success of the response
            Method getSuccessMethod = response.getClass().getMethod("getSuccess");
            if (getSuccessMethod != null) {
                if (response != null && (boolean) getSuccessMethod.invoke(response, new Object[0])) {
                    return "Success: Ok";
                } else {
                    return "Failure: " + failureMessage;
                }
            } else {
                return "Failure: " + failureMessage;
            }
        } catch (NoSuchMethodException e) {
            logger.severe("Exception [NoSuchMethodException]: " + e.getMessage());
            return "Exception [NoSuchMethodException]: " + failureMessage;
        } catch (InvocationTargetException e) {
            logger.severe("Exception [InvocationTargetException]: " + e.getMessage());
            return "Exception [InvocationTargetException]: " + failureMessage;
        } catch (IllegalAccessException e) {
            logger.severe("Exception [InvocationTargetException]: " + e.getMessage());
            return "Exception [IllegalAccessException]: " + failureMessage;
        }
    }

    public static <T extends Message> String processComplexGrpcResponse(T response, String failureMessage) {
        if (response != null) {
            try {
                String jsonResponse = JsonFormat.printer().print(response);
                return "Success: " + jsonResponse;
            } catch (InvalidProtocolBufferException e) {
                logger.severe("Error converting response to JSON: " + e.getMessage());
                return "Failure: Error processing response.";
            }
        } else {
            return "Failure: " + failureMessage;
        }
    }
}
