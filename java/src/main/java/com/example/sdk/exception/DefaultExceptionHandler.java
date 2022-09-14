package com.example.sdk.exception;

import com.example.sdk.bean.vo.ResponseVo;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Created with IntelliJ IDEA at 2022/5/12 18:00
 * User: @Qi Long
 */
@Controller
@RestControllerAdvice()
@Slf4j
public class DefaultExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseVo<String>> bindExceptionHandler(Exception e){
//        e.printStackTrace();
        log.info("jinlai1");
        log.error(e.getMessage());
        e.printStackTrace();
        return ResponseEntity.ok(ResponseVo.error(e.getMessage()));
    }
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ResponseVo<String>> loginExpired(ExpiredJwtException e){
        log.info("jinlai2");
        log.error(e.getMessage());
        return ResponseEntity.ok(ResponseVo.error(e.getMessage()));
    }

}
