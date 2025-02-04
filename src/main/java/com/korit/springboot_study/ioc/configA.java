package com.korit.springboot_study.ioc;

import org.springframework.asm.ByteVector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class configA {

    @Bean(value = "aaa") // 을 등록하면 메소드 명이 컴포넌트 명이 됨, Bean어노테이션 뒤에도 컴포넌트 명을 줄 수 있다.
    public ClassD call() {
        System.out.println("ConfigA call");
        return new ClassD();
    }

    @Bean
    public ByteVector byteVector() {
        return new ByteVector();
    }
}
