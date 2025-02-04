package com.korit.springboot_study.controller;

import com.korit.springboot_study.ioc.ClassA;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IocController {


    @GetMapping("/api/ioc")
    public ResponseEntity<?> call() {

        return ResponseEntity.ok(null);
    }
}
