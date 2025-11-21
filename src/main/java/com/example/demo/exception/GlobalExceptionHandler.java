package com.example.demo.exception;

import com.example.demo.dto.ResultDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//@RestControllerAdvice
//public class GlobalExceptionHandler {
//    public GlobalExceptionHandler(){
//
//    }
//
//   /* @ExceptionHandler(RuntimeException.class)
//    public ResponseEntity<ResultDto> handleRuntimeException(HttpServletRequest req, RuntimeException ex) {
//        ResultDto resultDto = new ResultDto();
//        resultDto.setStatus(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
//        resultDto.setMessage(ex.getMessage());
//        return ResponseEntity.status(500).body(resultDto);
//    }*/
//}
