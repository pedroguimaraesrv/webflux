package com.apirest.webflux.exception;

import java.util.Map;
import java.util.Optional;

import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Component
@Order(-2)
public class GlobalExceptionHandler extends AbstractErrorWebExceptionHandler {


	public GlobalExceptionHandler(ErrorAttributes errorAttributes, ResourceProperties resourceProperties,
			ApplicationContext applicationContext, ServerCodecConfigurer codecConfigurer) {
		super(errorAttributes, resourceProperties, applicationContext);
		this.setMessageWriters(codecConfigurer.getWriters());
	}

	@Override
	protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
		return RouterFunctions.route(RequestPredicates.all(), this::formatErrorResponse );
	}
	
	private Mono<ServerResponse> formatErrorResponse(ServerRequest request){
		Map<String, Object> errorAttributesMap = getErrorAttributes(request, ErrorAttributeOptions.defaults());
		int status = (int) Optional.ofNullable(errorAttributesMap.get("status")).orElse(500);
		return ServerResponse.status(status)
				.contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromValue(errorAttributesMap));
	}
}
//import java.util.Map;
//import java.util.Optional;
//
//import org.springframework.boot.autoconfigure.web.ResourceProperties;
//import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
//import org.springframework.boot.web.error.ErrorAttributeOptions;
//import org.springframework.boot.web.reactive.error.ErrorAttributes;
//import org.springframework.context.ApplicationContext;
//import org.springframework.core.annotation.Order;
//import org.springframework.http.MediaType;
//import org.springframework.http.codec.ServerCodecConfigurer;
//import org.springframework.stereotype.Component;
//import org.springframework.web.reactive.function.BodyInserters;
//import org.springframework.web.reactive.function.server.HandlerFunction;
//import org.springframework.web.reactive.function.server.RequestPredicates;
//import org.springframework.web.reactive.function.server.RouterFunction;
//import org.springframework.web.reactive.function.server.RouterFunctions;
//import org.springframework.web.reactive.function.server.ServerRequest;
//import org.springframework.web.reactive.function.server.ServerResponse;
//
//import reactor.core.publisher.Mono;
//
//@Component
//@Order(-2)
//public class GlobalExcpetionHandler extends AbstractErrorWebExceptionHandler{
//
//
//	public GlobalExcpetionHandler(ErrorAttributes errorAttributes, ResourceProperties resourceProperties,
//			ApplicationContext applicationContext,
//			ServerCodecConfigurer codecConfigurer) {
//		super(errorAttributes, resourceProperties, applicationContext);
//		this.setMessageWriters(codecConfigurer.getWriters());
//	}
//
//	@Override
//	protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
//		return RouterFunctions.route(RequestPredicates.all(), this::formatErrorResponse);
//	}
//	
//	private Mono<ServerResponse> formatErrorResponse(ServerResponse request){
//		Map<String, Object> errorAttributesMap = getErrorAttributes(request, ErrorAttributeOptions.defaults());
//		int status = (int)Optional.ofNullable(errorAttributesMap.get("status")).orElse(500);
//		
//		return ServerResponse.status(status)
//				.contentType(MediaType.APPLICATION_JSON)
//				.body(BodyInserters.fromValue(errorAttributesMap));
//	}
//}
