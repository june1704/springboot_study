package com.korit.springboot_study.controller.advice;

import com.korit.springboot_study.dto.response.common.BadRequestResponseDto;
import com.korit.springboot_study.dto.response.common.NotFoundResponseDto;
import com.korit.springboot_study.exception.CustomDuplicateKeyException;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalRestControllerAdvice {

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<NotFoundResponseDto<?>> notFound(NotFoundException e) {
        return ResponseEntity.status(404).body(new NotFoundResponseDto<>(e.getMessage()));
    }

    @ExceptionHandler(value = AuthenticationException.class)
    public ResponseEntity<BadRequestResponseDto<?>> signinError(AuthenticationException e) {
        return ResponseEntity.status(403).body(new BadRequestResponseDto<>(e.getMessage()));
    }

    @ExceptionHandler(value = CustomDuplicateKeyException.class)
    public ResponseEntity<BadRequestResponseDto<?>> duplicateKey(CustomDuplicateKeyException e) {
        return ResponseEntity.status(400).body(new BadRequestResponseDto<>(e.getErrors()));
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<BadRequestResponseDto<?>> validation(ConstraintViolationException e) {
        return ResponseEntity.status(400).body(new BadRequestResponseDto<>(
                e.getConstraintViolations()
                        .stream()
                        .map(constraintViolation -> Map.of(constraintViolation.getPropertyPath(), constraintViolation.getMessage()))
                        .collect(Collectors.toList())
        ));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<BadRequestResponseDto<?>> validation(MethodArgumentNotValidException e) {
        List<Map<String, String>> errorMap = null;
        BindingResult bindingResult = e.getBindingResult();
        if (bindingResult.hasErrors()) {
            errorMap = bindingResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> Map.of(fieldError.getField(), fieldError.getDefaultMessage()))
                    .collect(Collectors.toList());
        }
        e. printStackTrace();
        return ResponseEntity.status(400).body(new BadRequestResponseDto<>(errorMap));
    }
}



/*
✔ try-catch가 있으면: 예외를 해당 코드에서 직접 처리하는 것이므로 GlobalRestControllerAdvice까지 전달되지 않음.
✔ try-catch가 없으면: 예외가 글로벌 예외 처리기로 전파되어 GlobalRestControllerAdvice에서 일괄 처리.
✔ 따라서 특정 예외가 글로벌 예외 처리기로 미뤄지는지 확인하려면, try-catch를 사용하는지 안 하는지를 보면 됨.

✅ 즉, try-catch가 없으면 예외가 글로벌 예외 처리기로 넘어간다고 보면 됩니다!

*/