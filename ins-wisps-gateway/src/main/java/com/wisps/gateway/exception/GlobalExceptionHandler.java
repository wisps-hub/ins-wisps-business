package com.wisps.gateway.exception;

import cn.hutool.json.JSONUtil;
import com.wisps.exception.BizErrorCode;
import com.wisps.exception.BizException;
import com.wisps.exception.SystemException;
import com.wisps.resp.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Slf4j
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();
        if (response.isCommitted()) {
            log.info("[GlobalExceptionHandler] response is already committed, ex={}", ex.getMessage());
            return Mono.empty();
        }
        if (ex instanceof BizException) {
            BizException e = (BizException) ex;
            log.info("[GlobalExceptionHandler#BizException] user request error. [code: {}, message: {}], uri={}",
                    e.getErrorCode().getCode(), e.getMessage(), exchange.getRequest().getURI());
            return writeResult(response, HttpStatus.OK, Result.error(e.getErrorCode().getCode(), e.getMessage()));
        }
        if (ex instanceof SystemException) {
            SystemException e = (SystemException) ex;
            log.info("[GlobalExceptionHandler#SystemException] user request error. [code: {}, message: {}], uri={}",
                    e.getErrorCode().getCode(), e.getMessage(), exchange.getRequest().getURI());
            return writeResult(response, HttpStatus.OK, Result.error(e.getErrorCode().getCode(), e.getMessage()));
        }
        return writeResult(response, HttpStatus.OK,
                Result.error(BizErrorCode.HTTP_SERVER_ERROR.getCode(), BizErrorCode.HTTP_SERVER_ERROR.getMsg()));
    }

    public static Mono<Void> writeResult(ServerHttpResponse response, HttpStatus httpStatus, Result result) {
        if (response.isCommitted()) {
            log.info("[HttpServerHandler] response has committed, can not write result: {}", result);
            return Mono.empty();
        }
        response.setStatusCode(httpStatus);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        byte[] reBytes = JSONUtil.toJsonStr(result).getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(reBytes);
        return response.writeWith(Mono.just(buffer));
    }
}
